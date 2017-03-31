package com.example.housefinded.activity;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.Task.UtilDialog;
import com.example.Tools.OnDataFinshedListen;
import com.example.Tools.TextUtil;
import com.example.housefinded.R;
import com.example.housefinded.net.HttpUrl;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FindPwd extends Activity {
	private RelativeLayout findlayout;
	private Button btn,up;
	private TextView text,findword;
	private EditText phone,pwd,word;
	ImageView backbtn;
	UtilDialog http;
	String URL= HttpUrl.BASE_URL + "rest/sysUser/forgetSendMsg";//获取验证码
	private String URL1 = HttpUrl.BASE_URL + "rest/sysUser/modifyPassword";//修改用户密码
	String data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.findpwd);
		initview();

	
//返回按钮
		backbtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FindPwd.this.finish();
			}
		});
		
		//提交按钮
		up.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!"".equals(phone.getText().toString()) && 
						!"".equals(pwd.getText().toString()) &&
						TextUtil.checkPassword(FindPwd.this, pwd.getText().toString())){
					if(!"".equals(word.getText().toString()) && word.getText().toString().equals(data)
							){
						HttpUtils http2=new HttpUtils();
						RequestParams params=new RequestParams();
						params.addBodyParameter("telephone",phone.getText().toString());
						params.addBodyParameter("password",pwd.getText().toString());
						http2.send(HttpMethod.POST, URL1, params, new RequestCallBack<String>() {

							@Override
							public void onFailure(HttpException arg0,
									String arg1) {
								Toast.makeText(FindPwd.this, "网络错误", 1).show();
								
							}

							@Override
							public void onSuccess(ResponseInfo<String> arg0) {
								try {
									JSONObject object=new JSONObject(arg0.result);
									if("1".equals(object.getString("status"))){
										Toast.makeText(FindPwd.this, "密码设置成功", 0).show();
										finish();
									}
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								finish();
								
							}
						});
					}else{
						Toast.makeText(FindPwd.this, "验证码错误", 0).show();	
					}
				}else{
					Toast.makeText(FindPwd.this, "请正确填写上面的信息", 0).show();
				}
			
				
			}
		});
		//获取验证码按钮
		findword.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
   if(TextUtil.isTelphone(phone.getText().toString())){
				HttpUtils http = new HttpUtils();
				RequestParams params = new RequestParams();
				params.addBodyParameter("telephone", phone.getText()
						.toString());
				http.send(HttpMethod.POST, URL, params,
						new RequestCallBack<String>() {
							@Override
							public void onStart() {
								// TODO Auto-generated method stub
								super.onStart();
							}

							@Override
							public void onLoading(long total, long current,
									boolean isUploading) {
								// TODO Auto-generated method stub
								super.onLoading(total, current, isUploading);
								findword.setText("正在获取");
							}

							@Override
							public void onFailure(HttpException arg0,
									String arg1) {
								// TODO Auto-generated method stub
								findword.setText("获取失败");
							}

							@Override
							public void onSuccess(ResponseInfo<String> arg0) {
								// "status":"1","msg":"获取验证码成功","exmsg":null,"data":"040412"
								String name = arg0.result;
								try {
									JSONObject jsonObject = new JSONObject(name);
									data = jsonObject.getString("data");
									findword.setText("获取成功");
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								System.out.print(name);

							}
							
						});
				/*
				 * // TODO Auto-generated method stub MessageForhttp message =
				 * new MessageForhttp(RegisteNew.this);
				 * message.setOnDataFinishedListener(new OnDataFinshedListen() {
				 * 
				 * @Override public void onDataSuccessfully(Object data) { //
				 * 把data数据转换成String类型 String ss=String.valueOf(data);
				 * System.out.print(ss); if (!regisyz.getText().toString()
				 * .equals(String.valueOf(data))) { dialog.showToast("验证码错误"); }
				 * 
				 * }
				 * 
				 * @Override public void onDataFailed() {
				 * 
				 * } }); message.execute();
				 */
			}else{
				Toast.makeText(FindPwd.this, "手机号不正确", 0).show();
			}
   }
		});
		phone.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus){
					if(!TextUtil.isTelphone(phone.getText().toString())){
						Toast.makeText(FindPwd.this, "手机号格式不正确", 0).show();
						phone.setText("");
					}
				}
				
			}
		});
	}
public void initview(){
	findlayout = (RelativeLayout) findViewById(R.id.z_findpwd);
	btn = (Button) findlayout.findViewById(R.id.btnswitch);
	text = (TextView) findlayout.findViewById(R.id.titlename);
	backbtn = (ImageView) findlayout.findViewById(R.id.btnback);
	text.setText("找回密码");
	btn.setVisibility(View.GONE);
	phone=(EditText)findViewById(R.id.findnum);
	pwd=(EditText)findViewById(R.id.findpwd_is);
	word=(EditText)findViewById(R.id.find_yz);
	findword=(TextView)findViewById(R.id.find_word);
	up=(Button)findViewById(R.id.find_up);
}

}
