package com.example.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class FragmentAdapter extends PagerAdapter {
	
	List<Fragment> fragment;
	FragmentManager fm;
	public FragmentAdapter(List<Fragment> fragment, FragmentManager fm) {
		super();
		this.fragment = fragment;
		this.fm = fm;
	}
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		Fragment fg = fragment.get(position);
		FragmentTransaction bt = fm.beginTransaction();
		if(!fg.isAdded()){
			bt.add(fg, fg.getClass().getSimpleName());
			//提交
			bt.commit();
			//立即执行
			fm.executePendingTransactions();
		}
		View views = fg.getView();
		if(views.getParent()==null){
			container.addView(views);
		}
		return views;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return fragment.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0==arg1;
	}
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		//super.destroyItem(container, position, object);
		container.removeView((View)object);
	}

}
