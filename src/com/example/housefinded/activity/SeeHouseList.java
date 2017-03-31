package com.example.housefinded.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.Tools.MyHttpUtil;
import com.example.Tools.MyHttpUtil.MyHttpCallback;
import com.example.housefinded.R;
import com.example.housefinded.adapter.NewHouseItemAdapter;
import com.example.housefinded.bean.HouseBean;
import com.example.housefinded.net.HttpUrl;
import com.example.javabean.House;
import com.example.javabean.SecondHouseBean;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class SeeHouseList extends Activity {
	private ImageView iv_back;
	private List<House> hList = new ArrayList<House>();
	private NewHouseItemAdapter adapter;
	private ListView listview;
	private TextView house_tv;
	private List<String> hidList = new ArrayList<String>(); // 看过的房子id集合
	private MyHttpUtil mhu;
	private SeeHouseList act;
	private String url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.see_house_list);
		act = this;
		mhu = new MyHttpUtil(act, "正在拼命加载中...");
		hidList = (List<String>) getIntent().getSerializableExtra("houselist");
		System.out.println("hidList" + hidList);
		sendRequest();

		returnback();
	}

	private void sendRequest() {
		house_tv = (TextView) findViewById(R.id.house_tv);
		listview = (ListView) findViewById(R.id.seelist);
		RequestParams params = new RequestParams();
		if(hidList==null){
			return;
		}
		for (int i = 0; i < hidList.size(); i++) {
			url = HttpUrl.BASE_URL + "rest/secondHouse/get/" + hidList.get(i);

			mhu.send(HttpMethod.GET, url, params, new MyHttpCallback() {

				@Override
				public void onSuccessResult(String result) {
					// TODO Auto-generated method stub
					System.out.println("jieguo " + result);
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
					System.out.println("联网失败");
				}
			});
		}
	}

	private HouseBean houseBean;
	private SecondHouseBean bean;
	private List<House> data = new ArrayList<House>();

	protected void parseJson(String result) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		houseBean = gson.fromJson(result, HouseBean.class);
		if ("1".equals(houseBean.getStatus())) {

			bean = new SecondHouseBean();
			hList.add(houseBean.getData());
			data.removeAll(hList);
			data.addAll(hList);
			adapter = new NewHouseItemAdapter(data, this);
			listview.setAdapter(adapter);
			if (hList.size() > 0) {
				house_tv.setVisibility(View.GONE);
			}

			listview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(act,
							SecondHouseDetailsActivity.class);
					House house = data.get(arg2);
					Bundle bundle = new Bundle();
					bundle.putSerializable("house", house);
					intent.putExtras(bundle);
					startActivity(intent);
				}
			});

		}

	}

	private void returnback() {
		// TODO Auto-generated method stub
		iv_back = (ImageView) findViewById(R.id.iv_back);
		iv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

}
