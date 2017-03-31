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
import android.widget.TextView;

public class InvestUIAcitivity extends Activity implements OnClickListener{
	private ImageView iv_back,iv_share,iv_cal,iv_bite_detail;
	private InvestUIAcitivity act;
	private Intent intent;
	private TextView tv_requset,tv_earn,tv_limit,tv_earncan,tv_project,tv_repaymentMethod,tv_closingDate;
	private InvestProject project;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.investui_activity);
		act=this;
		intent=new Intent();
		project=(InvestProject) getIntent().getSerializableExtra("project");
		
		setOnClick();
		setText();
	}
	private void setText() {
		// 年化收益
		tv_earn=(TextView) findViewById(R.id.tv_earn);
		tv_earn.setText("年化收益："+project.getAnnualIncome()+"%");
		
		//融资期限
		tv_limit=(TextView) findViewById(R.id.tv_limit);
		tv_limit.setText("融资期限："+project.getDeadline()+"个月");
		
		//  融资金额
		tv_earncan=(TextView) findViewById(R.id.tv_earncan);
		tv_earncan.setText("融资金额:"+project.getAmount()+"万元");
		
		//  项目名称
		tv_project=(TextView) findViewById(R.id.tv_project);
		tv_project.setText("投资项目:"+project.getName());
		
		//  还款方式
		tv_repaymentMethod=(TextView) findViewById(R.id.tv_repaymentMethod);
		tv_repaymentMethod.setText("融资金额:"+project.getRepaymentMethod());
		
		//  投标倒计时:
		tv_closingDate=(TextView) findViewById(R.id.tv_closingDate);
		tv_closingDate.setText(project.getClosingDate());
	}
	private void setOnClick() {
		//后退
		iv_back = (ImageView) findViewById(R.id.iv_back);
		iv_back.setOnClickListener(this);
		
		//分享
		iv_share = (ImageView) findViewById(R.id.iv_share);
		iv_share.setOnClickListener(this);
		
		// 计算器
		iv_cal=(ImageView) findViewById(R.id.iv_cal);
		iv_cal.setOnClickListener(this);
		
		//立即投资
		tv_requset=(TextView) findViewById(R.id.tv_requset);
		tv_requset.setOnClickListener(this);
		
		//点击看详情
		iv_bite_detail=(ImageView) findViewById(R.id.iv_bite_detail);
		iv_bite_detail.setOnClickListener(this);
		
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
		case R.id.iv_back:
			finish();
			break;
		case R.id.iv_share:
			showShare();
			break;
		case R.id.iv_cal:
			intent.setClass(act, InvCalActivity.class);
			startActivity(intent);
			break;
		case R.id.tv_requset:
			intent.setClass(act, InvRequestActivity.class);
			startActivity(intent);
			break;
		case R.id.iv_bite_detail:
			intent.setClass(act, InvDetailActivity.class);
			
			Bundle bundle=new Bundle();
			bundle.putSerializable("project", project);
			intent.putExtras(bundle);
			startActivity(intent);
			break;

		default:
			break;
		}
	}
}
