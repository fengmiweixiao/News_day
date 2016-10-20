package com.example.Util;

import android.content.Context;

import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class Config {
	//ip 域名
	public static String ip="http://118.244.212.82:9092/newsClient/";
	//新闻接口
	public static String news_list="news_list?";
	//登录接口user_login?ver=版本号&uid=用户名&pwd=密码&device=0
	public static String user_login="user_login?";
	//注册接口user_register?ver=版本号&uid=用户名&email=邮箱&pwd=登陆密码
	public static String user_register="user_register?";
	//找回密码接口user_forgetpass?ver=版本号&email=邮箱
	public static String user_forgetpass="user_forgetpass?";
	//用户信息中心接口user_home?ver=版本号&imei=手机标识符&token =用户令牌
	public static String user_home="user_home?";
	//头像上传接口user_image?token=用户令牌& portrait =头像
	public static String user_image="user_image?";
	//新闻评论接口cmt_commit?ver=版本号&nid=新闻编号&token=用户令牌&imei=手机标识符&ctx=评论内容
	public static String cmt_commit="cmt_commit?";
	//显示评论接口cmt_list ?ver=版本号&nid=新闻id&type=1&stamp=yyyyMMdd&cid=评论id&dir=0&cnt=20
	public static String cmt_list="cmt_list?";
	//屏幕宽
	public static int WIDTH=0;
	//屏幕高
	public static int HEIGHT=0;
	
	 /**
 	 * 初始化图片缓存组件
 	 * 
 	 * @param context
 	 */
 	public static void initImageLoader(Context context) {
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

}
