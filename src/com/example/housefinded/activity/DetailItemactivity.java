package com.example.housefinded.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.housefinded.R;
import com.example.javabean.RentHousebaen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DetailItemactivity extends Activity implements OnClickListener {
	RelativeLayout layout;
	ImageView back;
	Button bb;
	TextView title;
	RentHousebaen rentbean;
	TextView detailtitle,detail_money,detail_pay,details_fs,details_area
	,details_hx,details_zx,details_cx,details_flo,details_des,details_xq;
	TextView [] tt=new TextView[8];
	ImageView [] mm=new ImageView[8];
	List<String> stringname ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_detailhouse);
		initview();
		initdata();
	}

	@SuppressWarnings("unchecked")
	public void initview() {
		rentbean = new RentHousebaen();
		layout = (RelativeLayout) findViewById(R.id.detail_title);
		back = (ImageView) layout.findViewById(R.id.btnback);
		bb = (Button) layout.findViewById(R.id.btnswitch);
		title = (TextView) layout.findViewById(R.id.titlename);
		detailtitle=(TextView)findViewById(R.id.detail_addres);
		detail_money=(TextView)findViewById(R.id.detail_money);
		detail_pay=(TextView)findViewById(R.id.detail_pay);
		details_fs=(TextView)findViewById(R.id.details_fs);
		details_area=(TextView)findViewById(R.id.details_area);
		details_hx=(TextView)findViewById(R.id.details_hx);
		details_zx=(TextView)findViewById(R.id.details_zx);
		details_cx=(TextView)findViewById(R.id.details_cx);
		details_flo=(TextView)findViewById(R.id.details_flo);
		details_des=(TextView)findViewById(R.id.details_des);
		details_xq=(TextView)findViewById(R.id.details_xq);
		
		tt[0]=(TextView)findViewById(R.id.details_text0);//床
		tt[1]=(TextView)findViewById(R.id.details_text1);//宽带
		tt[2]=(TextView)findViewById(R.id.details_text2);//电视
		tt[3]=(TextView)findViewById(R.id.details_text3);//洗衣机
		tt[4]=(TextView)findViewById(R.id.details_text4);//暖气
		tt[5]=(TextView)findViewById(R.id.details_text5);//冰箱
		tt[6]=(TextView)findViewById(R.id.details_text6);//空调
		tt[7]=(TextView)findViewById(R.id.details_text7);//热水器
		
		mm[0]=(ImageView)findViewById(R.id.detali_image0);
		mm[1]=(ImageView)findViewById(R.id.detali_image1);
		mm[2]=(ImageView)findViewById(R.id.detali_image2);
		mm[3]=(ImageView)findViewById(R.id.detali_image3);
		mm[4]=(ImageView)findViewById(R.id.detali_image4);
		mm[5]=(ImageView)findViewById(R.id.detali_image5);
		mm[6]=(ImageView)findViewById(R.id.detali_image6);
		mm[7]=(ImageView)findViewById(R.id.detali_image7);
		bb.setVisibility(View.GONE);
		title.setText("庙里");// 显示租房的位置
		back.setOnClickListener(this);
		rentbean = (RentHousebaen) getIntent().getSerializableExtra(
				"RentHousebaen");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnback:
			finish();
			break;

		default:
			break;
		}

	}
public void initdata(){
	/*"id":"61","userId":"95","createDate":"2015-11-03 10:11:01","title":null,
	"cellName":"请选择小区(必填)","cellAdress":null,"cellArea":null,"type":"2",
	"tenType":"1","jointrentType":"次卧","jointrentMode":null,
	"houseType":"3室2厅1卫","houseArea":"100","houseLayer":"5","houseMode":null,
	"rent":"1500","rentUnit":null,"isPropertyfee":null,"propertyfee":null,
	"officeLevel":null,"officeType":null,"shopsType":null,"shopsStatus":null,
	"isAttorn":null,"isCede":null,"shopsTarget":null,"bed":false,"broadband":false,
	"tv":false,"washer":false,"heating":false,"airconditioner":false,"fridge":false,
	"waterHeater":false,"housePtss":"床  宽带  冰箱  洗衣机  ","houseLabel":null,
	"description":"交通便利，环境适合居住","personalName":"先","telephone":"15038219245",
	"release":null,"userType":null,"zbpt":null,"jtlx":null,"face":"东南",
	"decorate":"精装修","payment_method":null,"floor":"2","ban":null,"roomnum":null,
	"house_type":null,"sex_limit":null,
	"imagePath":"upload\/widget\/201511\/20151103105101_950.jpg",*/
	detailtitle.setText("出租  "+rentbean.getCellName()+" "+rentbean.getRent()+"元/月" +"郑州房多贷网");
	detail_money.setText(rentbean.getRent()+"元/月");
	detail_pay.setText("("+rentbean.getPayment_method()+")");
	if("1".equals(rentbean.getTenType())){
	details_fs.setText("合租"+"("+rentbean.getJointrentType()+")");
	}else{
		details_fs.setText("整租");
	}
	details_area.setText(rentbean.getHouseArea()+"㎡");
	details_hx.setText(rentbean.getHouseType());
	details_zx.setText(rentbean.getDecorate());
	details_cx.setText(rentbean.getFace());
	details_flo.setText(rentbean.getFloor()+"层"+"/"+rentbean.getHouseLayer()+"层");
	details_des.setText(rentbean.getDescription());
	details_xq.setText(rentbean.getCellName());
	getstringname(rentbean.getHousePtss());
	for(int k=0;k<stringname.size();k++){
	if("床".equals(stringname.get(k))){
		mm[0].setImageResource(R.drawable.chat_yellow_bg);
	}else if("宽带".equals(stringname.get(k))){
		mm[1].setImageResource(R.drawable.chat_yellow_bg);
	}
	else if("电视".equals(stringname.get(k))){
		mm[2].setImageResource(R.drawable.chat_yellow_bg);
	}
	else if("洗衣机".equals(stringname.get(k))){
		mm[3].setImageResource(R.drawable.chat_yellow_bg);
	}
	else if("暖气".equals(stringname.get(k))){
		mm[4].setImageResource(R.drawable.chat_yellow_bg);
	}
	else if("冰箱".equals(stringname.get(k))){
		mm[5].setImageResource(R.drawable.chat_yellow_bg);
	}
	else if("空调".equals(stringname.get(k))){
		mm[6].setImageResource(R.drawable.chat_yellow_bg);
	}
	else if("热水器".equals(stringname.get(k))){
		mm[7].setImageResource(R.drawable.chat_yellow_bg);
	}
	}
	
}
public List<String> getstringname(String s){
	String[] split = s.split("\\s+");
	 stringname = new ArrayList<String>();
	for(String each : split) {
		stringname.add(each);
		}
	return stringname;
}
}
