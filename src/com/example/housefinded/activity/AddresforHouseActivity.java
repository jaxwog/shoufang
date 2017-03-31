package com.example.housefinded.activity;

import com.example.housefinded.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AddresforHouseActivity extends Activity {
	private RelativeLayout layout;
	private TextView title;
	private Button bb;
	
	private EditText etext;
	private ImageView image,back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.searchhouse_activity);
		initview();
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		etext.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				image.setVisibility(View.VISIBLE);
			}
		});
		//搜索按钮点击
		image.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if("".equals(etext.getText().toString())){
					
				}else{
					Intent intent=getIntent();
					Bundle bundle=new Bundle();
					bundle.putString("addres",etext.getText().toString());
					intent.putExtras(bundle);
					setResult(13, intent);
					finish();
				}
			}
		});
	}

	public void initview() {
		layout = (RelativeLayout) findViewById(R.id.addres_house);
		title = (TextView) layout.findViewById(R.id.titlename);
		back = (ImageView) layout.findViewById(R.id.btnback);
		bb = (Button) layout.findViewById(R.id.btnswitch);
		title.setText("选择小区");
		bb.setVisibility(View.GONE);
		etext=(EditText)findViewById(R.id.serch_text);
		image=(ImageView)findViewById(R.id.search_to);
	}
}
