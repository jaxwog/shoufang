package com.example.housefinded.activity;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.example.Tools.MyHttpUtil;
import com.example.Tools.MyHttpUtil.MyHttpCallback;
import com.example.Tools.SharePreferences;
import com.example.housefinded.R;
import com.example.housefinded.adapter.RentHouseViewPagerAdapter;
import com.example.housefinded.bean.Agent;
import com.example.housefinded.bean.AgentBean;
import com.example.housefinded.db.SqlHelpCRUD;
import com.example.housefinded.net.HttpUrl;
import com.example.housefinded.view.MyScrollView;
import com.example.housefinded.view.MyScrollView.OnScrollListener;
import com.example.housefinded.view.MyViewPager;
import com.example.javabean.CollectBean;
import com.example.javabean.House;
import com.example.javabean.HosueImage;
import com.example.javabean.HouseImageBean;
import com.example.javabean.SecondHouseBean;
import com.example.javabean.UserBean;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.TranslateAnimation;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SecondHouseDetailsActivity extends Activity implements
		OnClickListener, OnScrollListener {
	private ImageView iv_back, iv_share, iv_collection, iv_agenthead;
	private TextView tv_title, tv_des, tv_house_price, tv_house_mode, tv_area,
			tv_floor, tv_direction, tv_decorate, tv_year, tv_facility,
			tv_relase_date, tv_desprition, tv_plot, tv_quyu, tv_traffic,
			tv_suround, tv_other_second;
	private FrameLayout ll_agent;
	private RelativeLayout rl_can,getcelldetails;
	private House house;
	private CollectBean coll;
	Intent intent;
	String houseId;
	private MyHttpUtil mhu;
	private static SecondHouseDetailsActivity act;
	private String addurl = HttpUrl.BASE_URL + "rest/collection/add";
	private String deteleurl = HttpUrl.BASE_URL + "rest/collection/delete";
	private String url;
	private HttpUrl baseUrl = new HttpUrl();
	private ViewPager viewPager;
	private TextView tv_page;
	private TextView tv_evaluate, tv_area_det;
	public static HouseImageBean houseImageBean;
	private LinearLayout ll_address;
	boolean flag = false;
	// 饼状图数据
	public static String[] FEE_TEXT;
	public static float[] FEE_DATA;
	// 折线图数据
	public static String[] HOUSE_PRICE;
	private UserBean userbean;
	/**
	 * 手机屏幕宽度
	 */
	private int screenHeight;
	private MyScrollView myScrollView;
	private FrameLayout searchLayout;
	private WindowManager mWindowManager;
   
	private SqlHelpCRUD sqlCRUD;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.house_item_details);
		intent = getIntent();
		act = this;
		house = (House) intent.getSerializableExtra("house");
		HOUSE_PRICE = new String[] { "8800", "12000", "9200", "9250", "9500",
				"9200", "10245" };
		houseId = house.getId();
		mhu = new MyHttpUtil(this, "正在拼命加载中...");
		sqlCRUD = new SqlHelpCRUD(this);
		mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		screenHeight = mWindowManager.getDefaultDisplay().getHeight();
		coll = new CollectBean();
        
        getcelldetails=(RelativeLayout)findViewById(R.id.getcelldetails);
        getcelldetails.setOnClickListener(this);
		// 初始化悬浮框
		init();
		returnback();
		setText();
		sendRequest();
		setOnclick();
		addCal();
		getAgent();

	}

	private void getAgent() {
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams();
		url = HttpUrl.BASE_URL + "rest/sysUser/get/" + house.getUserId();
		mhu.send(HttpMethod.GET, url, params, new MyHttpCallback() {

			@Override
			public void onSuccessResult(String result) {
				// TODO Auto-generated method stub
				System.out.println("经纪人" + result);
				parseAgentJson(result);
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

	private TextView tv_agentname, tv_agentnum;
	private LinearLayout ll_call_agent;
	private AgentBean agentBean;
	private Agent agent;

	protected void parseAgentJson(String result) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		agentBean = gson.fromJson(result, AgentBean.class);
		agent = agentBean.getData();
		if (agent == null) {
			return;
		}
		// 经纪人信息
		tv_agentname = (TextView) findViewById(R.id.tv_agentname);
		tv_agentnum = (TextView) findViewById(R.id.tv_agentnum);
		iv_agenthead = (ImageView) findViewById(R.id.iv_agenthead);
		ll_call_agent = (LinearLayout) findViewById(R.id.ll_call_agent);

		tv_agentname.setText(agent.getName());
		tv_agentnum.setText(agent.getTelephone());
		ll_call_agent.setOnClickListener(this);
		BitmapUtils bitmapUtils = new BitmapUtils(act);
		if (agent.getHeadImg() == null) {
			return;
		}
		bitmapUtils
				.display(iv_agenthead, HttpUrl.BASE_URL + agent.getHeadImg());
	}

	private void init() {
		// TODO Auto-generated method stub
		myScrollView = (MyScrollView) findViewById(R.id.myScrollView);
		searchLayout = (FrameLayout) findViewById(R.id.ll_agent);
		myScrollView.setOnScrollListener(this);
	}

	private float house_total;
	private float business_tax;
	private float personal_tax;
	private float deed_tax;
	private float stamp_tax;
	private float motostamp_tax;
	private float tax_total;

	/**
	 * 税率计算
	 */
	private void addCal() {

		// 房款总价
		house_total = house.getHousePrice();
		// 营业税
		business_tax = (float) (house_total * 10000 * 0.056);
		// 契税
		if (house.getHouseArea() < 90) {
			deed_tax = (float) (house_total * 10000 * 0.01);
		} else if (house.getHouseArea() > 90 && house.getHouseArea() < 140) {
			deed_tax = (float) (house_total * 10000 * 0.02);
		} else {
			deed_tax = (float) (house_total * 10000 * 0.04);
		}
		// 个人所得税
		personal_tax = (float) (house_total * 10000 * 0.01);
		// 印花税
		stamp_tax = 0;
		// 宫本印花税
		motostamp_tax = 5;
		// 税金总额
		tax_total = business_tax + deed_tax + personal_tax + stamp_tax
				+ motostamp_tax;

		FEE_TEXT = new String[] { "房款总价  " + house_total + "万",
				"营业税  " + business_tax + "元", "契税  " + deed_tax + "元",
				"个人所得税  " + personal_tax + "元", "印花税  " + stamp_tax + "元",
				"工本印花税  " + motostamp_tax + "元", "税金总额  " + tax_total + "元" };
		FEE_DATA = new float[] { 0, (360 * business_tax / tax_total),
				(360 * deed_tax / tax_total), (360 * personal_tax / tax_total),
				(360 * stamp_tax / tax_total),
				(360 * motostamp_tax / tax_total), 0 };
	}

	/**
	 * 可点击的事件
	 */
	private void setOnclick() {
		// TODO Auto-generated method stub
		tv_evaluate = (TextView) findViewById(R.id.tv_evaluate);
		tv_area_det = (TextView) findViewById(R.id.tv_area_det);
		ll_address = (LinearLayout) findViewById(R.id.ll_address);
		iv_share = (ImageView) findViewById(R.id.iv_share);
		iv_collection = (ImageView) findViewById(R.id.iv_collection);
		rl_can = (RelativeLayout) findViewById(R.id.rl_can);
		iv_agenthead = (ImageView) findViewById(R.id.iv_agenthead);
		ll_agent=(FrameLayout) findViewById(R.id.ll_agent);

		SharedPreferences sp = getPreferences(MODE_PRIVATE);
		iv_collection.setSelected(sp.getBoolean(houseId, false));
		// 这个是很重要的一个东西，注意查看!

		tv_evaluate.setOnClickListener(this);
		//tv_area_det.setOnClickListener(this);
		ll_address.setOnClickListener(this);
		iv_share.setOnClickListener(this);
		iv_collection.setOnClickListener(this);
		rl_can.setOnClickListener(this);
		iv_agenthead.setOnClickListener(this);
		ll_agent.setOnClickListener(this);
	}

	/**
	 * 联网请求
	 */
	public void sendRequest() {
		// TODO Auto-generated method stub

		RequestParams params = new RequestParams();

		url = HttpUrl.BASE_URL + "rest/shImage/getImageList?secondId="
				+ houseId;

		mhu.send(HttpMethod.GET, url, params, new MyHttpCallback() {

			@Override
			public void onSuccessResult(String result) {
				// TODO Auto-generated method stub

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
				Toast.makeText(act, "fail", 0).show();
			}
		});
	}

	public static String urlPath;
	// public static AttributeSet urlPath;
	private RentHouseViewPagerAdapter adapter;

	public void parseJsonResult(String result) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		houseImageBean = gson.fromJson(result, HouseImageBean.class);
		tv_page.setText(1 + "/" + houseImageBean.getData().size());
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		adapter = new RentHouseViewPagerAdapter(houseImageBean.getData

		(), act);
		viewPager.setAdapter(adapter);
		viewPager.setOnPageChangeListener(new OnPageChangeListener()

		{

			@Override
			public void onPageSelected(int arg0) {

			}

			@Override
			public void onPageScrolled(int position, float arg1,

			int arg2) {
				tv_page.setText((position

				+ 1) + "/" + houseImageBean.getData().size());
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

	/**
	 * 文本设置
	 */
	private void setText() {
		// TODO Auto-generated method stub
		tv_page = (TextView) findViewById(R.id.tv_page);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_des = (TextView) findViewById(R.id.tv_des);
		tv_house_price = (TextView) findViewById(R.id.tv_house_price);
		tv_house_mode = (TextView) findViewById(R.id.tv_house_mode);
		tv_area = (TextView) findViewById(R.id.tv_area);
		tv_floor = (TextView) findViewById(R.id.tv_floor);
		tv_direction = (TextView) findViewById(R.id.tv_direction);
		tv_decorate = (TextView) findViewById(R.id.tv_decorate);
		tv_year = (TextView) findViewById(R.id.tv_year);
		tv_facility = (TextView) findViewById(R.id.tv_facility);
		tv_relase_date = (TextView) findViewById(R.id.tv_relase_date);
		tv_desprition = (TextView) findViewById(R.id.tv_desprition);
		tv_plot = (TextView) findViewById(R.id.tv_plot);
		tv_quyu = (TextView) findViewById(R.id.tv_quyu);
		tv_other_second = (TextView) findViewById(R.id.tv_other_second);
		tv_suround = (TextView) findViewById(R.id.tv_suround);
		tv_traffic = (TextView) findViewById(R.id.tv_traffic);

		tv_title.setText(house.getBuildingAdress());
		tv_des.setText(house.getTitle());
		tv_house_price.setText(house.getHousePrice() + "万元/套");
		tv_house_mode.setText(house.getHouseType());
		tv_area.setText(house.getHouseArea() + "m²");
		tv_floor.setText(house.getHouseFloor());
		if ("1".equals(house.getFace())) {

			tv_direction.setText("东");
		}
		if ("2".equals(house.getFace())) {

			tv_direction.setText("南");
		}
		if ("3".equals(house.getFace())) {

			tv_direction.setText("西");
		}
		if ("4".equals(house.getFace())) {

			tv_direction.setText("北");
		}
		if ("5".equals(house.getFace())) {

			tv_direction.setText("东南");
		}
		if ("6".equals(house.getFace())) {

			tv_direction.setText("西南");
		}
		if ("7".equals(house.getFace())) {

			tv_direction.setText("西北");
		}
		if ("8".equals(house.getFace())) {

			tv_direction.setText("东北");
		}
		if ("9".equals(house.getFace())) {

			tv_direction.setText("南北");
		}
		if ("10".equals(house.getFace())) {

			tv_direction.setText("东西");
		}
		if ("1".equals(house.getDecorate())) {

			tv_decorate.setText("豪华装修");
		}
		if ("2".equals(house.getDecorate())) {

			tv_decorate.setText("精装修");
		}
		if ("3".equals(house.getDecorate())) {

			tv_decorate.setText("中装修");
		}
		if ("4".equals(house.getDecorate())) {

			tv_decorate.setText("简装修");
		}
		if ("5".equals(house.getDecorate())) {

			tv_decorate.setText("毛坯");
		}

		tv_year.setText(house.getHouseIn());
		tv_facility.setText(house.getHousePtss());
		tv_relase_date.setText(house.getReleaseDate());
		tv_desprition.setText(house.getDescription());
		tv_plot.setText(house.getBuildingAdress());
		tv_quyu.setText(house.getBuildingArea());
		tv_other_second.setText(house.getBuildingAdress() + "其他二手房源");
		tv_suround.setText(house.getZbpt());
		tv_traffic.setText(house.getJtlx());

	}

	/**
	 * 后退
	 */
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
		case R.id.iv_share:
			ShareSDK.initSDK(this);
			OnekeyShare oks = new OnekeyShare();

			oks.disableSSOWhenAuthorize();
			oks.setTitle(getString(R.string.share));
			oks.setTitleUrl("http://sharesdk.cn");
			oks.setText("我新发现了一个不错的房源" + house.getBuildingAdress()
					+ house.getHouseType() + house.getHousePrice() + "万元/套");
			oks.setUrl("http://sharesdk.cn");
			oks.setComment("我是测试评论文本");
			oks.setSite(getString(R.string.app_name));
			oks.setSiteUrl("http://sharesdk.cn");
			oks.show(this);
			break;
		case R.id.tv_evaluate:
			Intent intent_ev = new Intent(act, CalActivity.class);
			startActivity(intent_ev);
			break;
		case R.id.rl_can:
			Intent intent_can = new Intent(act, TaxcalculationActivity.class);
			startActivity(intent_can);
			break;
	/*	case R.id.tv_area_det:
			Intent intent_area = new Intent(act, CalActivity.class);
			startActivity(intent_area);
			break;*/
		case R.id.ll_address:

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
		// 添加收藏数据
		case R.id.iv_collection:
			if (SharePreferences.islogin(act)) {
				if (iv_collection.isSelected()) {
					flag = false;
					iv_collection.setSelected(flag);
					MyHttpUtil http2 = new MyHttpUtil(SecondHouseDetailsActivity.this,"正在取消收藏...*^_^*");
					RequestParams params = new RequestParams();
					params.addBodyParameter("id", coll.getId());
					http2.sendto(HttpMethod.POST, deteleurl, params, new MyHttpCallback() {
						
						@Override
						public void onSuccessResult(String result) {
							Toast.makeText(SecondHouseDetailsActivity.this, "已取消收藏", 0).show();
						}
						
						@Override
						public void onLoadingResult(long total, long current, boolean isUploading) {
							
						}
						
						@Override
						public void onFailureResult(String msg) {
							Toast.makeText(SecondHouseDetailsActivity.this, "网络错误", 0).show();
						}
					});
					/*
					 * SharedPreferences.Editor edit =
					 * getPreferences(MODE_PRIVATE).edit();
					 * edit.putBoolean(houseId, flag); edit.commit();
					 * house.setIscol("0");
					 * house.setIssee(sqlCRUD.getHouseIssee(house.getId()));
					 * sqlCRUD.insertOrUpdate(house);
					 */
				} else {
					flag = true;
					iv_collection.setSelected(flag);
					userbean = SharePreferences.readuserbean(act);
					MyHttpUtil http = new MyHttpUtil(SecondHouseDetailsActivity.this,"正在收藏中...");
					RequestParams params = new RequestParams();
					params.addBodyParameter("userId", userbean.getId() + "");
					params.addBodyParameter("houseType", "1");
					params.addBodyParameter("houseId", house.getId());
					http.sendto(HttpMethod.POST, addurl, params, new MyHttpCallback() {
						
						@Override
						public void onSuccessResult(String result) {
							// TODO Auto-generated method stub
							Toast.makeText(SecondHouseDetailsActivity.this, "已收藏", 0).show();
						}
						
						@Override
						public void onLoadingResult(long total, long current, boolean isUploading) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void onFailureResult(String msg) {
							Toast.makeText(SecondHouseDetailsActivity.this, "网络错误", 0).show();
							
						}
					});


					/*
					 * SharedPreferences.Editor edit =
					 * getPreferences(MODE_PRIVATE) .edit();
					 * edit.putBoolean(houseId, flag); edit.commit();
					 * house.setIscol("1");
					 * house.setIssee(sqlCRUD.getHouseIssee(house.getId()));
					 * sqlCRUD.insertOrUpdate(house);
					 */
				}

			} else {
				Toast.makeText(act, "亲，登录之后，才能收藏", 0).show();
			}

			break;
		case R.id.iv_agenthead:
			Intent intent_agent = new Intent(act, AgentDetailActivity.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("agent", agent);
			intent_agent.putExtras(bundle);

			startActivity(intent_agent);
			break;
		case R.id.ll_call_agent:
			AlertDialog.Builder builder = new Builder(act);
			builder.setTitle("电话")
					.setMessage(agent.getTelephone())
					.setPositiveButton("拨打",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									Intent intent = new Intent(
											Intent.ACTION_CALL,
											Uri.parse("tel:"
													+ agent.getTelephone()));
									startActivity(intent);
								}

							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									dialog.cancel();
								}
							}).show();

			break;
		case R.id.getcelldetails :
			Intent intent=new Intent(SecondHouseDetailsActivity.this,CellActivity.class);
			intent.putExtra("villageId", house.getVillageId());//小区id
			startActivity(intent);
			break;
		default:
			break;
		}
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

	@Override
	public void onScroll(int scrollY) {
		// TODO Auto-generated method stub
		if (scrollY >= 60) {
			TranslateAnimation trans = new TranslateAnimation(0, 0,
					screenHeight, screenHeight - 60);
			trans.setDuration(800);
			searchLayout.setVisibility(View.VISIBLE);
		} else if (scrollY < 60) {
			TranslateAnimation trans = new TranslateAnimation(0, 0,
					screenHeight - 60, screenHeight);
			trans.setDuration(800);
			searchLayout.setVisibility(View.GONE);
		}
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getisselect();
	}

	// 判断是否已经被收藏
	public void getisselect() {
		if (SharePreferences.islogin(act)) {
			userbean = SharePreferences.readuserbean(act);
			HttpUtils istrue = new HttpUtils(8000);// 请求超时的时间为8s
			istrue.configCurrentHttpCacheExpiry(2000); // 设置缓存5秒,5秒内直接返回上次成功请求的结果。
			istrue.send(HttpMethod.GET,
					HttpUrl.BASE_URL
							+ "rest/collection/validateByParam?userId="
							+ userbean.getId() + "&houseType=" + 1
							+ "&houseId=" + houseId,
					new RequestCallBack<String>() {

						@Override
						public void onFailure(HttpException arg0, String arg1) {
						}

						@Override
						public void onSuccess(ResponseInfo<String> arg0) {
							String ss = arg0.result;
							try {
								JSONObject object = new JSONObject(ss);

								if ("1".equals(object.getString("status"))) {
									// 已收藏
									Gson gson = new Gson();
									coll = gson.fromJson(
											object.getString("data"),
											CollectBean.class);
									System.out.print(coll);
									iv_collection.setSelected(true);
								} else {
									// 未收藏
									iv_collection.setSelected(false);
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							System.out.print(ss);
						}
					});
		}
	}
}
