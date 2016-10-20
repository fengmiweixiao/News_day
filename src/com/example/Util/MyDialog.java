package com.example.Util;

import com.example.View.photoview.PhotoView;
import com.example.news_day.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import android.widget.ImageView;

public class MyDialog {
	
	private static Dialog dialog;
	private static LayoutInflater layout=null;
	private static Dialog dialog_img_show;

	public static void showdialog(Context context){
		if(layout==null){
			layout = LayoutInflater.from(context);
		}
		
		View v = layout.inflate(R.layout.start_dialog, null);
		Animation loadAnimation = AnimationUtils.loadAnimation(context, R.anim.loading_dialog);
		dialog = new Dialog(context, R.style.My_dialog);
		ImageView img_load=(ImageView) v.findViewById(R.id.img_load);
		dialog.setContentView(v);
		img_load.startAnimation(loadAnimation);
		dialog.show();
		
		
	}
	public static void dismiss(){
		if(dialog!=null){
			dialog.dismiss();
			dialog=null;
			
		}
	}
	public static void ShowImgDialog(Context context,String path){
		if(layout==null){
			layout = LayoutInflater.from(context);
		}
		
		View v = layout.inflate(R.layout.show_img_item, null);
		v.findViewById(R.id.ll_show_dialog).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MyDialog.show_img_dismiss();
			}
		});
		PhotoView imgshow=(PhotoView) v.findViewById(R.id.img_show_dialog);
		ImageLoader.getInstance().displayImage(path, imgshow);
		dialog_img_show = new Dialog(context, R.style.My_dialog);
		LayoutParams lp = dialog_img_show.getWindow().getAttributes();
		lp.width=Config.WIDTH;
		lp.height=Config.HEIGHT;
		dialog_img_show.getWindow().setAttributes(lp);
		dialog_img_show.setContentView(v, lp);
		dialog_img_show.show();
		
	}
	public static void show_img_dismiss(){
		if(dialog_img_show!=null){
			dialog_img_show.dismiss();
		}
	}

}
