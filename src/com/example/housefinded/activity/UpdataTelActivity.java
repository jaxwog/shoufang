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
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.app.Activity;
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

public class UpdataTelActivity extends Activity implements OnClickListener {
	RelativeLayout layout;
	private ImageView image;
	private Button bb, takeup;
	private TextView title, yzm, updata_oldtel;
	private EditText updata_yzm, updata_newtel;
	private UserBean bean;
	private String data;
	String url1 = HttpUrl.BASE_URL + "rest/sysUser/update";
	String url = HttpUrl.BASE_URL + "rest/sysUser/modifySendMsg";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_updatatel);
		initview();
	}

	public void initview() {
		layout = (RelativeLayout) findViewById(R.id.updata_title);
		image = (ImageView) layout.findViewById(R.id.btnback);
		bean = SharePreferences.readuserbean(UpdataTelActivity.this);
		bb = (Button) layout.findViewById(R.id.btnswitch);
		takeup = (Button) findViewById(R.id.takeupdatatel);
		title = (TextView) layout.findViewById(R.id.titlename);
		yzm = (TextView) findViewById(R.id.updata_getyy);
		updata_oldtel = (TextView) findViewById(R.id.updata_oldtel);
		updata_yzm = (EditText) findViewById(R.id.updata_yzm);
		updata_newtel = (EditText) findViewById(R.id.updata_newtel);
		title.setText("修改手机号");
		updata_oldtel.setText(bean.getTelephone());
		bb.setVisibility(View.GONE);
		yzm.setOnClickListener(this);
		image.setOnClickListener(this);
		takeup.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnback:
			finish();
			break;
		// 获取验证码
		case R.id.updata_getyy:
			if (TextUtil.isTelphone(updata_newtel.getText().toString())
					&& !"".equals(yzm.getText().toString())) {
				HttpUtils http = new HttpUtils();
				RequestParams params = new RequestParams();
				params.addBodyParameter("telephone", updata_newtel.getText()
						.toString());
				http.send(HttpMethod.POST, url, params,
						new RequestCallBack<String>() {
							@Override
							public void onStart() {

								super.onStart();
							}

							@Override
							public void onLoading(long total, long current,
									boolean isUploading) {

								super.onLoading(total, current, isUploading);
								yzm.setText("正在获取");
							}

							@Override
							public void onFailure(HttpException arg0,
									String arg1) {
								yzm.setText("获取失败");
							}

							@Override
							public void onSuccess(ResponseInfo<String> arg0) {

								// "status":"1","msg":"获取验证码成功","exmsg":null,"data":"040412"
								String name = arg0.result;
								try {
									JSONObject jsonObject = new JSONObject(name);
									data = jsonObject.getString("data");
									yzm.setText("获取成功");
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								System.out.print(name);

							}
						});
			} else {
				Toast.makeText(UpdataTelActivity.this, "请正确填写信息", 0).show();
			}
			break;
		// 提交数据
		case R.id.takeupdatatel:
			if (updata_yzm.getText().toString().equals(data)
					&& TextUtil.isTelphone(updata_newtel.getText().toString())) {
				HttpUtils http2 = new HttpUtils();
				RequestParams param = new RequestParams();
				param.addBodyParameter("id", bean.getId() + "");
				param.addBodyParameter("telephone", updata_newtel.getText()
						.toString());
				http2.send(HttpMethod.POST, url1, param,
						new RequestCallBack<String>() {

							@Override
							public void onFailure(HttpException arg0,
									String arg1) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onSuccess(ResponseInfo<String> arg0) {
								// TODO Auto-generated method stub
								String result = arg0.result;
								try {
									JSONObject json = new JSONObject(result);
									Gson gson = new Gson();
									bean = gson.fromJson(
											json.getString("data"),
											UserBean.class);
									if (json.getString("status").equals("0")) {
										Toast.makeText(UpdataTelActivity.this,
												"修改失败", 1).show();
									} else if (json.getString("status").equals(
											"1")) {

										SharePreferences.savaUserbean(bean,
												UpdataTelActivity.this);
										Toast.makeText(UpdataTelActivity.this,
												"修改成功", 1).show();
									}
								} catch (JSONException e) {
									Toast.makeText(UpdataTelActivity.this,
											"修改失败", 1).show();

								}
							}
						});

			} else {
				Toast.makeText(UpdataTelActivity.this, "请正确输入手机号", 0).show();
			}
			break;
		default:
			break;
		}

	}

}
