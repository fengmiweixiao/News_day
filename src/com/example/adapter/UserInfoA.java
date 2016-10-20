package com.example.adapter;

import java.util.List;

import com.example.model.User;
import com.example.model.UserInfoid;
import com.example.news_day.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class UserInfoA extends BaseAdapter {
	private List<UserInfoid> list;
	
	private LayoutInflater layout;

	
	

	public UserInfoA(List<UserInfoid> list, LayoutInflater layout) {
		super();
		this.list = list;
		this.layout = layout;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		// TODO Auto-generated method stub
		viewHolder vh=null;
		if(v==null){
			vh=new viewHolder();
			v=layout.inflate(R.layout.userinfo_name_item, null);
			v.setTag(vh);
		}else{
			vh=(viewHolder) v.getTag();
		}
		User user = list.get(position);
		vh.tv_name=(TextView) v.findViewById(R.id.tv_name);
		vh.tv_name.setText(user.getUsername());
		return v;
	}
	static class viewHolder{
		TextView tv_name; 
	}

}
