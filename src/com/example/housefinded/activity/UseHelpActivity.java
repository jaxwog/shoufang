package com.example.housefinded.activity;

import com.example.housefinded.R;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class UseHelpActivity extends Activity {
	private TextView tv_2, tv_3, tv_4, tv_1;
	private ImageView iv_back;
	String text1 = "  “首页”和“搜索”页为你提供多种方式搜索房源/楼盘，区域、关键词、地铁、地图等帮你找房";
	String text2 = "  视频、图片、语言等多种在线咨询方式帮你快速联系房东、经纪人";
	String text3 = "  “我的房天下”里可以方便查看最近联系记录、浏览历史、我的收藏、我的优惠等";
	String text4 = "  “我的二手房”和“我的租房”里为你提供便捷的发布房源入口，还可以委托给经纪人";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.use_help_activity);

		returnback();
		showText();
	}

	private void showText() {
		// TODO Auto-generated method stub
		tv_1 = (TextView) findViewById(R.id.tv_1);
		tv_1.setText(text1);
		tv_1.setTextColor(Color.BLACK);
		tv_2 = (TextView) findViewById(R.id.tv_2);
		tv_2.setText(text2);
		tv_2.setTextColor(Color.BLACK);
		tv_3 = (TextView) findViewById(R.id.tv_3);
		tv_3.setText(text3);
		tv_3.setTextColor(Color.BLACK);
		tv_4 = (TextView) findViewById(R.id.tv_4);
		tv_4.setText(text4);
		tv_4.setTextColor(Color.BLACK);
	}

	private void returnback() {
		// TODO Auto-generated method stub
		iv_back = (ImageView) findViewById(R.id.iv_back);
		iv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

}
