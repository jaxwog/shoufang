package com.example.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.example.Tools.CustomerHttpClient;
import com.example.Tools.SharePreferences;
import com.example.housefinded.R;
import com.example.housefinded.net.HttpUrl;
import com.example.javabean.UserBean;
import com.google.gson.Gson;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HttpUtilTools {
	private boolean islogin = true;
	private String status;
	Context context;
	UserBean bean;
	// 防止请求时间过长
	CustomerHttpClient client;
	public HttpUtilTools(Context context) {
		this.context = context;
		client = new CustomerHttpClient();
	}

	public Boolean submintDataByHttpClientDoPost(Map<String, Object> map,
			String path) {
		// 1.
		// ���һ���൱�����������HttpClient��ʹ������ӿڵ�ʵ��������������DefaultHttpClient
		// HttpClient hc = new DefaultHttpClient();
		HttpClient hc = client.getHttpClient();

		// DoPost��ʽ�����ʱ���������󣬹ؼ���·��
		HttpPost request = new HttpPost(path);
		// 2. Ϊ�����������������Ҳ���ǽ�Ҫ�ϴ���web�������ϵĲ���
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		for (Entry<String, Object> entry : map.entrySet()) {
			NameValuePair nameValuePairs = new BasicNameValuePair(
					entry.getKey(), (String) entry.getValue());
			parameters.add(nameValuePairs);
		}
		System.out.print(parameters);
		// ����ʵ��HttpEntityҲ��һ���ӿڣ�����������ʵ����UrlEncodedFormEntity����������ע�����һ��String���͵Ĳ���������ָ�������
		HttpEntity entity;
		try {
			entity = new UrlEncodedFormEntity(parameters, "UTF-8");

			request.setEntity(entity);
			// 3. ִ������
			HttpResponse response = hc.execute(request);
			// 4. ͨ�����������ж�����ɹ����
			int s=response.getStatusLine().getStatusCode();
			System.out.print(s);
			if (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) {
				islogin = true;

			} else {
				islogin = false;
			}
		} catch (UnsupportedEncodingException e) {
			islogin = false;
			return false;
		} catch (ClientProtocolException e) {
			islogin = false;
			return false;
		} catch (IOException e) {
			islogin = false;
			return false;
		}
		return islogin;
	}

	/**
	 * ��HttpClient��DoPost��ʽ�ύ���ݵ�������
	 * 
	 * @param map
	 *            ���ݽ��������ݣ���map����ʽ�����˷�װ
	 * @param path
	 *            Ҫ�������servlet�ĵ�ַ
	 * @return ���ص�boolean���͵Ĳ���
	 * @throws Exception
	 */

	public String loginDataByHttpClientDoPost(Map<String, String> map,
			String path) {
		bean=new UserBean();
		HttpClient hc = client.getHttpClient();
	
		HttpPost request = new HttpPost(path);
		System.out.println(map);
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();

		try {
			for (Entry<String, String> entry : map.entrySet()) {
				NameValuePair nameValuePairs = new BasicNameValuePair(
						entry.getKey(), entry.getValue());
				parameters.add(nameValuePairs);
			}
			
			HttpEntity entity;
			entity = new UrlEncodedFormEntity(parameters, "UTF-8");
			request.setEntity(entity);
			HttpResponse response = hc.execute(request);
			
			if (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) {
				//解决服务器端数据乱码的情况
				HttpEntity httpEntity = response.getEntity();
				String jsonRString = EntityUtils.toString(httpEntity, "UTF-8"); 
				//InputStream inputStream = httpEntity.getContent();
				//String jsonRString = inputStreamToString(inputStream).toString();      
				JSONObject jsonObject = new JSONObject(jsonRString);
				status = jsonObject.getString("status");
				String ss=jsonObject.getString("data");
				System.out.print("ss");
				Gson gson=new Gson();
				bean=gson.fromJson(jsonObject.getString("data"), UserBean.class);
				SharePreferences.savaUserbean(bean, context);
			} else {
				status = "2";
			}
		} catch (UnsupportedEncodingException e) {
			status = "2";
			return status;
		} catch (ClientProtocolException e) {
			status = "2";
			return status;
		} catch (IOException e) {
			status = "2";
			return status;
		} catch (JSONException e) {

		}
		return status;
	}

	// 登录界面，并获取登录的一些数据信息
	// 比较重要的一些数据信息
	public String loginDataByHttpClientDoget(String path) {
		String name = null;
		// HttpClient hc = new DefaultHttpClient();
		HttpClient hc = client.getHttpClient();
		HttpGet request = new HttpGet(path);
		HttpResponse response;
		try {
			response = hc.execute(request);
		int ss=	response.getStatusLine().getStatusCode();

		System.out.print(ss);
			if (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) {
				HttpEntity httpEntity = response.getEntity();
				InputStream inputStream = httpEntity.getContent();
				String jsonRString = inputStreamToString(inputStream)
						.toString();
				JSONObject jsonObject = new JSONObject(jsonRString);
				System.out.print(jsonObject);
				name = jsonObject.getString("status");
				// JSONArray mArray = jsonObject.getJSONArray("Records");
				// for(int i=0;i<mArray.length();i++){
				// JSONObject object=mArray.getJSONObject(i);
				// String name=object.getString("name");
				// }

			} else {
				name = "2";
			}
		} catch (ClientProtocolException e) {
			name = "2";
			return name;
		} catch (IOException e) {
			name = "2";
			return name;
		} catch (JSONException e) {
			name = "2";
			return name;
		}
		return name;
	}

	/**
	 * ��HttpClient��DoPost��ʽ�ύ���ݵ�������
	 * 
	 * @param in
	 *            ���صĵ�����
	 * 
	 * @throws Exception
	 */

	public String inputStreamToString(InputStream in) {
		StringBuffer responsetext = null;
		String readText;
		BufferedReader reader;
		try {
			reader = new BufferedReader(new InputStreamReader(in, "GBK"));

			responsetext = new StringBuffer();
			readText = reader.readLine();
			while (readText != null) {

				responsetext.append(readText);

				responsetext.append(System.getProperty("line.separator"));

				readText = reader.readLine();

			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			Toast.makeText(context, "网络连接超时", Toast.LENGTH_LONG).show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Toast.makeText(context, "解析错误", Toast.LENGTH_LONG).show();
		}

		return responsetext.toString();
	}

	public String isexitByHttpClientDoget(String path) {
		String staus = "0";

		// HttpClient hc = new DefaultHttpClient();
		HttpClient hc = client.getHttpClient();

		HttpGet request = new HttpGet(path);

		HttpResponse response;
		try {
			response = hc.execute(request);
			
			if (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) {
				HttpEntity httpEntity = response.getEntity();
				InputStream inputStream = httpEntity.getContent();
				String jsonRString = inputStreamToString(inputStream)
						.toString();

				JSONObject jsonObject = new JSONObject(jsonRString);
				String name = jsonObject.getString("status");
				System.out.print(name);
				staus = name;
				// JSONArray mArray = jsonObject.getJSONArray("Records");
				// for(int i=0;i<mArray.length();i++){
				// JSONObject objecet=mArray.getJSONObject(i);
				// String name=object.getString("name");
				// }
			} else {
				staus = "0";
			}
		} catch (JSONException e) {
			staus = "2";
			return staus;
		} catch (ClientProtocolException e) {
			staus = "2";
			return staus;
		} catch (IOException e) {
			staus = "2";
			return staus;
		}
		return staus;
	}

	public String GetNumDataByHttpClientDoget(String path) {
		HttpClient hc = client.getHttpClient();
		HttpGet request = new HttpGet(path);
		String name = null;
		try {
			HttpResponse response = hc.execute(request);
			if (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) {
				HttpEntity httpEntity = response.getEntity();
				InputStream inputStream = httpEntity.getContent();
				String jsonRString = inputStreamToString(inputStream)
						.toString();
				JSONObject jsonObject = new JSONObject(jsonRString);
				name = jsonObject.getString("data");
				System.out.print(name);
				// JSONArray mArray = jsonObject.getJSONArray("Records");
				// for(int i=0;i<mArray.length();i++){
				// JSONObject object=mArray.getJSONObject(i);
				// String name=object.getString("name");
				// }
			} else {
				name = "";
				return name;
			}
		} catch (JSONException e) {

			name = "";
			return name;
		} catch (ClientProtocolException e) {

			name = "";
			return name;
		} catch (IOException e) {
			name = "";
			return name;
		}
		return name;
	}
	public void LoadImageView(ImageView image,String URL){
		RequestQueue mQueue = Volley.newRequestQueue(context); 
        final LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(20); 
        ImageCache imageCache = new ImageCache() { 
            @Override 
            public void putBitmap(String key, Bitmap value) { 
                lruCache.put(key, value); 
            } 
   
            @Override 
            public Bitmap getBitmap(String key) { 
                return lruCache.get(key); 
            } 
        }; 
        ImageLoader imageLoader = new ImageLoader(mQueue, imageCache); 
        ImageListener listener = ImageLoader.getImageListener(image, R.drawable.hugh,R.drawable.hugh); 
        imageLoader.get(URL, listener); 
	}
}
