package com.example.Task;

import com.example.housefinded.net.HttpUrl;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;

public class UsernameisexistTask extends AsyncTask<Void, Void, String> {

	UtilDialog dialog;
	HttpUtilTools tool;
	Context context;
	private String path = "";
	EditText name;

	public UsernameisexistTask(Context context, EditText name) {
		this.context = context;
		tool = new HttpUtilTools(context);
		this.name = name;
		dialog = new UtilDialog(context);
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
			path = HttpUrl.BASE_URL+"rest/sysUser/checkName" + "?name="
					+ name.getText().toString();
			isexit = tool.isexitByHttpClientDoget(path);
		} catch (Exception e) {
			isexit = "2";
			return isexit;

		}
		return isexit;

	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		if (result.equals("0")) {
			dialog.showAlertDialog("提示", "用户名已存在...请重输入");
			name.setText("");
		} else if (result.equals("2")) {
			dialog.showToast("网络连接超时");
		}
	}
}
