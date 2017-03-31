package com.example.housefinded.activity;

import com.example.housefinded.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RentalHouse extends Activity {
	RelativeLayout layout, alllaout, onlylayout;
	TextView title;
	Button  bb;
	ImageView back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 房屋的发布和出租
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.publishhouse_activity);
		// 返回按钮
		initView();
		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		// 整租点击事件
		alllaout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("type", 0);
				intent.setClass(RentalHouse.this, HouseAllAvtivity.class);
				startActivity(intent);
			}
		});
		//合租点击事件
		onlylayout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("type", 1);
				intent.setClass(RentalHouse.this, HouseAllAvtivity.class);
				startActivity(intent);
			}
		});
	}

	// 整屋出租,注册的点击事件的名称在xml文件中
	public void Allpublish(View view) {

		/*
		 * Intent intent=new Intent(); intent.setClass(RentalHouse.this, "");
		 */
	}

	// 合租
	public void Onlypublish(View view) {

	}

	public void initView() {
		layout = (RelativeLayout) findViewById(R.id.publish_house);
		title = (TextView) layout.findViewById(R.id.titlename);
		back = (ImageView) layout.findViewById(R.id.btnback);
		bb = (Button) layout.findViewById(R.id.btnswitch);
		alllaout = (RelativeLayout) findViewById(R.id.allhouse);
		title.setText("出租方式");
		onlylayout = (RelativeLayout) findViewById(R.id.onlyhouse);
		bb.setVisibility(View.GONE);

	}
}
