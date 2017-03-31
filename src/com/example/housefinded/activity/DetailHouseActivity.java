package com.example.housefinded.activity;
import com.example.housefinded.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DetailHouseActivity extends Activity {
	RelativeLayout layout;
	private LinearLayout hz_linear,zz_layout,zz_linear;
	TextView title, textaim, textredoing, textpay;
	Button bb;
	ImageView back;
	AlertDialog.Builder dialog;
	EditText ft,ft1,ft2,ft3,hzs,hzt,hzw;
	ListView listone;
	String[] textstrig = { "毛坯", "简装修", "精装修", "豪华装修" };
	String[] aimstring = { "南北", "南", "东南", "东", "西南", "北", "西", "东西", "东北",
			"西北" };
	String[] paystring = { "押一付三", "押一付二", "押一付一", "半年付", "年付", "面议" };
    String cx,zx,zf,lc,zl,ld,fh,hzss,hzts,hzws;//朝向，装修，支付方式，楼层，总楼层，几号楼，房间号。
    String cx1="",zx1="",lc1="",zl1="",ld1="",fh1="",hzss1="",hzts1="",hzws1="";//朝向，装修，支付方式，楼层，总楼层，几号楼，房间号。
    Intent intent;
    int typenum;
    boolean isfirst=true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.detail_house);
		intent=getIntent();
		typenum=intent.getIntExtra("typenum", 1);
		initview();
		if(!intent.getBooleanExtra("isfirst",true)){
			textaim.setText(intent.getStringExtra("cx"));
			textredoing.setText(intent.getStringExtra("zx"));
			textpay.setText(intent.getStringExtra("zf"));
			ft.setText(intent.getStringExtra("lc"));
			ft1.setText(intent.getStringExtra("zl"));
			ft2.setText(intent.getStringExtra("ld"));
			ft3.setText(intent.getStringExtra("fh"));
		}
		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	public void initview() {
		layout = (RelativeLayout) findViewById(R.id.detail_title);
		title = (TextView) layout.findViewById(R.id.titlename);
		back = (ImageView) layout.findViewById(R.id.btnback);
		bb = (Button) layout.findViewById(R.id.btnswitch);
		title.setText("填写房屋详情");
		bb.setVisibility(View.GONE);
		textaim = (TextView) findViewById(R.id.aim);
		textredoing = (TextView) findViewById(R.id.redoing);
		textpay = (TextView) findViewById(R.id.textpay);
		textpay.setText("押一付三");
		hz_linear=(LinearLayout)findViewById(R.id.z_all_type);
		zz_layout=(LinearLayout)findViewById(R.id.zz_layout2);
		zz_linear=(LinearLayout)findViewById(R.id.zz_layout);
		if(typenum==1){
			hz_linear.setVisibility(View.VISIBLE);
		}else if(typenum==0){
			zz_layout.setVisibility(View.VISIBLE);
			zz_linear.setVisibility(View.VISIBLE);
		}
		ft=(EditText)findViewById(R.id.flower_1);
		ft1=(EditText)findViewById(R.id.flower_2);
		ft2=(EditText)findViewById(R.id.flower_3);
		ft3=(EditText)findViewById(R.id.flower_4);
		
		hzs=(EditText)findViewById(R.id.hz_shi);
		hzt=(EditText)findViewById(R.id.hz_ting);
		hzw=(EditText)findViewById(R.id.hz_wei);
	}

	// 装修情况
	public void isredoing(View view) {
		LayoutInflater inflater;
		inflater = LayoutInflater.from(getApplicationContext());
		view = inflater.inflate(R.layout.view_forsel, null);

		dialog = new AlertDialog.Builder(DetailHouseActivity.this).setItems(
				textstrig, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						textredoing.setText(textstrig[which]);
						dialog.dismiss();
					}
				});
		dialog.show();
		// listone = (ListView) view.findViewById(R.id.listone);
		// listone.setAdapter(new ArrayAdapter<String>(DetailHouseActivity.this,
		// android.R.layout.simple_expandable_list_item_1, getData()));

	}

	// 房屋朝向
	public void whereaim(View view) {
		dialog = new AlertDialog.Builder(DetailHouseActivity.this).setItems(
				aimstring, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						textaim.setText(aimstring[which]);
						dialog.dismiss();
					}
				});
		dialog.show();
	}

	// 支付方式
	public void ispay(View view) {
		dialog = new AlertDialog.Builder(DetailHouseActivity.this).setItems(
				paystring, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						textpay.setText(paystring[which]);
						dialog.dismiss();
					}
				});
		dialog.show();
	}
