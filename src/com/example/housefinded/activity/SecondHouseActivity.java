package com.example.housefinded.activity;

import java.io.File;
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
import com.example.housefinded.adapter.MyPopMoreAdapter;
import com.example.housefinded.adapter.MyPopNearAdapter;
import com.example.housefinded.adapter.MyPopTotalAdapter;
import com.example.housefinded.adapter.NewHouseItemAdapter;
import com.example.housefinded.bean.AreaBean;
import com.example.housefinded.bean.ProductBean;
import com.example.housefinded.db.SqlHelpCRUD;
import com.example.housefinded.net.HttpUrl;
import com.example.javabean.House;
import com.example.javabean.SecondHouseBean;

import com.google.gson.Gson;

import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.format.DateUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnTouchListener;

import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class SecondHouseActivity extends Activity implements OnClickListener {
	private ImageView iv_back;
	private AutoCompleteTextView atv_search;
	private SecondHouseActivity act;
	private RadioButton rb_mine, rb_list;
	private RadioButton rb_sell;
	private LinearLayout ll_near, ll_total, ll_door_mode, ll_more;
	private TextView tv_near, tv_more, tv_door_mode, tv_total, tv_loading,
			tv_map;
	private List<ProductBean> list = null;

	String ss_total[] = { "不限", "30万以下", "30-40万", "40-50万", "50-60万",
			"60-80万", "100万以上" };
	String ss_door[] = { "不限", "一室", "二室", "三室", "四室", "五室" };
	String ss_more[] = { "不限", "0-50m²", "50-80m²", "80-100m²", "100-130m²",
			"130-150m²", "150-200m²", "200m²以上" };
	private MyPopNearAdapter adapter1 = null;
	private MyPopTotalAdapter adapter2 = null;
	private MyPopDoorModeAdapter adapter3 = null;
	private MyPopMoreAdapter adapter4 = null;
	private PullToRefreshListView houseList;
	private boolean isLoading;

	private SecondHouseBean secondHouseBean;
	/**
	 * 手机屏幕宽度
	 */
	private int screenHeight;
	private WindowManager mWindowManager;
	private NewHouseItemAdapter adapter;
	private int CURRENT_PAGE = 1;
	private int visibleItemCount = 10; // 当前窗口可见项总数
	private MyHttpUtil mhu;
	private PopupWindow popupwindow;
	String url = HttpUrl.BASE_URL + "rest/secondHouse/getList";
	private List<String> hList = new ArrayList<String>();
	private SqlHelpCRUD sqlCRUD;
	private Intent intent;

	private String title;
	private String nearby;
	private String highPrice;
	private String lowPrice;
	private String houseType;
	private String highHouseArea;
	private String lowHouseArea;
	private AreaBean areaBean;
	private String villageId;//小区id

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.second_activity);
		act = this;
		villageId=getIntent().getStringExtra("villageId");
		mhu = new MyHttpUtil(this, "正在拼命加载中...");
		sqlCRUD = new SqlHelpCRUD(this);
		intent = new Intent();
		mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		screenHeight = mWindowManager.getDefaultDisplay().getHeight();
		areaBean = (AreaBean) getIntent().getSerializableExtra("areaBean");
		title = getIntent().getStringExtra("title");

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
		// 面积
		ll_more = (LinearLayout) findViewById(R.id.ll_more);
		tv_more = (TextView) findViewById(R.id.tv_more);
		ll_more.setOnClickListener(act);

		// 地图
		tv_map = (TextView) findViewById(R.id.tv_map);
		tv_map.setOnClickListener(this);
	}

	private void sendReqest() {
		// TODO Auto-generated method stub

		RequestParams rq = new RequestParams();
		
		rq.addBodyParameter("pageSize", visibleItemCount + "");
		rq.addBodyParameter("currentPage", CURRENT_PAGE + "");
		rq.addBodyParameter("title", title);
		rq.addBodyParameter("nearby", nearby);
		rq.addBodyParameter("highPrice", highPrice);
		rq.addBodyParameter("lowPrice", lowPrice);
		rq.addBodyParameter("houseType", houseType);
		rq.addBodyParameter("highHouseArea", highHouseArea);
		rq.addBodyParameter("lowHouseArea", lowHouseArea);
		rq.addBodyParameter("release", 1 + "");
		if(villageId!=null && villageId !=""){
			rq.addBodyParameter("villageId",villageId);	
		}
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

		// listview
		houseList = (PullToRefreshListView) findViewById(R.id.house_list);
		data = secondHouseBean.getList();
		dataSecont.addAll(data);
		int ii = dataSecont.size() * totalPages;

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
			adapter = new NewHouseItemAdapter(dataSecont,
					SecondHouseActivity.this);
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
								SecondHouseActivity.this);
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
						House house = data.get(arg2);
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
				intent.setClass(act, MineActivity.class);
				startActivity(intent);
			}
		});
	}

	private void addSeek() {
		// TODO Auto-generated method stub
		atv_search = (AutoCompleteTextView) findViewById(R.id.atv_search);
		atv_search.setText(title);
		atv_search.setFocusable(false);
		atv_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				intent.setClass(act, SeekActivity.class);
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
				String[] area = list.get(arg2).getMore().split("-");
				if (arg2 == 0) {
					lowHouseArea = "";
					highHouseArea = "";
				} else if (arg2 > 0 && arg2 < ss_more.length - 1) {
					lowHouseArea = area[0];
					highHouseArea = area[1].substring(0,
							area[1].lastIndexOf("m²"));
				} else if (arg2 == ss_more.length - 1) {
					lowHouseArea = list
							.get(arg2)
							.getMore()
							.substring(0,
									list.get(arg2).getMore().lastIndexOf("m²"));
					highHouseArea = "";
				}
				tv_more.setText(list.get(arg2).getMore());
				dataSecont.removeAll(data);
				CURRENT_PAGE = 1;
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
		popupwindow.setOutsideTouchable(true);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				popupwindow.dismiss();
				houseType = list.get(arg2).getDoormode();
				tv_door_mode.setText(houseType);
				dataSecont.removeAll(data);
				CURRENT_PAGE = 1;
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
		listview.setFocusable(true);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				popupwindow.dismiss();
				String[] price = list.get(arg2).getToatl().split("-");
				if (arg2 == 0) {
					lowPrice = "";
					highPrice = "";
				} else if (arg2 == 1) {
					lowPrice = "0";
					highPrice = list.get(arg2).getToatl().substring(0, 2);
				} else if (arg2 > 1 && arg2 < ss_total.length - 1) {
					lowPrice = price[0];
					highPrice = price[1].substring(0, price[1].lastIndexOf("万"));
				} else if (arg2 == ss_total.length - 1) {
					lowPrice = list
							.get(arg2)
							.getToatl()
							.substring(0,
									list.get(arg2).getToatl().lastIndexOf("万"));
					highPrice = "";
				}
				tv_total.setText(list.get(arg2).getToatl());
				dataSecont.removeAll(data);
				CURRENT_PAGE = 1;
				sendReqest();
			}
		});
	}

	private void initNearPop() {
		// TODO Auto-generated method stub

		View popView = getLayoutInflater().inflate(R.layout.pop_activity, null,
				false);

		popupwindow = new PopupWindow(popView, LayoutParams.MATCH_PARENT,
				2 * screenHeight / 3, true);

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

		for (int i = 0; i < areaBean.getData().size() + 1; i++) {
			ProductBean productBean = new ProductBean();
			if (i == 0) {
				productBean.setNear("不限");
			} else {
				productBean.setNear(areaBean.getData().get(i - 1).getName());
			}

			productBean.setFlag(false);
			list.add(productBean);

		}
		adapter1 = new MyPopNearAdapter(this, list);
		listview.setAdapter(adapter1);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				popupwindow.dismiss();
				if (arg2 == 0) {
					nearby = "";
					tv_near.setText("不限");
				} else {
					nearby = list.get(arg2).getNear();
					tv_near.setText(nearby);
				}
				dataSecont.removeAll(data);
				CURRENT_PAGE = 1;
				sendReqest();
			}
		});
	}

}
