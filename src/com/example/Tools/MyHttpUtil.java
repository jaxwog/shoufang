package com.example.Tools;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

/**
 * http请求通用类（XUtils:httpUtils）
 * 
 */
public class MyHttpUtil {
	
	private Context context;
	private ProgressDialog progressDialog;
	private HttpUtils httpUtils = new HttpUtils();
	
	/**
	 * 初始化context与dialog
	 * 
	 * @param mContext
	 * @param dialogMessage
	 */
	public MyHttpUtil(Context mContext, String dialogMessage) {
		this.context = mContext;
		progressDialog = new ProgressDialog(context);
		progressDialog.setMessage(dialogMessage);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setIndeterminate(false);
		progressDialog.setCancelable(false);
	}

	/**
	 * 初始化context
	 * 
	 * @param mContext
	 */
	public MyHttpUtil(Context mContext) {
		this.context = mContext;
	}

	/**
	 * 发送http请求
	 * 
	 * @param method
	 *            http方法
	 * @param params
	 *            请求字符串
	 * @param httpManagerCallback
	 *            请求成功的回调方法
	 */
	public void send(HttpMethod method, String url ,RequestParams params,
			final MyHttpCallback myHttpCallback) {
			progressDialog.show();
			httpUtils.send(method, url, params, new RequestCallBack<String>() {
                @Override
                public void onStart() {
                 	// TODO Auto-generated method stub
                	super.onStart();
                }
                @Override
                public void onLoading(long total, long current,
                		boolean isUploading) {
                	/*progressDialog.setMax((int) total);
                	progressDialog.setProgress((int) current);*/
                    super.onLoading(total, current, isUploading);
                }
				@Override
				public void onFailure(HttpException paramHttpException, String paramString) {
					if (progressDialog.isShowing()
							&& progressDialog != null) {
						progressDialog.dismiss();
					}
					Toast.makeText(context,
							"网络连接错误",
							Toast.LENGTH_LONG).show();
					myHttpCallback.onFailureResult(paramString);
				}

				@Override
				public void onSuccess(ResponseInfo<String> paramResponseInfo) {
					if (progressDialog.isShowing()
							&& progressDialog != null) {
						progressDialog.dismiss();
					}
					myHttpCallback.onSuccessResult(paramResponseInfo.result);
				}
			});


	}
	public synchronized void sendto(HttpMethod method, String url ,RequestParams params,
			final MyHttpCallback myHttpCallback) {
			progressDialog.show();
			httpUtils.send(method, url, params, new RequestCallBack<String>() {
                @Override
                public void onStart() {
                 	// TODO Auto-generated method stub
                	super.onStart();
                }
                @Override
                public void onLoading(long total, long current,
                		boolean isUploading) {
                	/*progressDialog.setMax((int) total);
                	progressDialog.setProgress((int) current);*/
                    super.onLoading(total, current, isUploading);
                }
				@Override
				public void onFailure(HttpException paramHttpException, String paramString) {
					if (progressDialog.isShowing()
							&& progressDialog != null) {
						progressDialog.dismiss();
					}
					Toast.makeText(context,
							"网络连接错误",
							Toast.LENGTH_LONG).show();
					myHttpCallback.onFailureResult(paramString);
				}

				@Override
				public void onSuccess(ResponseInfo<String> paramResponseInfo) {
					if (progressDialog.isShowing()
							&& progressDialog != null) {
						progressDialog.dismiss();
					}
					myHttpCallback.onSuccessResult(paramResponseInfo.result);
				}
			});


	}
	public interface MyHttpCallback {
		public void onLoadingResult(long total, long current, boolean isUploading);

		public void onSuccessResult(String result);

		public void onFailureResult(String msg);
	}
}
