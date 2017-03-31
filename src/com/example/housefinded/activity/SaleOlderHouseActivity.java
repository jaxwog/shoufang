package com.example.housefinded.activity;
import com.example.housefinded.R;
import com.example.housefinded.view.PinChartview;
import com.google.zxing.common.reedsolomon.GF256;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SaleOlderHouseActivity extends Activity implements OnClickListener{
private RelativeLayout wylayout,makelayout,yearlayout,relayout;
private AlertDialog.Builder dialog;
private TextView property,setjs,nx;
private RadioGroup radioGroup,yearGroup;
private RadioButton rb1,rb2,yb1,yb2;
private Button take;
private LinearLayout getdatail,pinchart_oldshow;
private String [] itemstirng={"普通住宅","非普通住宅","经济适用房"};
private String [] makestring={"按总价计算","按差价计算"};
private String [] nianstring={"不满两年","二到五年","五年以上"};
private TextView fkzj,yys,qs,grs,yhs,gzs,zhds,sjze;
private int yysm,grsm,qsm,yhsm,jzmj,zj,yj,sjz;
private EditText amoney,area,yuanjia;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_saleoldhouse);
		initview();
		//是否首次购房 单选按钮
		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId==R.id.gf){
				}else if(checkedId==R.id.gf_1){
				}
				
			}
		});
		//是否是唯一住房 单选按钮
		yearGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId==R.id.wf){
					
				}else if(checkedId==R.id.wf1){
					
				}
				
			}
		});
	}
	@Override
	public void onClick(View v) {
	switch (v.getId()) {
	case R.id.wuye:
		dialog=new AlertDialog.Builder(SaleOlderHouseActivity.this).setItems(itemstirng, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				property.setText(itemstirng[which]);
				dialog.dismiss();
			}
		});
		dialog.show();
		break;
	case R.id.makejs:
		dialog=new AlertDialog.Builder(SaleOlderHouseActivity.this).setItems(makestring, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				setjs.setText(makestring[which]);
				if(which==1){
					relayout.setVisibility(View.VISIBLE);	
				}
				if(which==0){
					relayout.setVisibility(View.GONE);	
				}
				dialog.dismiss();
			}
		});
		dialog.show();
		break;
	case R.id.nianxian:
dialog=new AlertDialog.Builder(SaleOlderHouseActivity.this).setItems(nianstring, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				nx.setText(nianstring[which]);
				
				dialog.dismiss();
			}
		});
dialog.show();
		break;
	case R.id.run1:
		if("".equals(area.getText().toString()) || "".equals(amoney.getText().toString())){
			Toast.makeText(SaleOlderHouseActivity.this, "请输入总价或者建筑面积", 1).show();
		}else{
		 getdatail.setVisibility(View.VISIBLE);
		 jzmj=	Integer.parseInt(area.getText().toString());//建筑面积
		 zj=Integer.parseInt(amoney.getText().toString());//总价
		 
		 //得到营业税 限制条件为购房年限
		if( nx.getText().toString().equals("不满两年")){
			yysm=(int)(zj*10000*0.056);
		}
		else if(nx.getText().toString().equals("二到五年") || nx.getText().toString().equals("五年以上")){
			yysm=0;//营业税
		}
		
		//得到 契税 限制条件 建筑面积 90，90-144,144  是否首次购房
		if(rb1.isChecked()){
			if(jzmj<90){
				qsm=(int)(zj*0.01*10000);
			}else if(jzmj<=140 && jzmj>=90){
				qsm=(int)(zj*0.02*10000);
			}
			else{
				qsm=(int)(zj*0.04*10000);
			}
		}else {
			qsm=(int)(zj*0.04*10000);
		}
		//个人所得税（ 按总价计算 条件限制 年限和是否唯一住房）（按差价计算 条件限制 ）
		if(setjs.getText().toString().equals("按总价计算")){
		if(nx.getText().toString().equals("不满两年") || nx.getText().toString().equals("二到五年")){
			grsm=(int)(zj*0.01*10000);
		}else{
			if(yb1.isChecked()){
				grsm=0;
			}else{
				grsm=(int)(zj*0.01*10000);
			}
		}
		}else{
			if(nx.getText().toString().equals("不满两年") || nx.getText().toString().equals("二到五年")){
			yj=Integer.parseInt(yuanjia.getText().toString());
			int ce=zj-yj;
			grsm=(int)(ce*0.2*10000);
			}else{
				grsm=0;
			}
}
		
		//得到印花税
		yhsm=0;
		sjz=yysm+qsm+grsm+yhsm;
		fkzj.setText("房款总价  "+amoney.getText().toString()+"万元");
		yys.setText("营业税  "+yysm+"元" );
		qs.setText("契税  "+qsm+"元");
		grs.setText("个人所得税  "+grsm+"元");
		yhs.setText("印花税  "+yhsm+"元");
		sjze.setText("税金总额  "+sjz+"元");
		}
		
		break;
	default:
		break;
	}
		
	}
public void initview(){
	wylayout=(RelativeLayout)findViewById(R.id.wuye);
	makelayout=(RelativeLayout)findViewById(R.id.makejs);
	yearlayout=(RelativeLayout)findViewById(R.id.nianxian);
	relayout=(RelativeLayout)findViewById(R.id.actionas);
	property=(TextView)findViewById(R.id.pt);
	setjs=(TextView)findViewById(R.id.jisuan);
	nx=(TextView)findViewById(R.id.nian);
	fkzj=(TextView)findViewById(R.id.ofkzj);
	yys=(TextView)findViewById(R.id.yys);
	qs=(TextView)findViewById(R.id.oqs);
	grs=(TextView)findViewById(R.id.grs);
	yhs=(TextView)findViewById(R.id.oyhs);
	sjze=(TextView)findViewById(R.id.osjze);
	
	radioGroup=(RadioGroup)findViewById(R.id.radioGroup3);
	yearGroup=(RadioGroup)findViewById(R.id.radioGroup4);
	rb1=(RadioButton)findViewById(R.id.gf);
	rb2=(RadioButton)findViewById(R.id.gf_1);
	yb1=(RadioButton)findViewById(R.id.wf);
	yb2=(RadioButton)findViewById(R.id.wf1);
	take=(Button)findViewById(R.id.run1);
	amoney=(EditText)findViewById(R.id.doller1);
	area=(EditText)findViewById(R.id.jzm1);
	yuanjia=(EditText)findViewById(R.id.yuanjia);
	getdatail=(LinearLayout)findViewById(R.id.sodetail);
	wylayout.setOnClickListener(this);
	makelayout.setOnClickListener(this);
	yearlayout.setOnClickListener(this);
	take.setOnClickListener(this);
}
}
