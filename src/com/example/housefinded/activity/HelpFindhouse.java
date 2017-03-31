package com.example.housefinded.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.housefinded.R;
import com.example.housefinded.adapter.FragmentAdaptE;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HelpFindhouse extends FragmentActivity {
	private ViewPager viewpager;
	private RelativeLayout layout;
	private TextView title;
	private Button back, bswitch;
	private FragmentManager fm;
	private FragmentAdaptE adapt;
	private List<Fragment> list;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_helpfindh);
		initview();
		// 返回按钮
		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	public void initview() {
		viewpager = (ViewPager) findViewById(R.id.activity_viewpage);
		layout = (RelativeLayout) findViewById(R.id.help_title);
		title = (TextView) layout.findViewById(R.id.titlename);
		back = (Button)layout.findViewById(R.id.btnback);
		bswitch = (Button) layout.findViewById(R.id.btnswitch);
		title.setText("帮你找房");
		bswitch.setVisibility(View.GONE);
		list = new ArrayList<Fragment>();
		fm = getSupportFragmentManager();

		adapt = new FragmentAdaptE(fm, list);
		viewpager.setAdapter(adapt);
		viewpager.setCurrentItem(0);
		viewpager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
	}
}
