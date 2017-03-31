package com.example.housefinded.activity;
import com.example.housefinded.R;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class MortgageActivvity extends TabActivity implements OnClickListener{
	private RelativeLayout layout;
	private Button bb;
	private TextView title;
	ImageView back;
	
	
	String []aimstring={"1年(12)期","2年(24)期","3年(36)期","4年(48)期","5年(60)期"
			,"6年(72)期","7年(84)期","8年(96)期","9年(108)期","10年(120)期","11年(132)期"
			,"12年(144)期","13年(156)期","14年(168)期","15年(180)期","16年(192)期","17年(204)期",
			"18年(216)期","19年(228)期","20年(240)期","21年(252)期","22年(264)期","23年(276)期","24年(288)期"
			,"25年(300)期","26年(312)期","27年(324)期","28年(336)期","29年(348)期","30年(360)期"};
	
@SuppressWarnings("deprecation")
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.mortgage_activity);
	initview();
	back.setOnClickListener(this);
	bb.setOnClickListener(this);
}
public void initview(){
layout=(RelativeLayout)findViewById(R.id.Mortgage_title);
back=(ImageView)layout.findViewById(R.id.btnback);
bb=(Button)layout.findViewById(R.id.btnswitch);
title=(TextView)layout.findViewById(R.id.titlename);
bb.setText("税费计算");
title.setText("房贷计算");
addtabone();
addtabtwo();
}

@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {

	super.onActivityResult(requestCode, resultCode, data);
	if(requestCode==1){
		if(resultCode==2){
			
		}
	}
}
public void addtabone(){
	Intent intent=new Intent();
	intent.setClass(MortgageActivvity.this, GonJJActivity.class);
	TabHost tabHost = getTabHost();
	TabSpec spec = tabHost.newTabSpec("One");   
       spec.setIndicator("公积金贷款");   
	       spec.setContent(intent);           
	       tabHost.addTab(spec);   
}
public void addtabtwo(){
	Intent intent=new Intent();
	intent.setClass(MortgageActivvity.this, ShangdaiisActivity.class);
	TabHost tabHost = getTabHost();
	TabSpec spec = tabHost.newTabSpec("two");   
       spec.setIndicator("商业贷款");   
	       spec.setContent(intent); 
	       tabHost.addTab(spec);  
	       
}
@Override
public void onClick(View v) {
	switch (v.getId()) {
	case R.id.btnback:
		finish();
		break;
	case R.id.btnswitch:
		Intent intent=new Intent();
		intent.setClass(MortgageActivvity.this, TaxcalculationActivity.class);
		startActivity(intent);
		finish();
	default:
		break;
	}
}
}
