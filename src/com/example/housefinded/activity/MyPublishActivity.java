package com.example.housefinded.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.Tools.MyHttpUtil;
import com.example.Tools.MyHttpUtil.MyHttpCallback;
import com.example.Tools.SharePreferences;
import com.example.housefinded.R;
import com.example.housefinded.adapter.GroupAdapt;
import com.example.housefinded.adapter.ListShowppadapt;
import com.example.housefinded.adapter.ListShowpublishadapt;
import com.example.housefinded.net.HttpUrl;
import com.example.javabean.PublicHouseBean;
import com.example.javabean.PublicRenthouseBean;
import com.example.javabean.RentHousebaen;
import com.example.javabean.SellHousebean;
import com.example.javabean.UserBean;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MyPublishActivity extends Activity implements OnClickListener {
	private RelativeLayout layout;
	private ImageView back;
	private PopupWindow popupWindow;
	private Button bb;
	private TextView title, noitem;
	private ListView list;
	private PublicHouseBean item;
	private PublicRenthouseBean item2;
	private List<SellHousebean> listbean;
	private List<RentHousebaen> listbean2;
	private String LIST_URL = HttpUrl.BASE_URL
			+ "rest/entrustHouse/getByClient";
	private String RENT_URL = HttpUrl.BASE_URL
			+ "rest/tenement/showByMobileClient"; 
	private UserBean bbean;
	private int type;// 1卖房信息列表，2发布出租信息列表

	/*
	 * private ListView lv_group; private View view; private List<String>
	 * groups;
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mypublish);
		initview();

	}

	public void initview() {
		layout = (RelativeLayout) findViewById(R.id.publish_title);
		back = (ImageView) layout.findViewById(R.id.btnback);
		bb = (Button) layout.findViewById(R.id.btnswitch);
		title = (TextView) layout.findViewById(R.id.titlename);
		noitem = (TextView) findViewById(R.id.noitem);
		list = (ListView) findViewById(R.id.publish_listitem);
		bb.setVisibility(View.GONE);
		//bb.setText("出租列表");
		title.setText("我的发布");
		back.setOnClickListener(this);
		//bb.setOnClickListener(this);
		initdata2();// 默认显示的是卖房信息列表
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnback:
			finish();
			break;
		/*case R.id.btnswitch:
			if (type == 2) {
				bb.setText("卖房列表");
				initdata2();// 显示出租列表
			} else if (type == 1) {
				bb.setText("出租列表");
				initdata();// 显示卖房列表
			}
			break;*/
		default:
			break;
		}

	}

	// 添加卖房信息列表
	/*public void initdata() {
		item = new PublicHouseBean();
		listbean = new ArrayList<SellHousebean>();
		type = 2;
		// 判断是否有用户登录
		if (SharePreferences.islogin(MyPublishActivity.this)) {
			bbean = SharePreferences.readuserbean(MyPublishActivity.this);
			MyHttpUtil http = new MyHttpUtil(MyPublishActivity.this,
					"正在加载数据,*^_^*");
			RequestParams params = new RequestParams();
			params.addBodyParameter("user_id", bbean.getId() + "");
			params.addBodyParameter("currentPage", "1");
			params.addBodyParameter("pageSize", "10");
			http.send(HttpRequest.HttpMethod.POST, LIST_URL, params,
					new MyHttpCallback() {

						@Override
						public void onSuccessResult(String result) {
							try {
								Gson gson = new Gson();
								JSONObject object = new JSONObject(result);
								JSONObject object2 = (JSONObject) object
										.get("page");
								JSONArray array = object.getJSONArray("list");
								if (array.length() > 0) {
									noitem.setVisibility(View.GONE);
									list.setVisibility(View.VISIBLE);
									for (int k = 0; k < array.length(); k++) {
										listbean.add(gson.fromJson(array.get(k)
												.toString(),
												SellHousebean.class));
									}
									item.setList(listbean);
									list.setAdapter(new ListShowpublishadapt(
											MyPublishActivity.this, item));
									if(type==2){
										list.setOnItemClickListener(new OnItemClickListener() {

											@Override
											public void onItemClick(
													AdapterView<?> parent,
													View view, int position,
													long id) {
										
											}
										});
									}
								} else {
									noitem.setVisibility(View.VISIBLE);
									list.setVisibility(View.GONE);
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}

						@Override
						public void onLoadingResult(long total, long current,
								boolean isUploading) {

						}

						@Override
						public void onFailureResult(String msg) {
							noitem.setVisibility(View.VISIBLE);
							list.setVisibility(View.GONE);
						}
					});
		} else {
			noitem.setVisibility(View.VISIBLE);
			list.setVisibility(View.GONE);
		}
	}*/

	// 添加出租信息列表
	public void initdata2() {
		type = 1;
		item2 = new PublicRenthouseBean();
		listbean2 = new ArrayList<RentHousebaen>();

		// 判断是否有用户登录
		if (SharePreferences.islogin(MyPublishActivity.this)) {
			bbean = SharePreferences.readuserbean(MyPublishActivity.this);
			MyHttpUtil http2 = new MyHttpUtil(MyPublishActivity.this,
					"正在加载数据,*^_^*");
			RequestParams params = new RequestParams();
			params.addBodyParameter("userId", bbean.getId() + "");
			params.addBodyParameter("currentPage", "1");
			params.addBodyParameter("pageSize", "10");
			http2.send(HttpRequest.HttpMethod.POST, RENT_URL, params,
					new MyHttpCallback() {

						@Override
						public void onSuccessResult(String result) {
							try {
								Gson gson = new Gson();
								JSONObject object = new JSONObject(result);
								JSONArray array = object.getJSONArray("list");
								if (array.length() > 0) {
									/*noitem.setVisibility(View.GONE);
									list.setVisibility(View.VISIBLE);*/
									for (int k = 0; k < array.length(); k++) {
										listbean2.add(gson.fromJson(array.get(k).toString(),
												RentHousebaen.class));
									}
									
									
									item2.setList(listbean2);
									list.setAdapter(new ListShowppadapt(MyPublishActivity.this, item2));
									System.out.print(item2);
									if (type == 1) {
										list.setOnItemClickListener(new OnItemClickListener() {

											@Override
											public void onItemClick(
													AdapterView<?> parent,
													View view, int position,
													long id) {
												Intent intent = new Intent(
														MyPublishActivity.this,
														DetailItemactivity.class);
												intent.putExtra("RentHousebaen", listbean2.get(position));
												startActivity(intent);

											}
										});
									}
								} else {
									noitem.setVisibility(View.VISIBLE);
									list.setVisibility(View.GONE);
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}

						@Override
						public void onLoadingResult(long total, long current,
								boolean isUploading) {

						}

						@Override
						public void onFailureResult(String msg) {
							noitem.setVisibility(View.VISIBLE);
							list.setVisibility(View.GONE);
						}
					});

		} else {
			noitem.setVisibility(View.VISIBLE);
			list.setVisibility(View.GONE);
		}

	}
	// 显示出不同的数据类型信息

	/*
	 * private void showWindow(View p) { if (popupWindow == null) {
	 * LayoutInflater layoutInflater = (LayoutInflater)
	 * getSystemService(Context.LAYOUT_INFLATER_SERVICE); view =
	 * layoutInflater.inflate(R.layout.liststring, null); lv_group = (ListView)
	 * view.findViewById(R.id.lvGroup); groups = new ArrayList<String>();
	 * groups.add("出租信息列表"); groups.add("卖房信息列表"); GroupAdapt groupAdapter = new
	 * GroupAdapt(this, groups); lv_group.setAdapter(groupAdapter); popupWindow
	 * = new PopupWindow(view, 300, 350); } popupWindow.setFocusable(true);
	 * popupWindow.setOutsideTouchable(true);
	 * popupWindow.setBackgroundDrawable(new BitmapDrawable()); WindowManager
	 * windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
	 * // 显示的位置为:屏幕的宽度的一半-PopupWindow的高度的一半 int xPos =
	 * windowManager.getDefaultDisplay().getWidth() / 2 - popupWindow.getWidth()
	 * / 2; popupWindow.showAsDropDown(p, xPos, 0);
	 * lv_group.setOnItemClickListener(new OnItemClickListener() {
	 * 
	 * @Override public void onItemClick(AdapterView<?> adapterView, View view,
	 * int position, long id) {
	 * 
	 * Toast.makeText(MyPublishActivity.this, groups.get(position), 1) .show();
	 * 
	 * if (popupWindow != null) { popupWindow.dismiss(); } } }); }
	 */

}
