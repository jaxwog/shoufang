package com.example.housefinded.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.example.Tools.MyHttpUtil;
import com.example.Tools.SharePreferences;
import com.example.Tools.MyHttpUtil.MyHttpCallback;
import com.example.housefinded.R;
import com.example.housefinded.PullToFresh.PullToRefreshBase;
import com.example.housefinded.PullToFresh.PullToRefreshBase.OnRefreshListener;
import com.example.housefinded.PullToFresh.PullToRefreshListView;
import com.example.housefinded.adapter.MyPopDoorModeAdapter;
import com.example.housefinded.adapter.MyPopNearAdapter;
import com.example.housefinded.adapter.MyPopTotalAdapter;
import com.example.housefinded.adapter.NewHouseItemAdapter;
import com.example.housefinded.bean.ProductBean;
import com.example.housefinded.db.SqlHelpCRUD;
import com.example.housefinded.net.HttpUrl;
import com.example.javabean.House;
import com.example.javabean.SecondHouseBean;
import com.google.gson.Gson;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.format.DateUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class NewHouseActivity extends Activity implements OnClickListener,
		Callback {
	private ImageView iv_back;
	private AutoCompleteTextView atv_search;
	private NewHouseActivity act;
	private RadioButton rb_mine, rb_list;
	private RadioButton rb_sell;
	private LinearLayout ll_near, ll_total, ll_door_mode, ll_more;
	private TextView tv_near, tv_more, tv_door_mode, tv_total, tv_loading;
	private List<ProductBean> list = null;

	String ss_near[] = { "不限", "金水区", "中原区", "管城区", "二七区" };
	String ss_total[] = { "不限", "30万以下", "30-40万", "40-50万", "50-60万",
			"60-80万", "100万以上" };
	String ss_door[] = { "不限", "一居", "二居", "三居", "四居", "五居" };
	private MyPopNearAdapter adapter1 = null;
	private MyPopTotalAdapter adapter2 = null;
	private MyPopDoorModeAdapter adapter3 = null;
	private Handler handler = null;
	private PullToRefreshListView houseList;
	private boolean isLoading;

	private SecondHouseBean secondHouseBean;

	private NewHouseItemAdapter adapter;
	private int CURRENT_PAGE = 1;
	private int visibleLastIndex = 0; // 最后的可视项索引
	private int visibleItemCount = 10; // 当前窗口可见项总数
	private Button loadMore;
	private MyHttpUtil mhu;
	private PopupWindow popupwindow;
	String url = HttpUrl.BASE_URL + "rest/secondHouse/getList";
	private List<String> hList = new ArrayList<String>();
	private SqlHelpCRUD sqlCRUD;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.newhouse_activity);

		act = this;
		mhu = new MyHttpUtil(this, "正在拼命加载中...");
		handler = new Handler(this);
		sqlCRUD = new SqlHelpCRUD(this);
		returnback();
		addSeek();
		addSeeHouseList();
		addMine();
		addSellHouse();
		sendReqest();
		setOnclick();
	}

	private void addSeeHouseList() {
		// TODO Auto-generated method stub
		rb_list = (RadioButton) findViewById(R.id.rb_list);
		rb_list.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(act, SeeHouseList.class);
				hList = sqlCRUD.getAllSeeHouse();
				intent.putExtra("houselist", (Serializable) hList);
				startActivity(intent);
			}
		});
	}

	private void setOnclick() {
		// TODO Auto-generated method stub
		// 附近
		ll_near = (LinearLayout) findViewById(R.id.ll_near);
		tv_near = (TextView) findViewById(R.id.tv_near);
		ll_near.setOnClickListener(this);

		// 总价
		ll_total = (LinearLayout) findViewById(R.id.ll_total);
		tv_total = (TextView) findViewById(R.id.tv_total);
		ll_total.setOnClickListener(act);

		// 户型
		ll_door_mode = (LinearLayout) findViewById(R.id.ll_door_mode);
		tv_door_mode = (TextView) findViewById(R.id.tv_door_mode);
		ll_door_mode.setOnClickListener(act);

	}

	private void sendReqest() {
		// TODO Auto-generated method stub

		RequestParams rq = new RequestParams();
		rq.addBodyParameter("pageSize", visibleItemCount + "");
		rq.addBodyParameter("currentPage", CURRENT_PAGE + "");

		rq.addBodyParameter("release", 1 + "");

		mhu.send(HttpMethod.POST, url, rq, new MyHttpCallback() {

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
				Toast.makeText(act, "联网失败", 0).show();
			}
		});

	}

	private List<House> data = new ArrayList<House>();
	private int totalPages;
	private List<House> dataSecont = new ArrayList<House>();

	protected void parseJson(String result) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		secondHouseBean = gson.fromJson(result, SecondHouseBean.class);
		if (secondHouseBean.getPage() == null) {
			return;
		}
		
		// 总页数
		String totalPagesString = secondHouseBean.getPage().getTotalPages();
		totalPages = Integer.valueOf(totalPagesString);

		// listView
		houseList = (PullToRefreshListView) findViewById(R.id.house_list);
		data = secondHouseBean.getList();
		dataSecont.addAll(data);
		int ii = dataSecont.size();
		// 共多少个房源
		tv_loading = (TextView) findViewById(R.id.tv_loading);
		tv_loading.setText("附近共有" + ii + "个房源");
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
			adapter = new NewHouseItemAdapter(dataSecont, NewHouseActivity.this);
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
					sendReqest();
					if (adapter == null) {
						adapter = new NewHouseItemAdapter(dataSecont,
								NewHouseActivity.this);
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
						house.setIscol(sqlCRUD.getHouseIscol(house.getId()));
						sqlCRUD.insertOrUpdate(house);

						Bundle bundle = new Bundle();
						bundle.putSerializable("house", house);
						intent.putExtras(bundle);
						startActivity(intent);
					}
				});

	}

	private void addSellHouse() {
		// TODO Auto-generated method stub
		rb_sell = (RadioButton) findViewById(R.id.rb_sell);
		rb_sell.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				if (SharePreferences.islogin(act)) {
					intent.setClass(act, SellHouseActivity.class);
					startActivity(intent);
				} else {
					intent.setClass(act, UserLogin.class);
					startActivity(intent);
				}
			}
		});
	}

	private void addMine() {
		// TODO Auto-generated method stub
		rb_mine = (RadioButton) findViewById(R.id.rb_mine);
		rb_mine.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(act, MineActivity.class);
				startActivity(intent);
			}
		});
	}

	private void addSeek() {
		// TODO Auto-generated method stub
		atv_search = (AutoCompleteTextView) findViewById(R.id.atv_search);
		atv_search.setFocusable(false);
		atv_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(act, SeekActivity.class);
				startActivity(intent);
			}
		});
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ll_near:
			if (popupwindow != null && popupwindow.isShowing()) {
				popupwindow.dismiss();
				return;
			} else {
				initNearPop();
				popupwindow.showAsDropDown(ll_near, 0, 0);
			}
			break;
		case R.id.ll_total:
			if (popupwindow != null && popupwindow.isShowing()) {
				popupwindow.dismiss();
				return;
			} else {
				initToatlPop();
				popupwindow.showAsDropDown(ll_total, 0, 0);
			}
			break;
		case R.id.ll_door_mode:
			if (popupwindow != null && popupwindow.isShowing()) {
				popupwindow.dismiss();
				return;
			} else {
				initDoorPop();
				popupwindow.showAsDropDown(ll_door_mode, 0, 0);
			}
			break;
		default:
			break;
		}
	}

	private void initDoorPop() {
		// TODO Auto-generated method stub
		View popView = getLayoutInflater().inflate(R.layout.pop_activity, null,
				false);

		popupwindow = new PopupWindow(popView, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, true);

		popupwindow.setOutsideTouchable(true);

		popView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (popupwindow != null && popupwindow.isShowing()) {
					popupwindow.dismiss();
					popupwindow = null;
				}
				return false;
			}
		});

		ListView listview = (ListView) popView.findViewById(R.id.lv_near);
		list = new ArrayList<ProductBean>();

		for (int i = 0; i < ss_door.length; i++) {
			ProductBean productBean = new ProductBean();
			productBean.setDoormode(ss_door[i]);

			productBean.setFlag(false);
			list.add(productBean);

		}
		adapter3 = new MyPopDoorModeAdapter(this,  list);
		listview.setAdapter(adapter3);
	}

	private void initToatlPop() {
		// TODO Auto-generated method stub
		View popView = getLayoutInflater().inflate(R.layout.pop_activity, null,
				false);

		popupwindow = new PopupWindow(popView, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, true);

		popupwindow.setOutsideTouchable(true);

		popView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (popupwindow != null && popupwindow.isShowing()) {
					popupwindow.dismiss();
					popupwindow = null;
				}
				return false;
			}
		});

		ListView listview = (ListView) popView.findViewById(R.id.lv_near);
		list = new ArrayList<ProductBean>();

		for (int i = 0; i < ss_total.length; i++) {
			ProductBean productBean = new ProductBean();
			productBean.setToatl(ss_total[i]);

			productBean.setFlag(false);
			list.add(productBean);

		}
		adapter2 = new MyPopTotalAdapter(this, list);
		listview.setAdapter(adapter2);

	}

	private void initNearPop() {
		// TODO Auto-generated method stub

		View popView = getLayoutInflater().inflate(R.layout.pop_activity, null,
				false);

		popupwindow = new PopupWindow(popView, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, true);

		popupwindow.setOutsideTouchable(true);

		popView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (popupwindow != null && popupwindow.isShowing()) {
					popupwindow.dismiss();
					popupwindow = null;
				}
				return false;
			}
		});

		ListView listview = (ListView) popView.findViewById(R.id.lv_near);
		list = new ArrayList<ProductBean>();

		for (int i = 0; i < ss_near.length; i++) {
			ProductBean productBean = new ProductBean();
			productBean.setNear(ss_near[i]);

			productBean.setFlag(false);
			list.add(productBean);

		}
		adapter1 = new MyPopNearAdapter(this, list);
		listview.setAdapter(adapter1);

	}

	@Override
	public boolean handleMessage(Message message) {
		Bundle data = message.getData();
		switch (message.what) {
		case 1:
			int position1 = data.getInt("nearIndex");
			tv_near.setText(list.get(position1).getNear());
			// tv_total.setText(list.get(position).getToatl());
			popupwindow.dismiss();
			break;
		case 2:
			int position2 = data.getInt("totalIndex");
			// tv_near.setText(list.get(position2).getNear());
			tv_total.setText(list.get(position2).getToatl());
			popupwindow.dismiss();
			break;
		case 3:
			int position3 = data.getInt("doorIndex");
			// tv_near.setText(list.get(position2).getNear());
			tv_door_mode.setText(list.get(position3).getDoormode());
			popupwindow.dismiss();
			break;
		}
		return false;
	}

}
