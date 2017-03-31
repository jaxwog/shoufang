package com.example.housefinded.activity;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.example.Tools.MyHttpUtil;
import com.example.Tools.MyHttpUtil.MyHttpCallback;
import com.example.housefinded.R;
import com.example.housefinded.net.HttpUrl;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoanRequestActivity extends Activity {
	private ImageView iv_back;
	private ImageView iv_share;
	private LoanRequestActivity act;
	private TextView tv_commit, tv_req;
	private EditText et_name, et_number, et_des;
	private MyHttpUtil mhu;
	private HttpUrl BaseUrl = new HttpUrl();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.request_activity);
		mhu = new MyHttpUtil(this, "提示");
		et_name = (EditText) findViewById(R.id.et_name);
		et_number = (EditText) findViewById(R.id.et_number);
		et_des = (EditText) findViewById(R.id.et_des);
		act = this;

		tv_req = (TextView) findViewById(R.id.tv_req);
		tv_req.setText("我要贷款");
		returnback();
		onKeyShare();
		addCommit();
	}

	private void addCommit() {
		// TODO Auto-generated method stub
		tv_commit = (TextView) findViewById(R.id.tv_commit);
		tv_commit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					RequestParams rp = new RequestParams();
					rp.addBodyParameter("name", et_name.getText().toString());
					if ("".equals(et_number.getText().toString())) {
						Toast.makeText(act, "电话不能为空", 0).show();
					} else {
						rp.addBodyParameter("phone", et_number.getText()
								.toString());

						rp.addBodyParameter("des", et_des.getText().toString());
						rp.addBodyParameter("type", "1");

						mhu.send(HttpMethod.POST, BaseUrl.BASE_URL
								+ "rest/estate/add", rp, new MyHttpCallback() {

							@Override
							public void onSuccessResult(String result) {
								new AlertDialog.Builder(act)

										.setTitle("标题")

										.setMessage("提交成功")

										.setPositiveButton(
												"确定",
												new DialogInterface.OnClickListener() {

													@Override
													public void onClick(
															DialogInterface dialog,
															int which) {
														// TODO
														// Auto-generated
														// method stub
														finish();
													}
												})

										.show();

							}

							@Override
							public void onLoadingResult(long total,
									long current, boolean isUploading) {

							}

							@Override
							public void onFailureResult(String msg) {
								Toast.makeText(LoanRequestActivity.this,
										"联网失败", 0).show();
							}
						});

					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

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
