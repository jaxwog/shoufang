package com.example.housefinded.activity;

import com.example.housefinded.R;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class DeclareActivity extends Activity {
	private ImageView iv_back;
	private TextView tv_declare;
	String text = "    1、一切网民在进入搜房网主页及各层页面时均被视为已经仔细看过本条款并完全同意。凡以任何方式登陆本网站，或直接、间接使用本网站资料者，均被视为自愿接受本网站相关声明和用户服务协议的约束\n"
			+ "    2、搜房网转载的内容并不代表搜房网之意见及观点，也不意味着本网赞同其观点或证实其内容的真实性。\n"
			+ "    3、搜房网转载的文字、图片、音视频等资料均由本网站用户提供，其真实性、准确性和合法性由信息发布人负责。搜房网不提供任何保证，并不承担任何法律责任。"
			+ "\n"
			+ "    4、搜房网所转载的文字、图片、音视频等资料，如果侵犯了第三方的知识产权或其他权利，责任由作者或转载者本人承担，本网站对此不承担责任。"
			+ "\n"
			+ "    5、搜房网不保证为向用户提供便利而设置的外部链接的准确性和完整性，同时，对于该外部链接指向的不由搜房网实际控制的任何网页上的内容，搜房网不承担任何责任。"
			+ "\n"
			+ "    6、用户明确并同意其使用搜房网网络服务所存在的风险将完全由其本人承担；因其使用搜房网网络服务而产生的一切后果也由其本人承担，搜房网对此不承担任何责任。"
			+ "\n"
			+ "    7、除搜房网注明之服务条款外，其它因不当使用本网而导致的任何意外、疏忽、合约毁坏、诽谤、版权或其他知识产权侵犯及其所造成的任何损失（包括因不当下载而感染电脑病毒等），搜房网概不负责，亦不承担任何法律责任。"
			+ "\n"
			+ "    8、对于因不可抗力或因黑客攻击、通讯线路中断等搜房网不能控制的原因造成的网络服务中断或其他缺陷，导致用户不能正常使用搜房网，搜房网不承担任何责任，但将尽力减少因此给用户造成的损失或影响。"
			+ "\n"
			+ "    9、本声明未涉及的问题请参见国家有关法律法规，当本声明与国家有关法律法规冲突时，以国家法律法规为准。"
			+ "\n" + "    10、本网站相关声明版权及其修改权、更新权和最终解释权均属搜房网所有。";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.declare_activity);

		returnback();
		showText();
	}

	private void showText() {
		// TODO Auto-generated method stub
		tv_declare = (TextView) findViewById(R.id.tv_declare);
		tv_declare.setText(text);
		tv_declare.setTextColor(Color.BLACK);
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
