package com.example.Task;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.Tools.SharePreferences;
import com.example.javabean.UserBean;
import com.google.gson.Gson;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class GetDatabyid extends AsyncTask<Void, Void, String>{
HttpUtilTools tools;
 String path;
 UserBean bean;
 Context context;
	public GetDatabyid(Context context,String path){
		this.path=path;
		this.context=context;
		tools=new HttpUtilTools(context);
	}
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}
	@Override
	protected String doInBackground(Void... params) {
	String data=	tools.GetNumDataByHttpClientDoget(path);
		return data;
	}
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		Gson gson=new Gson();
		JSONObject json;
		try {
			json = new JSONObject(result);
			bean=gson.fromJson(json.getString("data"), UserBean.class);
			SharePreferences.savaUserbean(bean, context);
		} catch (JSONException e) {
			
		Toast.makeText(context, "解析json数据错误", 1).show();
		}
		
	}
}
