package com.example.housefinded.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.example.Tools.MyHttpUtil;
import com.example.Tools.SharePreferences;
import com.example.Tools.MyHttpUtil.MyHttpCallback;
import com.example.housefinded.R;
import com.example.housefinded.adapter.RentHouseViewPagerAdapter;
import com.example.housefinded.bean.Agent;
import com.example.housefinded.bean.AgentBean;
import com.example.housefinded.net.HttpUrl;
import com.example.housefinded.view.MyScrollView;
import com.example.housefinded.view.MyScrollView.OnScrollListener;
import com.example.housefinded.view.MyViewPager;
import com.example.javabean.CollectBean;
import com.example.javabean.HosueImage;
import com.example.javabean.HouseImageBean;
import com.example.javabean.RentHouse;
import com.example.javabean.UserBean;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import

com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.TranslateAnimation;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;

public class RentHouseDetailsActivity extends Activity implements

OnClickListener, OnScrollListener {
	private ImageView iv_back,iv_share,iv_collection,iv_agenthead;
	private LinearLayout ll_facility1,ll_facility2,ll_address;
	private TextView tv_facility, tv_title, tv_des, tv_relase_date,
			tv_rent_cost, tv_rent_type, tv_rent_mode, tv_area, tv_house_type,
			tv_direction, tv_decorate, tv_flor;
	private FrameLayout ll_agent;
	private TextView tv_bed, tv_broadband, tv_TV, tv_washer, tv_teating,
			tv_air_condition, tv_icebox, tv_water_heater,tv_desprition,tv_plot,tv_quyu,
			tv_house_address,tv_other_second,tv_jtlx,tv_zbpt;
	RentHouse rentHouse;
	private CollectBean coll;
	String rentHouseId;
	String url;
	boolean flag = false;
	private HttpUrl baseUrl = new HttpUrl();
	private TextView tv_page;
	private ViewPager viewPager;
	private MyHttpUtil mhu;
	private RentHouseDetailsActivity act;
	public static HouseImageBean houseImageBean;
	private UserBean userbean;
	private String addurl=HttpUrl.BASE_URL+"rest/collection/add";
	private String deteleurl=HttpUrl.BASE_URL+"rest/collection/delete";
	/**
	 * 手机屏幕宽度
	 */
	private int screenHeight;
	private MyScrollView myScrollView;
	private FrameLayout searchLayout;
	private RelativeLayout arearent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.rent_details_activity);
		act = this;
		Intent intent = getIntent();
		rentHouse = (RentHouse) intent.getSerializableExtra("rentHouse");
		rentHouseId = rentHouse.getId();
		System.out.println("rentHouseId" + rentHouseId);
		mhu = new MyHttpUtil(act, "正在拼命加载中...");
        coll=new CollectBean();
    	iv_agenthead = (ImageView) findViewById(R.id.iv_agenthead);
    	// 初始化悬浮框
		init();
		setOnClick();
		setText();
		sendRequest();
		getReleasePeople();
	}

	private void getReleasePeople() {
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams();
		url = HttpUrl.BASE_URL + "rest/sysUser/get/" + rentHouse.getUserId();
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
	/**
	 * 解析经纪人javaBean
	 * @param result
	 */
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
	
		ll_call_agent = (LinearLayout) findViewById(R.id.ll_call_agent);

		tv_agentname.setText(agent.getName());
		tv_agentnum.setText(agent.getTelephone());
		ll_call_agent.setOnClickListener(this);
		if("0".equals(rentHouse.getUserType())){
			iv_agenthead.setVisibility(View.VISIBLE);
			BitmapUtils bitmapUtils = new BitmapUtils(act);
			System.out.println("中介房源-----0000-------"+rentHouse.getUserType());
			bitmapUtils
			.display(iv_agenthead, HttpUrl.BASE_URL + agent.getHeadImg());
			
		}else {
			iv_agenthead.setVisibility(View.GONE);
			System.out.println("个人房源------0000------"+rentHouse.getUserType());
		}
	}

	private void init() {
		// TODO Auto-generated method stub
		myScrollView = (MyScrollView) findViewById(R.id.myScrollView);
		searchLayout = (FrameLayout) findViewById(R.id.ll_agent);
		myScrollView.setOnScrollListener(this);
	}		


	private void sendRequest() {
		// TODO Auto-generated method stub
		RequestParams rq = new RequestParams();

		url = baseUrl.BASE_URL + "rest/tenementImage/getImageList?tenementId="
				+ rentHouseId;

		mhu.send(HttpMethod.GET, url, rq, new MyHttpCallback() {

			@Override
			public void onSuccessResult(String result) {
				// TODO Auto-generated method stub
				Toast.makeText(act, "cg ", 0).show();
				System.out.println("jieg" + result); 
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

	private RentHouseViewPagerAdapter adapter;

	protected void parseJsonResult(String result) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		houseImageBean = gson.fromJson(result, HouseImageBean.class);
		if (houseImageBean == null) {
			return;
		}
		tv_page.setText(1 + "/" + houseImageBean.getData().size());
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		adapter = new RentHouseViewPagerAdapter(houseImageBean.getData(), act);
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

	private void setText() {
		// TODO Auto-generated method stub
		tv_page = (TextView) findViewById(R.id.tv_page);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_des = (TextView) findViewById(R.id.tv_des);
		tv_relase_date = (TextView) findViewById(R.id.tv_relase_date);
		tv_rent_cost = (TextView) findViewById(R.id.tv_rent_cost);
		tv_rent_type = (TextView) findViewById(R.id.tv_rent_type);
		tv_rent_mode = (TextView) findViewById(R.id.tv_rent_mode);
		tv_area = (TextView) findViewById(R.id.tv_area);
		tv_house_type = (TextView) findViewById(R.id.tv_house_type);
		tv_direction = (TextView) findViewById(R.id.tv_direction);
		tv_decorate = (TextView) findViewById(R.id.tv_decorate);
		tv_facility = (TextView) findViewById(R.id.tv_facility);
		ll_facility1 = (LinearLayout) findViewById(R.id.ll_facility1);
		ll_facility2 = (LinearLayout) findViewById(R.id.ll_facility2);
		arearent=(RelativeLayout)findViewById(R.id.arearent);
		arearent.setOnClickListener(this);
		tv_flor = (TextView) findViewById(R.id.tv_flor);
		tv_bed = (TextView) findViewById(R.id.tv_bed);
		tv_broadband = (TextView) findViewById(R.id.tv_broadband);
		tv_TV = (TextView) findViewById(R.id.tv_TV);
		tv_washer = (TextView) findViewById(R.id.tv_washer);
		tv_teating = (TextView) findViewById(R.id.tv_teating);
		tv_air_condition = (TextView) findViewById(R.id.tv_air_condition);
		tv_icebox = (TextView) findViewById(R.id.tv_icebox);
		tv_water_heater = (TextView) findViewById(R.id.tv_water_heater);
		tv_desprition = (TextView) findViewById(R.id.tv_desprition);
		tv_plot = (TextView) findViewById(R.id.tv_plot);
		tv_quyu = (TextView) findViewById(R.id.tv_quyu);
		tv_house_address = (TextView) findViewById(R.id.tv_house_address);
		tv_other_second = (TextView) findViewById(R.id.tv_other_second);
		tv_zbpt = (TextView) findViewById(R.id.tv_zbpt);
		tv_jtlx = (TextView) findViewById(R.id.tv_jtlx);
		
		tv_other_second.setText(rentHouse.getCellName()+"其他租房房源");
		tv_house_address.setText(rentHouse.getCellAdress());
		tv_quyu.setText(rentHouse.getCellArea());
		tv_plot.setText(rentHouse.getCellName());
		tv_desprition.setText(rentHouse.getHouseLabel());
		tv_title.setText(rentHouse.getCellName());
		tv_des.setText(rentHouse.getTitle());
		tv_relase_date.setText(rentHouse.getCreateDate());
		tv_rent_cost.setText(rentHouse.getRent() + "元/月");
		tv_rent_type.setText(rentHouse.getRentUnit());
		tv_zbpt.setText(rentHouse.getZbpt());
		tv_jtlx.setText(rentHouse.getJtlx());
		if ("0".equals(rentHouse.getTenType())) {
			tv_rent_mode.setText("整租");
		} else if ("1".equals(rentHouse.getTenType())) {
			tv_rent_mode.setText("合租");
		} else {
			tv_rent_mode.setText("求租");
		}
		tv_area.setText(rentHouse.getHouseArea() + "m²");
		tv_house_type.setText(rentHouse.getHouseType());

		tv_flor.setText(rentHouse.getFloor());

		if ("0".equals(rentHouse.getUserType())) {
			tv_facility.setText(rentHouse.getHousePtss());
			tv_facility.setVisibility(View.VISIBLE);
			ll_facility1.setVisibility(View.GONE);
			ll_facility2.setVisibility(View.GONE);
		} else {
			tv_facility.setVisibility(View.GONE);
			ll_facility1.setVisibility(View.VISIBLE);
			ll_facility2.setVisibility(View.VISIBLE);
			if (rentHouse.getBed().equals("true")) {
				tv_bed.setSelected(true);

			}
			if (rentHouse.getBroadband().equals("true")) {
				tv_broadband.setSelected(true);
			}
			if (rentHouse.getTv().equals("true")) {
				tv_TV.setSelected(true);
			}
			if (rentHouse.getWasher().equals("true")) {
				tv_washer.setSelected(true);
			}
			if (rentHouse.getHeating().equals("true")) {
				tv_teating.setSelected(true);
			}
			if (rentHouse.getAirconditioner().equals("true")) {
				tv_air_condition.setSelected(true);
			}
			if (rentHouse.getFridge().equals("true")) {
				tv_icebox.setSelected(true);
			}
			if (rentHouse.getWaterHeater().equals("true")) {
				tv_water_heater.setSelected(true);
			}
		}
		if ("1".equals(rentHouse.getFace())) {
			tv_direction.setText("东");
		}
		if ("2".equals(rentHouse.getFace())) {

			tv_direction.setText("南");
		}
		if ("3".equals(rentHouse.getFace())) {

			tv_direction.setText("西");
		}
		if ("4".equals(rentHouse.getFace())) {

			tv_direction.setText("北");
		}
		if ("5".equals(rentHouse.getFace())) {

			tv_direction.setText("东南");
		}
		if ("6".equals(rentHouse.getFace())) {

			tv_direction.setText("西南");
		}
		if ("7".equals(rentHouse.getFace())) {

			tv_direction.setText("西北");
		}
		if ("8".equals(rentHouse.getFace())) {

			tv_direction.setText("东北");
		}
		if ("9".equals(rentHouse.getFace())) {

			tv_direction.setText("南北");
		}
		if ("10".equals(rentHouse.getFace())) {

			tv_direction.setText("东西");
		}
		if ("1".equals(rentHouse.getDecorate())) {

			tv_decorate.setText("豪华装修");
		}
		if ("2".equals(rentHouse.getDecorate())) {

			tv_decorate.setText("精装修");
		}
		if ("3".equals(rentHouse.getDecorate())) {

			tv_decorate.setText("中装修");
		}
		if ("4".equals(rentHouse.getDecorate())) {

			tv_decorate.setText("简装修");
		}
		if ("5".equals(rentHouse.getDecorate())) {

			tv_decorate.setText("毛坯");
		}

	}

	private void setOnClick() {
		// TODO Auto-generated method stub
		iv_back = (ImageView) findViewById(R.id.iv_back);
		iv_share=(ImageView) findViewById(R.id.iv_share);
		iv_collection=(ImageView) findViewById(R.id.iv_collection);
		ll_address=(LinearLayout) findViewById(R.id.ll_address);
		ll_agent=(FrameLayout) findViewById(R.id.ll_agent);
		
		SharedPreferences sp = getPreferences(MODE_PRIVATE);
		iv_collection.setSelected(sp.getBoolean(rentHouseId, false));
		
		iv_back.setOnClickListener(this);
		iv_share.setOnClickListener(this);
		iv_collection.setOnClickListener(this);
		ll_address.setOnClickListener(this);
		iv_agenthead.setOnClickListener(this);
		ll_agent.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			break;
		case R.id.iv_share:
			ShareSDK.initSDK(this);
			OnekeyShare oks = new OnekeyShare();

			oks.disableSSOWhenAuthorize();
			oks.setTitle(getString(R.string.share));
			oks.setTitleUrl("http://sharesdk.cn");
			oks.setText("我新发现了一个不错的房源" + rentHouse.getCellArea()
					+ rentHouse.getHouseType() + rentHouse.getRent() + "元/");
			oks.setUrl("http://sharesdk.cn");
			oks.setComment("我是测试评论文本");
			oks.setSite(getString(R.string.app_name));
			oks.setSiteUrl("http://sharesdk.cn");
			oks.show(this);
			break;
		case R.id.iv_collection:
			if(SharePreferences.islogin(act)){
				if (iv_collection.isSelected()) {
					iv_collection.setSelected(false);
					  HttpUtils http2=new HttpUtils();
				       RequestParams params=new RequestParams();
				       params.addBodyParameter("id", coll.getId());
				       http2.send(HttpMethod.POST, deteleurl, params, new RequestCallBack<String>() {

						@Override
						public void onFailure(HttpException arg0, String arg1) {
							System.out.print(arg0+arg1);
							
						}

						@Override
						public void onSuccess(ResponseInfo<String> arg0) {
						System.out.print(arg0.result);
							
						}
					});
					Toast.makeText(act, "已取消收藏", 0).show();
					/*SharedPreferences.Editor edit = getPreferences(MODE_PRIVATE).edit();
					edit.putBoolean(houseId, flag);
					edit.commit();
					house.setIscol("0");
					house.setIssee(sqlCRUD.getHouseIssee(house.getId()));
					sqlCRUD.insertOrUpdate(house);*/
				} else {
					
					iv_collection.setSelected(true);
					   userbean=SharePreferences.readuserbean(act);
				       HttpUtils http=new HttpUtils();
				       RequestParams params=new RequestParams();
				       params.addBodyParameter("userId",userbean.getId()+"");
				       params.addBodyParameter("houseType","2");
				       params.addBodyParameter("houseId", rentHouse.getId());
				       http.send(HttpMethod.POST, addurl, params, new RequestCallBack<String>() {

						@Override
						public void onFailure(HttpException arg0, String arg1) {
							System.out.print(arg0+arg1);
							
						}

						@Override
						public void onSuccess(ResponseInfo<String> arg0) {
						System.out.print(arg0.result);
							
						}
					});
				       
					Toast.makeText(act, "已收藏", 0).show();
					
					/*SharedPreferences.Editor edit = getPreferences(MODE_PRIVATE)
							.edit();
					edit.putBoolean(houseId, flag);
					edit.commit();
					house.setIscol("1");
					house.setIssee(sqlCRUD.getHouseIssee(house.getId()));
					sqlCRUD.insertOrUpdate(house);*/
				}
				
			}else{
    	   Toast.makeText(act, "亲，登录之后，才能收藏", 0).show();
       }
			break;
		case R.id.ll_address:
			try {
				Uri mUri = Uri.parse("geo:34.7483785,113.6995692?q=御玺大厦");
				Intent mIntent = new Intent(Intent.ACTION_VIEW, mUri);
				if(isInstallByread("com.baidu.BaiduMap")){
				startActivity(mIntent);
				}else{
					Toast.makeText(act, "你没有安装百度地图", 0).show();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case R.id.iv_agenthead:
			Intent intent_agent = new Intent(act, RentHouseAgentDetailActivity.class);
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
		case R.id.arearent :
			Intent intent=new Intent(RentHouseDetailsActivity.this,CellActivity.class);
			intent.putExtra("villageId",rentHouse.getVillageId());
			startActivity(intent);
			break;
		default:
			break;
		}
	}
	/**  
     * 判断是否安装目标应用  
     * @param packageName 目标应用安装后的包名  
     * @return 是否已安装目标应用  
     */   
    private boolean isInstallByread(String packageName) {    
     return new File("/data/data/" + packageName).exists();    
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
	//判断是否已经被收藏
	public void getisselect(){
		if(SharePreferences.islogin(act)){
	    userbean=SharePreferences.readuserbean(act);
		HttpUtils istrue=new HttpUtils(8000);//请求超时的时间为8s
		istrue.configCurrentHttpCacheExpiry(2000); // 设置缓存5秒,5秒内直接返回上次成功请求的结果。
		istrue.send(HttpMethod.GET, HttpUrl.BASE_URL+"rest/collection/validateByParam?userId="+userbean.getId()+"&houseType="+2+"&houseId="+rentHouseId,new RequestCallBack<String>() {
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				String ss=arg0+arg1;
				System.out.print(ss);
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				String ss=arg0.result;
				try {
					JSONObject object=new JSONObject(ss);
					
					if("1".equals(object.getString("status"))){
						//已收藏
						Gson gson=new Gson();
						coll=gson.fromJson(object.getString("data"), CollectBean.class);
						System.out.print(coll);
						iv_collection.setSelected(true);
					}else{
						//未收藏
						iv_collection.setSelected(false);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		});
		}
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

}
