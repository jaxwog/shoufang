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

public class AboutActivity extends Activity {
	private ImageView iv_back;
	private TextView tv_about;
	String text = "    搜房网天下贷是由纽交所上市公司搜房网倾力打造的互联网房地产金融服务网站，于2014年4月正式上线。搜房天下贷为用户打造了一个安全、诚信、专业、高效的房产信贷投融资交易平台，通过互联网平台为有房屋贷款需求的人士取得贷款，同时也为平台投资人提供安全、便捷、高收益的投资回报。";
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.about);

		returnback();
		showText();
	}

	private void showText() {
		// TODO Auto-generated method stub
		tv_about = (TextView) findViewById(R.id.tv_about);
		tv_about.setText(text);
		tv_about.setTextColor(Color.BLACK);
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
