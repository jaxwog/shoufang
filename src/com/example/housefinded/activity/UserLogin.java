package com.example.housefinded.activity;

import java.util.HashMap;
import java.util.Map;

import com.example.Task.LoginTask;
import com.example.Task.UtilDialog;
import com.example.Tools.OnDataFinshedListen;
import com.example.Tools.SharePreferences;
import com.example.housefinded.MainActivity;
import com.example.housefinded.R;
import com.example.housefinded.fragement.MineFragment;
import com.example.javabean.UserBean;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class UserLogin extends Activity {
	RelativeLayout loginuser;
	TextView titlename;
	EditText username, userpwd;
	Button titlebut, findbut, regisbut, loginnow;
	UserBean userbean;
	LoginTask logintask;
	UtilDialog dialog;
	ImageView backbut;
    private Intent intent1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// û�б���
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//
		setContentView(R.layout.userlogin);
		dialog = new UtilDialog(UserLogin.this);
		userbean = new UserBean();
		username = (EditText) findViewById(R.id.etAccountName);
		userpwd = (EditText) findViewById(R.id.etaccountpwd);
		loginuser = (RelativeLayout) findViewById(R.id.z_logintitle);
		titlename = (TextView) loginuser.findViewById(R.id.titlename);
		titlebut = (Button) loginuser.findViewById(R.id.btnswitch);
		loginnow = (Button) findViewById(R.id.loginuul);
		backbut = (ImageView) loginuser.findViewById(R.id.btnback);
		findbut = (Button) findViewById(R.id.findback);
		regisbut = (Button) findViewById(R.id.regisbut);
		titlebut.setText("切换账号");
		titlebut.setVisibility(View.GONE);
		titlename.setText("登录");

		// getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.);
		// 找回密码按钮
		findbut.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent userintent = new Intent();
				userintent.setClass(UserLogin.this, FindPwd.class);
				startActivity(userintent);
			}
		});

		// 注册按钮事件
		regisbut.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(UserLogin.this, RegisteNew.class);
				startActivity(intent);
			}
		});
		// 返回按钮
		backbut.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				UserLogin.this.finish();
			}
		});

		// 登录按钮点击事件

		loginnow.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if ("".equals(username.getText().toString())
						|| "".equals(userpwd.getText().toString())) {
					dialog.showToast("用户名或者密码为空");
				} else {
					// logintask = new LoginTask(UserLogin.this, username,
					// userpwd);
					Map<String, String> map = new HashMap<String, String>();
					map.put("name", username.getText().toString());
					map.put("password", userpwd.getText().toString());
					logintask=new LoginTask(UserLogin.this, map);
					logintask.setOnDataFinishedListener(new OnDataFinshedListen() {
								@Override
								public void onDataSuccessfully(Object data) {
									SharePreferences.login(getApplicationContext());
									intent1=getIntent();
									if("jianyi".equals(intent1.getStringExtra("jianyi")))
									{
										Intent intent = new Intent();
										intent.putExtra("jianyi", "jianyi");
										intent.setClass(UserLogin.this,ComplainActivity.class);
										startActivity(intent);
									}else if("jianyito".equals(intent1.getStringExtra("jianyi"))){

										Intent intent = new Intent();
										intent.putExtra("jianyi", "jianyi");
										intent.setClass(UserLogin.this,FeedbackActivity.class);
										startActivity(intent);
									}
									else
									{
									Intent intent = new Intent();
									intent.setClass(UserLogin.this,MainActivity.class);
									startActivity(intent);
									}
								}

								@Override
								public void onDataFailed() {
								}
							});
					logintask.execute();
				}

			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
