package com.example.housefinded.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.example.CircleImage.CircleImageView;
import com.example.CircleImage.LinearGridView;
import com.example.Task.UtilDialog;
import com.example.Tools.SharePreferences;
import com.example.housefinded.R;
import com.example.housefinded.adapter.GridviewAdapt;
import com.example.housefinded.db.SqlHelpCRUD;
import com.example.housefinded.net.HttpUrl;
import com.example.javabean.House;
import com.example.javabean.UserBean;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.readystatesoftware.viewbadger.BadgeView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.LruCache;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MineActivity extends Activity implements OnClickListener {
	private MineActivity act;
	private BadgeView b;
	private ImageView imageview1, iv_back;
	private CircleImageView userimage;
	private LinearGridView gridview;
	private LinearLayout ortgaglayout, taxlayout, ll_collection,linear_mypublish;
	private GridviewAdapt adapt;
	private Map<String, Object> mapt;
	private List<Map<String, Object>> map;
	private String RENT_URL = HttpUrl.BASE_URL
			+ "rest/tenement/showByMobileClient";
	private String url = HttpUrl.BASE_URL
			+ "rest/collection/ShowCollectionInfo";
	String gridtitle[] = { "浏览记录", "投资收益提醒", "货款还款提醒", "投诉", "建议", "审批流程",
			"帮你卖房", "帮你出租", "评估房子" };
	int imagedraw[] = { R.drawable.z_liulan, R.drawable.z_touzi,
			R.drawable.z_tixing, R.drawable.z_tousu, R.drawable.z_liuyan,
			R.drawable.z_shenpi, R.drawable.z_mai, R.drawable.z_chuzu,
			R.drawable.z_zhao };
	UtilDialog dialog;
	Intent intent;
	UserBean bean;
	private List<House> hList = new ArrayList<House>();
	private SqlHelpCRUD sqlCRUD;
	private TextView minepublish, minesell;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mine_activity);

		act = this;
		initView();
		returnback();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getdata();// 获取我的收藏数
		getpublishnum();// 获取我的发布数
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

	@SuppressLint("NewApi")
	private void initView() {

		map = new ArrayList<Map<String, Object>>();
		sqlCRUD = new SqlHelpCRUD(act);
		imageview1 = (ImageView) findViewById(R.id.imageView1_1);
		imageview1.setVisibility(View.GONE);
		userimage = (CircleImageView) findViewById(R.id.piture_1);
		ortgaglayout = (LinearLayout) findViewById(R.id.Mortgag_button);
		taxlayout = (LinearLayout) findViewById(R.id.taxbutton);
		ll_collection = (LinearLayout) findViewById(R.id.ll_collection);
		linear_mypublish=(LinearLayout)findViewById(R.id.linear_mypublish);
		minepublish = (TextView) findViewById(R.id.minepublish);
		minesell = (TextView) findViewById(R.id.minesell);
		ortgaglayout.setOnClickListener(this);
		taxlayout.setOnClickListener(this);
		ll_collection.setOnClickListener(this);
		linear_mypublish.setOnClickListener(this);
		// GridView 的跳转intent
		intent = new Intent();
		if (SharePreferences.islogin(act.getApplicationContext())) {
			bean = SharePreferences.readuserbean(act);
			if (bean != null) {
				if (bean.getHeadImg() != null && bean.getHeadImg() != "") {
					RequestQueue mQueue = Volley.newRequestQueue(act);
					final LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(
							20);
					ImageCache imageCache = new ImageCache() {
						@SuppressLint("NewApi")
						@Override
						public void putBitmap(String key, Bitmap value) {
							lruCache.put(key, value);
						}

						@SuppressLint("NewApi")
						@Override
						public Bitmap getBitmap(String key) {
							return lruCache.get(key);
						}
					};
					ImageLoader imageLoader = new ImageLoader(mQueue,
							imageCache);
					ImageListener listener = ImageLoader.getImageListener(
							userimage, R.drawable.tx, R.drawable.tx);
					imageLoader.get(HttpUrl.BASE_URL + bean.getHeadImg(),
							listener);
					// BitmapUtils bitmapUtils = new
					// BitmapUtils(getActivity().getApplicationContext());
					// 根据图片路径加载头像
					// bitmapUtils.display(userimage,HttpUrl.BASE_URL+bean.getHeadImg());
					// 默认加载失败设置的图片
					// bitmapUtils.configDefaultLoadFailedImage(R.drawable.tx);

				}
			}
		}

		userimage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (SharePreferences.islogin(act)) {
					Intent userintent = new Intent();
					userintent.setClass(act, UserDetails.class);
					startActivity(userintent);

				} else {
					Intent userintent = new Intent();
					userintent.setClass(act, UserLogin.class);
					startActivity(userintent);
					// startActivityForResult(userintent, 1);
				}
			}
		});

		/*
		 * b = new BadgeView(act.getApplicationContext(), imageview1);
		 * b.setText("2");// 设置为读的信息数量 // 设置显示的位置
		 * b.setBadgePosition(BadgeView.POSITION_TOP_RIGHT); b.show();
		 */

		// ���ü��
		// b.setBadgeMargin(15, 10);
		// �����������ɫ
		// b.setTextColor(Color.BLUE);
		// ���ñ���ɫ
		// b.setBadgeBackgroundColor(Color.parseColor("#A4C639"));
		// ���ñ���ͼƬ

		gridview = (LinearGridView) findViewById(R.id.gridview);
		initview();

		adapt = new GridviewAdapt(act.getApplicationContext(), map);
		gridview.setAdapter(adapt);
		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				switch (position) {
				case 0:
					intent.setClass(act, Browsehistory.class);
					startActivity(intent);
					break;
				case 1:
					Toast.makeText(act.getApplicationContext(), "该界面正在维护中...",
							Toast.LENGTH_LONG).show();
					intent.setClass(act, UserDetails.class);
					startActivity(intent);
					break;
				case 2:
					Toast.makeText(act.getApplicationContext(), "该界面正在维护中...",
							Toast.LENGTH_LONG).show();
					break;
				case 3:
					if (SharePreferences.islogin(act)) {
						intent.setClass(act, ComplainActivity.class);
						startActivity(intent);
					} else {
						intent.setClass(act, UserLogin.class);
						intent.putExtra("jianyi", "jianyi");
						startActivity(intent);
					}
					break;
				case 4:
					if (SharePreferences.islogin(act)) {
						intent.setClass(act, ComplainActivity.class);
						startActivity(intent);
					} else {
						intent.setClass(act, UserLogin.class);
						intent.putExtra("jianyi", "jianyi");
						startActivity(intent);
					}
					break;
				case 5:
					intent.setClass(act, LoanActivityfrom.class);
					intent.putExtra("jianyi", "jianyi");
					startActivity(intent);
					break;
				case 6:
					if (SharePreferences.islogin(act)) {
						intent.setClass(act, SellHouseActivity.class);
						startActivity(intent);
					} else {
						intent.setClass(act, UserLogin.class);
						startActivity(intent);
					}

					break;
				case 7:
					if (SharePreferences.islogin(act)) {
						intent.setClass(act, RentalHouse.class);
						startActivity(intent);
					} else {
						intent.setClass(act, UserLogin.class);
						startActivity(intent);
					}
					break;
				default:
					intent.setClass(act, CalActivity.class);
					startActivity(intent);
					break;
				}
			}

		});
	}

	public void initview() {

		for (int i = 0; i < gridtitle.length; i++) {
			// mapt要在for循环中产生对象，这样就不会产生覆盖的现象了
			mapt = new HashMap<String, Object>();
			mapt.put("title1", gridtitle[i]);
			mapt.put("image1", imagedraw[i]);
			map.add(mapt);
		}

	}

	public void getuserdata() {
		HttpUtils http1 = new HttpUtils();
		http1.send(HttpRequest.HttpMethod.GET, HttpUrl.BASE_URL
				+ "rest/sysUser/select/" + bean.getId(),
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {

					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						// TODO Auto-generated method stub
						Gson gson = new Gson();
						try {
							JSONObject json = new JSONObject(arg0.result);
							bean = gson.fromJson(json.getString("data"),
									UserBean.class);
							SharePreferences.savaUserbean(bean,
									act.getApplicationContext());
						} catch (JSONException e) {

						}

					}
				});

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.ll_collection: // 我的收藏
			intent.setClass(act, CollectionActivvity.class);
			hList = sqlCRUD.getAllColHouse();

			intent.putExtra("houselist", (Serializable) hList);
			startActivity(intent);

			break;
		// 房贷计算器点击事件
		case R.id.Mortgag_button:
			intent.setClass(act, MortgageActivvity.class);
			startActivity(intent);
			break;
		case R.id.taxbutton:
			intent.setClass(act, TaxcalculationActivity.class);
			startActivity(intent);
			break;
		case R.id.linear_mypublish :
			intent.setClass(act, MyPublishActivity.class);
			startActivity(intent);
			break;
		default:
			break;

		}

	}

	public void getdata() {
		if (SharePreferences.islogin(MineActivity.this)) {
			bean = SharePreferences.readuserbean(MineActivity.this);
			HttpUtils utils = new HttpUtils(8000);
			utils.configCurrentHttpCacheExpiry(2000);
			RequestParams params = new RequestParams();
			params.addBodyParameter("userId", bean.getId() + "");
			utils.send(HttpMethod.POST, url, params,
					new RequestCallBack<String>() {

						@Override
						public void onFailure(HttpException arg0, String arg1) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onSuccess(ResponseInfo<String> arg0) {
							JSONObject object;
							try {
								object = new JSONObject(arg0.result);
								JSONArray array = object.getJSONArray("data");
								if (array == null) {
									minesell.setText("0");
								} else {
									minesell.setText(array.length() + "");
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}

						}
					});
		}
	}

	public void getpublishnum() {
		if (SharePreferences.islogin(MineActivity.this)) {
			HttpUtils http1 = new HttpUtils(6000);
			http1.configCurrentHttpCacheExpiry(4000);
			RequestParams params = new RequestParams();
			params.addBodyParameter("userId", bean.getId() + "");
			params.addBodyParameter("currentPage", "1");
			params.addBodyParameter("pageSize", "10");
			http1.send(HttpRequest.HttpMethod.POST, RENT_URL, params,
					new RequestCallBack<String>() {

						@Override
						public void onFailure(HttpException arg0, String arg1) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onSuccess(ResponseInfo<String> arg0) {
							// TODO Auto-generated method stub

							try {
								JSONObject object = new JSONObject(arg0.result);
								JSONObject object2 = (JSONObject) object
										.get("page");
								minepublish.setText(object2
										.getString("totalRows"));// 共有的数据量
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
		}
	}
}
