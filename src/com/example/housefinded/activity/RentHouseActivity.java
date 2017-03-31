package com.example.housefinded.activity;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.example.Tools.MyHttpUtil;
import com.example.Tools.SharePreferences;
import com.example.Tools.MyHttpUtil.MyHttpCallback;
import com.example.housefinded.R;

import com.example.housefinded.PullToFresh.PullToRefreshBase;
import com.example.housefinded.PullToFresh.PullToRefreshListView;
import com.example.housefinded.PullToFresh.PullToRefreshBase.OnRefreshListener;
import com.example.housefinded.adapter.MyPopDoorModeAdapter;
import com.example.housefinded.adapter.MyPopMoreAdapter;
import com.example.housefinded.adapter.MyPopNearAdapter;
import com.example.housefinded.adapter.MyPopTotalAdapter;
import com.example.housefinded.adapter.NewHouseItemAdapter;
import com.example.housefinded.adapter.RentHouseAdapter;

import com.example.housefinded.bean.AreaBean;
import com.example.housefinded.bean.ProductBean;
import com.example.housefinded.net.HttpUrl;
import com.example.javabean.House;
import com.example.javabean.RentHouse;
import com.example.javabean.RentHouseBean;
import com.example.javabean.SecondHouseBean;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class RentHouseActivity extends Activity implements OnClickListener {
	private ImageView iv_back;
	private AutoCompleteTextView atv_search;
	private RentHouseActivity act;
	private RadioButton rb_mine;
	private RadioButton rb_agent;
	private RadioButton rb_sell;
	private LinearLayout ll_near, ll_total, ll_door_mode, ll_more;
	private TextView tv_near, tv_more, tv_door_mode, tv_total, tv_loading,
			tv_map, tv_releaser;
	private List<ProductBean> list = null;

	String ss_near[] = { "不限", "金水区", "中原区", "管城区", "二七区" };
	String ss_total[] = { "不限", "个人", "经纪人" };
	String ss_more[] = { "不限", "一室", "二室", "三室", "四室", "五室" };
	String ss_door[] = { "不限", "500-1000", "1000-2000", "2000-3000",
			"3000-5000", "5000-8000", "8000以上" };
	String url = HttpUrl.BASE_URL + "/rest/tenement/showByMobileClient";
	private MyPopNearAdapter adapter1 = null;
	private MyPopTotalAdapter adapter2 = null;
	private MyPopDoorModeAdapter adapter3 = null;
	private MyPopMoreAdapter adapter4 = null;

	private RentHouseBean rentHouseBean;
	private MyHttpUtil mhu;
	private PopupWindow popupwindow;
	private int CURRENT_PAGE = 1;
	private int visibleItemCount = 10; // 当前窗口可见项总数

	private String nearby;
	private String userTypes;
	private String userType;
	private String highPrice;
	private String lowPrice;
	private String houseType;
	private AreaBean areaBean;
	private String villageId;
	/**
	 * 手机屏幕宽度
	 */
	private int screenHeight;
	private WindowManager mWindowManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.rent_activity);
		act = this;
		villageId=getIntent().getStringExtra("villageId");
		mhu = new MyHttpUtil(this, "正在拼命加载中...");
		mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		screenHeight = mWindowManager.getDefaultDisplay().getHeight();
		areaBean = (AreaBean) getIntent().getSerializableExtra("areaBean");
		returnback();
		addSeek();
		addMine();
		sendReqest();
		setOnclick();
		addAgent();
		addRentalHouse();
	}

	private void addRentalHouse() {
		// TODO Auto-generated method stub
		rb_sell = (RadioButton) findViewById(R.id.rb_sell);
		rb_sell.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				if (SharePreferences.islogin(act)) {
					intent.setClass(act, RentalHouse.class);
					startActivity(intent);
				} else {
					intent.setClass(act, UserLogin.class);
					startActivity(intent);
				}
			}
		});
	}

	private void addAgent() {
		// TODO Auto-generated method stub
		rb_agent = (RadioButton) findViewById(R.id.rb_agent);
		rb_agent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(act, AgentActivity.class);
				startActivity(intent);
			}
		});
	}

	private void setOnclick() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		// 附近
		ll_near = (LinearLayout) findViewById(R.id.ll_near);
		tv_near = (TextView) findViewById(R.id.tv_near);
		ll_near.setOnClickListener(this);

		// 来源
		ll_total = (LinearLayout) findViewById(R.id.ll_total);
		tv_total = (TextView) findViewById(R.id.tv_total);
		ll_total.setOnClickListener(act);

		// 户型
		ll_door_mode = (LinearLayout) findViewById(R.id.ll_door_mode);
		tv_door_mode = (TextView) findViewById(R.id.tv_door_mode);
		ll_door_mode.setOnClickListener(act);
		// gengduo
		ll_more = (LinearLayout) findViewById(R.id.ll_more);
		tv_more = (TextView) findViewById(R.id.tv_more);
		ll_more.setOnClickListener(act);

		// 地图
		tv_map = (TextView) findViewById(R.id.tv_map);
		tv_map.setOnClickListener(this);
	}

	private PullToRefreshListView listview;
	private RentHouseAdapter adapter;

	private void sendReqest() {
		// TODO Auto-generated method stub
		RequestParams rq = new RequestParams();
		rq.addBodyParameter("pageSize", visibleItemCount + "");
		rq.addBodyParameter("currentPage", CURRENT_PAGE + "");
		rq.addBodyParameter("nearby", nearby);
		rq.addBodyParameter("userType", userType);
		rq.addBodyParameter("highPrice", highPrice);
		rq.addBodyParameter("lowPrice", lowPrice);
		rq.addBodyParameter("houseType", houseType);
		rq.addBodyParameter("release", 1 + "");
		if(villageId!=null && villageId!=""){
			rq.addBodyParameter("villageId",villageId);
		}
		mhu.send(HttpMethod.POST, url, rq, new MyHttpCallback() {

			@Override
			public void onSuccessResult(String result) {
				// TODO Auto-generated method stub
				parseJsonResult(result);
				System.out.println("结果" + result);

			}


 @Override
			public void onLoadingResult(long total, long current,
					boolean isUploading) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFailureResult(String msg) {
				// TODO Auto-generated method stub
				Toast.makeText(act, "联网失败，请检查网络...", 0).show();
			}
		});
	}

	private List<RentHouse> data = new ArrayList<RentHouse>();
	private int totalPages;
	private List<RentHouse> dataSecont = new ArrayList<RentHouse>();

	protected void parseJsonResult(String result) {
		// TODO Auto-generated method stub

		Gson gson = new Gson();
		rentHouseBean = gson.fromJson(result, RentHouseBean.class);
		if (rentHouseBean.getPage() == null) {
			return;
		} else {
			// 总页数
			String totalPagesString = rentHouseBean.getPage().getTotalPages();
			totalPages = Integer.valueOf(totalPagesString);

			listview = (PullToRefreshListView) findViewById(R.id.listview);
			data = rentHouseBean.getList();
			dataSecont.addAll(data);
			String ii = rentHouseBean.getPage().getTotalRows();
			// 共多少个房源
			tv_loading = (TextView) findViewById(R.id.tv_loading);
			tv_loading.setText("附近共有" + ii + "个房源");

			// 设置滚动加载可用
			listview.setScrollLoadEnabled(true);
			// 设置上啦加载可用
			listview.setPullLoadEnabled(true);
			// 设置下拉刷新可用
			listview.setPullRefreshEnabled(false);
			// 刚进来在设置下拉按钮不能用
			listview.setHasMoreData(false);
			// 刚进来在设置下拉按钮可用
			listview.setHasMoreData(true);
			// 下拉刷新的listview设置adapter

			if (CURRENT_PAGE > 1) {// 根据currentPage判断是不是第一页；大于1不是
				adapter.notifyDataSetChanged();

			} else {// 是第一页
				adapter = new RentHouseAdapter(dataSecont,
						RentHouseActivity.this);
				listview.getRefreshableView().setAdapter(adapter);
			}
			
			listview.setOnRefreshListener(new OnRefreshListener<ListView>() {

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
						listview.setHasMoreData(false);
						Toast.makeText(act, "没有更多房源了", 0).show();
					} else {// 如果有更多数据，请求下一页
						sendReqest();
						if (adapter == null) {
							adapter = new RentHouseAdapter(dataSecont,
									RentHouseActivity.this);
							listview.getRefreshableView().setAdapter(adapter);
						} else {
							adapter.notifyDataSetChanged();
						}
						listview.setHasMoreData(true);

					}
					// 下拉刷新完成
					// houseList.onPullDownRefreshComplete();
					// 设置加载更多完成
					listview.onPullUpRefreshComplete();
				}
			});

			listview.getRefreshableView().setOnItemClickListener(
					new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							// TODO Auto-generated method stub
							Intent intent = new Intent(act,
									RentHouseDetailsActivity.class);
							RentHouse rentHouse = data.get(arg2);
							Bundle bundle = new Bundle();
							bundle.putSerializable("rentHouse", rentHouse);
							intent.putExtras(bundle);
							startActivity(intent);
						}
					});
		}
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
		case R.id.ll_more:
			if (popupwindow != null && popupwindow.isShowing()) {
				popupwindow.dismiss();
				return;
			} else {
				initMorePop();
				popupwindow.showAsDropDown(ll_more, 0, 0);
			}
			break;
		case R.id.tv_map:
			try {
				Uri mUri = Uri.parse("geo:34.7483785,113.6995692?q=御玺大厦");
				Intent mIntent = new Intent(Intent.ACTION_VIEW, mUri);
				if (isInstallByread("com.baidu.BaiduMap")) {
					startActivity(mIntent);
				} else {
					Toast.makeText(act, "你没有安装百度地图", 0).show();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}

	private void initMorePop() {
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

		for (int i = 0; i < ss_more.length; i++) {
			ProductBean productBean = new ProductBean();
			productBean.setMore(ss_more[i]);
			productBean.setFlag(false);
			list.add(productBean);

		}
		adapter4 = new MyPopMoreAdapter(this, list);
		listview.setAdapter(adapter4);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				popupwindow.dismiss();
				dataSecont.removeAll(data);
				houseType = list.get(arg2).getMore();
				tv_more.setText(houseType);
				CURRENT_PAGE=1;
				sendReqest();
			}
		});
	}

	/**
	 * 判断是否安装目标应用
	 * 
	 * @param packageName
	 *            目标应用安装后的包名
	 * @return 是否已安装目标应用
	 */
	private boolean isInstallByread(String packageName) {
		return new File("/data/data/" + packageName).exists();
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
		adapter3 = new MyPopDoorModeAdapter(this, list);
		listview.setAdapter(adapter3);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				popupwindow.dismiss();
				dataSecont.removeAll(data);
				String[] price = list.get(arg2).getDoormode().split("-");
				if (arg2 == 0) {
					lowPrice = "";
					highPrice = "";
				} else if (arg2 > 0 && arg2 < ss_door.length - 1) {
					lowPrice = price[0];
					highPrice = price[1];
				} else if (arg2 == ss_door.length - 1) {
					lowPrice = list
							.get(arg2)
							.getDoormode()
							.substring(
									0,
									list.get(arg2).getDoormode()
											.lastIndexOf("以"));
					highPrice = "";
				}
				tv_door_mode.setText(list.get(arg2).getDoormode());
				CURRENT_PAGE=1;
				sendReqest();
			}
		});
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
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				popupwindow.dismiss();
				dataSecont.removeAll(data);
				userTypes = list.get(arg2).getToatl();
				tv_total.setText(userTypes);
				if (arg2 == 0) {
					userType = "";
				} else if (arg2 == 1) {
					userType = "1";
				} else if (arg2 == 2) {
					userType = "0";
				}
				CURRENT_PAGE=1;
				System.out.println("data"+data);
				System.out.println("dataSecont"+dataSecont);
				sendReqest();
			}
		});
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

}
