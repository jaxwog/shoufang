package com.example.housefinded.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.Tools.SharePreferences;
import com.example.Tools.TextUtil;
import com.example.housefinded.R;
import com.example.housefinded.net.HttpUrl;
import com.example.javabean.UserBean;
import com.google.gson.Gson;
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
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ChangeUserdd extends Activity {
	RelativeLayout layout;
	TextView titlename, tit;
	Button  titlebut;
	EditText textname, textangle;
	TextUtil tool;
	ImageView image,backbut;
	HttpUtils http;
	int isright = 0;// 如果是1，就是昵称修改界面，如果是0的话就是个性修改界面
	String URL = HttpUrl.BASE_URL + "rest/sysUser/update";
private UserBean bean;//用户修改数据后的信息
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_username);

		final Intent intent = getIntent();
		bean=SharePreferences.readuserbean(ChangeUserdd.this);
		initView();
		if (intent.getStringExtra("title").equals("昵称修改")) {
			titlebut.setText("完成");
			titlename.setText("昵称修改");
			tit.setVisibility(View.GONE);
			textangle.setVisibility(View.GONE);
			textname.setText(intent.getStringExtra("name"));
			isright = 1;
			textname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
				
				@Override
				public void onFocusChange(View v, boolean hasFocus) {
				image.setVisibility(View.VISIBLE);
				}
			});
			image.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
				
					textname.setText("");
				}
			});
		} else if (intent.getStringExtra("title").equals("个性签名")) {
			titlebut.setText("完成");
			titlename.setText("个性签名");
			textname.setVisibility(View.GONE);
			textangle.setText(intent.getStringExtra("textangle"));
		}

		titlebut.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isright == 1) {
					if (!("").equals(textname.getText().toString())) {
						if (TextUtil.checkName(ChangeUserdd.this, textname
								.getText().toString())) {

							Bundle data = new Bundle();
							data.putString("name_1", textname.getText()
									.toString());
							intent.putExtras(data);
							// 设置该SelectCityActivity结果码，并设置结束之后退回的Activity
							ChangeUserdd.this.setResult(1, intent);
							RequestParams params = new RequestParams("UTF-8");		
							params.addBodyParameter("id", bean.getId()+"");
							params.addBodyParameter("showName", textname
									.getText().toString());
						
							uploadMethod(params, URL);
							finish();
						}
					} else {
						Toast.makeText(ChangeUserdd.this, "昵称不能为空",
								Toast.LENGTH_SHORT).show();
					}
				} else if (isright == 0) {
					if (textangle.getText().length() <= 20) {
						Bundle data = new Bundle();
						System.out.print(textangle.getText().toString());
						data.putString("name_1", textangle.getText().toString());
						intent.putExtras(data);
						// 设置该SelectCityActivity结果码，并设置结束之后退回的Activity
						ChangeUserdd.this.setResult(2, intent);
						RequestParams params = new RequestParams("UTF-8");
						params.addBodyParameter("id", bean.getId()+"");
						params.addBodyParameter("description", textangle
								.getText().toString());
						params.addBodyParameter("showName", textangle.getText()
								.toString());

						uploadMethod(params, URL);
						finish();
					} else {
						Toast.makeText(ChangeUserdd.this, "输入的字不能超过20个",
								Toast.LENGTH_SHORT).show();
					}
				}
			}});
		backbut.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	public void initView() {

		layout = (RelativeLayout) findViewById(R.id.changetoname);
		titlename = (TextView) layout.findViewById(R.id.titlename);
		backbut = (ImageView) layout.findViewById(R.id.btnback);
		titlebut = (Button) layout.findViewById(R.id.btnswitch);
		textname = (EditText) findViewById(R.id.name_1);
		tit = (TextView) findViewById(R.id.name_3);
		textangle = (EditText) findViewById(R.id.name_2);
		image=(ImageView)findViewById(R.id.change_image);

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
						Toast.makeText(ChangeUserdd.this, "网络错误", 1).show();

					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						// 服务器返回的数据
						String result = arg0.result;
						bean=SharePreferences.readuserbean(ChangeUserdd.this);
						try {
							JSONObject json = new JSONObject(result);
							json.getString("status");

							 Gson gson = new Gson();
                            String jsonstring=json.getString("data");
                            
							if (json.getString("status").equals("0")) {
								Toast.makeText(ChangeUserdd.this, "修改失败", 1)
										.show();
							} else if(json.getString("status").equals("1")){
								bean=gson.fromJson(jsonstring, UserBean.class);
								System.out.print(bean);
								SharePreferences.savaUserbean(bean, ChangeUserdd.this);
								Toast.makeText(ChangeUserdd.this, "修改成功", 1)
								.show();
							}
						} catch (JSONException e) {}

					}
				});
	}

}
