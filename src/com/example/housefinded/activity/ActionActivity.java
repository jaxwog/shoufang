package com.example.housefinded.activity;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.example.housefinded.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ActionActivity extends Activity {
	private LinearLayout ll_cal, ll_guide, ll_case;
	private RelativeLayout rl_title;
	private ImageView iv_back;
	private TextView tv_request;
	private ActionActivity act;
	private ImageView share, iv_phone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//
		setContentView(R.layout.action_activity);
		act = this;
		returnback();
		addCal();
		addRequest();
		oneKeyShare();
		addTelPhone();
		addGuide();
		addCase();
	}

	private void addCase() {
		// TODO Auto-generated method stub
		ll_case = (LinearLayout) findViewById(R.id.ll_case);
		ll_case.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(act, CaseActivity.class);
				startActivity(intent);
			}
		});
	}

	private void addGuide() {
		// TODO Auto-generated method stub
		ll_guide = (LinearLayout) findViewById(R.id.ll_guide);
		ll_guide.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(act, GuideActivity.class);
				startActivity(intent);
			}
		});
	}

	private void addTelPhone() {
		// TODO Auto-generated method stub
		iv_phone = (ImageView) findViewById(R.id.iv_phone);
		iv_phone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new Builder(act);
				builder.setTitle("电话")
						.setMessage("400-850-5689")
						.setPositiveButton("拨打",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										Intent intent = new Intent(
												Intent.ACTION_CALL,
												Uri.parse("tel:"
														+ "400-850-5689"));
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
			}
		});
	}

	private void oneKeyShare() {
		// TODO Auto-generated method stub
		share = (ImageView) findViewById(R.id.iv_share);
		share.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showShare();
			}
		});
	}

	/**
	 * 一键分享
	 */
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
		// oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
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

	private void addRequest() {
		// TODO Auto-generated method stub
		tv_request = (TextView) findViewById(R.id.tv_requset);
		tv_request.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(act, NowRequestActivity.class);
				intent.putExtra("type", "2");
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
				finish();

			}
		});

	}

	private void addCal() {
		ll_cal = (LinearLayout) findViewById(R.id.ll_cal);
		ll_cal.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(act, EarnCalActivity.class);
				startActivity(intent);
			}
		});
	}
}
