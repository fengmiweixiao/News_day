package com.example.news_day;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.Util.Config;
import com.example.Util.HttpRequstUtil;
import com.example.Util.MyDialog;
import com.example.Util.MySharedPreferences;
import com.example.adapter.UserInfoA;
import com.example.dskbase.UserLiteDb;
import com.example.model.Student;
import com.example.model.User;
import com.example.model.UserInfoid;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class LogActivity extends Activity implements OnClickListener,OnItemClickListener {
	Handler mhandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				MyDialog.dismiss();
				Toast.makeText(LogActivity.this, "登录成功", 1).show();
				Intent intent=new Intent(LogActivity.this,UserInfoActivity.class);
				startActivity(intent);
				break;
			case 1:
				MyDialog.dismiss();
				Toast.makeText(LogActivity.this, "账号或密码错误", 1).show();	
					break;
			case 2:
				MyDialog.dismiss();
				Toast.makeText(LogActivity.this, "注册成功", 1).show();
				LogActivity.this.finish();
				break;
			case 3:
				MyDialog.dismiss();
				String str=(String) msg.obj;
				Toast.makeText(LogActivity.this, str, 1).show();
				break;
			case 4:
				MyDialog.dismiss();
				Toast.makeText(LogActivity.this, "发送成功", 1).show();
				LogActivity.this.finish();
				break;
			case 5:
				MyDialog.dismiss();
				String st=(String) msg.obj;
				Toast.makeText(LogActivity.this, st, 1).show();
				break;
			default:
				break;
			}
		};
	};
	private EditText ed_name;
	private EditText ed_pwd;
	private String username;
	private String userpwd;
	
	//状态 0登录 1注册 2忘记密码
	int state=0;
	private TextView text_head;
	private View ll_register;
	private View ll_log;
	private EditText regist_username;
	private EditText regist_pwd;
	private EditText ed_yx;
	private CheckBox cb;
	private String registyx;
	private String registname;
	private String registpwd;
	private View ll_back_pwd;
	private EditText ed_back_yx;
	private String back_yx;
	private PopupWindow popu;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		((MyApp)getApplication()).addActivity(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.log_activity);
		userlitedb = new UserLiteDb(this);
		initHead();
		initView();
	}
	private void initView() {
		regist_username = (EditText) findViewById(R.id.regist_username);
		regist_pwd = (EditText) findViewById(R.id.regist_pwd);
		ed_yx = (EditText) findViewById(R.id.ed_yx);
		cb = (CheckBox) findViewById(R.id.cb);
		ll_log = findViewById(R.id.ll_log);
		ll_back_pwd = findViewById(R.id.ll_back_pwd);
		ll_register = findViewById(R.id.ll_register);
		ed_name = (EditText) findViewById(R.id.ed_name);
		ed_pwd = (EditText) findViewById(R.id.ed_pwd);
		ed_back_yx = (EditText) findViewById(R.id.ed_back_yx);
		findViewById(R.id.butt_log).setOnClickListener(this);
		findViewById(R.id.butt_zc).setOnClickListener(this);
		findViewById(R.id.butt_regist).setOnClickListener(this);
		findViewById(R.id.butt_back).setOnClickListener(this);
		findViewById(R.id.bt_back_pwd).setOnClickListener(this);
		findViewById(R.id.img_spinner_down).setOnClickListener(this);
		MySharedPreferences mysp=new MySharedPreferences(this);
		String getusername = mysp.getusername();
		String getuserpwd = mysp.getuserpwd();
		ed_name.setText(getusername);
		ed_pwd.setText(getuserpwd);
		
	}
	private void initHead() {
		// TODO Auto-generated method stub
		ImageView head_left=(ImageView) findViewById(R.id.head_left);
		ImageView head_right=(ImageView) findViewById(R.id.head_right);
		head_left.setOnClickListener(this);
		head_left.setVisibility(View.VISIBLE);
		head_right.setVisibility(View.GONE);
		text_head = (TextView) findViewById(R.id.text_head);
		text_head.setText("登录");
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.head_left){
			if(state==0){
				this.finish();
			}else if(state==1||state==2){
				state=0;
				ll_log.setVisibility(View.VISIBLE);
				ll_register.setVisibility(View.GONE);
				ll_back_pwd.setVisibility(View.GONE);
				text_head.setText("登录");
			}
			
		}else if(v.getId()==R.id.butt_log){
			log();
		}else if(v.getId()==R.id.butt_zc){
			state=1;
			ll_log.setVisibility(View.GONE);
			ll_back_pwd.setVisibility(View.GONE);
			ll_register.setVisibility(View.VISIBLE);
			text_head.setText("注册");
		}else if(v.getId()==R.id.butt_regist){
			regist();
		}else if(v.getId()==R.id.bt_back_pwd){
			state=2;
			ll_log.setVisibility(View.GONE);
			ll_register.setVisibility(View.GONE);
			ll_back_pwd.setVisibility(View.VISIBLE);
			text_head.setText("找回密码");
		}else if(v.getId()==R.id.butt_back){
			backpwd();
		}else if(v.getId()==R.id.img_spinner_down){
			ShowUserInfo();
		}
	}
	
	private UserInfoA adapter;
	private UserLiteDb userlitedb;
	private void ShowUserInfo() {
		// TODO Auto-generated method stub
		ListView lv=new ListView(this);
		lv.setOnItemClickListener(this);
		lv.setVerticalScrollBarEnabled(false);
		lv.setBackgroundResource(R.drawable.icon_spinner_listview_background);
		List<UserInfoid> list = userlitedb.select(UserInfoid.class);
		
		adapter = new UserInfoA(list, getLayoutInflater());
		lv.setAdapter(adapter);
		popu = new PopupWindow(lv, ed_name.getWidth()-5, 300);
		//外部点击或者触摸关闭
		popu.setOutsideTouchable(true);
		//设置空背景
		popu.setBackgroundDrawable(new BitmapDrawable());
		//设置获得焦点
		popu.setFocusable(true);
		popu.showAsDropDown(ed_name, 5, -5);
	}
	private void backpwd() {
		back_yx = ed_back_yx.getText().toString();
		if(back_yx.length()<=0){
			Toast.makeText(this, "邮箱不能为空", 1).show();
			return;
		}
		//ver=版本号&email=邮箱
		MyDialog.showdialog(this);
		RequestParams params=new RequestParams();
		params.put("ver", "1");
		params.put("email", back_yx);
		AsyncHttpClient ac=new AsyncHttpClient();
		HttpRequstUtil.getHttpRequst().getRequestData(Config.user_forgetpass, params, 4, mhandler, null);
		/**ac.get(Config.ip+Config.user_forgetpass, params, new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				String str=new String(arg2);
				try {
					JSONObject jsonobject=new JSONObject(str);
					int status = jsonobject.getInt("status");
					if(status==0){
						JSONObject data = jsonobject.getJSONObject("data");
						int result = data.getInt("result");
						if(result==0){
							mhandler.sendEmptyMessage(4);
						}else if(result==-1){
							Message message = mhandler.obtainMessage();
							message.obj="发送失败（该邮箱未注册）";
							message.what=5;
							mhandler.sendMessage(message);
						}else if(result==-2){
							Message message = mhandler.obtainMessage();
							message.obj="发送失败（邮箱不存在或被封号）";
							message.what=5;
							mhandler.sendMessage(message);
						}
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
		});*/
		
	}
	private void regist() {
		// TODO Auto-generated method stub
		boolean checked = cb.isChecked();
		if(!checked){
			Toast.makeText(this, "没有同意服务条款", 1).show();
			return;
		}
		registyx = ed_yx.getText().toString();
		registname = regist_username.getText().toString();
		registpwd = regist_pwd.getText().toString();
		if(registname.length()<=0||registpwd.length()<=0){
			Toast.makeText(LogActivity.this, "账号或密码不能为空", 1).show();
			return;
		}
		if(registyx.length()<=0){
			Toast.makeText(LogActivity.this, "邮箱不能为空", 1).show();
			return;
		}
		MyDialog.showdialog(this);
		RequestParams params=new RequestParams();
		//ver=版本号&uid=用户名&email=邮箱&pwd=登陆密码
		params.put("ver", "1");
		params.put("email", registyx);
		params.put("uid", registname);
		
		params.put("pwd", registpwd);
		AsyncHttpClient ac=new AsyncHttpClient();
//		HttpRequstUtil.getHttpRequst().getRequestData(Config.user_register, params, 2, mhandler, null);
		ac.get(Config.ip+Config.user_register, params, new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				String str=new String(arg2);
				try {
					JSONObject jsonobject=new JSONObject(str);
					int status = jsonobject.getInt("status");
					if(status==0){
						JSONObject data = jsonobject.getJSONObject("data");
//						String token = data.getString("token");
//						User u=new User();
//						u.setToken(token);
//						u.setUsername(registname);
//						u.setUserpwd(registpwd);
//						MyApp app=(MyApp)LogActivity.this.getApplication();
//						app.user=u;
						int result = data.getInt("result");
						if(result==0){
						mhandler.sendEmptyMessage(2);
					}else if(result==-1){
						Message message = mhandler.obtainMessage();
						message.obj="服务器不允许注册(用户数量已满)";
						message.what=3;
						mhandler.sendMessage(message);
					}else if(result==-2){
						Message message = mhandler.obtainMessage();
						message.obj="用户名重复";
						message.what=3;
						mhandler.sendMessage(message);
					}else if(result==-3){
						Message message = mhandler.obtainMessage();
						message.obj="邮箱重复";
						message.what=3;
						mhandler.sendMessage(message);
					}
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
	private void log() {
		
		username = ed_name.getText().toString();
		userpwd = ed_pwd.getText().toString();
		if(username.length()<=0||userpwd.length()<=0){
			Toast.makeText(LogActivity.this, "账号或密码不能为空", 1).show();
			return;
		}
		MyDialog.showdialog(this);
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
						MySharedPreferences sp=new MySharedPreferences(LogActivity.this);
						sp.setusername(username);
						sp.setuserpwd(userpwd);
						sp.outapp(true);
						
						MyApp app=(MyApp)LogActivity.this.getApplication();
						app.user=u;
						AddLiteDb();
						mhandler.sendEmptyMessage(0);
					}else if(status==-1){
						mhandler.sendEmptyMessage(1);
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
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		
		if(keyCode==4){
			if(state==0){
				this.finish();
			}else if(state==1||state==2){
				state=0;
				ll_log.setVisibility(View.VISIBLE);
				ll_register.setVisibility(View.GONE);
				ll_back_pwd.setVisibility(View.GONE);
				text_head.setText("登录");
		}
		
	}
		return true;
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		User item = (User) adapter.getItem(position);
		ed_name.setText(item.getUsername());
		ed_pwd.setText(item.getUserpwd());
		popu.dismiss();
		log();
	}
	private void AddLiteDb(){
		MyApp app=(MyApp) getApplication();
		UserInfoid uf=new UserInfoid();
		uf.setUsername(username);
		uf.setUserpwd(userpwd);
		uf.setToken(app.user.getToken());
		List<UserInfoid> list = userlitedb.getQueryByWhere(UserInfoid.class, "token=? and username=?", new String[]{app.user.getToken(),username});
	    if(list!=null&&list.size()>0){
	    	
	}else{
		userlitedb.Add(uf); 
	}
	}
	
		
	

}
