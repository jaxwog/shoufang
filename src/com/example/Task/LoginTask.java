package com.example.Task;

import java.util.Map;

import com.example.Tools.OnDataFinshedListen;
import com.example.housefinded.net.HttpUrl;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

public class LoginTask extends AsyncTask<Void, Void, String> {
	Context context;
	Map<String, String> map;
	HttpUtilTools httptools;
	UtilDialog dialog;
	//TextView name, pwd;
	private String path = HttpUrl.BASE_URL+"rest/sysUser/login";
	OnDataFinshedListen onDataFinishedListener;

	// UserBean userbean;
	/*public LoginTask(Context context, TextView name, TextView pwd) {
		// TODO Auto-generated constructor stub
		this.context = context;
		dialog = new UtilDialog(context);
		this.name = name;
		this.pwd = pwd;
	}*/
	public LoginTask(Context context, Map<String, String> map) {
		// TODO Auto-generated constructor stub
		this.context = context;
		dialog = new UtilDialog(context);
		this.map=map;
	}

	public void setOnDataFinishedListener(
			OnDataFinshedListen onDataFinishedListener) {
		this.onDataFinishedListener = onDataFinishedListener;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		httptools = new HttpUtilTools(context);
		dialog.showDialogProcess("登录", "正在登陆中...,请稍等");
	}

	@Override
	protected String doInBackground(Void... params) {
		String status = "0";
		//String url = path + "?name=" + name.getText().toString() + "&password="
			//	+ pwd.getText().toString();
       // System.out.print(url);
		try {

			status = httptools.loginDataByHttpClientDoPost(map, path);
			System.out.print(status);
		} catch (Exception e) {
			status = "2";
			return status;
		}
		return status;
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		dialog.disMissdialog();
		if ("1".equals(result)) {
			onDataFinishedListener.onDataSuccessfully(result);
		} else if ("0".equals(result)) {
			dialog.showToast("账号或者密码有误");
		} else {
			dialog.showToast("网络异常");
		}
	}

}
