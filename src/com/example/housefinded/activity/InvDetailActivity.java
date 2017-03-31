package com.example.housefinded.activity;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.example.housefinded.R;
import com.example.javabean.InvestProject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

public class InvDetailActivity extends Activity implements OnClickListener {
	private ImageView iv_back, iv_share, iv_cal;
	private TextView tv_requset,tv_control_list;
	private RadioButton rb_detail,rb_control,rb_plan,rb_list;
	private Intent intent;
	private InvDetailActivity act;
	private LinearLayout ll_detail;
	private InvestProject project;
	private TextView tv_people,tv_location,tv_rate,tv_use,tv_amount,tv_credit,tv_plan,tv_actualPdate,tv_closingDate;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.investdetail_activity);
		act=this;
		intent=new Intent();
		project=(InvestProject) getIntent().getSerializableExtra("project");
		
		setOnClick();
		setText();
	}

	private void setText() {
		// 融资人
		tv_people=(TextView) findViewById(R.id.tv_people);
		tv_people.setText(project.getFinancier());
		
		// 项目位置
		tv_location=(TextView) findViewById(R.id.tv_location);
		tv_location.setText(project.getPlace());
		
		// 年化利率 
		tv_rate=(TextView) findViewById(R.id.tv_rate);
		tv_rate.setText(project.getAnnualRate());
		
		// 借款用途
		tv_use=(TextView) findViewById(R.id.tv_use);
		tv_use.setText(project.getUse());
		
		// 本期融资金额
		tv_amount=(TextView) findViewById(R.id.tv_amount);
		tv_amount.setText(project.getAmount());
		
		// 本次授信额度
		tv_credit=(TextView) findViewById(R.id.tv_credit);
		tv_credit.setText(project.getCredit());
		
		// 计划还款日期
		tv_plan=(TextView) findViewById(R.id.tv_plan);
		tv_plan.setText(project.getPlanPdate());
		
		// 实际还款日期
		tv_actualPdate=(TextView) findViewById(R.id.tv_actualPdate);
		tv_actualPdate.setText(project.getActualPdate());
		
		// 投标截止时间
		tv_closingDate=(TextView) findViewById(R.id.tv_closingDate);
		tv_closingDate.setText(project.getClosingDate());
	}

	private void setOnClick() {

		// 后退
		iv_back = (ImageView) findViewById(R.id.iv_back);
		iv_back.setOnClickListener(this);

		// 分享
		iv_share = (ImageView) findViewById(R.id.iv_share);
		iv_share.setOnClickListener(this);

		// 计算器
		iv_cal = (ImageView) findViewById(R.id.iv_cal);
		iv_cal.setOnClickListener(this);

		// 立即投资
		tv_requset = (TextView) findViewById(R.id.tv_requset);
		tv_requset.setOnClickListener(this);

		// 项目详情
		ll_detail=(LinearLayout) findViewById(R.id.ll_detail);
		rb_detail = (RadioButton) findViewById(R.id.rb_detail);
		rb_detail.setChecked(true);
		rb_detail.setOnClickListener(this);
		
		//风险控制
		tv_control_list=(TextView) findViewById(R.id.tv_control_list);
		rb_control=(RadioButton) findViewById(R.id.rb_control);
		rb_control.setOnClickListener(this);
		
		//投资计划
		rb_plan=(RadioButton) findViewById(R.id.rb_plan);
		rb_plan.setOnClickListener(this);
		
		//投资列表
		rb_list=(RadioButton) findViewById(R.id.rb_list);
		rb_list.setOnClickListener(this);
		
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_back:             //后退
			finish();
			break;
		case R.id.iv_share:             //分享
			showShare();
			break;
		case R.id.iv_cal:                //投资计算器
			intent.setClass(act, InvCalActivity.class);
			startActivity(intent);
			break;
		case R.id.tv_requset:             //立即投资
			intent.setClass(act, InvRequestActivity.class);
			startActivity(intent);
			break;
			
		case R.id.rb_detail:                 //项目详情
			rb_detail.setChecked(true);
			tv_control_list.setVisibility(View.GONE);
			ll_detail.setVisibility(View.VISIBLE);
			break;
		case R.id.rb_control:                //风险控制
			rb_control.setChecked(true);
			tv_control_list.setText(project.getDamageControl());
			tv_control_list.setVisibility(View.VISIBLE);
			ll_detail.setVisibility(View.GONE);
			break;
		case R.id.rb_plan:                  //投资计划
			rb_plan.setChecked(true);
			tv_control_list.setText(project.getRepaymentPlan());
			tv_control_list.setVisibility(View.VISIBLE);
			ll_detail.setVisibility(View.GONE);
			break;
		case R.id.rb_list:
			rb_list.setChecked(true);//投资列表
			tv_control_list.setText(project.getProjectList());
			tv_control_list.setVisibility(View.VISIBLE);
			ll_detail.setVisibility(View.GONE);
			break;
		default:
			break;
		}
	}

}
