package com.example.housefinded;

import com.example.housefinded.fragement.HomeFragment;
import com.example.housefinded.fragement.MineFragment;
import com.example.housefinded.fragement.MoreFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

	private RadioButton rb_home;
	private RadioButton rb_mine;
	private RadioButton rb_more;
	public static boolean isChoose = false;
	public static String city = "";
	public boolean isExit=false;
	private long  istime=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 取消标题栏
		setContentView(R.layout.activity_main);

		rb_home = (RadioButton) findViewById(R.id.rb_home);
		rb_mine = (RadioButton) findViewById(R.id.rb_mine);
		rb_more = (RadioButton) findViewById(R.id.rb_more);
		rb_home.setChecked(true);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.frame_content, new HomeFragment()).commit();

		rb_home.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.frame_content, new HomeFragment())
						.commit();

			}
		});

		rb_mine.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.frame_content, new MineFragment())
						.commit();

			}
		});
		rb_more.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.frame_content, new MoreFragment())
						.commit();

			}
		});

	}
@Override
public boolean onKeyDown(int keyCode, KeyEvent event) {
	
	if(keyCode==KeyEvent.KEYCODE_BACK){
		exit();
		return false;
	}
	return super.onKeyDown(keyCode, event);
}
//监听返回按钮事件，点击两次 退出程序
private void exit(){
	if((System.currentTimeMillis() - istime) > 2000){
		isExit=true;
		Toast.makeText(MainActivity.this, "再点击一次，退出", 0).show();
		istime= System.currentTimeMillis();
	}else{
	    finish();
	}
}
}
