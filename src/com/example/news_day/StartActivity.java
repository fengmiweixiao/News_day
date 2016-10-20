package com.example.news_day;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.Util.Config;
import com.example.Util.LogService;
import com.example.Util.MyDialog;
import com.example.Util.MySharedPreferences;
import com.example.adapter.ImageAdapter;
import com.example.model.User;
import com.example.service.EventMessage;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import de.greenrobot.event.EventBus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.widget.Toast;

public class StartActivity extends Activity {
	Handler mhandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				MyDialog.dismiss();
				Intent intent=new Intent(StartActivity.this,MainActivity.class);
				startActivity(intent);
				StartActivity.this.finish();
				break;

			default:
				break;
			}
		};
	};
	ViewPager vp;
	private ImageView img_bd;
	private SharedPreferences sp;
	int[] res=new int[]{R.drawable.bd,R.drawable.small,R.drawable.wy,R.drawable.welcome};
	private String username;
	private String userpwd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);
		
		((MyApp)getApplication()).addActivity(this);
		setContentView(R.layout.start_activity);
		sp=getSharedPreferences("sp", Context.MODE_PRIVATE);
//		EventBus.getDefault().register(this);
		initView();
		initImageLoader(this);
		Display display = getWindowManager().getDefaultDisplay();
		Config.WIDTH=display.getWidth();
		Config.HEIGHT=display.getHeight();
		
	}
	
	private void initView() {
		// TODO Auto-generated method stub
		vp=(ViewPager) findViewById(R.id.img_vp);
		img_bd=(ImageView) findViewById(R.id.img_bd);
		if(getisstart()==0){
			updatestart();
			img_bd.setVisibility(View.GONE);
			vp.setVisibility(View.VISIBLE);
			List<View> views = loadViewPagerData();
			ImageAdapter adapter=new ImageAdapter(views);
			vp.setAdapter(adapter);
			View view = views.get(views.size()-1);
			view.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					mhandler.sendEmptyMessage(1);
					
				}
			});
			
		}else{
			MyDialog.showdialog(this);
			LogService.Log(this);
			EventMessage event=new EventMessage();
			event.obj="登录成功";
			event.what=0;
			EventBus.getDefault().post(event);
			
				mhandler.postDelayed(new Runnable() {
					
					@Override
					public void run() {
//						 TODO Auto-generated method stub
						MySharedPreferences sp=new MySharedPreferences(StartActivity.this);
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
											
											
											MyApp app=(MyApp)StartActivity.this.getApplication();
											app.user=u;
											
										
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
						mhandler.sendEmptyMessage(1);
						
					}
				}, 3000);
			
			} 
			
		
	}
	public int getisstart(){
		int int1 = sp.getInt("isstart", 0);
		
		return int1;
	}
	public void updatestart(){
		Editor edit = sp.edit();
		edit.putInt("isstart", 1);
		edit.commit();
	}
	public List<View> loadViewPagerData(){
		List<View> views=new ArrayList<View>();
		for(int i=0;i<res.length;i++){
			ImageView imageview=new ImageView(this);
			LayoutParams params=new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
			imageview.setLayoutParams(params);
			imageview.setScaleType(ScaleType.FIT_XY);
			imageview.setImageResource(res[i]);
			views.add(imageview);
			
		}
		return views;
	}
	 /**
 	 * 初始化图片缓存组件
 	 * 
 	 * @param context
 	 */
 	protected static void initImageLoader(Context context) {
 		// Create default options which will be used for every
 		// displayImage(...) call if no options will be passed to this method
 		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
 				.cacheInMemory(true)
 				.cacheOnDisc(true)
 				.build();
 		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
 				.threadPoolSize(3)	// default
 				.threadPriority(Thread.NORM_PRIORITY - 1)	// default
 				.tasksProcessingOrder(QueueProcessingType.LIFO)
 				.memoryCache(new LruMemoryCache(2 * 1024 * 1024))
 				.memoryCacheSizePercentage(13) 	// default
 				.defaultDisplayImageOptions(defaultOptions)
 				.writeDebugLogs() 	// Remove for release app
 				.build();
 		// Initialize ImageLoader with configuration.
 		ImageLoader.getInstance().init(config);
 		/*
 		 * url 
 		 * 1 去Lur里面找 有就直接返回 没有继续
 		 * 2 存储在数据库 url 作为唯一表示 有就直接返回
 		 * 这两步都没找到  下载
 		 * 在Lur 里保存一份
 		 * 同时也会在 本地数据库存一份
 		 * Lur
 		 * 数据库 Lur
 		 *  Lur
 		 * */
 	
 	}
 	//事件回调 和handler功能一样
// 	public void onEventMainThread(EventMessage em){
// 		switch (em.what) {
//		case 0:
//			Toast.makeText(StartActivity.this, em.obj+"", 1).show();
//          
//		case 1:
//			Toast.makeText(StartActivity.this, em.obj+"", 1).show();
//          
//
//		default:
//			mhandler.postDelayed(new Runnable() {
//				
//				@Override
//				public void run() {
//					mhandler.sendEmptyMessage(1);
//					
//				}
//			}, 3000);
//		
//		
//				
//			break;
//		}
// 	}
// 	@Override
// 	protected void onDestroy() {
// 		// TODO Auto-generated method stub
// 		super.onDestroy();
// 		EventBus.getDefault().unregister(this);
// 	}

}
