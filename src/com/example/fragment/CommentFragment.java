package com.example.fragment;

import java.security.KeyRep.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.Util.Config;
import com.example.Util.HttpRequstUtil;
import com.example.adapter.CommonAdapter;
import com.example.model.CommentInfo;
import com.example.news_day.R;
import com.example.news_day.StartActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
/**
 * 跟帖
 * */

public class CommentFragment extends Fragment {
	Handler mhandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 8:
				commentInfo.clear();
				List<CommentInfo> list=(List<CommentInfo>) msg.obj;
				commentInfo.addAll(list);
				adapter.notifyDataSetChanged();
				
				break;

			default:
				break;
			}
		};
	};
	private List<CommentInfo> commentInfo;
	private CommonAdapter<CommentInfo> adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.comment_fragment, null);
		ListView comment_listview=(ListView) v.findViewById(R.id.comment_listview);
		commentInfo = new ArrayList<CommentInfo>();
		adapter = new CommonAdapter<CommentInfo>(getActivity(), commentInfo, R.layout.news_item){
			@Override
			public void setViewData(View currentView, CommentInfo item) {
				// TODO Auto-generated method stub
				super.setViewData(currentView, item);
				ImageView img=CommonAdapter.get(currentView, R.id.img_news);
				TextView texthead=CommonAdapter.get(currentView, R.id.tv_news_head);
				TextView texttime=CommonAdapter.get(currentView, R.id.tv_news_context);
				texthead.setText(item.getUid());
				texttime.setText(item.getStamp());
				ImageLoader.getInstance().displayImage(item.getPortrait(), img);
			}
		};
		comment_listview.setAdapter(adapter);
		AsyncHttpClient ac=new AsyncHttpClient();
		RequestParams params=new RequestParams();
		//ver=版本号&nid=新闻id&type=1&stamp=yyyyMMdd&cid=评论id&dir=0&cnt=20
		params.put("ver", "1");
		params.put("nid", "20");
		params.put("type", "1");
		params.put("stamp", "20160617");
		params.put("dir", "0");
		params.put("cid", "111");
		TypeToken<List<CommentInfo>> type=new TypeToken<List<CommentInfo>>(){};
		HttpRequstUtil.getHttpRequst().getRequestDataArray(Config.cmt_list, params, 8, mhandler, type);
		
		/**ac.get(Config.ip+Config.cmt_list, params, new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				try {
					JSONObject json=new JSONObject(new String(arg2));
					int status = json.getInt("status");
					if(status==0){
						JSONArray data = json.getJSONArray("data");
						TypeToken<List<CommentInfo>> type=new TypeToken<List<CommentInfo>>(){};
						List<CommentInfo> list=new Gson().fromJson(data.toString(), type.getType());
						Message message = mhandler.obtainMessage();
						message.obj=list;
						message.what=8;
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
		});*/
		
		return v;
	}

}
