package com.example.housefinded.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.example.Tools.SharePreferences;
import com.example.housefinded.MainActivity;
import com.example.housefinded.R;
import com.example.housefinded.net.HttpUrl;
import com.example.javabean.UserBean;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class UserDetails extends Activity implements OnClickListener{
	private RelativeLayout titlelayout,userdetail_phone;
	private TextView title, user_name, textanglel, sexis, agetext, teltext;
	private ImageView tx, back;
	private Button bb, tui;
	private HttpUtils http, http2;
	private String URL = HttpUrl.BASE_URL + "rest/sysUser/update";// 更新用户的信息
	private String URL1 = HttpUrl.BASE_URL + "UploadHeadAction";// 更新用户的头像
	private String URL2 = HttpUrl.BASE_URL + "rest/sysUser/select";// 头像更新成功后，获取最新的用户数据
	private static final int PHOTO_REQUEST_CAMERA = 1;// 相册
	private static final int PHOTO_REQUEST_GALLERY = 2;// 照相
	private static final int PHOTO_REQUEST_CUT = 3;// 截取后的图片
	private Bitmap bitmap;
	private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
	private String PHOTO_URL;
	private File tempFile;
	private String filepath;
	AlertDialog ddialog;
	UserBean bean;
	boolean issucess = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.userdetails);
		// 初始化界面
		initview();
		initdataview();
		// 返回按钮
		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				UserDetails.this.finish();
			}
		});
		// 头像按钮的点击事件
		tx.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				LayoutInflater inflater = LayoutInflater.from(UserDetails.this);
				View view = inflater.inflate(R.layout.foruserdetails_touxiang,
						null);
				ddialog = new AlertDialog.Builder(UserDetails.this)
						.setTitle("选择")
						.setView(view)
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub

									}
								}).show();

			}
		});
		// 退出按钮点击事件

		tui.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				ddialog = new AlertDialog.Builder(UserDetails.this)
						.setTitle("选择")
						.setMessage("退出登录不会删除历史数据,下次登录依然使用本账号")
						.setPositiveButton("退出",
								new DialogInterface.OnClickListener() {

									@SuppressLint("NewApi")
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										SharePreferences
												.setloginF(UserDetails.this);
										Intent intent = new Intent();
										intent.setClass(UserDetails.this,
												MainActivity.class);
										startActivity(intent);
										finish();

									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {

									}
								}).show();

			}
		});
	}

	public void initview() {
		tx = (ImageView) findViewById(R.id.userimage);
		titlelayout = (RelativeLayout) findViewById(R.id.details_title);
		title = (TextView) titlelayout.findViewById(R.id.titlename);
		back = (ImageView) titlelayout.findViewById(R.id.btnback);
		bb = (Button) titlelayout.findViewById(R.id.btnswitch);
		user_name = (TextView) findViewById(R.id.detailtext);
		textanglel = (TextView) findViewById(R.id.detailtext_5);
		sexis = (TextView) findViewById(R.id.detailtext_1);
		agetext = (TextView) findViewById(R.id.detailtext_2);
		tui = (Button) findViewById(R.id.takeout);
		teltext = (TextView) findViewById(R.id.detailtext_3);
		userdetail_phone=(RelativeLayout)findViewById(R.id.userdetail_phone);
		userdetail_phone.setOnClickListener(this);
		bb.setVisibility(View.GONE);
		title.setText("我的账号");
	}

	public void initdataview() {

		bean = SharePreferences.readuserbean(UserDetails.this);
		if (bean != null) {
			user_name.setText(bean.getShowName());
			if ("1".equals(bean.getGender())) {
				sexis.setText("女");
			} else {
				sexis.setText("男");
			}
			agetext.setText(bean.getAge() + "");
			textanglel.setText(bean.getDescription());
			teltext.setText(bean.getTelephone() + "");
			if (bean.getHeadImg() != null && bean.getHeadImg() != "") {
				RequestQueue mQueue = Volley.newRequestQueue(UserDetails.this);
				final LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(
						20);
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
				ImageListener listener = ImageLoader.getImageListener(tx,
						R.drawable.tx, R.drawable.tx);
				imageLoader.get(HttpUrl.BASE_URL + bean.getHeadImg(), listener);
			}
		}
	}

	public void gallery(View view) {
		// 从手机相册中选择
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");
		startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
	}

	public void camera(View view) {
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		// 从照相机中截取图片
		if (hasSdcard()) {
			PHOTO_URL=UUID.randomUUID()+PHOTO_FILE_NAME;
			intent.putExtra(MediaStore.EXTRA_OUTPUT,
					Uri.fromFile(new File(Environment
							.getExternalStorageDirectory(), PHOTO_URL)));
		}
		startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
	}

	private boolean hasSdcard() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == PHOTO_REQUEST_GALLERY) {
			// 手机相册
			ContentResolver resolver = getContentResolver();
			if (data != null) {
				Bitmap bm;
				Uri uri = data.getData();
				// 获取别截取图片的一些信息
				/*
				 * try { bm = MediaStore.Images.Media.getBitmap(resolver, uri);
				 * } catch (FileNotFoundException e) { // TODO Auto-generated
				 * catch block e.printStackTrace(); } catch (IOException e) { //
				 * TODO Auto-generated catch block e.printStackTrace(); }
				 * 
				 * String[] url = { MediaColumns.DATA };
				 * 
				 * Cursor cursor = managedQuery(uri, url, null, null, null);
				 * cursor.moveToFirst(); int column_index =
				 * cursor.getColumnIndex(MediaColumns.DATA);
				 */
				// filepath = cursor.getString(column_index);
				// tempFile=new File(filepath);
				crop(uri);
			}

		} else if (requestCode == PHOTO_REQUEST_CAMERA) {
			// 拍摄照相
			if (hasSdcard()) {
				tempFile = new File(Environment.getExternalStorageDirectory(),
						PHOTO_URL);
				if (tempFile.exists()) { 
					crop(Uri.fromFile(tempFile));
				}
			} else {
				Toast.makeText(UserDetails.this, "未找到图片信息", 0).show();
			}

		} else if (requestCode == PHOTO_REQUEST_CUT) {
			try {
				ddialog.dismiss();
				// 获取截取后的图片路径和信息
				bean = new UserBean();
				bean = SharePreferences.readuserbean(UserDetails.this);
				bitmap = data.getParcelableExtra("data");
				tempFile = savaMyBitmap(bitmap, "temppit");
				filepath = tempFile.getAbsolutePath();
				this.tx.setImageBitmap(bitmap);

				// boolean delete = tempFile.delete();
				RequestParams params = new RequestParams();
				params.setHeader("id", bean.getId() + "");
				params.addBodyParameter(filepath.replace("/", ""), tempFile);
				uploadMethod(params, URL1);
				// if(issucess){
				// GetDatabyid task=new GetDatabyid(UserDetails.this, new
				// SharePreferences().config +
				// "/rest/sysUser/select/"+bean.getId());
				// task.execute();}

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (requestCode == 4) {
			if (resultCode == 1) {
				user_name.setText(data.getStringExtra("name_1"));
			}
		} else if (requestCode == 5) {
			if (resultCode == 2) {
				textanglel.setText(data.getStringExtra("name_1"));
				System.out.print(data);
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void crop(Uri uri) {
		// 截取图片信息
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");// 截取
		// 截取框比例为1:1
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// 输出图片的大小
		intent.putExtra("outputX", 500);
		intent.putExtra("outputY", 500);
		// 设置图片的格式为jpg格式
		intent.putExtra("outputFormat", "JPEG");
		intent.putExtra("noFaceDetection", true);//
		intent.putExtra("return-data", true);//
		startActivityForResult(intent, PHOTO_REQUEST_CUT);
	}

	public void uploadMethod(final RequestParams params, final String URL) {
		http = new HttpUtils();
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
						// 上传失败
						Toast.makeText(UserDetails.this, "上传失败", 1).show();

						System.out.print(arg0 + arg1);
						String ss = arg1;
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						// 服务器返回的数据
						String result = arg0.result;
						if (result == "" || result == null) {
							http2 = new HttpUtils();
							http2.send(HttpRequest.HttpMethod.GET, URL2 + "/"
									+ bean.getId(),
									new RequestCallBack<String>() {

										@Override
										public void onFailure(
												HttpException arg0, String arg1) {
											// TODO Auto-generated method stub

										}

										@Override
										public void onSuccess(
												ResponseInfo<String> arg0) {
											String reString = arg0.result;
											JSONObject json;
											try {
												json = new JSONObject(reString);
												Gson gson = new Gson();
												bean = gson.fromJson(
														json.getString("data"),
														UserBean.class);
												SharePreferences.savaUserbean(
														bean, UserDetails.this);
												Toast.makeText(
														UserDetails.this,
														"头像修改成功", 0).show();
											} catch (JSONException e) {
												// TODO Auto-generated catch
												// block
												e.printStackTrace();
											}
										}
									});
						} else {
							try {
								JSONObject json = new JSONObject(result);
								Gson gson = new Gson();
								bean = gson.fromJson(json.getString("data"),
										UserBean.class);
								if (json.getString("status").equals("0")) {
									Toast.makeText(UserDetails.this, "修改失败", 1)
											.show();
								} else if (json.getString("status").equals("1")) {

									SharePreferences.savaUserbean(bean,
											UserDetails.this);
									Toast.makeText(UserDetails.this, "修改成功", 1)
											.show();
								}
							} catch (JSONException e) {
								issucess = true;
								Toast.makeText(UserDetails.this, "上传成功", 1)
										.show();
								e.printStackTrace();

							}
						}

					}
				});
	}

	// 改变用户昵称
	public void changename(View view) {
		Intent intent = new Intent();
		// 获取的昵称名
		intent.putExtra("name", user_name.getText().toString());
		intent.putExtra("title", "昵称修改");
		intent.setClass(UserDetails.this, ChangeUserdd.class);
		startActivityForResult(intent, 4);
	}

	// 选择用户性别
	public void changesex(View view) {

		LayoutInflater inflater = LayoutInflater.from(UserDetails.this);
		view = inflater.inflate(R.layout.sex_select, null);
		ddialog = new AlertDialog.Builder(UserDetails.this).setTitle("性别")
				.setView(view).show();

	}

	// 选择用户的年龄
	public void changeage(View view) {
		LayoutInflater inflater = LayoutInflater.from(UserDetails.this);
		view = inflater.inflate(R.layout.data_select, null);
		final DatePicker picker;
		picker = (DatePicker) view.findViewById(R.id.datePicker1);
		picker.updateDate(1995, 10, 03);
		final Calendar c = Calendar.getInstance();

		ddialog = new AlertDialog.Builder(UserDetails.this).setTitle("年龄")
				.setView(view)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						int year = c.get(Calendar.YEAR) - picker.getYear();
						if (year <= 0) {
							ddialog.dismiss();
							Toast.makeText(UserDetails.this,
									"你选择的日期大于当前日期，请重新输入", Toast.LENGTH_SHORT)
									.show();
						} else {

							agetext.setText(year + "");
							RequestParams params = new RequestParams("UTF-8");
							params.addBodyParameter("age", agetext.getText()
									.toString());
							params.addBodyParameter("id", bean.getId() + "");
							uploadMethod(params, URL);
							ddialog.dismiss();
						}
					}
				}).show();
	}

	// 用户的签名
	public void changeangle(View view) {
		Intent intent = new Intent();
		// 获取的个性签名内容
		intent.putExtra("textangle", textanglel.getText().toString());
		intent.putExtra("title", "个性签名");
		intent.setClass(UserDetails.this, ChangeUserdd.class);
		startActivityForResult(intent, 5);
	}

	// 性别的选择修改
	public void sexselect1(View view) {
		sexis.setText("男");
		RequestParams params = new RequestParams("UTF-8");
		params.addBodyParameter("id", bean.getId() + "");
		params.addBodyParameter("gender", "0");
		uploadMethod(params, URL);
		ddialog.dismiss();
	}

	public void sexselect(View view) {
		sexis.setText("女");
		RequestParams params = new RequestParams("UTF-8");
		params.addBodyParameter("id", bean.getId() + "");
		params.addBodyParameter("gender", "1");
		uploadMethod(params, URL);
		ddialog.dismiss();
	}

	public File savaMyBitmap(Bitmap map, String bitname) {
		File f = new File("/sdcard/Note/" + bitname + ".jpg");
		f.mkdirs();

		FileOutputStream out = null;
		try {
			if (f.exists()) {
				f.delete();
			}
			f.createNewFile();
			out = new FileOutputStream(f);
			map.compress(Bitmap.CompressFormat.JPEG, 100, out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return f;
	}

	@Override
	public void onClick(View v) {
	switch (v.getId()) {
	case R.id.userdetail_phone:
		Intent intent=new Intent(UserDetails.this,UpdataTelActivity.class);
		startActivity(intent);
		break;

	default:
		break;
	}
		
	}

}
