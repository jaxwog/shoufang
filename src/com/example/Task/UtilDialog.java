package com.example.Task;
import com.example.Tools.OnDataFinshedListen;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.widget.Toast;

public class UtilDialog {
	private Context context;
	private ProgressDialog dialog;
	HttpUtils http;
	OnDataFinshedListen onDataFinishedListener;
	public UtilDialog(Context context) {
		this.context = context;
		dialog = new ProgressDialog(context);
		http=new HttpUtils();
	
	}
	public void setOnDataFinishedListener(
			OnDataFinshedListen onDataFinishedListener) {
		this.onDataFinishedListener = onDataFinishedListener;
	}
	/*
	 * 显示登录进度或者下载的进度
	 * 
	 * @param title 标题
	 * 
	 * @param message 显示的内容
	 */
	public void showDialogProcess(String title, String message) {
		dialog = new ProgressDialog(context);
		dialog.setTitle(title);
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dialog.setMessage(message);
		dialog.show();
	}

	public void disMissdialog() {
		dialog.dismiss();
	}

	/*
	 * 显示提示对话框
	 * 
	 * @param title 标题
	 * 
	 * @param message 显示的内容
	 */
	public void showAlertDialog(String title, String message) {
		AlertDialog.Builder alert = new AlertDialog.Builder(context);
		alert.setTitle(title);
		alert.setMessage(message);
		alert.setPositiveButton("确定",
				new android.content.DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

					}
				});
		alert.show();
	}

	/*
	 * 显示toast
	 * 
	 * @param message 显示的内容
	 * 
	 * @param
	 */
	public void showToast(String message) {
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
		
	}
	
	public void uploadMethod( RequestParams params, final String URL) {
		http.send(HttpRequest.HttpMethod.POST, URL, params,
				new RequestCallBack<String>() {

					@Override
					public void onStart() {
						// 上传开始
						super.onStart();
					}

					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
						// 上传中
						super.onLoading(total, current, isUploading);
					}

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						Toast.makeText(context, "网络错误", 1).show();

					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						// 服务器返回的数据
						onDataFinishedListener.onDataSuccessfully(arg0.result);
					}
				});
	}
}
