package com.example.housefinded.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.Tools.TextUtil;
import com.example.housefinded.R;
import com.example.housefinded.view.PieDataItem;
import com.example.housefinded.view.PinChartview;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SaleNewHouseActivity extends Activity implements OnClickListener{
private EditText danjia,mianji;
	private TextView qs,yhs,gzf,wtf,fwf,sjze,fwzj;
	private Button dbclick;
	private LinearLayout layout,pinchart_show;
	private int color[]={Color.BLUE,Color.GREEN,Color.YELLOW,Color.LTGRAY,Color.RED,Color.MAGENTA,Color.CYAN};
	@Override

protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.activity_salenewhouse);
	initview();
	dbclick.setOnClickListener(this);
}
	
	public void initview(){
		danjia=(EditText)findViewById(R.id.jzm);
		mianji=(EditText)findViewById(R.id.doller);
		layout=(LinearLayout)findViewById(R.id.suidetail);
		qs=(TextView)findViewById(R.id.qs);
		yhs=(TextView)findViewById(R.id.yhs);
		gzf=(TextView)findViewById(R.id.gzs);
		wtf=(TextView)findViewById(R.id.wtf);
		fwf=(TextView)findViewById(R.id.fwf);
		sjze=(TextView)findViewById(R.id.sjze);
		fwzj=(TextView)findViewById(R.id.fkzj);
		dbclick=(Button)findViewById(R.id.run);
		
		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.run:
			if("".equals(mianji.getText().toString()) || "".equals(danjia.getText().toString())){
				Toast.makeText(SaleNewHouseActivity.this, "请输入新房面积或者新房单间", 1).show();
			}else{
				InputMethodManager manager=(InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
				manager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
				if("0".equals(mianji.getText().toString()) || "0".equals(danjia.getText().toString())){
					Toast.makeText(SaleNewHouseActivity.this, "请正确输入数据", 0).show();
				}else{
				layout.setVisibility(View.VISIBLE);
				int squae=Integer.parseInt(mianji.getText().toString());
				int smoney=Integer.parseInt(danjia.getText().toString());
				int allmoney=squae*smoney;
				int qsm=(int) (allmoney*0.03);
				int yhsm=(int)(allmoney*0.0005);
				int gzfm=(int)(allmoney*0.003);
				int wtfm=(int)(allmoney*0.003);
				int fwfm=(int)(allmoney*0.0005);
				int sjzem=qsm+yhsm+gzfm+wtfm+fwfm;
				fwzj.setText("房款总价   "+allmoney+"元");
				qs.setText("契税  "+qsm+"元");
				yhs.setText("印花税  "+yhsm+"元");
				gzf.setText("公证费  "+gzfm+"元");
				wtf.setText("委托办理产权手续费  "+wtfm+"元");
				fwf.setText("房屋买卖手续费  "+fwfm+"元");
				sjze.setText("税金总额  "+sjzem+"元");
			String text[]={"房款总价   "+allmoney+"元","契税  "+qsm+"元","印花税  "+yhsm+"元","公证费  "+gzfm+"元",
					"委托办理产权手续费  "+wtfm+"元","房屋买卖手续费  "+fwfm+"元","税金总额  "+sjzem+"元"};
			float data[]={0,(360 * qsm / sjzem),(360 * yhsm / sjzem),(360 * gzfm / sjzem),(360 * wtfm / sjzem)
					,(360 * fwfm / sjzem),0};
				}
			}
			break;

		default:
			break;
		}
		
	}




}
