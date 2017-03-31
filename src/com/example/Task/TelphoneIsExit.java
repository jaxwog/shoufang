package com.example.Task;

import com.example.housefinded.net.HttpUrl;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;

public class TelphoneIsExit extends AsyncTask<Void, Void, String> {
	UtilDialog dialog;
	HttpUtilTools tool;
	Context context;
	private String path = "";
	EditText num;

	public TelphoneIsExit(Context context, EditText num) {
		this.context = context;
		tool = new HttpUtilTools(context);
		dialog = new UtilDialog(context);
		this.num = num;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

	@Override
	protected String doInBackground(Void... params) {
		String isexit = "0";
		try {
			path = HttpUrl.BASE_URL+"rest/sysUser/checkTelephone"
					+ "?telephone=" + num.getText().toString();
			isexit = tool.isexitByHttpClientDoget(path);
		} catch (Exception e) {
			// dialog.showToast("连接错误");
			return "2";
		}
		return isexit;

	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		if (result.equals("0")) {
			dialog.showAlertDialog("提示", "电话号码已经存在...请重输入");
			num.setText("");
		} else if (result.equals("2")) {
			dialog.showToast("网络连接错误");
		}
	}
}
