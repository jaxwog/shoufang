package com.example.housefinded.activity;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.Tools.SharePreferences;
import com.example.Tools.TextUtil;
import com.example.housefinded.MainActivity;
import com.example.housefinded.R;
import com.example.housefinded.net.HttpUrl;
import com.example.javabean.UserBean;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ComplainActivity extends Activity implements OnClickListener{
	private RelativeLayout titlelayout;
	private TextView title;
	private Button  bb;
	private EditText e1, e2;
	private HttpUtils http;
	private Intent intent;
    private UserBean userbean;
    private ImageView back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.complain_activity);
		initview();
	}

	public void initview() {
		titlelayout = (RelativeLayout) findViewById(R.id.complain_for);
		title = (TextView) titlelayout.findViewById(R.id.titlename);
		back = (ImageView) titlelayout.findViewById(R.id.btnback);
		bb = (Button) titlelayout.findViewById(R.id.btnswitch);
		e1 = (EditText) findViewById(R.id.complain_2);
		e2 = (EditText) findViewById(R.id.complain_3);
		bb.setText("提交");
		title.setText("意见");
		back.setOnClickListener(this);
		bb.setOnClickListener(this);
	}
	public void uploadMethod(final RequestParams params, final String URL) {
		http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.POST, URL, params,
				new RequestCallBack<String>() {

					@Override
					public void onStart() {
						// 上传开始
						super.onStart();
					}

					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
						// 上传中
						super.onLoading(total, current, isUploading);
					}

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						Toast.makeText(ComplainActivity.this, "网络错误", 1).show();

					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						String name=null;
						// 服务器返回的数据
							String result = arg0.result;
							JSONObject json;
							try {
								json = new JSONObject(result);
								 name=json.getString("status");
								 if("1".equals(name)){
									 Toast.makeText(ComplainActivity.this, "提交成功", 1)
										.show();
									 finish();
								 }
							} catch (JSONException e) {
								 Toast.makeText(ComplainActivity.this, "提交失败"+name, 1)
														.show();				 			 
							}
					}
				});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		//返回按钮
		case R.id.btnback:
			intent=getIntent();
			if("jianyi".equals(intent.getStringExtra("jianyi"))){
				Intent intentto=new Intent();
				intentto.setClass(ComplainActivity.this, MainActivity.class);
				startActivity(intentto);
				finish();
			}else{
				finish();
			}
			break;
			//提交按钮
case R.id.btnswitch:
	if (!("").equals(e2.getText().toString())
			&& !("").equals(e1.getText().toString())
			&& e1.getText().length() >= 10) {
		if (TextUtil.isTelphone(e2.getText().toString())) {
			userbean=SharePreferences.readuserbean(ComplainActivity.this);
			RequestParams params = new RequestParams();
			params.addBodyParameter("userId", userbean.getId()+"");
			params.addBodyParameter("content", e1.getText()
					.toString());
			params.addBodyParameter("contactInfo", e2.getText()
					.toString());
			uploadMethod(params,HttpUrl.BASE_URL+"rest/feedback/add");

		}
	} else {
		Toast.makeText(ComplainActivity.this, "请按要求填写其中的信息", 1)
				.show();
	}


			break;
		default:
			break;
		}
		
	}
}
