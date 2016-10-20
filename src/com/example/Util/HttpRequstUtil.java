package com.example.Util;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
/**
 * �������ݽ���
 * */
public class HttpRequstUtil {
	private static HttpRequstUtil list=null;
	private AsyncHttpClient ac;
	private Gson gson;
	private HttpRequstUtil(){
		ac = new AsyncHttpClient();
		gson = new Gson();
	}
	public static HttpRequstUtil getHttpRequst(){
		if(list==null){
			return new HttpRequstUtil();
		}
		return list;
	}
	/**
	 * get ������������ ����
	 * */
	public void getRequestData(String url,RequestParams params,final int requestCode,final Handler mhandler,final Class c){
		ac.get(Config.ip+url, params, new AsyncHttpResponseHandler() {
			
			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				String s=new String(arg2);
				try {
					JSONObject json=new JSONObject(s);
					int status = json.getInt("status");
					if(status==0){
						JSONObject data = json.getJSONObject("data");
						Object object = gson.fromJson(data.toString(), c);
						Message message = mhandler.obtainMessage();
						message.obj=object;
						message.what=requestCode;
						mhandler.sendMessage(message);
					}else{
						mhandler.sendEmptyMessage(status);
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
	}
	/**
	 * get ����������� ����
	 * @param <T>
	 * */
	public <T> void getRequestDataArray(String url,RequestParams params,final int requestCode,final Handler mhandler,final TypeToken<T> token){
		ac.get(Config.ip+url, params, new AsyncHttpResponseHandler() {
			
			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				String s=new String(arg2);
				try {
					JSONObject json=new JSONObject(s);
					int status = json.getInt("status");
					if(status==0){
						JSONArray data = json.getJSONArray("data");
						Object object = gson.fromJson(data.toString(), token.getType());
						Message message = mhandler.obtainMessage();
						message.obj=object;
						message.what=requestCode;
						mhandler.sendMessage(message);
					}else{
						mhandler.sendEmptyMessage(status);
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
	}
	/**
	 * post ������������ ����
	 * */
	public void postRequestData(String url,RequestParams params,final int requestCode,final Handler mhandler,final Class c){
		ac.get(Config.ip+url, params, new AsyncHttpResponseHandler() {
			
			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				String s=new String(arg2);
				try {
					JSONObject json=new JSONObject(s);
					int status = json.getInt("status");
					if(status==0){
						JSONObject data = json.getJSONObject("data");
						Object object = gson.fromJson(data.toString(), c);
						Message message = mhandler.obtainMessage();
						message.obj=object;
						message.what=requestCode;
						mhandler.sendMessage(message);
					}else{
						mhandler.sendEmptyMessage(status);
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
	}
	/**
	 * post ����������� ����
	 * @param <T>
	 * */
	public <T> void postRequestDataArray(String url,RequestParams params,final int requestCode,final Handler mhandler,final TypeToken<T> token){
		ac.get(Config.ip+url, params, new AsyncHttpResponseHandler() {
			
			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				String s=new String(arg2);
				try {
					JSONObject json=new JSONObject(s);
					int status = json.getInt("status");
					if(status==0){
						JSONArray data = json.getJSONArray("data");
						Object object = gson.fromJson(data.toString(), token.getType());
						Message message = mhandler.obtainMessage();
						message.obj=object;
						message.what=requestCode;
						mhandler.sendMessage(message);
					}else{
						mhandler.sendEmptyMessage(status);
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
	}

}
