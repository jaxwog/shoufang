package com.example.housefinded.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.Tools.MyHttpUtil;
import com.example.Tools.MyHttpUtil.MyHttpCallback;
import com.example.housefinded.R;
import com.example.housefinded.PullToFresh.PullToRefreshBase;
import com.example.housefinded.PullToFresh.PullToRefreshListView;
import com.example.housefinded.PullToFresh.PullToRefreshBase.OnRefreshListener;
import com.example.housefinded.adapter.NewHouseItemAdapter;
import com.example.housefinded.bean.AreaBean;
import com.example.housefinded.db.SqlHelpCRUD;
import com.example.housefinded.net.HttpUrl;
import com.example.javabean.House;
import com.example.javabean.SecondHouseBean;
import com.google.gson.Gson;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import android.widget.RelativeLayout;
import android.widget.AdapterView.OnItemClickListener;

public class SeekActivity extends Activity implements OnClickListener {

	private AutoCompleteTextView tv_search;
	private ImageView ivDeleteText;
	private ImageView iv_back;
	private RelativeLayout rl_title;
	private TextView btnSearch;
	private String title;
	private Intent intent;
	private AreaBean areaBean;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.seek_activity);
		intent=new Intent();
		areaBean=(AreaBean) getIntent().getSerializableExtra("areaBean");
		
		initView();
		setOnClick();
	}

	private void setOnClick() {
		// TODO Auto-generated method stub
		btnSearch=(TextView) findViewById(R.id.btnSearch);
		
		
		
		btnSearch.setOnClickListener(this);
		
	}

	private void initView() {
		// TODO Auto-generated method stub
		iv_back = (ImageView) findViewById(R.id.iv_back);
		iv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});
		editSearch();

	}

	private void editSearch() {
		tv_search = (AutoCompleteTextView) findViewById(R.id.tv_Search);
		ivDeleteText = (ImageView) findViewById(R.id.ivDeleteText);
		ivDeleteText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tv_search.setText("");
			}
		});
		tv_search.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (s.length() == 0) {

					ivDeleteText.setVisibility(View.GONE);
				} else {
					ivDeleteText.setVisibility(View.VISIBLE);

				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnSearch:
			title=tv_search.getText().toString();
			intent.setClass(SeekActivity.this, SecondHouseActivity.class);
			Bundle bundle=new Bundle();
			bundle.putSerializable("areaBean", areaBean);
			intent.putExtras(bundle);
			intent.putExtra("title", title);
			startActivity(intent);
			break;

		default:
			break;
		}
	}
	

}
