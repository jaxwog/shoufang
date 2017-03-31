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
import android.widget.TextView;
import android.widget.TabHost.TabSpec;

public class TaxcalculationActivity extends TabActivity implements OnClickListener{
	RelativeLayout layout;
	Button bb;
	TextView title;
	ImageView back;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_taxcalculation);
		initview();
		back.setOnClickListener(this);
		bb.setOnClickListener(this);
	}
	

	public void initview(){
		layout=(RelativeLayout)findViewById(R.id.taxtitle);
		back=(ImageView)layout.findViewById(R.id.btnback);
		bb=(Button)layout.findViewById(R.id.btnswitch);
		title=(TextView)layout.findViewById(R.id.titlename);
		bb.setText("房贷计算");
		title.setText("税费计算");
		addtabone();
		addtabtwo();
		}
	
	public void addtabone(){
		Intent intent=new Intent();
		intent.setClass(TaxcalculationActivity.this, SaleNewHouseActivity.class);
		TabHost tabHost = getTabHost();
		TabSpec spec = tabHost.newTabSpec("One");   
	       spec.setIndicator("新房");   
		       spec.setContent(intent);           
		       tabHost.addTab(spec);   
	}
	public void addtabtwo(){
		Intent intent=new Intent();
		intent.setClass(TaxcalculationActivity.this, SaleOlderHouseActivity.class);
		TabHost tabHost = getTabHost();
		TabSpec spec = tabHost.newTabSpec("two");   
	       spec.setIndicator("二手房");   
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
		intent.setClass(TaxcalculationActivity.this, MortgageActivvity.class);
		startActivity(intent);
		finish();
		break;
	default:
		break;
	}
		
	}
}
