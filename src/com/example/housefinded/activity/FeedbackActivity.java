package com.example.housefinded.activity;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.Tools.SharePreferences;
import com.example.Tools.TextUtil;
import com.example.housefinded.MainActivity;
import com.example.housefinded.R;
import com.example.housefinded.fragement.MoreFragment;
import com.example.housefinded.net.HttpUrl;
import com.example.javabean.UserBean;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FeedbackActivity extends Activity {
	private ImageView iv_back;
	private TextView tv_commit;
	private FeedbackActivity act;
	private EditText context,phonenum;
	private HttpUtils http;
	private UserBean userbean;
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.feedback_activity);
		context=(EditText)findViewById(R.id.feed_edit);
		phonenum=(EditText)findViewById(R.id.feed_phone);
		
		act = this;
		returnback();
		addCommit();
	}

	private void addCommit() {
		// TODO Auto-generated method stub
		tv_commit = (TextView) findViewById(R.id.tv_commit);
		tv_commit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new Builder(act);
				builder.setTitle("提示");
				builder.setMessage("确定提交？");
				builder.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								if(!"".equals(context.getText().toString()) && !"".equals(phonenum.getText().toString())
										&& phonenum.length()>=10 ){
									if(TextUtil.isTelphone(phonenum.getText().toString())){
										userbean=SharePreferences.readuserbean(act);
										RequestParams params = new RequestParams();
										params.addBodyParameter("userId", userbean.getId()+"");
										params.addBodyParameter("content", context.getText()
												.toString());
										params.addBodyParameter("contactInfo", phonenum.getText()
												.toString());
										uploadMethod(params,HttpUrl.BASE_URL+"rest/feedback/add");
									}else{
										Toast.makeText(act, "请正确填写以上信息", 0).show();
									}
								}else{
									Toast.makeText(act, "请正确填写以上信息", 0).show();
								}
								
							}
						});
				builder.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								dialog.cancel();

							}
						});
				builder.show();
			}
		});
	}

	private void returnback() {
		// TODO Auto-generated method stub
		iv_back = (ImageView) findViewById(R.id.iv_back);
		iv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				intent=getIntent();
				if("jianyi".equals(intent.getStringExtra("jianyi"))){
					Intent intentto=new Intent();
					intentto.setClass(act, MainActivity.class);
					startActivity(intentto);
					finish();
				}else{
					finish();
				}
			}
		});
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
						Toast.makeText(act, "网络错误", 1).show();

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
									 Toast.makeText(act, "提交成功", 1)
										.show();
									 finish();
								 }
							} catch (JSONException e) {
								 Toast.makeText(act, "提交失败"+name, 1)
														.show();				 			 
							}
					}
				});
	}
}
