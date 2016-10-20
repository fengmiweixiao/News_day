package com.example.news_day;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.Util.Config;
import com.example.Util.MyDialog;
import com.example.Util.MySharedPreferences;
import com.example.Util.AlbumUtil.CropImageActivity;
import com.example.View.CircularImage;
import com.example.adapter.CommonAdapter;
import com.example.model.Loginlog;
import com.example.model.News;
import com.example.model.Personal;
import com.example.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 用户个人信息页面
 * */

public class UserInfoActivity extends Activity implements OnClickListener,OnLongClickListener {
	private Personal list;
	Handler mhandler=new Handler(){
		

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				personalinfo.clear();
				MyDialog.dismiss();
				list = (Personal) msg.obj;
				tv_user_name.setText(list.getUid());
				tv_user_integral.setText("积分："+list.getIntegration());
				tv_user_number.setText("跟帖数量统计："+list.getComnum());
				//通过picasso-2.5.2.jar获取图片
//				Picasso.with(UserInfoActivity.this).load(list.getPortrait()).into(img_user);
				//通过universal-image-loader-1.9.2-with-sources.jar获取图片
				ImageLoader.getInstance().displayImage(list.getPortrait(), img_user);
				List<Loginlog> loginlog=list.getLoginlog();
				personalinfo.addAll(loginlog);
				adapter.notifyDataSetChanged();
				break;
			case 2:
				MyDialog.dismiss();
				Toast.makeText(UserInfoActivity.this, "获取数据失败", 1).show();
				break;
			case 3:
				Toast.makeText(UserInfoActivity.this, "头像上传成功", 1).show();
				MyDialog.dismiss();
				String path=(String) msg.obj;
				Bitmap b = BitmapFactory.decodeFile(path);
				img_user.setImageBitmap(b);
				break;

			default:
				break;
			}
		};
	};
	private CircularImage img_user;
	private TextView tv_user_name;
	private TextView tv_user_integral;
	private TextView tv_user_number;
	private ListView user_listview;
	private List<Loginlog> personalinfo;
	private CommonAdapter<Loginlog> adapter;
	private Dialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		((MyApp)getApplication()).addActivity(this);
		setContentView(R.layout.user_info_activity);
		initHead();
		initView();
	}
	private void initView() {
		// TODO Auto-generated method stub
		findViewById(R.id.bt_user_back).setOnClickListener(this);
		img_user = (CircularImage) findViewById(R.id.img_user);
		img_user.setOnLongClickListener(this);
		img_user.setOnClickListener(this);
		tv_user_name = (TextView) findViewById(R.id.tv_user_name);
		tv_user_integral = (TextView) findViewById(R.id.tv_user_integral);
		tv_user_number = (TextView) findViewById(R.id.tv_user_number);
	    user_listview = (ListView) findViewById(R.id.user_listview);
		loadUserInfoData();
		personalinfo = new ArrayList<Loginlog>();
		adapter = new CommonAdapter<Loginlog>(this, personalinfo, R.layout.user_item){
			@Override
			public void setViewData(View currentView, Loginlog item) {
				// TODO Auto-generated method stub
				super.setViewData(currentView, item);
				
				TextView tv_user_address=CommonAdapter.get(currentView, R.id.tv_user_address);
				
				tv_user_address.setText(item.getAddress()+"-"+item.getTime());
			}
		};  
		user_listview.setAdapter(adapter);
	}
	private void loadUserInfoData() {
		// TODO Auto-generated method stub
		MyDialog.showdialog(this);
		TelephonyManager telephonymanager=(TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
		String imei = telephonymanager.getDeviceId();
		MyApp app = (MyApp) getApplication();
		String token = app.user.getToken();
		AsyncHttpClient ac=new AsyncHttpClient();
		RequestParams params=new RequestParams();
		params.put("ver", "1");
		params.put("token", token);
		params.put("imei", imei);
		ac.get(Config.ip+Config.user_home, params, new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				String str=new String(arg2);
				
				try {
					JSONObject jsonobject=new JSONObject(str);
					int status = jsonobject.getInt("status");
					if(status==0){
						JSONObject data = jsonobject.getJSONObject("data");
//						String uid = data.getString("uid");
//						String portrait = data.getString("portrait");
//						int integration = data.getInt("integration");
//						int comnum = data.getInt("comnum");
						//加载头像图片
//						if(portrait==null){
//							img_user.setImageResource(R.drawable.ic_launcher);
//						}else{
//							Picasso.with(UserInfoActivity.this).load(portrait).into(img_user);
//						}
//										
//						tv_user_name.setText(uid);
//						tv_user_integral.setText("积分："+integration);
//						tv_user_number.setText("跟帖数量统计："+comnum);
//						
//						JSONArray loginlog = data.getJSONArray("loginlog");					
//						TypeToken<List<Personal>> type=new TypeToken<List<Personal>>(){};
						TypeToken<Personal> type=new TypeToken<Personal>(){};
						Gson gson=new Gson();
						Personal personal = gson.fromJson(data.toString(), type.getType());
						
						Message message=mhandler.obtainMessage();
						  message.what=1;
						  message.obj=personal;
						  mhandler.sendMessage(message); 
					}else{
						mhandler.sendEmptyMessage(2);
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
	private void initHead() {
		// TODO Auto-generated method stub
		ImageView head_left=(ImageView) findViewById(R.id.head_left);
		ImageView head_right=(ImageView) findViewById(R.id.head_right);
		head_left.setOnClickListener(this);
		head_right.setVisibility(View.GONE);
		TextView text_head = (TextView) findViewById(R.id.text_head);
		text_head.setText("个人信息中心");
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.head_left){
			this.finish();
		}else if(v.getId()==R.id.bt_user_back){
			
			MyApp application = (MyApp) getApplication();
			application.user=null;
			MySharedPreferences sp=new MySharedPreferences(UserInfoActivity.this);
			sp.outapp(false);
			this.finish();
		}else if(v.getId()==R.id.img_user){
			MyApp application = (MyApp) getApplication();
			if(application.user!=null){
				MyDialog.ShowImgDialog(UserInfoActivity.this, list.getPortrait());
				
			}
		}else if(v.getId()==R.id.bt_photograph){
			OppenCamera();
			dialog.dismiss();
		}else if(v.getId()==R.id.bt_album){
			OppenPhoto();
			dialog.dismiss();
		}else if(v.getId()==R.id.bt_cancel){
			dialog.dismiss();
		}
			
		
		
	}
	@Override
	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.img_user){
			imgselect();
		}
		return true;
	}
	private void imgselect() {
		// TODO Auto-generated method stub
		View v=getLayoutInflater().inflate(R.layout.ing_select, null);
		v.findViewById(R.id.bt_photograph).setOnClickListener(this);
		v.findViewById(R.id.bt_album).setOnClickListener(this);
		v.findViewById(R.id.bt_cancel).setOnClickListener(this);
		dialog = new Dialog(this, R.style.My_dialog);
		LayoutParams lp = dialog.getWindow().getAttributes();
		lp.width=WindowManager.LayoutParams.MATCH_PARENT;
		lp.height=WindowManager.LayoutParams.MATCH_PARENT;
		lp.gravity=Gravity.BOTTOM;
		dialog.getWindow().setAttributes(lp);
		dialog.setContentView(v, lp);
		dialog.show();
	}
	public static final String IMAGE_PATH = "Jokeep";
	private static String localTempImageFileName = "";
	private static final int FLAG_CHOOSE_IMG = 5;
	private static final int FLAG_CHOOSE_PHONE = 6;
	private static final int FLAG_MODIFY_FINISH = 7;
	public static final File FILE_SDCARD = Environment
			.getExternalStorageDirectory();
	public static final File FILE_LOCAL = new File(FILE_SDCARD, IMAGE_PATH);
	public static final File FILE_PIC_SCREENSHOT = new File(FILE_LOCAL,
			"images/screenshots");
	// 打开相机
		public void OppenCamera() {
			String status = Environment.getExternalStorageState();
			if (status.equals(Environment.MEDIA_MOUNTED)) {

				try {
					localTempImageFileName = "";
					localTempImageFileName = String.valueOf((new Date()).getTime())
							+ ".png";
					File filePath = FILE_PIC_SCREENSHOT;
					if (!filePath.exists()) {
						filePath.mkdirs();
					}
					Intent intent = new Intent(
							android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
					File f = new File(filePath, localTempImageFileName);
					// localTempImgDir和localTempImageFileName是自己定义的名字
					Uri u = Uri.fromFile(f);
					intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
					intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
					startActivityForResult(intent, FLAG_CHOOSE_PHONE);

				} catch (Exception e) {
					//
				}
			}

		}
		// 打开相册
		public void OppenPhoto() {
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_PICK);
			intent.setType("image/*");
			startActivityForResult(intent, FLAG_CHOOSE_IMG);

		}
		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {

			if (requestCode == FLAG_CHOOSE_IMG && resultCode == RESULT_OK) {

				if (data != null) {
					Uri uri = data.getData();
					if (!TextUtils.isEmpty(uri.getAuthority())) {
						Cursor cursor = getContentResolver().query(uri,
								new String[] { MediaStore.Images.Media.DATA },
								null, null, null);
						if (null == cursor) {

							return;
						}
						cursor.moveToFirst();
						String path = cursor.getString(cursor
								.getColumnIndex(MediaStore.Images.Media.DATA));
						cursor.close();

						Intent intent = new Intent(this, CropImageActivity.class);
						intent.putExtra("path", path);
						startActivityForResult(intent, FLAG_MODIFY_FINISH);
					} else {

						Intent intent = new Intent(this, CropImageActivity.class);
						intent.putExtra("path", uri.getPath());
						startActivityForResult(intent, FLAG_MODIFY_FINISH);
					}
				}
			} else if (requestCode == FLAG_CHOOSE_PHONE && resultCode == RESULT_OK) {
				File f = new File(FILE_PIC_SCREENSHOT, localTempImageFileName);

				Intent intent = new Intent(this, CropImageActivity.class);
				intent.putExtra("path", f.getAbsolutePath());
				startActivityForResult(intent, FLAG_MODIFY_FINISH);
			} else if (requestCode == FLAG_MODIFY_FINISH && resultCode == RESULT_OK) {
				if (data != null) {
					final String path = data.getStringExtra("path");

					Bitmap b = BitmapFactory.decodeFile(path);
//					img_user.setImageBitmap(b);
					MyDialog.showdialog(this);
					AsyncHttpClient ac=new AsyncHttpClient();
					RequestParams params=new RequestParams();
					MyApp app=(MyApp) getApplication();
					params.put("token", app.user.getToken());
					try {
						params.put("portrait", new File(path));
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ac.post(Config.ip+Config.user_image, params, new AsyncHttpResponseHandler() {
						
						@Override
						public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
							// TODO Auto-generated method stub
							String s=new String(arg2);
							try {
								JSONObject jsonobject=new JSONObject(s);
								int status = jsonobject.getInt("status");
								if(status==0){
									Message message = mhandler.obtainMessage();
									message.what=3;
									message.obj=path;
									mhandler.sendMessage(message);
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
		}
		

}
