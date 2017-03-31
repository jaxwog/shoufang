package com.example.housefinded.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.Tools.MyHttpUtil;
import com.example.Tools.MyHttpUtil.MyHttpCallback;
import com.example.housefinded.R;
import com.example.housefinded.PullToFresh.PullToRefreshBase;
import com.example.housefinded.PullToFresh.PullToRefreshListView;
import com.example.housefinded.PullToFresh.PullToRefreshBase.OnRefreshListener;
import com.example.housefinded.adapter.HouseSourceAdapter;
import com.example.housefinded.adapter.NewHouseItemAdapter;
import com.example.housefinded.bean.Agent;
import com.example.housefinded.db.SqlHelpCRUD;
import com.example.housefinded.net.HttpUrl;
import com.example.javabean.House;
import com.example.javabean.SecondHouseBean;
import com.google.gson.Gson;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class HouseSourceActivity extends Activity implements OnClickListener {

	private ImageView iv_back;
	private Agent agent;
	private String url;
	private int CURRENT_PAGE = 1;
	private int visibleItemCount = 10; // 当前窗口可见项总数
	private MyHttpUtil mhu;
	private HouseSourceActivity act;
	private SecondHouseBean secondHouseBean;
	private PullToRefreshListView houseList;
	private HouseSourceAdapter adapter;
	private TextView tv_title;
	private TextView tv_loading;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.house_source_activity);
		agent = (Agent) getIntent().getSerializableExtra("agent");
		act = this;
		mhu = new MyHttpUtil(act, "正在拼命加载中...");
		setOnClick();
		sendRequest();
	}

	private void sendRequest() {
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams();

		params.addBodyParameter("userId", agent.getId());
		params.addBodyParameter("pageSize", visibleItemCount + "");
		params.addBodyParameter("currentPage", CURRENT_PAGE + "");
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

	private List<House> data = new ArrayList<House>();
	private int totalPages;
	private List<House> dataSecont1 = new ArrayList<House>();

	protected void parseJson(String result) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		secondHouseBean = gson.fromJson(result, SecondHouseBean.class);
		if (secondHouseBean.getPage() == null) {
			return;
		}
		// 总房源数
		String totalRowsString = secondHouseBean.getPage().getTotalRows();
		totalPages = Integer.valueOf(totalRowsString);
		// listview
		houseList = (PullToRefreshListView) findViewById(R.id.house_list);
		data = secondHouseBean.getList();
		dataSecont1.addAll(data);
		// 共多少个房源
		tv_loading = (TextView) findViewById(R.id.tv_loading);
		tv_loading.setText("附近共有" + totalRowsString + "个房源");

		// 设置滚动加载可用
		houseList.setScrollLoadEnabled(true);
		// 设置上啦加载可用
		houseList.setPullLoadEnabled(true);
		// 设置下拉刷新可用
		houseList.setPullRefreshEnabled(false);
		// 刚进来在设置下拉按钮不能用
		houseList.setHasMoreData(false);
		// 刚进来在设置下拉按钮可用
		houseList.setHasMoreData(true);
		// 下拉刷新的listview设置adapter
		if (CURRENT_PAGE > 1) {// 根据currentPage判断是不是第一页；大于1不是
			adapter.notifyDataSetChanged();

		} else {// 是第一页
			adapter = new HouseSourceAdapter(dataSecont1,
					HouseSourceActivity.this);
			houseList.getRefreshableView().setAdapter(adapter);
		}

		houseList.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				Toast.makeText(act, "下拉刷新", 0).show();

			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				CURRENT_PAGE = CURRENT_PAGE + 1;
				// 判断是否有更多的数据,如果当前页大于总页数就是没有更多了
				if (CURRENT_PAGE > totalPages) {
					houseList.setHasMoreData(false);
					Toast.makeText(act, "没有更多房源了", 0).show();
				} else {// 如果有更多数据，请求下一页
					sendRequest();
					if (adapter == null) {
						adapter = new HouseSourceAdapter(dataSecont1,
								HouseSourceActivity.this);
						houseList.getRefreshableView().setAdapter(adapter);
					} else {
						adapter.notifyDataSetChanged();
					}
					houseList.setHasMoreData(true);

				}
				// 下拉刷新完成
				// houseList.onPullDownRefreshComplete();
				// 设置加载更多完成
				houseList.onPullUpRefreshComplete();

			}

		});

		houseList.getRefreshableView().setOnItemClickListener(
				new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(act,
								SecondHouseDetailsActivity.class);
						House house = secondHouseBean.getList().get(arg2);
						// hList.add(house);
						// house中issee属性 1代表查看 0代表没有查看
						house.setIssee("1");

						Bundle bundle = new Bundle();
						bundle.putSerializable("house", house);
						intent.putExtras(bundle);
						startActivity(intent);
					}
				});

	}

	private void setOnClick() {
		// TODO Auto-generated method stub
		iv_back = (ImageView) findViewById(R.id.iv_back);
		tv_title=(TextView) findViewById(R.id.tv_title);
		tv_title.setText(agent.getName()+"的二手房源");

		iv_back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			break;

		default:
			break;
		}
	}

}
