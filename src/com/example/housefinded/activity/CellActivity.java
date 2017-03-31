package com.example.housefinded.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.example.Tools.MyHttpUtil;
import com.example.Tools.MyHttpUtil.MyHttpCallback;
import com.example.Tools.SharePreferences;
import com.example.housefinded.R;
import com.example.housefinded.adapter.RentHouseViewPagerAdapter;
import com.example.housefinded.net.HttpUrl;
import com.example.javabean.HosueImage;
import com.example.javabean.HouseImageBean;
import com.example.javabean.VillageBean;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CellActivity extends Activity implements OnClickListener {
	ImageView back;
	private LinearLayout cell_sellhouse, cell_mapview;
	private CellActivity cey;
	private ViewPager page;
	private int height, width;
	private RentHouseViewPagerAdapter adapt;
	private HouseImageBean imagebean;
	private List<HosueImage> bbb;
	private ImageView area_share;
	private Button areabutton;
	String villageId;// 小区id
	private VillageBean areabean;
	private TextView area_title, areasecond, arearent, cy_addres, cy_leixing,
			cy_wyxl, cy_year, cy_cq, cy_gs, cy_wyf, cy_kfs, cy_lhl, cy_rjl,
			cy_tcw, cy_xqjs, cy_dz, cy_index;;
private LinearLayout cy_second,cy_rent;
	// 小区图片详情
	private String IMAGEURL = HttpUrl.BASE_URL
			+ "rest/villageImage/getImageList";
	private String detailsURl = HttpUrl.BASE_URL + "rest/village/get";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_areadetails);
		areabean = new VillageBean();
		bbb = new ArrayList<HosueImage>();
		villageId = getIntent().getStringExtra("villageId");
		if (villageId != null) {
			sendrequest();
		}
		initView();
		getDisplaymetu();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if (villageId != null) {
			getdetails();
		}
	}

	public void initView() {
		this.cey = this;
		imagebean = new HouseImageBean();
		cell_sellhouse = (LinearLayout) findViewById(R.id.cell_sellhouse);
		cell_mapview = (LinearLayout) findViewById(R.id.cell_mapview);
		cy_second=(LinearLayout)findViewById(R.id.cy_second);
		cy_rent=(LinearLayout)findViewById(R.id.cy_rent);
		back = (ImageView) findViewById(R.id.area_back);
		area_share = (ImageView) findViewById(R.id.area_share);
		areabutton = (Button) findViewById(R.id.areabutton);
		page = (ViewPager) findViewById(R.id.viewPager);
		area_title=(TextView)findViewById(R.id.area_title);
		areasecond=(TextView)findViewById(R.id.areasecond);
		arearent=(TextView)findViewById(R.id.arearent);
		cy_addres=(TextView)findViewById(R.id.cy_addres);
		cy_leixing=(TextView)findViewById(R.id.cy_leixing);
		cy_wyxl=(TextView)findViewById(R.id.cy_wylx);
		cy_year=(TextView)findViewById(R.id.cy_year);
		cy_cq=(TextView)findViewById(R.id.cy_cq);
		cy_gs=(TextView)findViewById(R.id.cy_gs);
		cy_wyf=(TextView)findViewById(R.id.cy_wyf);
		cy_kfs=(TextView)findViewById(R.id.cy_kf);
		cy_lhl=(TextView)findViewById(R.id.cy_lhl);
		cy_rjl=(TextView)findViewById(R.id.cy_rjl);
		cy_tcw=(TextView)findViewById(R.id.cy_tcw);
		cy_xqjs=(TextView)findViewById(R.id.cy_xqjs);
		cy_dz=(TextView)findViewById(R.id.cy_dz);
		cy_index=(TextView)findViewById(R.id.cy_index);
		
		back.setOnClickListener(this);
		cell_sellhouse.setOnClickListener(this);
		cell_mapview.setOnClickListener(this);
		area_share.setOnClickListener(this);
		areabutton.setOnClickListener(this);
		cy_second.setOnClickListener(this);
		cy_rent.setOnClickListener(this);
	}
	//初始化数据
