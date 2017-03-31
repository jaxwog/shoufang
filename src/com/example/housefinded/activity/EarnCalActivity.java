package com.example.housefinded.activity;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.example.housefinded.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class EarnCalActivity extends Activity {
	private ImageView iv_back;
	private RelativeLayout rl_title;
	private ImageView iv_share;
	private TextView tv_evaluate, tv_des, tv_commit, tv_house_prices,
			tv_month_income, tv_year_income;
	private LinearLayout ll_year_income, ll_month_income;
	private EarnCalActivity act;
	private EditText et_area, et_unit_cost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.cal_activity);
		act = this;
		returnback();
		onKeyShare();
		addRequest();

		tv_evaluate = (TextView) findViewById(R.id.tv_evaluate);
		tv_evaluate.setText("收益计算器");
	}

	private void addRequest() {
		// TODO Auto-generated method stub
		tv_commit = (TextView) findViewById(R.id.tv_commit);
		tv_commit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 描述
				tv_des = (TextView) findViewById(R.id.tv_des);
				tv_des.setText("以上数据仅供参考");
				tv_des.setTextColor(Color.GRAY);

				et_area = (EditText) findViewById(R.id.et_area);
				et_unit_cost = (EditText) findViewById(R.id.et_unit_cost);

				if ("".equals(et_area.getText().toString())) {
					Toast.makeText(act, "请输入面积", 0).show();
				} else if ("".equals(et_unit_cost.getText().toString())) {
					Toast.makeText(act, "请输入每平米价钱", 0).show();
				} else {
					// 获得面积值
					int area = Integer.parseInt(et_area.getText().toString());
					// 获得每平米的单价
					int unit_cost = Integer.parseInt(et_unit_cost.getText()
							.toString());
					// 房价显示
					int hosue_prices = area * unit_cost;
					tv_house_prices = (TextView) findViewById(R.id.tv_house_prices);
					tv_house_prices.setText(hosue_prices + "  元");
					tv_house_prices.setVisibility(View.VISIBLE);

					// 年收益及显示
					float year_income = Math.round(hosue_prices * 18 * 7
							/ (float) 1000);
					tv_year_income = (TextView) findViewById(R.id.tv_year_income);
					tv_year_income.setText(year_income + "  元");

					ll_year_income = (LinearLayout) findViewById(R.id.ll_year_income);
					ll_year_income.setVisibility(View.VISIBLE);

					// 月收益及显示
					float month_income = (float) (Math.round(year_income / 12) / 1.0);
					tv_month_income = (TextView) findViewById(R.id.tv_month_income);
					tv_month_income.setText(month_income + "  元");

					ll_month_income = (LinearLayout) findViewById(R.id.ll_month_income);
					ll_month_income.setVisibility(View.VISIBLE);
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
