package com.example.housefinded.activity;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.example.Tools.MyHttpUtil;
import com.example.Tools.MyHttpUtil.MyHttpCallback;
import com.example.housefinded.R;
import com.example.housefinded.adapter.MyInvestAdapter;
import com.example.housefinded.net.HttpUrl;
import com.example.javabean.InvestProject;
import com.example.javabean.InvestProjectBean;
import com.google.gson.Gson;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class InvestAcitivity extends Activity {
	private ImageView iv_back;
	private ImageView iv_share;
	private ListView listView;
	private MyInvestAdapter adapter;
	private InvestAcitivity act;
	private MyHttpUtil mhu;
	private String url=HttpUrl.BASE_URL+"rest/projects/getList";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.invest_activity);
		act=this;
		mhu=new MyHttpUtil(act, "正在拼命加载中...");
		returnback();
		onKeyShare();
		unitUI();
	}

	private void unitUI() {
		listView=(ListView) findViewById(R.id.listView);
		
		RequestParams params=new RequestParams();
		params.addBodyParameter("pageSize", "10");
		params.addBodyParameter("currentPage", "1");
		
		mhu.send(HttpMethod.POST, url, params, new MyHttpCallback() {
			
			@Override
			public void onSuccessResult(String result) {
				// TODO Auto-generated method stub
				System.out.println("result"+result);
				parseJson(result);
			}
			
			@Override
			public void onLoadingResult(long total, long current, boolean isUploading) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onFailureResult(String msg) {
				// TODO Auto-generated method stub
				Toast.makeText(act, "联网失败", 0).show();
			}
		});
		
		
		/*adapter=new MyInvestAdapter(this);
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(act, InvestUIAcitivity.class);
				startActivity(intent);
			}
		});*/
	}

private InvestProjectBean bean;
	protected void parseJson(String result) {
		// TODO Auto-generated method stub
		Gson gson=new Gson();
		bean=gson.fromJson(result, InvestProjectBean.class);
		adapter=new MyInvestAdapter(bean,this);
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(act, InvestUIAcitivity.class);
				InvestProject project=bean.getList().get(arg2);
				
				Bundle bundle=new Bundle();
				bundle.putSerializable("project", project);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}

	private void onKeyShare() {
		// TODO Auto-generated method stub
		iv_share = (ImageView) findViewById(R.id.iv_share);

		iv_share.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				showShare();
			}
		});

	}

	public void showShare() {
		ShareSDK.initSDK(this);
		OnekeyShare oks = new OnekeyShare();
		// 关闭sso授权
		oks.disableSSOWhenAuthorize();

		// 分享时Notification的图标和文字 2.5.9以后的版本不调用此方法
		// oks.setNotification(R.drawable.ic_launcher,
		// getString(R.string.app_name));
		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		oks.setTitle(getString(R.string.share));
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		oks.setTitleUrl("http://sharesdk.cn");
		// text是分享文本，所有平台都需要这个字段
		oks.setText("我是分享文本");
		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		oks.setImagePath("/sdcard/test.jpg");// 确保SDcard下面存在此张图片
		// url仅在微信（包括好友和朋友圈）中使用
		oks.setUrl("http://sharesdk.cn");
		// comment是我对这条分享的评论，仅在人人网和QQ空间使用
		oks.setComment("我是测试评论文本");
		// site是分享此内容的网站名称，仅在QQ空间使用
		oks.setSite(getString(R.string.app_name));
		// siteUrl是分享此内容的网站地址，仅在QQ空间使用
		oks.setSiteUrl("http://sharesdk.cn");

		// 启动分享GUI
		oks.show(this);
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
	
}
