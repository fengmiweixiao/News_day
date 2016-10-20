package com.example.news_day;

import java.util.ArrayList;
import java.util.List;

import com.example.model.User;

import android.app.Activity;
import android.app.Application;

public class MyApp extends Application {
	public User user;
	public List<Activity> activityinfo=null;
	//��¼�򿪵Ľ���
	public void addActivity(Activity a){
		if(activityinfo==null){
			activityinfo=new ArrayList<Activity>();
		}
		activityinfo.add(a);
	}
	//�˳�
	public void outActivity(){
		for(int i=0;i<activityinfo.size();i++){
			Activity activity = activityinfo.get(i);
			activity.finish();
		}
	}

}
