package com.example.fragment;

import java.util.List;

import com.example.adapter.CommonAdapter;
import com.example.adapter.ImgBaseAdapter;
import com.example.model.News;
import com.example.news_day.MainActivity;
import com.example.news_day.R;
import com.example.news_day.ShowNewsActivity;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
/**
 * ͼƬ
 * */

public class ImgFragment extends Fragment implements OnItemClickListener{
//	private CommonAdapter<News> adapter;
	private List<News> newsinfo;
	private Context context;
	private ImgBaseAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.img_fragment, null);
		ListView img_listview=(ListView) v.findViewById(R.id.img_listview);
		img_listview.setOnItemClickListener(this);
		MainActivity activity = (MainActivity) getActivity();
		newsinfo = activity.newsfragment.newsinfo;
		if(newsinfo.size()>0&&newsinfo!=null){
//			adapter = new CommonAdapter<News>(activity, newsinfo, R.layout.img_list_item){@Override
//				public void setViewData(View currentView, News item) {
//					// TODO Auto-generated method stub
//					super.setViewData(currentView, item);
//					context=getActivity();
//					TextView tv_img_content=CommonAdapter.get(currentView, R.id.tv_img_content);
//					EditText ed_img_content=CommonAdapter.get(currentView, R.id.ed_img_content);
//					ImageView img_list=CommonAdapter.get(currentView, R.id.img_list);
//					ImageView ll_img_view=CommonAdapter.get(currentView, R.id.ll_img_view);
//					ll_img_view.setOnClickListener(new MyOnClick(item));
//					View v=CommonAdapter.get(currentView, R.id.img_ll);
//					String title = item.getTitle();
//					if(title.length()>=14){
//						tv_img_content.setText(title.substring(0, 14)+"...");
//					}else{
//						tv_img_content.setText(title);
//					}
//					ImageLoader.getInstance().displayImage(item.getIcon(), img_list);
//					InputMethodManager input=(InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//					if(item.isIsshow()){
//						v.setVisibility(View.VISIBLE);
//						ed_img_content.setFocusable(true);
//						ed_img_content.setFocusableInTouchMode(true);
//						ed_img_content.requestFocus();
//						input.showSoftInput(ed_img_content, 0);
//					
//					}else{
//						v.setVisibility(View.GONE);
//						input.hideSoftInputFromInputMethod(ed_img_content.getWindowToken(), 0);
//					}
//				}};
//				img_listview.setAdapter(adapter);
			adapter=new ImgBaseAdapter(newsinfo, getActivity());
			img_listview.setAdapter(adapter);
		}
		
		
		return v;
	}
//	class MyOnClick implements OnClickListener{
//		News item;
//
//		public MyOnClick(News item) {
//			super();
//			this.item = item;
//		}
//
//		@Override
//		public void onClick(View v) {
//			// TODO Auto-generated method stub
//			
//				for(int i=0;i<newsinfo.size();i++){
//					News news = newsinfo.get(i);
//					if(news.getNid()==item.getNid()){
//					if(item.isIsshow()){
//						item.setIsshow(false);
//					}else{
//						item.setIsshow(true);
//					}
//					}else{
//						news.setIsshow(false);
//					}
//				}
//			
//			adapter.notifyDataSetChanged();
//		}
		
//	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		News item = (News) adapter.getItem(position);
		String json = new Gson().toJson(item);
		Intent intent=new Intent(getActivity(),ShowNewsActivity.class);
		intent.putExtra("json", json);
		startActivity(intent);
	}

}
