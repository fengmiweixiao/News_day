package com.example.news_day;

import java.util.List;

import com.example.dskbase.LiteDb;
import com.example.model.CollectionNews;
import com.example.model.News;
import com.example.model.User;
import com.google.gson.Gson;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ShowNewsActivity extends Activity implements OnClickListener {
	private WebView webview;
	private News news;
	private LiteDb litedb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		((MyApp)getApplication()).addActivity(this);
		setContentView(R.layout.show_news_activity);
		initView();
		initHead();
		litedb = new LiteDb(this);
	}

	private void initView() {
		webview = (WebView) findViewById(R.id.webview);
		WebSettings settings = webview.getSettings();
		settings.setJavaScriptEnabled(true);
		webview.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
//				webview.loadUrl(news.getLink());
				view.loadUrl(url);
				return false;
			}
		});
		Gson gson=new Gson();
		Intent intent = getIntent();
		String json = intent.getStringExtra("json");
		news = gson.fromJson(json, News.class);
		webview.loadUrl(news.getLink());
		
	}
	private void initHead() {
		// TODO Auto-generated method stub
		ImageView head_left=(ImageView) findViewById(R.id.head_left);
		ImageView head_right=(ImageView) findViewById(R.id.head_right);
		head_left.setOnClickListener(this);
		head_right.setOnClickListener(this);
		head_right.setVisibility(View.VISIBLE);
		head_right.setImageResource(R.drawable.add_img);
		TextView text_head = (TextView) findViewById(R.id.text_head);
		text_head.setText("咨询");
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.head_left){
			this.finish(); 
		}else if(v.getId()==R.id.head_right){
			MyApp app=(MyApp) getApplication();
			User user = app.user;
			if(user==null){
//				Intent intent=new Intent(this,LogActivity.class);
//				startActivity(intent);
				Toast.makeText(this, "没有登录账号 不能收藏", 1).show();
				return;
			}
			CollectionNews ns=new CollectionNews();
			ns.setIcon(news.getIcon());
			ns.setLink(news.getLink());
			ns.setNid(news.getNid());
			ns.setStamp(news.getStamp());
			ns.setSummary(news.getSummary());
			ns.setTitle(news.getTitle());
			ns.setType(news.getType());
			ns.setToken(user.getToken());
			List<CollectionNews> list = litedb.getQueryByWhere(CollectionNews.class, "nid=? and token=?", new String[]{news.getNid()+"",user.getToken()});
		    if(list!=null&&list.size()>0){
		    	Toast.makeText(this, "已收藏 不能重复收藏", 1).show();
		    }else{
		    	Toast.makeText(this, "收藏成功", 1).show();
		    	litedb.Add(ns);
		    }
		}
	}

}
