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
import com.example.housefinded.adapter.MyHouseCollAdapter;
import com.example.housefinded.bean.HouseBean;
import com.example.housefinded.net.HttpUrl;
import com.example.javabean.CollectBean;
import com.example.javabean.House;
import com.example.javabean.RentHouse;
import com.example.javabean.SecondHouseBean;
import com.example.javabean.UserBean;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class CollectionActivvity extends Activity{
	private ImageView iv_back;
	private TextView tv_houselist;
	private List<House> hidList;
	private List<House> hList=new ArrayList<House>();
	private MyHouseItemAdapter adapter;
	private MyHouseCollAdapter adapter1;
	private ListView listview;
	private TextView house_tv;
	private MyHttpUtil mhu;
	private CollectionActivvity act;
	private String url=HttpUrl.BASE_URL+"rest/collection/ShowCollectionInfo";
	private UserBean userbean;
	private CollectBean collbean;
	private House housebean;//二手房bean
	private RentHouse rentbean;//出租房屋

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.see_house_list);
		
		tv_houselist=(TextView) findViewById(R.id.tv_houselist);
		tv_houselist.setText("我的收藏");
		house_tv = (TextView) findViewById(R.id.house_tv);
		listview = (ListView) findViewById(R.id.seelist);
		act=this;
		mhu=new MyHttpUtil(act, "正在加载数据...*^_^*");
		hidList = (List<House>) getIntent().getSerializableExtra("houselist");
		
		returnback();
		//sendRequest();
		

		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getdata();
	}
	
	private void sendRequest() {
		house_tv = (TextView) findViewById(R.id.house_tv);
		listview = (ListView) findViewById(R.id.seelist);
		RequestParams params= new RequestParams();
		for (int i = 0; i < hidList.size(); i++) {
			 url=HttpUrl.BASE_URL+"rest/secondHouse/get/"+hidList.get(i).getId();
		System.out.println("0000000"+hidList.get(i).getId());
		mhu.send(HttpMethod.GET, url, params, new MyHttpCallback() {
			
			@Override
			public void onSuccessResult(String result) {
				// TODO Auto-generated method stub
				parseJson(result);
			}
			
			@Override
			public void onLoadingResult(long total, long current, boolean isUploading) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onFailureResult(String msg) {
				// TODO Auto-generated method stub
				System.out.println("联网失败");
			}
		});
		}
	}
private HouseBean houseBean;
private SecondHouseBean bean;
private List<CollectBean> bean1;
	protected void parseJson(String result) {
		// TODO Auto-generated method stub
		Gson gson=new Gson();
		houseBean=gson.fromJson(result, HouseBean.class);
		 bean = new SecondHouseBean();
		hList.add(houseBean.getData());
		bean.setList(hList);
		adapter = new MyHouseItemAdapter(bean, this);
		listview.setAdapter(adapter);
		if(hList.size() > 0){
			house_tv.setVisibility(View.GONE);	
		}
		
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(act,
						SecondHouseDetailsActivity.class);
				House house = bean.getList().get(arg2);
				Bundle bundle = new Bundle();
				bundle.putSerializable("house", house);
				intent.putExtras(bundle);
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
	public void getdata(){
		if(SharePreferences.islogin(act)){
			userbean=SharePreferences.readuserbean(act);
			RequestParams params=new RequestParams();
			params.addBodyParameter("userId", userbean.getId()+"");
			mhu.send(HttpMethod.POST, url, params, new MyHttpCallback() {
				@Override
				public void onSuccessResult(String result) {
						parseJsonto(result);	
				}
				
				@Override
				public void onLoadingResult(long total, long current, boolean isUploading) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onFailureResult(String msg) {
					// TODO Auto-generated method stub
			Toast.makeText(act, "网络错误", 0).show();
				}
			});
		}
		else{
			listview.setVisibility(View.GONE);
		}
	}
	protected void parseJsonto(String result) {
		// TODO Auto-generated method stub
		Gson gson=new Gson();
		bean1=new ArrayList<CollectBean>();
		
		try {
			JSONObject jsonObject=new JSONObject(result);
		JSONArray array=jsonObject.getJSONArray("data");
		if(array.length()>0 ){
			house_tv.setVisibility(View.GONE);	
		for(int i=0;i<array.length();i++){
			collbean=new CollectBean();
			collbean=  gson.fromJson(array.get(i).toString(), CollectBean.class);
			System.out.print(collbean);
			bean1.add(collbean);
		}
		adapter1 = new MyHouseCollAdapter(bean1, act);
		listview.setAdapter(adapter1);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, final int position,
					long arg3) {
				final Intent intent=new Intent();
				//二手房信息
				if("1".equals(bean1.get(position).getHouseType())){
					HttpUtils  utils=new HttpUtils();
					utils.send(HttpMethod.GET, HttpUrl.BASE_URL+"rest/secondHouse/get/"+bean1.get(position).getHouseId(), new RequestCallBack<String>() {

						@Override
						public void onFailure(HttpException arg0, String arg1) {
							System.out.print(arg0+arg1);
							
						}

						@Override
						public void onSuccess(ResponseInfo<String> arg0) {
							String ss=arg0.result;
							try {
								JSONObject jsonObject=new JSONObject(ss);
								Gson gson=new Gson();
								housebean=gson.fromJson(jsonObject.getString("data"), House.class);
								System.out.print(housebean);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							intent.putExtra("house", housebean);
							intent.setClass(act, SecondHouseDetailsActivity.class);
							startActivity(intent);
						}
					});
					
				}else if("2".equals(bean1.get(position).getHouseType())){
					//租房信息
					HttpUtils  http3=new HttpUtils();
					http3.send(HttpMethod.GET, HttpUrl.BASE_URL+"rest/tenement/get/"+bean1.get(position).getHouseId(), new RequestCallBack<String>() {

						@Override
						public void onFailure(HttpException arg0, String arg1) {
							System.out.print(arg0+arg1);
							
						}

						@Override
						public void onSuccess(ResponseInfo<String> arg0) {
							String ss=arg0.result;
							try {
								JSONObject jsonObject=new JSONObject(ss);
								Gson gson=new Gson();
								rentbean=gson.fromJson(jsonObject.getString("data"), RentHouse.class);
							intent.putExtra("rentHouse", rentbean);
							intent.setClass(act, RentHouseDetailsActivity.class);
							startActivity(intent);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
				
				}
				/*
				Intent intent = new Intent(act,
						SecondHouseDetailsActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("house", house);
				intent.putExtras(bundle);
				startActivity(intent);
			*/}
		});
		}else{
			listview.setVisibility(View.GONE);	
		}
		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
