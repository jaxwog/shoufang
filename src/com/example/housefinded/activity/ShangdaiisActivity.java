package com.example.housefinded.activity;

import java.math.BigDecimal;

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

public class ShangdaiisActivity extends Activity implements OnClickListener{
	RelativeLayout rlayout;
	Intent intent;
	RadioGroup rd;
	RadioButton rb1,rb2;
	private EditText allmoney;
	private double lilv,monthlilv;
	private int totalmoney,paylilv,monthmoney;
	private TextView biglilv,bigone,ll,ze,setlixi,yj;
	private Button updata;
	private LinearLayout llayout;
	private String []gridtitle={"基准利率","基准利率的1.5倍","基准利率的85折",
			"基准利率的7折"
			};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mu_tab2);
		initview();
		initdata();
		rd.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				
				
			}
		});
	}
public void initview(){
	rlayout=(RelativeLayout)findViewById(R.id.tab_lll1);
	rd=(RadioGroup)findViewById(R.id.radioGroup2);
	rb1=(RadioButton)findViewById(R.id.rb_3);
	rb2=(RadioButton)findViewById(R.id.rb_4);
	
	bigone=(TextView)findViewById(R.id.tab_ll1);
	biglilv=(TextView)findViewById(R.id.text_ll1);
	ll=(TextView)findViewById(R.id.ll1);
	ze=(TextView)findViewById(R.id.ze1);
	setlixi=(TextView)findViewById(R.id.setlixi1);
	yj=(TextView)findViewById(R.id.yj1);
	
	updata=(Button)findViewById(R.id.getlixi1);
	allmoney=(EditText)findViewById(R.id.allmoney_1);
	llayout=(LinearLayout)findViewById(R.id.showdetail1);
	rlayout.setOnClickListener(this);
	updata.setOnClickListener(this);
}
@Override
public void onClick(View v) {
	switch (v.getId()) {
	case R.id.tab_lll1:
		if(bigone.getText().toString().equals("基准利率")){
			lilv=5.15;
		}else if(bigone.getText().toString().equals("基准利率的1.5倍")){
			lilv=5.55;
		}else if(bigone.getText().toString().equals("基准利率的85折")){
			lilv=4.38;
		}else if(bigone.getText().toString().equals("基准利率的7折")){
			lilv=3.60;
		}
		intent=new Intent();
		intent.putExtra("key", 2);
		intent.putExtra("lilv", lilv);
		intent.setClass(ShangdaiisActivity.this, ShangdaiAvtivity.class);
		startActivityForResult(intent, 2);
		break;
	case R.id.getlixi1:
		
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
			llayout.setVisibility(View.VISIBLE);
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
				Toast.makeText(ShangdaiisActivity.this, "请输入房贷总额", 1).show();
			}
			break;
	default:
		break;
	}
}
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	super.onActivityResult(requestCode, resultCode, data);
	
		if(resultCode==2){
			if(!"8".equals(data.getStringExtra("position"))){
				bigone.setText(gridtitle[Integer.parseInt(data.getStringExtra("position"))]);
			}else{
				bigone.setText(data.getStringExtra("lixi"));
			}
		}
	
}
public void initdata(){
	//商业贷款    基准利率5.15%  1.5倍（5.66%） 85折(4.38%) 7折(3.60%)
		lilv=5.15;
}

public double getlilv(){
	if(bigone.getText().toString().equals("基准利率")){
		lilv=5.15;
	}else if(bigone.getText().toString().equals("基准利率的1.5倍")){
		lilv=5.55;
	}else if(bigone.getText().toString().equals("基准利率的85折")){
		lilv=4.38;
	}else if(bigone.getText().toString().equals("基准利率的7折")){
		lilv=3.60;
	}else{
		lilv=Double.parseDouble(bigone.getText().toString());
	}
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
