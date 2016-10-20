package com.example.fragment;

import java.util.ArrayList;
import java.util.List;

import com.example.adapter.CommonAdapter;
import com.example.dskbase.LiteDb;
import com.example.model.CollectionNews;
import com.example.model.News;
import com.example.news_day.MyApp;
import com.example.news_day.R;
import com.example.news_day.ShowNewsActivity;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 收藏
 * */

public class CollectionFragment extends Fragment implements OnItemLongClickListener,OnItemClickListener {
	
	private CommonAdapter<CollectionNews> adapter;
	private LiteDb litedb;
	private MyApp app;
	private List<CollectionNews> mlist;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
//		Toast.makeText(getActivity(), "收藏页面已启动", 1).show();
		View v = inflater.inflate(R.layout.collection_fragment, null);
		app = (MyApp) getActivity().getApplication();
		litedb = new LiteDb(getActivity());
		ListView collection_listview=(ListView) v.findViewById(R.id.collection_listview);
		collection_listview.setOnItemLongClickListener(this);
		collection_listview.setOnItemClickListener(this);
		mlist = new ArrayList<CollectionNews>();
		adapter = new CommonAdapter<CollectionNews>(getActivity(), mlist, R.layout.news_item){
			@Override
			public void setViewData(View currentView, CollectionNews item) {
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
		collection_listview.setAdapter(adapter);
		return v;
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		Log.i("aaa", "onresume");
		if(app.user==null){
			Toast.makeText(getActivity(), "没有登录账号", 1).show();
			mlist.clear();
			adapter.notifyDataSetChanged();
			return;
		}
		selectquery();
	}
	public void selectquery(){ 
		
		List<CollectionNews> list=litedb.getQueryByWhere(CollectionNews.class,"token = ?",new String[]{app.user.getToken()+""});
		mlist.clear();
		mlist.addAll(list);
		adapter.notifyDataSetChanged();
	}
	int index=-1;
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		// TODO Auto-generated method stub
		index=position;
		AlertDialog.Builder dialog=new Builder(getActivity());
		dialog.setTitle("要删除这个收藏？");
		dialog.setMessage("你确定要删除此条目吗？");
		dialog.setPositiveButton("确定", new OnClickListener() {
			
		

			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				String tokenapp = app.user.getToken();
				News news = mlist.get(index);
				int nid = news.getNid();
				litedb.deleteWhere(CollectionNews.class, "nid=? and token=?", new String[]{nid+"",tokenapp});
				dialog.dismiss();
				selectquery();
			}
		});
		dialog.setNegativeButton("取消", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		AlertDialog createdialog = dialog.create();
		createdialog.show();
		return true;
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		News ns = adapter.getItem(position);
		Gson gson=new Gson();
		String json = gson.toJson(ns);
		Intent intent=new Intent(getActivity(),ShowNewsActivity.class);
		intent.putExtra("json", json);
		startActivity(intent);
		
	}

}
