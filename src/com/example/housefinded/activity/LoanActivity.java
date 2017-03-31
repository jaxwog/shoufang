package com.example.housefinded.activity;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.example.housefinded.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

public class LoanActivity extends Activity {
	private RadioButton rb_diya, rb_credit;
	private ImageView iv_back, iv_share, iv_phone;
	private TextView tv_introduce;
	private TextView tv_request;
	private LoanActivity act;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 取消标题栏
		setContentView(R.layout.loan_activity);

		act = this;
		rb_diya = (RadioButton) findViewById(R.id.rb_diya);
		tv_introduce = (TextView) findViewById(R.id.tv_introduce);
		rb_diya.setChecked(true);

		bitDiya();
		bitCredit();
		returnback();
		onKeyShare();
		addRequest();
		addTelphone();
	}

	private void addTelphone() {
		// TODO Auto-generated method stub
		iv_phone = (ImageView) findViewById(R.id.iv_phone);
		iv_phone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(act);
				builder.setTitle("电话");
				builder.setMessage("400-850-5689");
				builder.setPositiveButton("呼叫",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								Intent intent = new Intent(Intent.ACTION_CALL,
										Uri.parse("tel:" + "400-850-5689"));
								startActivity(intent);
							}
						});
				builder.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								dialog.cancel();
							}
						});
				builder.show();
			}
		});

	}

	private void addRequest() {
		// TODO Auto-generated method stub
		tv_request = (TextView) findViewById(R.id.tv_requset);
		tv_request.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(act, LoanRequestActivity.class);
				intent.putExtra("type", "1");
				startActivity(intent);
			}
		});
	}

	private void bitCredit() {
		// TODO Auto-generated method stub
		rb_credit = (RadioButton) findViewById(R.id.rb_credit);
		rb_credit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tv_introduce
						.setText("信用贷款是指以借款人的信誉发放的贷款， 借款人不需要提供担保。其特征就是债务人无需提供抵押品或第三方担保仅凭自己的信誉就能取得贷款，并以借款人信用程度作为还款保证的。这种信用贷款是中国银行长期以来的主要放款方式。");
				tv_introduce.setTextColor(Color.GRAY);
				tv_introduce.setTextSize(20);
			}
		});
	}

	private void bitDiya() {
		// TODO Auto-generated method stub
		rb_diya.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tv_introduce
						.setText("抵押贷款指借款者以一定的抵押品作为物品保证向银行取得贷款。它是银行的一种放款形式、抵押品通常包括有价证劵、国债劵、各种股票、房地产、以及货物的提单、栈单或其他各种证明物品所有权的单据。贷款到期，借款者必须如数归还，否则银行有权处理抵押品，作为一种补偿。");
				tv_introduce.setTextColor(Color.GRAY);
				tv_introduce.setTextSize(20);
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