public void initdata(){
	area_title.setText(areabean.getVillageName());
	areasecond.setText("二手房"+areabean.getSecondCount()+"套");
	arearent.setText("租房"+areabean.getTenementCount()+"套");
	cy_addres.setText(areabean.getVillageArea());
	cy_leixing.setText(areabean.getHouseType());
	cy_wyxl.setText(areabean.getPropertyType());
	cy_year.setText(areabean.getBuildYear());
	cy_cq.setTag(areabean.getPropertyDescription());
	cy_gs.setText(areabean.getPropertyCompany());
	cy_wyf.setText(areabean.getPropertyFee());
	cy_kfs.setText(areabean.getDeveloper());
	cy_lhl.setText(areabean.getGreenRate());
	cy_rjl.setText(areabean.getPlotRatio());
	cy_tcw.setText(areabean.getPackingSpace());
	cy_xqjs.setText(areabean.getIntroduce());
	cy_dz.setText(areabean.getAddress());
	cy_index.setText(areabean.getVillageIndex());
}
	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {

		case R.id.area_back:
			finish();
			break;
		case R.id.cell_sellhouse:
			if (SharePreferences.islogin(cey)) {
				intent.setClass(cey, SellHouseActivity.class);
				startActivity(intent);
			} else {
				intent.setClass(cey, UserLogin.class);
				startActivity(intent);
			}
			break;
		case R.id.cell_mapview:
			try {
				Uri mUri = Uri.parse("geo:34.7483785,113.6995692?q=御玺大厦");
				 intent = new Intent(Intent.ACTION_VIEW, mUri);
				if (isInstallByread("com.baidu.BaiduMap")) {
					startActivity(intent);
				} else {
					Toast.makeText(cey, "你没有安装百度地图", 0).show();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case R.id.area_share:
			ShareSDK.initSDK(this);
			OnekeyShare oks = new OnekeyShare();

			oks.disableSSOWhenAuthorize();
			oks.setTitle(getString(R.string.share));
			oks.setTitleUrl("http://sharesdk.cn");
			/*
			 * oks.setText("我新发现了一个不错的房源" + house.getBuildingAdress() +
			 * house.getHouseType() + house.getHousePrice() + "万元/套");
			 */
			oks.setUrl("http://sharesdk.cn");
			oks.setComment("我是测试评论文本");
			oks.setSite(getString(R.string.app_name));
			oks.setSiteUrl("http://sharesdk.cn");
			oks.show(this);
			break;
		case R.id.areabutton:
			intent = new Intent(cey, CalActivity.class);
			startActivity(intent);
			break;
		case R.id.cy_second:
			intent=new Intent(cey,SecondHouseActivity.class);
			intent.putExtra("villageId", villageId);
			startActivity(intent);
			break;
		case R.id.cy_rent :
			intent=new Intent(cey,RentHouseActivity.class);
			intent.putExtra("villageId", villageId);
			startActivity(intent);
			break;
		default:
			break;
		}

	}

	// 获取手机的宽度和高度
	public void getDisplaymetu() {
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		height = metrics.heightPixels;
		width = metrics.widthPixels;
		// 设置pageview 的宽度和高度
		page.setLayoutParams(new FrameLayout.LayoutParams(
				(int) (width * 1f + 0.5f), (int) (height * 0.3f + 0.5f)));
	}

	private boolean isInstallByread(String packageName) {
		return new File("/data/data/" + packageName).exists();
	}

	// 获取图片
	public void sendrequest() {
		HttpUtils http = new HttpUtils(5000);
		http.send(HttpMethod.GET, IMAGEURL + "?villageId=" + villageId,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {

					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						Gson gson = new Gson();
						try {
							JSONObject object = new JSONObject(arg0.result);
							JSONArray array = object.getJSONArray("data");
							for (int k = 0; k < array.length(); k++) {
								bbb.add(gson.fromJson(array.get(k).toString(),
										HosueImage.class));
							}
							imagebean.setData(bbb);
							System.out.print(imagebean);
							  adapt=new RentHouseViewPagerAdapter(imagebean.getData(), cey);
							  page.setAdapter(adapt);
							  page.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
							  
							  @Override 
							  public void onPageSelected(int arg0) { 
							  
							  }
							  
							  @Override 
							  public void onPageScrolled(int arg0, float arg1, int arg2)
							  { 
							  
							  }
							  
							  @Override 
							  public void onPageScrollStateChanged(int arg0) { 
							  
							 } });
							System.out.print(imagebean);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						// "status":"0","msg":"暂无上传图片","exmsg":null,"data":[{"id":"10","villageId":"1","source":"1","orders":"1","title":"1"}]}
					}
				});
	}

	public void getdetails() {
		MyHttpUtil http = new MyHttpUtil(cey, "正在加载...*^_^*");
		http.send(HttpMethod.GET, detailsURl + "/" + villageId, null,
				new MyHttpCallback() {

					@Override
					public void onSuccessResult(String result) {
						Gson gson = new Gson();
						try {
							JSONObject jsonObject = new JSONObject(result);
							areabean = gson.fromJson(
									jsonObject.getString("data"),
									VillageBean.class);
							initdata();
						} catch (JSONException e) {
							Toast.makeText(cey, "网络错误", 0).show();
						}
					}

					@Override
					public void onLoadingResult(long total, long current,
							boolean isUploading) {

					}

					@Override
					public void onFailureResult(String msg) {
						Toast.makeText(cey, "网络错误", 0).show();

					}
				});
	}
}
