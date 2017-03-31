package com.example.housefinded.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.housefinded.R;
import com.example.housefinded.adapter.ListlixiAdapt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ShangdaiAvtivity extends Activity implements OnClickListener{
	private RelativeLayout layout;
	private TextView title;
	private Button bb;
	private ListView listitem;
	private EditText edt;
	ImageView back;
	//商业贷款    基准利率5.15%  1.5倍（5.66%） 85折(4.38%) 7折(3.60%)
	//公积金贷款     基准利率3.25%  1.5倍（3.58%） 85折(2.76%) 7折(2.28%)
	private String []gridtitle={"基准利率","基准利率的1.5倍","基准利率的85折",
			"基准利率的7折"
			};
	private Double []glixi={3.25,3.58,2.76,2.28};
	private Double []slixi={5.15,5.66,4.38,3.60};
private ListlixiAdapt adapt;
private Map<String, Object> map;
private List<Map<String, Object>> listItems;
private int iswho;

Intent intent;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.shangdai_activity);
	intent =getIntent();
	iswho=intent.getIntExtra("key", 0);
	initview();
	initdata();
     adapt=new ListlixiAdapt(ShangdaiAvtivity.this, listItems);
	listitem.setAdapter(adapt);
	listitem.setOnItemClickListener(new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Bundle bundle=new Bundle();
			if(iswho==2){
			bundle.putString("lixi", slixi[position]+"");
			}else{
				bundle.putString("lixi", glixi[position]+"");
			}
			bundle.putString("position", position+"");
			intent.putExtras(bundle);
			setResult(2, intent);
			finish();
		}
	});
}
public void initview(){
	layout=(RelativeLayout)findViewById(R.id.shangdaititle);
	back=(ImageView)layout.findViewById(R.id.btnback);
	bb=(Button)layout.findViewById(R.id.btnswitch);
	title=(TextView)layout.findViewById(R.id.titlename);
	title.setText("商贷利率");
	bb.setVisibility(View.GONE);
	listitem=(ListView)findViewById(R.id.shangdai_list);
	edt=(EditText)findViewById(R.id.shangdai_edt);
	back.setOnClickListener(this);
	edt.setOnClickListener(this);
	map=new HashMap<String, Object>();
	listItems=new ArrayList<Map<String,Object>>();
}
@Override
public void onClick(View v) {
	switch (v.getId()) {
	case R.id.btnback:
		Bundle bundle=new Bundle();
		bundle.putString("lixi", edt.getText().toString()+"");
		bundle.putString("position","8");
		intent.putExtras(bundle);
		setResult(2, intent);
		finish();
		break;
	default:
		break;
	}
	
}
public void initdata(){
	for (int i = 0; i < gridtitle.length; i++) {
		// mapt要在for循环中产生对象，这样就不会产生覆盖的现象了
		map = new HashMap<String, Object>();
		map.put("title1", gridtitle[i]);
		listItems.add(map);
	}

	edt.setText(intent.getDoubleExtra("lilv", 0.0)+"");


}
}