public void savasim(View view){
	if("".equals(ft.getText().toString()) || "".equals(ft1.getText().toString())){

Bundle data=new Bundle();
if(textaim.getText().toString().equals("不限")){
	cx="";
	
}else{
	cx=textaim.getText().toString();
	cx1=textaim.getText().toString()+",";
}
	if(textredoing.getText().toString().equals("不限")){
		zx="";
		
	}else{
		zx=textredoing.getText().toString();
		zx1=textredoing.getText().toString()+",";
	}
	zf=textpay.getText().toString();
	
	if("".equals(ft.getText().toString()) ){
		lc="";
		
	}else{
		lc=ft.getText().toString();
		lc1="第"+ft.getText().toString()+"层,";
	}
	if("".equals(ft1.getText().toString())){
		zl="";
		
	}else{
		zl=ft1.getText().toString();
		zl1=ft1.getText().toString()+",";
	}
		if("".equals(ft2.getText().toString())){
			ld="";
			
		}	else{
			ld=ft2.getText().toString();
			ld1="第"+ft2.getText().toString()+"栋,";
		}
		
		if("".equals(ft3.getText().toString())){
			fh="";
		
		}	else{
			fh=ft3.getText().toString();
			fh1="第"+ft3.getText().toString()+"室,";
		}
		if("".equals(hzs.getText().toString())){
			hzss="";
		}else{
			hzss=hzs.getText().toString();
			hzss1=hzs.getText().toString()+"室";
		}
		if("".equals(hzt.getText().toString())){
			hzts="";
		}else{
			hzts=hzt.getText().toString();
			hzts1=hzt.getText().toString()+"厅";
		}
		if("".equals(hzw.getText().toString())){
			hzws="";
		}else{
			hzws=hzw.getText().toString();
			hzws1=hzw.getText().toString()+"卫,";
		}
		data.putBoolean("isfirst", false);
		data.putString("cx", cx);
		data.putString("zx", zx);
		data.putString("zf", zf);
		data.putString("lc", lc);
		data.putString("zl", zl);
		data.putString("ld", ld);
		data.putString("fh", fh);
		data.putString("hzs", hzss);
		data.putString("hzt", hzts);
		data.putString("hzw", hzws);
		String detail=cx1+zx1+zf+lc1+ld1+fh1+hzss1+hzts1+hzws1;
		data.putString("detail", detail);
		intent.putExtras(data);
        setResult(12, intent);
        finish();
}else if(Integer.parseInt(ft.getText().toString())>Integer.parseInt(ft1.getText().toString()))
{
	Toast.makeText(DetailHouseActivity.this, "楼层不能高于总的楼层", 1).show();
	}else{


Bundle data=new Bundle();
if(textaim.getText().toString().equals("不限")){
	cx="";

}else{
	cx=textaim.getText().toString();
	cx1=textaim.getText().toString()+",";
}
	if(textredoing.getText().toString().equals("不限")){
		zx="";
	
	}else{
		zx=textredoing.getText().toString();
		zx1=textredoing.getText().toString()+",";
	}
	zf=textpay.getText().toString();
	if("".equals(ft.getText().toString()) ){
		lc="";
		
	}else{
		lc=ft.getText().toString();
		lc1="第"+ft.getText().toString()+"层,";
	}
	if("".equals(ft1.getText().toString())){
		zl="";
		
	}else{
		zl=ft1.getText().toString();
		zl1=ft1.getText().toString()+",";
	}
		if("".equals(ft2.getText().toString())){
			ld="";
		
		}	else{
			ld=ft2.getText().toString();
			ld1="第"+ft2.getText().toString()+"栋,";
		}
		
		if("".equals(ft3.getText().toString())){
			fh="";
			
		}	else{
			fh=ft3.getText().toString();
			fh1="第"+ft3.getText().toString()+"室,";
		}
		if("".equals(hzs.getText().toString())){
			hzss="";
		}else{
			hzss=hzs.getText().toString();
			hzss1=hzs.getText().toString()+"室";
		}
		if("".equals(hzt.getText().toString())){
			hzts="";
		}else{
			hzts=hzt.getText().toString();
			hzts1=hzt.getText().toString()+"厅";
		}
		if("".equals(hzw.getText().toString())){
			hzws="";
		}else{
			hzws=hzw.getText().toString();
			hzws1=hzw.getText().toString()+"卫,";
		}
		data.putBoolean("isfirst", false);
		data.putString("cx", cx);
		data.putString("zx", zx);
		data.putString("zf", zf);
		data.putString("lc", lc);
		data.putString("zl", zl);
		data.putString("ld", ld);
		data.putString("fh", fh);
		data.putString("hzs", hzss);
		data.putString("hzt", hzts);
		data.putString("hzw", hzws);
		String detail1=cx1+zx1+zf+lc1+ld1+fh1+hzss1+hzts1+hzws1;
		data.putString("detail", detail1);
		intent.putExtras(data);
		 setResult(12, intent);
finish();
	}
}
}
