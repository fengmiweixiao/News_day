package com.example.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class MySharedPreferences {
	private SharedPreferences sp;

	public MySharedPreferences (Context context){
		sp = context.getSharedPreferences("sp", Context.MODE_PRIVATE);
	}
	//保存用户名
	public void setusername(String username){
		Editor edit = sp.edit();
		edit.putString("username", username);
		edit.commit();
	}
	//获取用户名
	public String getusername(){
		return sp.getString("username", "");
	}
	//保存密码
		public void setuserpwd(String userpwd){
			Editor edit = sp.edit();
			edit.putString("userpwd", userpwd);
			edit.commit();
		}
		//获取密码
		public String getuserpwd(){
			return sp.getString("userpwd", "");
		}
	public void outapp(boolean isapp){
		Editor edit = sp.edit();
		edit.putBoolean("isapp", isapp);
		edit.commit();
	}
	public boolean inapp(){
		return sp.getBoolean("isapp", true);
	}

}
