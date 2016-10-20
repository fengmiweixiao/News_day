package com.example.Util;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.model.User;
import com.example.news_day.MainActivity;
import com.example.news_day.MyApp;
import com.example.news_day.StartActivity;
import com.example.service.EventMessage;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import de.greenrobot.event.EventBus;

import android.app.Activity;
import android.content.Intent;

public class LogService {
	private static String username;
	private static String userpwd;

	public static void Log(final Activity activity){
		MySharedPreferences sp=new MySharedPreferences(activity);
		
		 username = sp.getusername();
		 userpwd = sp.getuserpwd();
		boolean inapp = sp.inapp();
		
		if(username.length()>0&&userpwd.length()>0&&inapp==true){
			AsyncHttpClient ac=new AsyncHttpClient();
			RequestParams params=new RequestParams();
			params.put("ver", "1");
			params.put("device", "0");
			params.put("uid", username);
			params.put("pwd", userpwd);
			ac.get(Config.ip+Config.user_login, params, new AsyncHttpResponseHandler() {
				
				@Override
				public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
					
					// TODO Auto-generated method stub
					String str=new String(arg2);
					try {
						JSONObject jsonobject=new JSONObject(str);
						int status = jsonobject.getInt("status");
						if(status==0){
							JSONObject data = jsonobject.getJSONObject("data");
							String token = data.getString("token");
							User u=new User();
							u.setToken(token);
							u.setUsername(username);
							u.setUserpwd(userpwd);
							
							
							MyApp app=(MyApp)activity.getApplication();
							app.user=u;
							
							EventMessage event=new EventMessage();
							event.obj="µÇÂ¼³É¹¦";
							event.what=0;
							EventBus.getDefault().post(event);
						}else if(status==-1){
							EventMessage event=new EventMessage();
							event.obj="µÇÂ¼Ê§°Ü";
							event.what=1;
							EventBus.getDefault().post(event);
						}
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				@Override
				public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
					// TODO Auto-generated method stub
					
				}
			});
		}else{
			Intent intent=new Intent(activity,MainActivity.class);
			activity.startActivity(intent);
		}
	}

}
