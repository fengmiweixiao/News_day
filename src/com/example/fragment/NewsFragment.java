package com.example.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.Util.Config;
import com.example.Util.HttpRequstUtil;
import com.example.Util.MyDialog;
import com.example.adapter.CommonAdapter;
import com.example.model.News;
import com.example.news_day.R;
import com.example.news_day.ShowNewsActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
/**
 * ÐÂÎÅ
 * */

public class NewsFragment extends Fragment implements OnItemClickListener{
	Handler mhandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				MyDialog.dismiss();
				newsinfo.clear();
				List<News> newslist=(List<News>) msg.obj;
				newsinfo.addAll(newslist);
				adapter.notifyDataSetChanged();
				break;

			default:
				break;
			}
		};
	};
	public List<News> newsinfo;
	private CommonAdapter<News> adapter;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.news_fragment, null);
		ListView list_view=(ListView) v.findViewById(R.id.list_view);
		list_view.setOnItemClickListener(this);
		newsinfo = new ArrayList<News>();
		adapter = new CommonAdapter<News>(getActivity(), newsinfo, R.layout.news_item){
			@Override
			public void setViewData(View currentView, News item) {
				// TODO Auto-generated method stub
				super.setViewData(currentView, item);
				TextView tv_news_head=CommonAdapter.get(currentView, R.id.tv_news_head);
				TextView tv_news_context=CommonAdapter.get(currentView, R.id.tv_news_context);
				ImageView img_news=CommonAdapter.get(currentView, R.id.img_news);
				
				String icon = item.getIcon();
				ImageLoader.getInstance().displayImage(icon, img_news);
				if(item.getTitle().length()>=15){
					tv_news_head.setText(item.getTitle().substring(0, 15)+"...");
				}else{
					tv_news_head.setText(item.getTitle());
				}
				if(item.getSummary().length()>=18){
					tv_news_context.setText(item.getSummary().intern().substring(0, 18)+"...");
				}else{
					tv_news_context.setText(item.getSummary().intern());
				}
			}
		};
		list_view.setAdapter(adapter);
		MyDialog.showdialog(getActivity());
		AsyncHttpClient ac=new AsyncHttpClient();
		RequestParams params=new RequestParams();
		//ver=0000000&subid=1&dir=1&nid=1&stamp=201609211
		params.put("ver", "1");
		params.put("subid", "1");
		params.put("dir", "1");
		params.put("nid", "1");
		params.put("stamp", "201609211");
		TypeToken<List<News>> type=new TypeToken<List<News>>(){};
		HttpRequstUtil.getHttpRequst().getRequestDataArray(Config.news_list, params, 1, mhandler, type);
		/**ac.get(Config.ip+Config.news_list, params, new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				String str=new String(arg2);
				
				try {
					JSONObject jsonobject=new JSONObject(str);
					int status = jsonobject.getInt("status");
					if(status==0){
						JSONArray data = jsonobject.getJSONArray("data");
						TypeToken<List<News>> type=new TypeToken<List<News>>(){};
						Gson gson=new Gson();
						List<News> newslist = gson.fromJson(data.toString(), type.getType());
						Message message=mhandler.obtainMessage();
						  message.what=1;
						  message.obj=newslist;
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


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		News item = adapter.getItem(position);
		String json = new Gson().toJson(item);
		Intent intent=new Intent(getActivity(),ShowNewsActivity.class);
		intent.putExtra("json", json);
		startActivity(intent);
	}

}
