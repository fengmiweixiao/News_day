package com.example.adapter;

import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;


import com.example.Util.Config;
import com.example.model.News;
import com.example.news_day.MainActivity;
import com.example.news_day.MyApp;
import com.example.news_day.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ImgBaseAdapter extends BaseAdapter {
	private List<News> list;
	private LayoutInflater layout;
	private ImgBaseAdapter adapter;
	Context context;
	private News news;


	public ImgBaseAdapter(List<News> list,
			Context context) {
		super();
		this.list = list;
		adapter = this;
		this.context = context;
		layout=LayoutInflater.from(context);
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
			v=layout.inflate(R.layout.img_list_item, null);
			v.setTag(vh);
		}else{
			vh=(viewHolder) v.getTag();
		}
		vh.tv_img_content=(TextView) v.findViewById(R.id.tv_img_content);
		vh.ed_img_content=(EditText) v.findViewById(R.id.ed_img_content);
		vh.img_list=(ImageView) v.findViewById(R.id.img_list);
		vh.ll_img_view=(ImageView) v.findViewById(R.id.ll_img_view);
		vh.views=v.findViewById(R.id.img_ll);
		vh.bt_ll_img_view=(Button) v.findViewById(R.id.bt_ll_img_view);
		vh.bt_ll_img_view.setOnClickListener(new sendOnClick(position, vh.ed_img_content));
		vh.ll_img_view.setOnClickListener(new MyOnClick(news));
		news = list.get(position);
		String title = news.getTitle();
		if(title.length()>=14){
			vh.tv_img_content.setText(title.substring(0, 14)+"...");
		}else{
			vh.tv_img_content.setText(title);
		}
		ImageLoader.getInstance().displayImage(news.getIcon(), vh.img_list);
		
		if(news.isIsshow()){
			vh.views.setVisibility(View.VISIBLE); 
			vh.ed_img_content.setFocusable(true);
			vh.ed_img_content.setFocusableInTouchMode(true);
			vh.ed_img_content.requestFocus();
			InputMethodManager input=(InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
			input.showSoftInput(vh.ed_img_content, 0);
		
		}else{
			vh.views.setVisibility(View.GONE);
			vh.ed_img_content.setText("");
//			input.hideSoftInputFromInputMethod(vh.ed_img_content.getWindowToken(), 0);
		}
		return v;
	}
	static class viewHolder{
		TextView tv_img_content;
		EditText ed_img_content;
		ImageView img_list;
		ImageView ll_img_view;
		View views;
		Button bt_ll_img_view;
	}
	class sendOnClick implements OnClickListener{
		private int index;
		private EditText ed_context;

		public sendOnClick(int index, EditText ed_context) {
			super();
			this.index = index;
			this.ed_context = ed_context;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			TelephonyManager telephonymanager=(TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			String imei = telephonymanager.getDeviceId();
			MainActivity m=(MainActivity) context;
			MyApp app=(MyApp) m.getApplication();
			if(app.user==null){
				Toast.makeText(context, "没有登录不能评论", 1).show();
				return;
			}
			News news2 = list.get(index);
			String text = ed_context.getText().toString();
			//ver=版本号&nid=新闻编号&token=用户令牌&imei=手机标识符&ctx=评论内容
			AsyncHttpClient ac=new AsyncHttpClient();
			RequestParams params=new RequestParams();
			params.put("ver", "1");
			params.put("nid", news2.getNid()+"");
			params.put("token", app.user.getToken());
			params.put("imei", imei);
			params.put("ctx", text);
			ac.get(Config.ip+Config.cmt_commit, params, new AsyncHttpResponseHandler() {
				
				@Override
				public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
					// TODO Auto-generated method stub
					try {
						JSONObject json=new JSONObject(new String(arg2));
						int status=json.getInt("status");
						if(status==0){
							ed_context.setText("");
							News news3 = list.get(index);
							news3.setIsshow(false);
							adapter.notifyDataSetChanged();
							JSONObject jsonObject = json.getJSONObject("data");
							String explain = jsonObject.getString("explain");
							Toast.makeText(context, explain, 1).show();
							
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
	class MyOnClick implements OnClickListener{
		News item;

		public MyOnClick(News item) {
			super();
			this.item = item;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
				for(int i=0;i<list.size();i++){
//					News news = news.get(i);
					News news2 = list.get(i);
					if(news2.getNid()==item.getNid()){
					if(item.isIsshow()){
						item.setIsshow(false);
					}else{
						item.setIsshow(true);
					}
					}else{
						news2.setIsshow(false);
					}
				}
			
			adapter.notifyDataSetChanged();
		}
		
	}
	

}
