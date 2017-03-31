package com.example.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.housefinded.net.HttpUrl;

import android.content.Context;
import android.os.AsyncTask;

public class RegisterTask extends AsyncTask<Void, Void, String> {
	Context context;
	HttpUtilTools tool;
	private static final String baseURL = HttpUrl.BASE_URL+"rest/sysUser/add";
	private Map<String, Object> usermap;
	String issucess;
	UtilDialog dialog;

	public RegisterTask(Context context, Map<String, Object> usermap) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.usermap = usermap;
		dialog = new UtilDialog(context);
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		dialog.showDialogProcess("注册", "正在注册中...");
	}

	@Override
	protected String doInBackground(Void... params) {
		// TODO Auto-generated method stub

		tool = new HttpUtilTools(context);
		try {
			if (tool.submintDataByHttpClientDoPost(usermap, baseURL)) {
				issucess = "sucess";
			} else {
				issucess = "lost";
			}

		} catch (Exception e) {
			issucess = "lost";
			return issucess;
		}
		return issucess;
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		dialog.disMissdialog();
		
		if (issucess.equals("sucess")) {
			dialog.showToast("注册成功");
		} else {
			dialog.showToast("失败");
		}
	}

	public void UserPostJsonData() throws JSONException, IOException {
		URL url1;
		url1 = new URL(baseURL);
		JSONObject ss = new JSONObject();
		ss.put("name", "��");
		ss.put("age", 23);
		JSONObject clint = new JSONObject();
		clint.put("people", ss);
		// ���ǰ�JSON����ת����String����ʹ��������������д
		String content = String.valueOf(clint);
		// �Ѿ���װ�����ݣ������������
		HttpURLConnection con = (HttpURLConnection) url1.openConnection();
		con.setConnectTimeout(5000);
		// �����������
		con.setDoOutput(true);
		// ��post�ķ�ʽ�����������
		con.setRequestMethod("POST");
		// ���ô��ݵ�Property
		con.setRequestProperty("ser-Agent", "Fiddler");
		con.setRequestProperty("content-Type", "JSOn");
		OutputStream os = con.getOutputStream();
		os.write(content.getBytes());
		os.close();
		// ���ط���������Ӧ��
		int code = con.getResponseCode();
		if (code == 200) {
			// ������Ӧ�ɹ��ˣ�����ȡ�˷�������������
			InputStream is = con.getInputStream();
			String jj = inputStreamToString(is);
			JSONObject pp = new JSONObject(jj);
			String name = pp.getString("name");
		}

	}

	public String inputStreamToString(InputStream in) throws IOException {
		StringBuffer responsetext;
		String readText;
		BufferedReader reader = new BufferedReader(new InputStreamReader(in,
				"GBK"));
		responsetext = new StringBuffer();
		readText = reader.readLine();
		while (readText != null) {

			responsetext.append(readText);

			responsetext.append(System.getProperty("line.separator"));

			readText = reader.readLine();

		}

		return responsetext.toString();
	}

}
