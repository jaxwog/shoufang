package com.example.housefinded.fragement;

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
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.Volley;
import com.example.CircleImage.CircleImageView;
import com.example.CircleImage.LinearGridView;
import com.example.Task.UtilDialog;
import com.example.Tools.SharePreferences;
import com.example.Tools.MyHttpUtil.MyHttpCallback;
import com.example.housefinded.MainActivity;
import com.example.housefinded.R;
import com.example.housefinded.activity.Browsehistory;
import com.example.housefinded.activity.CalActivity;
import com.example.housefinded.activity.CollectionActivvity;
import com.example.housefinded.activity.ComplainActivity;
import com.example.housefinded.activity.LoanActivityfrom;
import com.example.housefinded.activity.MortgageActivvity;
import com.example.housefinded.activity.MyPublishActivity;
import com.example.housefinded.activity.RentalHouse;
import com.example.housefinded.activity.SeeHouseList;
import com.example.housefinded.activity.SellHouseActivity;
import com.example.housefinded.activity.TaxcalculationActivity;
import com.example.housefinded.activity.UserDetails;
import com.example.housefinded.activity.UserLogin;
import com.example.housefinded.adapter.GridviewAdapt;
import com.example.housefinded.db.SqlHelpCRUD;
import com.example.housefinded.net.HttpUrl;
import com.example.javabean.House;
import com.example.javabean.UserBean;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.db.sqlite.CursorUtils.FindCacheSequence;
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
import android.util.LruCache;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MineFragment extends BaseFragement implements OnClickListener {

	private BadgeView b;
	private ImageView imageview1;
	private CircleImageView userimage;
	private LinearGridView gridview;
	private GridviewAdapt adapt;
	private TextView publish_total,mycollection;
	private Map<String, Object> mapt;
	private List<Map<String, Object>> map;
	private LinearLayout ortgaglayout, taxlayout,ll_collection,mine_mypublish;
	String gridtitle[] = { "浏览记录", "投资收益提醒", "货款还款提醒", "投诉", "建议", "审批流程",
			"帮你卖房", "帮你出租", "评估房子" };
	int imagedraw[] = { R.drawable.z_liulan, R.drawable.z_touzi,
			R.drawable.z_tixing, R.drawable.z_tousu, R.drawable.z_liuyan,
			R.drawable.z_shenpi, R.drawable.z_mai, R.drawable.z_chuzu,
			R.drawable.z_zhao };
	UtilDialog dialog;
	Intent intent;
	private TextView mianname;
	UserBean bean;
	private List<String> hList = new ArrayList<String>();
	private SqlHelpCRUD sqlCRUD;
	private String LIST_URL = HttpUrl.BASE_URL
			+ "rest/entrustHouse/getByClient";
	private String RENT_URL = HttpUrl.BASE_URL
			+ "rest/tenement/showByMobileClient";
	private String url=HttpUrl.BASE_URL+"rest/collection/ShowCollectionInfo";
	 int totalnum=0;
	@SuppressLint("NewApi")
	@Override
	public View initView() {
		View view = View.inflate(getActivity(), R.layout.activity_f2, null);
		map = new ArrayList<Map<String, Object>>();
		imageview1 = (ImageView) view.findViewById(R.id.imageView1_1);
		imageview1.setVisibility(View.GONE);
		userimage = (CircleImageView) view.findViewById(R.id.piture_1);
		ortgaglayout = (LinearLayout) view.findViewById(R.id.Mortgag_button);
		taxlayout = (LinearLayout) view.findViewById(R.id.taxbutton);
		ll_collection = (LinearLayout) view.findViewById(R.id.ll_collection);
		publish_total=(TextView)view.findViewById(R.id.publish_total);
		mycollection=(TextView)view.findViewById(R.id.mycollection);
		mine_mypublish=(LinearLayout)view.findViewById(R.id.mine_mypublish);
		mianname=(TextView)view.findViewById(R.id.mianname);
		sqlCRUD = new SqlHelpCRUD(getActivity());
		
		ll_collection.setOnClickListener(this);
		ortgaglayout.setOnClickListener(this);
		taxlayout.setOnClickListener(this);
		mine_mypublish.setOnClickListener(this);
		// GridView 的跳转intent
		intent = new Intent();
		
		
		userimage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (SharePreferences.islogin(getActivity())) {
					Intent userintent = new Intent();
					userintent.setClass(getActivity(), UserDetails.class);
					startActivity(userintent);

				} else {
					Intent userintent = new Intent();
					userintent.setClass(getActivity(), UserLogin.class);
					startActivity(userintent);
					// startActivityForResult(userintent, 1);
				}
			}
		});

	/*	b = new BadgeView(getActivity().getApplicationContext(), imageview1);
		b.setText("2");// 设置为读的信息数量
		// 设置显示的位置
		b.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
		b.show();*/

		// ���ü��
		// b.setBadgeMargin(15, 10);
		// �����������ɫ
		// b.setTextColor(Color.BLUE);
		// ���ñ���ɫ
		// b.setBadgeBackgroundColor(Color.parseColor("#A4C639"));
		// ���ñ���ͼƬ

		gridview = (LinearGridView) view.findViewById(R.id.gridview);
		initview();
		adapt = new GridviewAdapt(getActivity().getApplicationContext(), map);
		gridview.setAdapter(adapt);
		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				switch (position) {
				case 0:
					intent.setClass(getActivity(), SeeHouseList.class);
					hList = sqlCRUD.getAllSeeHouse();
					intent.putExtra("houselist", (Serializable) hList);
					startActivity(intent);
					break;
				case 1:
					Toast.makeText(getActivity().getApplicationContext(),
							"功能未实现", Toast.LENGTH_LONG).show();
					/*intent.setClass(getActivity(), UserDetails.class);
					startActivity(intent);*/
					break;
				case 2:
					Toast.makeText(getActivity().getApplicationContext(),
							"功能未实现", Toast.LENGTH_LONG).show();
					break;
				case 3:
					Toast.makeText(getActivity().getApplicationContext(),
							"功能未实现", Toast.LENGTH_LONG).show();
					/*if(SharePreferences.islogin(getActivity())){
						intent.setClass(getActivity(), ComplainActivity.class);
						startActivity(intent);
						}else{
							intent.setClass(getActivity(), UserLogin.class);
							intent.putExtra("jianyi", "jianyi");
							startActivity(intent);
						}*/
					break;
				case 4:
					if(SharePreferences.islogin(getActivity())){
					intent.setClass(getActivity(), ComplainActivity.class);
					startActivity(intent);
					}else{
						intent.setClass(getActivity(), UserLogin.class);
						intent.putExtra("jianyi", "jianyi");
						startActivity(intent);
					}
					break;
				case 5:
					intent.setClass(getActivity(), LoanActivityfrom.class);
					intent.putExtra("jianyi", "jianyi");
					startActivity(intent);
					break;
				case 6:
					
					if(SharePreferences.islogin(getActivity())){
						intent.setClass(getActivity(), SellHouseActivity.class);
						startActivity(intent);
						}else{
							intent.setClass(getActivity(), UserLogin.class);
							startActivity(intent);
						}

					break;
				case 7:
					if (SharePreferences.islogin(getActivity())) {
						intent.setClass(getActivity(), RentalHouse.class);
						startActivity(intent);
						}else{
							intent.setClass(getActivity(), UserLogin.class);
							startActivity(intent);
						}
					break;
				default:
					  intent.setClass(getActivity(), CalActivity.class);
					 startActivity(intent);
					break;
				}
			}

		});
		return view;
	}

	@Override
	public void initDate() {
		// TODO Auto-generated method stub

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

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
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
							SharePreferences.savaUserbean(bean, getActivity()
									.getApplicationContext());
						} catch (JSONException e) {

						}

					}
				});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_collection:             //我的收藏
			intent.setClass(getActivity(), CollectionActivvity.class);
			hList = sqlCRUD.getAllSeeHouse();
			intent.putExtra("houselist", (Serializable) hList);
			startActivity(intent);
			
			break;
		
		
		// 房贷计算器点击事件
		case R.id.Mortgag_button:
			intent.setClass(getActivity(), MortgageActivvity.class);
			startActivity(intent);
			
			break;
		case R.id.taxbutton:
			intent.setClass(getActivity(), TaxcalculationActivity.class);
			startActivity(intent);
			
			break;
		case R.id.mine_mypublish:
			intent.setClass(getActivity(), MyPublishActivity.class);
			startActivity(intent);
			break;
		default:
			break;

		}

	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		initviewto();//加载头像
		getdata();//获取我的收藏数
		getpublishnum();//获取我的发布数
		
	}
	
	@SuppressLint("NewApi")
	public void initviewto(){
		if (SharePreferences.islogin(getActivity().getApplicationContext())) {
			bean = SharePreferences.readuserbean(getActivity());
			if (bean != null) {
				if (bean.getHeadImg() != null && bean.getHeadImg() != "") {
					RequestQueue mQueue = Volley.newRequestQueue(getActivity());
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
				if(bean.getShowName()!="" && bean.getShowName()!=null){
					mianname.setText(bean.getShowName());
				}
			}
		}
	}
	public void getpublishnum(){
		if(SharePreferences.islogin(getActivity())){
						HttpUtils http1=new HttpUtils(6000);
						http1.configCurrentHttpCacheExpiry(4000);
						RequestParams params = new RequestParams();
						params.addBodyParameter("userId", bean.getId() + "");
						params.addBodyParameter("currentPage", "1");
						params.addBodyParameter("pageSize", "10");
						http1.send(HttpRequest.HttpMethod.POST, RENT_URL, params,new RequestCallBack<String>() {

							@Override
							public void onFailure(HttpException arg0,
									String arg1) {
								// TODO Auto-generated method stub
								
							}

							@Override
							public void onSuccess(ResponseInfo<String> arg0) {
								// TODO Auto-generated method stub
								
								try {
									JSONObject object = new JSONObject(arg0.result);
									JSONObject object2 = (JSONObject) object.get("page");
									publish_total.setText(object2.getString("totalRows"));// 共有的数据量
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						});
					}
	}
	
	public void getdata(){
		if(SharePreferences.islogin(getActivity())){
			bean=SharePreferences.readuserbean(getActivity());
			HttpUtils utils=new HttpUtils(8000);
			utils.configCurrentHttpCacheExpiry(2000);
			RequestParams params=new RequestParams();
			params.addBodyParameter("userId", bean.getId()+"");
			utils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

				@Override
				public void onFailure(HttpException arg0, String arg1) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onSuccess(ResponseInfo<String> arg0) {
					JSONObject object;
					try {
						object = new JSONObject(arg0.result);
						JSONArray array=object.getJSONArray("data");
						if(array==null){
							mycollection.setText("0");
						}else{
							mycollection.setText(array.length()+"");	
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
					
				}
			});
		}
		else{
		
		}
	}
}