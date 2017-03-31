package com.example.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

public class RegistNewTask extends AsyncTask<Void, Void, Integer> {
	private String baseURL = "URL";
	List<NameValuePair> bean;

	@Override
	protected Integer doInBackground(Void... params) {
		// ��post�ķ�ʽ����������Ϣ
		// TODO Auto-generated method stub

		HttpEntity requestHttpEntity = null;
		try {
			requestHttpEntity = new UrlEncodedFormEntity(bean);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// URLʹ�û���URL���ɣ����в���Ҫ�Ӳ���

		HttpPost httpPost = new HttpPost(baseURL);
		// �����������ݼ���������
		httpPost.setEntity(requestHttpEntity);
		// ��Ҫ�ͻ��˶�������������
		HttpClient httpClient = new DefaultHttpClient();
		// ��������
		HttpResponse response = null;
		try {
			response = httpClient.execute(httpPost);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// ��ʾ��Ӧ�����ص���������
		showResponseResult(response);
		return 0;
	}

	private void showResponseResult(HttpResponse response) {
		if (null == response) {
			return;
		}
		if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity httpEntity = response.getEntity();

			try {
				InputStream inputStream = httpEntity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(inputStream, "GBK"));

				String result = "";
				String line = "";
				while (null != (line = reader.readLine())) {
					result += line;

				}
				/*
				 * ���Ƿֿ������� /
				 */
				// ����json����
				String jsonRString = inputStreamToString(inputStream)
						.toString();
				try {
					JSONObject jsonObject = new JSONObject(jsonRString);
					JSONArray mArray = jsonObject.getJSONArray("Records");
					for (int i = 0; i < mArray.length(); i++) {
						JSONObject object = mArray.getJSONObject(i);
						String name = object.getString("name");
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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

	// ͨ��HttpURLConnection���д���JSON���ݣ��ͽ������������ص�JSON����
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
}
