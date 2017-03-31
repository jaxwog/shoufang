package com.example.Task;

import com.example.Tools.OnDataFinshedListen;
import com.example.housefinded.net.HttpUrl;

import android.content.Context;
import android.os.AsyncTask;

public class MessageForhttp extends AsyncTask<String, Void, String> {
	UtilDialog dialog;
	HttpUtilTools tool;
	Context context;
	private String path = HttpUrl.BASE_URL+"rest/sysUser/sendMessage";
	OnDataFinshedListen onDataFinishedListener;

	public MessageForhttp(Context context) {
		this.context = context;
		tool = new HttpUtilTools(context);
		dialog = new UtilDialog(context);
	}

	public void setOnDataFinishedListener(
			OnDataFinshedListen onDataFinishedListener) {
		this.onDataFinishedListener = onDataFinishedListener;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

	@Override
	protected String doInBackground(String... params) {
		String num = null;
		try {
			num = tool.GetNumDataByHttpClientDoget(path);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			num = null;
			return num;
		}
		return num;
	}

	@Override
	protected void onPostExecute(String num) {
		super.onPostExecute(num);
		// bean.setYzm(num);
		if (num != null) {
			onDataFinishedListener.onDataSuccessfully(num);
		} else {
			onDataFinishedListener.onDataFailed();
		}

	}
}
