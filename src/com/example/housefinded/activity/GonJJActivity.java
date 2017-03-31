package com.example.housefinded.activity;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import com.example.housefinded.R;

import android.app.Activity;
import android.content.Intent;
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

public class GonJJActivity extends Activity implements OnClickListener{
	RadioGroup rd;
	RadioButton rb1,rb2;
	RelativeLayout tab_xi,tab_lll;
	TextView ya,textll,tabll,setlixi,ll,ze,yj;
	LinearLayout showdetail;
	EditText allmoney;
	Button getdetail;
	private double lilv,monthlilv;
	private int totalmoney,paylilv,monthmoney;
	private String []gridtitle={"基准利率","基准利率的1.5倍","基准利率的85折",
			"基准利率的7折"
			};
	
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.mu_tab1);
	initview();
	initdata();
	
	rd.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			if(checkedId==rb1.getId()){
				
			}
			else if(checkedId==rb2.getId()){
				
			}
			
		}
	});
}
public void initview(){
	rd=(RadioGroup)findViewById(R.id.radioGroup1);
	rb1=(RadioButton)findViewById(R.id.rb_1);
	rb2=(RadioButton)findViewById(R.id.rb_2);
	tab_xi=(RelativeLayout)findViewById(R.id.tab_xi);
	ya=(TextView)findViewById(R.id.tab_nq);
	textll=(TextView)findViewById(R.id.text_ll);
	tabll=(TextView)findViewById(R.id.tab_ll);
	tab_lll=(RelativeLayout)findViewById(R.id.tab_lll);
	setlixi=(TextView)findViewById(R.id.setlixi);
	allmoney=(EditText)findViewById(R.id.allmoney);
	showdetail=(LinearLayout)findViewById(R.id.showdetail);
	ll=(TextView)findViewById(R.id.ll);
	ze=(TextView)findViewById(R.id.ze);
	yj=(TextView)findViewById(R.id.yj);
	getdetail=(Button)findViewById(R.id.getlixi);
	//tab_xi.setOnClickListener(this);
	tab_lll.setOnClickListener(this);
	getdetail.setOnClickListener(this);

}
@Override
public void onClick(View v) {
Intent intent;
	switch (v.getId()) {
	case R.id.tab_lll:
		if(tabll.getText().toString().equals("基准利率")){
			lilv=3.25;
		}else if(tabll.getText().toString().equals("基准利率的1.5倍")){
			lilv=3.58;
		}else if(tabll.getText().toString().equals("基准利率的85折")){
			lilv=2.76;
		}else if(tabll.getText().toString().equals("基准利率的7折")){
			lilv=2.28;
		}
		intent=new Intent();
		intent.putExtra("key", 1);
		intent.putExtra("lilv", lilv);
		intent.setClass(GonJJActivity.this, ShangdaiAvtivity.class);
		startActivityForResult(intent, 1);
		break;
	case R.id.getlixi:
		if(!"".equals(allmoney.getText().toString())){
		/*设贷款额为a，月利率为i，年利率为I，还款月数为n，每月还款额为b，还款利息总和为Y
		月均还款:b＝a×i×（1＋i）^n÷〔（1＋i）^n－1〕 
		支付利息:Y＝n×a×i×（1＋i）^n÷〔（1＋i）^n－1〕－a 
		还款总额:n×a×i×（1＋i）^n÷〔（1＋i）^n－1〕 
		出来结果：年利率    贷款总额   利息   月均还款*/
		monthlilv=getDouble(getlilv()/12);//月利率
		totalmoney=Integer.parseInt(allmoney.getText().toString());//这是总的贷款金额
		int totalmoneyto=totalmoney*10000;
		double montlilvto=monthlilv*0.01;
		showdetail.setVisibility(View.VISIBLE);
		if(rb1.isChecked()){
			int partleft=(int)(360*totalmoneyto*montlilvto);
			double partmoddle=getDouble(Math.pow(1+montlilvto, 360));
			double partright=getDouble(partmoddle-1);
			double left=getDouble(partmoddle/partright);
			int left1=(int)(partleft*left);
			paylilv=left1-totalmoneyto;
			ll.setText("利       率:"+lilv+"%");
			ze.setText("贷款总额:"+allmoney.getText().toString()+"万元");
			setlixi.setText("利       息:"+paylilv);
			yj.setText("月均还款:"+left1/360);
		}
		if(rb2.isChecked()){
			//总利息=〔(总贷款额÷还款月数+总贷款额×月利率)+总贷款额÷还款月数×(1+月利率)〕÷2×还款月数-总贷款额
			int partleft=totalmoneyto/360;
			int partleft2=(int)(totalmoneyto*montlilvto);
			double partright2=montlilvto+1;
			int partright=(int)(partleft*partright2);
			int lleft=partleft+partleft2+partright;
			int hze=lleft*180;
			paylilv=hze-totalmoneyto;
			ll.setText("利       率:"+lilv+"%");
			ze.setText("贷款总额:"+allmoney.getText().toString()+"万元");
			setlixi.setText("利       息:"+paylilv);
			yj.setText("月均还款:"+hze/360);
		}
		
		}else{
			Toast.makeText(GonJJActivity.this, "请输入房贷总额", 1).show();
		}
		break;
	default:
		break;
	}
}
public void initdata(){
	//公积金贷款     基准利率3.25%  1.5倍（3.58%） 85折(2.76%) 7折(2.28%)
		lilv=3.25;
}
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	super.onActivityResult(requestCode, resultCode, data);
	
	if(resultCode==2)
	{

		if(!"8".equals(data.getStringExtra("position"))){
			tabll.setText(gridtitle[Integer.parseInt(data.getStringExtra("position"))]);
		}else{
			tabll.setText(data.getStringExtra("lixi"));
		}
	
	}
}
public double getlilv(){
	if(tabll.getText().toString().equals("基准利率")){
		lilv=3.25;
	}else if(tabll.getText().toString().equals("基准利率的1.5倍")){
		lilv=3.58;
	}else if(tabll.getText().toString().equals("基准利率的85折")){
		lilv=2.76;
	}else if(tabll.getText().toString().equals("基准利率的7折")){
		lilv=2.28;
	}else{
		lilv=Double.parseDouble(tabll.getText().toString());
	}
	System.out.print(lilv);
	return lilv;
}
//返回double
public double getDouble(double db){
 /*DecimalFormat df = new DecimalFormat("###.00");//显示 double后面的三位小数
	return Double.parseDouble(df.format(db));*/
	 BigDecimal bg = new BigDecimal(db);  
     double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();  
     return f1;
}

}
