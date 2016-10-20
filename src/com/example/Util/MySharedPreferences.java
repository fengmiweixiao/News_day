package com.example.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class MySharedPreferences {
	private SharedPreferences sp;

	public MySharedPreferences (Context context){
		sp = context.getSharedPreferences("sp", Context.MODE_PRIVATE);
	}
	//�����û���
	public void setusername(String username){
		Editor edit = sp.edit();
		edit.putString("username", username);
		edit.commit();
	}
	//��ȡ�û���
	public String getusername(){
		return sp.getString("username", "");
	}
	//��������
		public void setuserpwd(String userpwd){
			Editor edit = sp.edit();
			edit.putString("userpwd", userpwd);
			edit.commit();
		}
		//��ȡ����
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
