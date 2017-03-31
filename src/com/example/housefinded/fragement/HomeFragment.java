package com.example.housefinded.fragement;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.example.Tools.CacheUtils;
import com.example.Tools.MyHttpUtil;
import com.example.Tools.SharePreferences;
import com.example.Tools.MyHttpUtil.MyHttpCallback;
import com.example.housefinded.MainActivity;
import com.example.housefinded.R;
import com.example.housefinded.activity.ActionActivity;
import com.example.housefinded.activity.CityList;
import com.example.housefinded.activity.InvestAcitivity;
import com.example.housefinded.activity.LoanActivity;
import com.example.housefinded.activity.MortgageActivvity;
import com.example.housefinded.activity.MortgageRequestActivity;
import com.example.housefinded.activity.MyHouseItemAdapter;
import com.example.housefinded.activity.NewHouseActivity;
import com.example.housefinded.activity.RentHouseActivity;
import com.example.housefinded.activity.RentalHouse;
import com.example.housefinded.activity.SecondHouseActivity;
import com.example.housefinded.activity.SecondHouseDetailsActivity;
import com.example.housefinded.activity.SeekActivity;
import com.example.housefinded.activity.SellHouseActivity;
import com.example.housefinded.activity.TransferRequestActivity;
import com.example.housefinded.activity.UserLogin;
import com.example.housefinded.bean.AreaBean;
import com.example.housefinded.net.HttpUrl;
import com.example.housefinded.view.MyListView;
import com.example.javabean.House;
import com.example.javabean.SecondHouseBean;
import com.google.gson.Gson;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeFragment extends BaseFragement {
	private TextView tv_location;
	private LocationClient mLocationClient;
	public MyLocationListenner myListenner = new MyLocationListenner();
	private AutoCompleteTextView actv;
	private LinearLayout ll_second_house;
	private LinearLayout ll_rent_house, ll_help_rent, ll_sell_house,
			ll_transfer, ll_mortgage;
	private LinearLayout ll_new_house, ll_loan, ll_invest, Mortgag_button;
	private MyHttpUtil mhu;
	private LinearLayout ll_action;
	String secondhouse_url = HttpUrl.BASE_URL + "rest/secondHouse/getList";
	String near_url = HttpUrl.BASE_URL + "rest/Area/query";

	private SecondHouseBean secondHouseBean;
	private MyListView listview;

	@Override
	public View initView() {
		View view = View.inflate(getActivity(), R.layout.frame_home, null);

		tv_location = (TextView) view.findViewById(R.id.tv_location);
		tv_location.setText(MainActivity.city == "" ? "郑州市" : MainActivity.city);
		mhu = new MyHttpUtil(getActivity(), "正在加载...");
		listview = (MyListView) view.findViewById(R.id.listview);
		addSeek(view);
		setListViewHeight(listview);
		addLocation(view);
		addAction(view);
		addSecondHouse(view);
		addRentHouse(view);
		addNewHouse(view);
		addLoan(view);
		addInvest(view);
		addRentHelp(view);
		addSendRequest(view);
		addHouseCal(view);
		addSellHouse(view);
		addMortgage(view);
		addTransfer(view);
		return view;
	}

	private void addMortgage(View view) {
		// TODO Auto-generated method stub
		ll_mortgage = (LinearLayout) view.findViewById(R.id.ll_mortgage);
		ll_mortgage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),
						MortgageRequestActivity.class);
				startActivity(intent);

			}
		});
	}

	private void addTransfer(View view) {
		// TODO Auto-generated method stub
		ll_transfer = (LinearLayout) view.findViewById(R.id.ll_transfer);
		ll_transfer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),
						TransferRequestActivity.class);
				startActivity(intent);
			}
		});
	}

	private void addSellHouse(View view) {
		// TODO Auto-generated method stub
		ll_sell_house = (LinearLayout) view.findViewById(R.id.ll_sell_house);
		ll_sell_house.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				if (SharePreferences.islogin(getActivity())) {
					intent.setClass(getActivity(), SellHouseActivity.class);
					startActivity(intent);
				} else {
					intent.setClass(getActivity(), UserLogin.class);
					startActivity(intent);
				}
			}
		});
	}

	private void addHouseCal(View view) {
		// TODO Auto-generated method stub
		Mortgag_button = (LinearLayout) view.findViewById(R.id.Mortgag_button);
		Mortgag_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getActivity(), MortgageActivvity.class);
				startActivity(intent);
			}
		});
	}

	private void addRentHelp(View view) {
		// TODO Auto-generated method stub
		ll_help_rent = (LinearLayout) view.findViewById(R.id.ll_help_rent);
		ll_help_rent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				if (SharePreferences.islogin(getActivity())) {
					intent.setClass(getActivity(), RentalHouse.class);
					startActivity(intent);
				} else {
					intent.setClass(getActivity(), UserLogin.class);
					startActivity(intent);
				}
			}
		});
	}

	/**
	 * 测量listview的高度
	 * 
	 * @param listview
	 */
	private void setListViewHeight(ListView listview) {
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

	private void addSendRequest(View view) {
		// TODO Auto-generated method stub

		String cacheJson = CacheUtils.getCacheJson(getActivity(),
				secondhouse_url);
		if (cacheJson != null) {
			parseJsonResult(cacheJson);
			System.out.println("cacheJson" + cacheJson);
		} else {
			RequestParams rq = new RequestParams();
			rq.addBodyParameter("pageSize", 3 + "");
			rq.addBodyParameter("currentPage", 1 + "");
			rq.addBodyParameter("release", 1 + "");
			mhu.send(HttpMethod.POST, secondhouse_url, rq,
					new MyHttpCallback() {

						@Override
						public void onSuccessResult(String result) {
							// TODO Auto-generated method stub
							System.out.println("成功");
							System.out.println("结果" + result);
							CacheUtils.saveCacheJson(getActivity(),
									secondhouse_url, result);
							parseJsonResult(result);
						}

						@Override
						public void onLoadingResult(long total, long current,
								boolean isUploading) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onFailureResult(String msg) {
							// TODO Auto-generated method stub
							System.out.println("联网超时，请检查你的网络");
						}
					});
		}
	}

	protected void parseJsonResult(String result) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		secondHouseBean = gson.fromJson(result, SecondHouseBean.class);

		listview.setAdapter(new MyHouseItemAdapter(secondHouseBean,
				getActivity()));
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),
						SecondHouseDetailsActivity.class);
				House house = secondHouseBean.getList().get(arg2);
				Bundle bundle = new Bundle();
				bundle.putSerializable("house", house);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});

	}

	private void addInvest(View view) {
		// TODO Auto-generated method stub
		ll_invest = (LinearLayout) view.findViewById(R.id.ll_invest);
		ll_invest.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), InvestAcitivity.class);
				startActivity(intent);
			}
		});
	}

	private void addLoan(View view) {
		// TODO Auto-generated method stub
		ll_loan = (LinearLayout) view.findViewById(R.id.ll_loan);
		ll_loan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), LoanActivity.class);
				startActivity(intent);
			}
		});
	}

	private void addNewHouse(View view) {
		// TODO Auto-generated method stub
		ll_new_house = (LinearLayout) view.findViewById(R.id.ll_new_house);
		ll_new_house.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),
						NewHouseActivity.class);
				startActivity(intent);
			}
		});
	}

	private void addRentHouse(View view) {
		// TODO Auto-generated method stub
		ll_rent_house = (LinearLayout) view.findViewById(R.id.ll_rent_house);
		ll_rent_house.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),
						RentHouseActivity.class);
				Bundle bundle=new Bundle();
				bundle.putSerializable("areaBean", areaBean);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}

	private void addSecondHouse(View view) {
		// TODO Auto-generated method stub
		ll_second_house = (LinearLayout) view
				.findViewById(R.id.ll_second_house);
		ll_second_house.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),
						SecondHouseActivity.class);
				Bundle bundle=new Bundle();
				bundle.putSerializable("areaBean", areaBean);
				intent.putExtras(bundle);
				startActivity(intent);

			}
		});
	}

	private void addAction(View view) {
		// TODO Auto-generated method stub
		ll_action = (LinearLayout) view.findViewById(R.id.ll_action);
		ll_action.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), ActionActivity.class);
				startActivity(intent);
			}
		});
	}

	private void addLocation(View view) {
		tv_location = (TextView) view.findViewById(R.id.tv_location);
		tv_location.setText(MainActivity.city == "" ? "郑州" : MainActivity.city);

		tv_location.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), CityList.class);
				intent.putExtra("currCity", tv_location.getText());
				startActivityForResult(intent, 101);
			}
		});
		mLocationClient = new LocationClient(getActivity());
		setLocationOption();
		mLocationClient.registerLocationListener(myListenner);
	}

	private void addSeek(View view) {
		actv = (AutoCompleteTextView) view.findViewById(R.id.auto_tv);
		actv.setFocusable(false);
		actv.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					Intent intent = new Intent(getActivity(),
							SeekActivity.class);
					startActivity(intent);
				}
			}
		});
		actv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), SeekActivity.class);
				Bundle bundle=new Bundle();
				bundle.putSerializable("areaBean", areaBean);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});

		/*
		 * tv_location.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub Intent intent = new Intent(getActivity(), CityList.class);
		 * startActivityForResult(intent, 101); } }); mLocationClient = new
		 * LocationClient(getActivity()); setLocationOption();
		 * mLocationClient.registerLocationListener(myListenner);
		 */

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		String city = data.getStringExtra("city");
		if (city == null) {
			addLocation(getView());
		}
		// 请求获取区县
		getArea(city);
		tv_location.setText(city);
		mLocationClient.unRegisterLocationListener(myListenner);
		mLocationClient.stop();
		MainActivity.isChoose = true;
		MainActivity.city = city;
	}

	@Override
	public void initDate() {
		// TODO Auto-generated method stub

	}

	/**
	 * 定位获取城市，区县
	 * 
	 * @author Administrator
	 * 
	 */
	private String city;
	public class MyLocationListenner implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			
			if (location != null) {
//				StringBuffer sb = new StringBuffer(128);
//				sb.append(location.getCity());
				city = location.getCity();
				// 请求获取区县
				getArea(city);
				if (!MainActivity.isChoose) {
					tv_location.setText(city);
				} else {
					tv_location.setText(MainActivity.city);
				}
			}
		}

	}

	/**
	 * //请求获取区县
	 * 
	 * @param city
	 */
	private void getArea(String city) {
		RequestParams params = new RequestParams();
		params.addBodyParameter("name", city);

		mhu.send(HttpMethod.POST, near_url, params, new MyHttpCallback() {

			@Override
			public void onSuccessResult(String result) {
				// TODO Auto-generated method stub
				System.out.println("城市区县：----" + result);
				parseAreaJson(result);
			}

			@Override
			public void onLoadingResult(long total, long current,
					boolean isUploading) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFailureResult(String msg) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), "网络超市，请检查网络...", 0).show();
			}
		});
	}
private AreaBean areaBean;
	protected void parseAreaJson(String result) {
		// TODO Auto-generated method stub
		Gson gson=new Gson();
		areaBean=gson.fromJson(result, AreaBean.class);
	}

	private void setLocationOption() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);
		option.setCoorType("bd09ll");
		int span = 999;
		option.setScanSpan(span);
		option.setIsNeedAddress(true);
		option.setOpenGps(true);
		option.setLocationNotify(false);
		option.setIgnoreKillProcess(false);
		option.SetIgnoreCacheException(false);
		mLocationClient.setLocOption(option);
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		mLocationClient.start();
		super.onStart();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		mLocationClient.unRegisterLocationListener(myListenner);
		mLocationClient.stop();
		super.onDestroy();

	}
}
