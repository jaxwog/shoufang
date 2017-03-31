package com.example.housefinded.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.Task.MessageForhttp;
import com.example.Task.RegisterTask;
import com.example.Task.TelphoneIsExit;
import com.example.Task.UsernameisexistTask;
import com.example.Task.UtilDialog;
import com.example.Tools.OnDataFinshedListen;
import com.example.Tools.TextUtil;
import com.example.housefinded.R;
import com.example.housefinded.net.HttpUrl;
import com.example.javabean.UserBean;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RegisteNew extends Activity {
	RelativeLayout registelayout;
	UserBean userbean;
	Map<String, Object> map = new HashMap<String, Object>();
	TextView text, getmess;

	Button but, Tb;
	private EditText regispwd, regispwdto, registel, regisname, regisyz,tuijian;
    private RadioGroup group;
    private RadioButton b1,b2;
	UtilDialog dialog;
	ImageView backbtn;
	private String path = HttpUrl.BASE_URL + "rest/sysUser/sendMessage";
	private String data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.newregist);
		userbean = new UserBean();

		dialog = new UtilDialog(RegisteNew.this);
		registelayout = (RelativeLayout) findViewById(R.id.z_regisnew);
		text = (TextView) registelayout.findViewById(R.id.titlename);
		but = (Button) registelayout.findViewById(R.id.btnswitch);
		backbtn = (ImageView) registelayout.findViewById(R.id.btnback);
		Tb = (Button) findViewById(R.id.takeup);
		registel = (EditText) findViewById(R.id.regisnum);
		regisname = (EditText) findViewById(R.id.regisname);
		regispwd = (EditText) findViewById(R.id.regispwd);
		regispwdto = (EditText) findViewById(R.id.regispwdto);
		regisyz = (EditText) findViewById(R.id.regisyz);
		tuijian=(EditText)findViewById(R.id.tuijian);
		getmess = (TextView) findViewById(R.id.getmess);
		group=(RadioGroup)findViewById(R.id.tjradioGroup3);
		b1=(RadioButton)findViewById(R.id.tj);
		b2=(RadioButton)findViewById(R.id.tj_1);
		but.setVisibility(View.GONE);
		text.setText("注册");

		backbtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				RegisteNew.this.finish();

			}

		});
		Tb.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				addmapvalues();
				RegisterTask task = new RegisterTask(RegisteNew.this, map);
				task.execute();
			}
		});
		// 验证手机号码的唯一性
		registel.setOnFocusChangeListener(new View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (!hasFocus) {
					TelphoneIsExit Teltask = new TelphoneIsExit(
							RegisteNew.this, registel);
					Teltask.execute();
				}
			}
		});
		// 验证用户名的唯一性
		regisname.setOnFocusChangeListener(new View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				UsernameisexistTask nametask = new UsernameisexistTask(
						RegisteNew.this, regisname);
				nametask.execute();
			}

			public void addmapvalues() {
				map.put("name", regisname.getText().toString());// 用户
				map.put("password", regispwd.getText().toString());// 密码
				// map.put("showname", "你好");
				map.put("telephone", registel.getText().toString());// 手机号
			}

		});
		Tb.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (TextUtil.checkPassword(RegisteNew.this, regispwd.getText()
						.toString())) {
					if ("".equals(regispwd.getText().toString())
							|| "".equals(regispwdto.getText().toString())
							|| "".equals(regisname.getText().toString())
							|| "".equals(regisyz.getText().toString())) {
						dialog.showToast("请填上以上所有信息");
					} else if(!regisyz.getText().toString().equals(data)){
						dialog.showToast("验证码输入错误");
					}else if(b1.isChecked() && "".equals(tuijian.getText().toString())){
						dialog.showToast("请输入推荐人ID");
					}else {
						if (regispwd.getText().toString().equals(regispwdto.getText().toString())) {
						
								addmapvalues();
							RegisterTask task = new RegisterTask(
									RegisteNew.this, map);
							task.execute();
						} else {
							dialog.showToast("两次密码输入不一样");
						}
					}
				}
			}
		});
		//单选按钮选择
		group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId==R.id.tj){
			tuijian.setVisibility(View.VISIBLE);
				}else if(checkedId==R.id.tj_1){
					tuijian.setVisibility(View.GONE);
					tuijian.setText("");
				}
				
			}
		});
		// 验证手机号码的唯一性
		registel.setOnFocusChangeListener(new View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {

				if (!hasFocus) {
					if (!"".equals(registel.getText().toString())) {
						if (TextUtil.isTelphone(registel.getText().toString())) {
							TelphoneIsExit Teltask = new TelphoneIsExit(
									RegisteNew.this, registel);
							Teltask.execute();
						} else {
							dialog.showToast("手机格式不正确");
							registel.setText("");
						}
					} else {
						dialog.showToast("请输入手机号码");
					}

				}
			}
		});

		// 验证用户名的唯一性
		regisname.setOnFocusChangeListener(new View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (!hasFocus) {

					if (TextUtil.checkName(RegisteNew.this, regisname.getText()
							.toString())) {
						UsernameisexistTask nametask = new UsernameisexistTask(
								RegisteNew.this, regisname);
						nametask.execute();
					} else {
						regisname.setText("");
					}
				}
			}
		});
		// 获取验证码
		getmess.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(TextUtil.isTelphone(registel.getText().toString())){
				HttpUtils http = new HttpUtils();
				RequestParams params = new RequestParams();
				params.addBodyParameter("telephone", registel.getText()
						.toString());
				http.send(HttpMethod.POST, path, params,
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
								getmess.setText("正在获取");
							}

							@Override
							public void onFailure(HttpException arg0,
									String arg1) {
								// TODO Auto-generated method stub
								getmess.setText("获取失败");
							}

							@Override
							public void onSuccess(ResponseInfo<String> arg0) {
								// "status":"1","msg":"获取验证码成功","exmsg":null,"data":"040412"
								String name = arg0.result;
								try {
									JSONObject jsonObject = new JSONObject(name);
									data = jsonObject.getString("data");
									getmess.setText("获取成功");
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
					Toast.makeText(RegisteNew.this, "请输入正确的手机号", 0).show();
				}
				
			}
		});
	}

	public void addmapvalues() {
		map.put("name", regisname.getText().toString());// 用户
		map.put("password", regispwd.getText().toString());// 密码
		// map.put("showname", "你好");
		map.put("telephone", registel.getText().toString());// 手机号
		if("".equals(tuijian.getText().toString()) || tuijian.getText().toString()==null){
			map.put("recommendedCode", "");
		}else{
			map.put("recommendedCode", tuijian.getText().toString());
		}
	}

	// 解析获取的json数据
	public void jsonstring(String data) {
		try {
			JSONObject json = new JSONObject(data);
			json.get("name");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
