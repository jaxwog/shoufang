package com.example.housefinded.activity;

import com.example.Tools.MyHttpUtil;
import com.example.Tools.MyHttpUtil.MyHttpCallback;
import com.example.housefinded.R;
import com.example.housefinded.bean.Agent;
import com.example.housefinded.net.HttpUrl;
import com.example.housefinded.view.MyListView;
import com.example.javabean.House;
import com.example.javabean.SecondHouseBean;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class AgentDetailActivity extends Activity implements OnClickListener {
	private ImageView iv_back, iv_tou;
	private TextView tv_name, tv_com, tv_severce, tv_time, tv_name_house,
			tv_housenum;
	private MyListView listview;
	private String url;
	private MyHttpUtil mhu;
	private Agent agent;
	private RelativeLayout rl_house_source;
	private AgentDetailActivity act;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.agent_details_activity);
		agent = (Agent) getIntent().getSerializableExtra("agent");
		act = this;
		mhu = new MyHttpUtil(act, "正在拼命加载中...");
		listview = (MyListView) findViewById(R.id.listview);
		
		sendRequest();
		setOnClick();
		setText();
		setListViewHeight();
	}
		private void sendRequest() {
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams();
		params.addBodyParameter("userId", agent.getId());
		params.addBodyParameter("pageSize", 5 + "");
		params.addBodyParameter("currentPage", 1 + "");
		url = HttpUrl.BASE_URL + "rest/secondHouse/getListByUserId";
		mhu.send(HttpMethod.POST, url, params, new MyHttpCallback() {

			@Override
			public void onSuccessResult(String result) {
				// TODO Auto-generated method stub
				System.out.println("经纪人房源" + result);
				parseJson(result);
			}

			@Override
			public void onLoadingResult(long total, long current,
					boolean isUploading) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFailureResult(String msg) {
				// TODO Auto-generated method stub

			}
		});

	}

	private SecondHouseBean secondHouseBean;

	protected void parseJson(String result) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		secondHouseBean = gson.fromJson(result, SecondHouseBean.class);
		// 总页数
		String totalRowsString = secondHouseBean.getPage().getTotalRows();
		tv_housenum = (TextView) findViewById(R.id.tv_housenum);
		tv_housenum.setText(totalRowsString+"套");
		
		listview.setAdapter(new MyHouseItemAdapter(secondHouseBean,
				AgentDetailActivity.this));
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(AgentDetailActivity.this,
						SecondHouseDetailsActivity.class);
				House house = secondHouseBean.getList().get(arg2);
				Bundle bundle = new Bundle();
				bundle.putSerializable("house", house);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}

	private void setText() {
		// TODO Auto-generated method stub
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_com = (TextView) findViewById(R.id.tv_com);
		tv_severce = (TextView) findViewById(R.id.tv_severce);
		tv_time = (TextView) findViewById(R.id.tv_time);
		tv_name_house = (TextView) findViewById(R.id.tv_name_house);
		tv_housenum = (TextView) findViewById(R.id.tv_housenum);
		iv_tou = (ImageView) findViewById(R.id.iv_tou);

		BitmapUtils bitmapUtils = new BitmapUtils(act);
		bitmapUtils.display(iv_tou, HttpUrl.BASE_URL + agent.getHeadImg());

		tv_name.setText(agent.getName());
		tv_com.setText(agent.getCompanyName());
		tv_severce.setText(agent.getDistrict());
		tv_time.setText(agent.getCreateDate());
		tv_name_house.setText(agent.getName() + "的二手房源");

	}

	private void setOnClick() {
		// TODO Auto-generated method stub
		iv_back = (ImageView) findViewById(R.id.iv_back);
		rl_house_source = (RelativeLayout) findViewById(R.id.rl_house_source);

		iv_back.setOnClickListener(this);
		rl_house_source.setOnClickListener(this);
	}
	private void setListViewHeight() {
		// TODO Auto-generated method stub
		ListAdapter listAdapter = listview.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listview);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listview.getLayoutParams();
		params.height = totalHeight
				+ (listview.getDividerHeight() * (listAdapter.getCount() - 1));
		((MarginLayoutParams) params).setMargins(10, 10, 10, 10);
		listview.setLayoutParams(params);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			break;
		case R.id.rl_house_source:
			Intent intent = new Intent(act, HouseSourceActivity.class);

			Bundle bundle = new Bundle();
			bundle.putSerializable("agent", agent);
			intent.putExtras(bundle);
			startActivity(intent);
			break;

		default:
			break;
		}
	}

}
