package com.example.news_day;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.Util.Config;
import com.example.Util.MySharedPreferences;
import com.example.adapter.FragmentAdapter;
import com.example.fragment.CollectionFragment;
import com.example.fragment.CommentFragment;
import com.example.fragment.ImgFragment;
import com.example.fragment.NewsFragment;
import com.example.model.User;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.os.Bundle;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements OnClickListener{
	
	ViewPager vp;
	private RadioGroup home_rg;
	String[] str=new String[]{"新闻","收藏","跟帖","图片"};
	private TextView text_head;
	private FragmentManager fm;
	public NewsFragment newsfragment;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		((MyApp)getApplication()).addActivity(this);
		setContentView(R.layout.activity_main);
		initView();
		initHead();
	}
	private void initHead() {
		// TODO Auto-generated method stub
		ImageView head_left=(ImageView) findViewById(R.id.head_left);
		ImageView head_right=(ImageView) findViewById(R.id.head_right);
		head_right.setOnClickListener(this);
		head_left.setVisibility(View.GONE);
		text_head = (TextView) findViewById(R.id.text_head);
		
	}

	private void initView() {
		// TODO Auto-generated method stub
		vp=(ViewPager) findViewById(R.id.home_vp);
		home_rg = (RadioGroup) findViewById(R.id.home_rg);
		home_rg.check(R.id.rb_news);
		RadioButton rb_news=(RadioButton) findViewById(R.id.rb_news);
		List<Fragment> data =getData();
		fm = getSupportFragmentManager();
		FragmentAdapter adapter=new FragmentAdapter(data, fm);
		vp.setAdapter(adapter);
		vp.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				if(arg0==0){
					home_rg.check(R.id.rb_news);
					text_head.setText(str[0]);
				}else if(arg0==1){
					home_rg.check(R.id.rb_collection);
					text_head.setText(str[1]);
				}else if(arg0==2){
					home_rg.check(R.id.rb_comment);
					text_head.setText(str[2]);
				}else if(arg0==3){
					home_rg.check(R.id.rb_img);
					text_head.setText(str[3]);
				}
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		home_rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if(checkedId==R.id.rb_news){
					vp.setCurrentItem(0);
					
				}else if(checkedId==R.id.rb_collection){
					vp.setCurrentItem(1);
				}else if(checkedId==R.id.rb_comment){
					vp.setCurrentItem(2);
				}else if(checkedId==R.id.rb_img){
					vp.setCurrentItem(3);
				}
			}
		});
		
	}
	public List<Fragment> getData(){
		List<Fragment> fragment=new ArrayList<Fragment>();
		newsfragment = new NewsFragment();
		CollectionFragment collectionfragment=new CollectionFragment();
		CommentFragment commentfragment=new CommentFragment();
		ImgFragment imgfragment=new ImgFragment();
		fragment.add(newsfragment);
		fragment.add(collectionfragment);
		fragment.add(commentfragment);
		fragment.add(imgfragment);
		
		return fragment;
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		//跳转到添加页面
		if(v.getId()==R.id.head_right){
			
			MyApp application =(MyApp) getApplication();
			if(application.user==null){
				jump(LogActivity.class);
			}else{
				
				jump(UserInfoActivity.class);
			}
			
		}
		
	}
	public void jump(Class c){
		Intent intent=new Intent(this,c);
		startActivity(intent);
	}
	long out=0;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK){
//			moveTaskToBack(true);
			if(System.currentTimeMillis()-out>2000){
				Toast.makeText(this, "再一次点击退出程序", 1).show();
				out=System.currentTimeMillis();
			}else{
				((MyApp)getApplication()).outActivity();
			}
		}
		return true;
	}

	
}
