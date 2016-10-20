package com.example.Util;

import android.content.Context;

import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class Config {
	//ip ����
	public static String ip="http://118.244.212.82:9092/newsClient/";
	//���Žӿ�
	public static String news_list="news_list?";
	//��¼�ӿ�user_login?ver=�汾��&uid=�û���&pwd=����&device=0
	public static String user_login="user_login?";
	//ע��ӿ�user_register?ver=�汾��&uid=�û���&email=����&pwd=��½����
	public static String user_register="user_register?";
	//�һ�����ӿ�user_forgetpass?ver=�汾��&email=����
	public static String user_forgetpass="user_forgetpass?";
	//�û���Ϣ���Ľӿ�user_home?ver=�汾��&imei=�ֻ���ʶ��&token =�û�����
	public static String user_home="user_home?";
	//ͷ���ϴ��ӿ�user_image?token=�û�����& portrait =ͷ��
	public static String user_image="user_image?";
	//�������۽ӿ�cmt_commit?ver=�汾��&nid=���ű��&token=�û�����&imei=�ֻ���ʶ��&ctx=��������
	public static String cmt_commit="cmt_commit?";
	//��ʾ���۽ӿ�cmt_list ?ver=�汾��&nid=����id&type=1&stamp=yyyyMMdd&cid=����id&dir=0&cnt=20
	public static String cmt_list="cmt_list?";
	//��Ļ��
	public static int WIDTH=0;
	//��Ļ��
	public static int HEIGHT=0;
	
	 /**
 	 * ��ʼ��ͼƬ�������
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
 		 * 1 ȥLur������ �о�ֱ�ӷ��� û�м���
 		 * 2 �洢�����ݿ� url ��ΪΨһ��ʾ �о�ֱ�ӷ���
 		 * ��������û�ҵ�  ����
 		 * ��Lur �ﱣ��һ��
 		 * ͬʱҲ���� �������ݿ��һ��
 		 * Lur
 		 * ���ݿ� Lur
 		 *  Lur
 		 * */
 	
 	}

}
