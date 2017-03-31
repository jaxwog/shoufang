package com.example.housefinded.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.UUID;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.Tools.MyHttpUtil;
import com.example.Tools.MyHttpUtil.MyHttpCallback;
import com.example.Tools.SharePreferences;
import com.example.Tools.TextUtil;
import com.example.housefinded.R;
import com.example.housefinded.Wheelview.OnWheelChangedListener;
import com.example.housefinded.Wheelview.OnWheelScrollListener;
import com.example.housefinded.Wheelview.UnitAdapter;
import com.example.housefinded.Wheelview.WheelView;
import com.example.housefinded.adapter.GridViewofNew;
import com.example.housefinded.adapter.ImageViewadapt;
import com.example.housefinded.net.HttpUrl;
import com.example.javabean.UserBean;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SellHouseActivity extends Activity implements OnClickListener {
	private ImageView iv_back;
	private Button nextto;
	private EditText sell_tepn,sell_area,sell_floower,sell_allflower,sell_money,sell_descript,sell_name,sell_address;
	private GridViewofNew gridview2;
	private LinearLayout sell_get_ya;
	private Map<String, Object> mapt2;
	private ImageViewadapt adapt;
	private List<Map<String, Object>> map2;
	private AlertDialog dialog;
	private AlertDialog.Builder[] dialog1 = new AlertDialog.Builder[3];
	private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
	private String PHOTO_CAMERA;
	private static final int PHOTO_REQUEST_CAMERA = 1;// 照相
	private static final int PHOTO_REQUEST_GALLERY = 2;// 相册
	private final String IMAGE_TYPE = "image/*";
	private File tempFile;
	private UserBean bean;
	private String URL=HttpUrl.BASE_URL+"rest/entrustHouse/addByClient";
	private String IMAGE_URL=HttpUrl.BASE_URL+"UploadAction";
	private String PIURE_URL=HttpUrl.BASE_URL+"rest/entrustImage/add";
	private HttpUtils http;
	private TextView sell_huxing, sell_cx, update_tel, sell_yanz,
         sell_time;
	String shistring2[] = { "0厅", "1厅", "2厅", "3厅", "4厅", "5厅", "6厅", "7厅" };
	String shistring1[] = { "1室", "2室", "3室", "4室", "5室", "6室", "7室", "8室" };
	String shistring3[] = { "0卫", "1卫", "2卫", "3卫", "4卫", "5卫", "6卫", "7卫" };
	String[] stringcx = { "南北", "南", "东南", "东", "西南", "北", "西", "东西", "东北",
			"西北" };
	String[] unitString = { "00:00", "01:00", "02:00", "03:00", "04:00",
			"05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00",
			"12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00",
			"19:00", "20:00", "21:00", "22:00", "23:00" };
	private boolean timeScrolled = false;
	
	private String houseid="";
	//来电时间选择框里面的一些判断数据
	int wxposition=7,wxtoposition=19;//启示时间不能小于结束时间
	String tt="7:00";
  String ttto="19:00";
  private List<File> filedate=new ArrayList<File>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.sell_house_activity);
		initview();
		initdata();
		adapt = new ImageViewadapt(SellHouseActivity.this, map2,filedate);
		gridview2.setAdapter(adapt);
		gridview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				if ((int) parent.getAdapter().getItemId(position) == 0) {

					LayoutInflater inflater = LayoutInflater
							.from(SellHouseActivity.this);
					view = inflater.inflate(R.layout.foruserdetails_touxiang,
							null);
					dialog = new AlertDialog.Builder(SellHouseActivity.this)
							.setTitle("选择")
							.setView(view)
							.setNegativeButton("取消",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {

										}
									}).show();

				}
			}
		});
		returnback();
	}

	private void returnback() {
		// TODO Auto-generated method stub
		iv_back = (ImageView) findViewById(R.id.iv_back);
		iv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	public void initview() {
		map2 = new ArrayList<Map<String, Object>>();
		nextto = (Button) findViewById(R.id.sell_house_button);
		gridview2 = (GridViewofNew) findViewById(R.id.rent_piture);
		bean = SharePreferences.readuserbean(SellHouseActivity.this);
		sell_get_ya = (LinearLayout) findViewById(R.id.sell_get_ya);
		sell_huxing = (TextView) findViewById(R.id.sell_huxing);
		sell_cx = (TextView) findViewById(R.id.sell_cx);
		update_tel = (TextView) findViewById(R.id.update_tel);
		sell_yanz = (TextView) findViewById(R.id.sell_yanz);
		sell_time = (TextView) findViewById(R.id.sell_time);
		sell_address=(EditText)findViewById(R.id.sell_address);
		sell_floower=(EditText)findViewById(R.id.sell_floower);
		sell_name=(EditText)findViewById(R.id.sell_name);
		sell_area=(EditText)findViewById(R.id.sell_area);
		sell_tepn = (EditText) findViewById(R.id.sell_tepn);
		sell_allflower=(EditText)findViewById(R.id.sell_allflower);
		sell_money=(EditText)findViewById(R.id.sell_money);
		sell_descript=(EditText)findViewById(R.id.sell_descript);
		sell_tepn.setText(bean.getTelephone());
		sell_tepn.setEnabled(false);// 不可编辑
		sell_cx.setOnClickListener(this);
		sell_time.setOnClickListener(this);
		sell_huxing.setOnClickListener(this);
		nextto.setOnClickListener(this);
		update_tel.setVisibility(View.GONE);
		//update_tel.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.sell_house_button:
			//小区名称 联系人 电话 不能为空
			if(!"".equals(sell_address.getText().toString()) &&
					!"".equals(sell_tepn.getText().toString()) &&
					!"".equals(sell_name.getText().toString()))
			{
			RequestParams params = new RequestParams();
			params.addBodyParameter(add());
			uploadMethod(params, URL);
			}else{
				Toast.makeText(SellHouseActivity.this, "请按要求填写以上房屋信息", 0).show();
			}
			break;
		case R.id.sell_huxing:

			dialog1[0] = new Builder(SellHouseActivity.this).setItems(
					shistring1, new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							final String n0 = shistring1[which];
							dialog1[1] = new AlertDialog.Builder(
									SellHouseActivity.this).setItems(
									shistring2,
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											final String n1 = shistring2[which];
											dialog1[2] = new AlertDialog.Builder(
													SellHouseActivity.this)
													.setItems(
															shistring3,
															new DialogInterface.OnClickListener() {

																@Override
																public void onClick(
																		DialogInterface dialog,
																		int which) {
																	String n2 = shistring3[which];
																	sell_huxing
																			.setText(n0
																					+ n1
																					+ n2);
																}
															});
											dialog1[2].show();
										}
									});
							dialog1[1].show();
						}
					});
			dialog1[0].show();

			break;
		case R.id.sell_cx:
			dialog1[0] = new AlertDialog.Builder(SellHouseActivity.this)
					.setItems(stringcx, new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							sell_cx.setText(stringcx[which]);
						}
					});
			dialog1[0].show();
			break;
		case R.id.update_tel:
			sell_tepn.setEnabled(true);// 可编辑
			update_tel.setVisibility(View.GONE);
			sell_get_ya.setVisibility(View.VISIBLE);
			break;
		case R.id.sell_yanz:

			break;
		case R.id.sell_time:
			
			View outview = LayoutInflater.from(SellHouseActivity.this).inflate(
					R.layout.wheel_detail, null);
			WheelView vw = (WheelView) outview.findViewById(R.id.wheel_from);
			WheelView vwto = (WheelView) outview.findViewById(R.id.wheel_to);
			vw.setAdapter(new UnitAdapter(unitString));
			vw.setLabel("");
			vw.setCyclic(true);// 设置是否循环

			vwto.setAdapter(new UnitAdapter(unitString));
			vwto.setLabel("");
			vwto.setCyclic(true);
			// 设置默认选中项
			vw.setCurrentItem(7);
			vwto.setCurrentItem(19);

			vw.addChangingListener(new OnWheelChangedListener() {

				@Override
				public void onChanged(WheelView wheel, int oldIndex,
						int newIndex) {
					wxposition=newIndex;
				tt = unitString[newIndex];
					
				}
			});

			vwto.addChangingListener(new OnWheelChangedListener() {
				@Override
				public void onChanged(WheelView wheel, int oldIndex,
						int newIndex) {
					wxtoposition=newIndex;
					 ttto = unitString[newIndex];
					
				}
			});
			OnWheelScrollListener scrollListener = new OnWheelScrollListener() {
				public void onScrollingStarted(WheelView wheel) {
					timeScrolled = true;
				}

				public void onScrollingFinished(WheelView wheel) {
					timeScrolled = false;
				}
			};
			vw.addScrollingListener(scrollListener);
			vwto.addScrollingListener(scrollListener);
			 new AlertDialog.Builder(SellHouseActivity.this)
					.setView(outview)
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
						if(wxposition<wxtoposition)
						{
							sell_time.setText(tt+"-"+ttto);
							}else{
				Toast.makeText(SellHouseActivity.this, "接电时间设置格式错误", 0).show();				
							}
						}
					}).show();

			break;
		default:
			break;
		}

	}

	public void camera(View view) {
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		// 从照相机中截取图片
		if (hasSdcard()) {
			PHOTO_CAMERA=UUID.randomUUID()+ PHOTO_FILE_NAME;
			intent.putExtra(MediaStore.EXTRA_OUTPUT,
					Uri.fromFile(new File(Environment
							.getExternalStorageDirectory(),PHOTO_CAMERA)));
		}
		startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
	}

	public void gallery(View view) {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType(IMAGE_TYPE);
		startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
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
		super.onActivityResult(requestCode, resultCode, data);

		Bitmap bm = null;
		ContentResolver resolver = getContentResolver();
		if (requestCode == PHOTO_REQUEST_GALLERY) {
			if(data!=null){
			Uri uri = data.getData();
			try {
				// 从相册中读取图片，并上传到服务器
				bm = MediaStore.Images.Media.getBitmap(resolver, uri);
				mapt2 = new HashMap<String, Object>();
				mapt2.put("image1", bm);
				map2.add(mapt2);
				adapt.notifyDataSetChanged();
				tempFile = savaMyBitmap(bm, "temppit");
				filedate.add(tempFile);
			
//				RequestParams params = new RequestParams();
//				params.setHeader("id", bean.getId() + "");
//				params.addBodyParameter(
//						tempFile.getAbsolutePath().replace("/", ""), tempFile);
				//uploadMethod(params, IMAGE_URL);
			} catch (FileNotFoundException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			}
			}
		} else if (requestCode == PHOTO_REQUEST_CAMERA) {
			// 通过照相获取照片，并上传到服务器
			if (hasSdcard()) {
				tempFile = new File(Environment.getExternalStorageDirectory(),
						PHOTO_CAMERA);
				if(tempFile.exists()){
				// imageview.setImageBitmap(BitmapFactory.decodeFile(tempFile.getAbsolutePath()));
				mapt2 = new HashMap<String, Object>();
				// Bitmap
				// bit=BitmapFactory.decodeFile(tempFile.getAbsolutePath());
				mapt2.put(
						"image1",
						decodeSampledBitmapFromResource(
								tempFile.getAbsolutePath(), 100, 100));
				map2.add(mapt2);
				adapt.notifyDataSetChanged();// 刷新操作
				filedate.add(tempFile);}
			} else {
				Toast.makeText(SellHouseActivity.this, "未找到图片信息", 0).show();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);

	}

	// 防止图片益处
	public static Bitmap decodeSampledBitmapFromResource(String res,
			int reqWidth, int reqHeight) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(res, options);
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(res, options);
	}

	public File savaMyBitmap(Bitmap map, String bitname) {
		File f = new File("/sdcard/Note/" + bitname +UUID.randomUUID()+ ".jpg");
		f.mkdirs();
		FileOutputStream out = null;
		try {
			if (f.exists()) {
				f.delete();
			}
			f.createNewFile();
			out = new FileOutputStream(f);
			map.compress(Bitmap.CompressFormat.JPEG, 50, out);//压缩图片的50%
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

	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (height > reqHeight || width > reqWidth) {
			final int halfHeight = height / 2;
			final int halfWidth = width / 2;
			while ((halfHeight / inSampleSize) > reqHeight
					&& (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}
		return inSampleSize;
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

						String ss = arg1;
						System.out.print(ss);
						Toast.makeText(SellHouseActivity.this,
								 "发布失败", 0) .show();
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						// 服务器返回的数据
						String result = arg0.result;
						System.out.print(result);
						Toast.makeText(SellHouseActivity.this,
								 "信息发布成功", 0) .show();
						JSONObject object;
						try {
							object = new JSONObject(result);
							JSONObject object2=new JSONObject(object.getString("data"));
							houseid=object2.getString("id");
							//开始上传图片信息
							if(houseid!=null && houseid!=""){
								if(filedate.size()>0){
								RequestParams params = new RequestParams();
								params.setHeader("id", bean.getId() + "");
								//params.addBodyParameter("", House_ID);
								for(int i=0;i<filedate.size();i++){
					params.addBodyParameter(filedate.get(i).getAbsolutePath().replace("/", ""), filedate.get(i));
								}
								
								MyHttpUtil http2=new MyHttpUtil(SellHouseActivity.this,"正在上传图片中，请稍等*^_^*");	
							
								http2.send(HttpRequest.HttpMethod.POST, IMAGE_URL, params, new MyHttpCallback() {
									
									@Override
									public void onSuccessResult(String result) {
										String ss=result;
										System.out.print(ss);
									if(result!=null && result!=""){
									RequestParams parm = new RequestParams();
									parm.addBodyParameter("entrustHouse",houseid);
									parm.addBodyParameter("source",getStringURL(result));
								//	uploadMethod(parm, PIURE_URL);
									MyHttpUtil http3=new MyHttpUtil(SellHouseActivity.this, "正在上传图片中，请稍等*^_^*");
									http3.send(HttpRequest.HttpMethod.POST, PIURE_URL, parm, new MyHttpCallback() {
										
										@Override
										public void onSuccessResult(String result) {
										finish();
											
										}
										
										@Override
										public void onLoadingResult(long total, long current, boolean isUploading) {
											// TODO Auto-generated method stub
											
										}
										
										@Override
										public void onFailureResult(String msg) {
											// TODO Auto-generated method stub
											String tp=msg;
											System.out.append(tp);
										}
									});
									}
									}
									
									@Override
									public void onLoadingResult(long total, long current, boolean isUploading) {
									
										
									}
									
									@Override
									public void onFailureResult(String msg) {
									
										
									}
								});
								//uploadMethod(params, IMAGE_URL);
								}else{
									finish();
								}
							}
						} catch (JSONException e) {
						
							e.printStackTrace();
						}
						
						
						/*
						 * try { JSONObject json = new JSONObject(result); Gson
						 * gson=new Gson();
						 * bean=gson.fromJson(json.getString("data"),
						 * UserBean.class); if
						 * (json.getString("status").equals("0")) {
						 * Toast.makeText(UserDetails.this, "修改失败", 1) .show();
						 * } else if (json.getString("status").equals("1")) {
						 * 
						 * SharePreferences.savaUserbean(bean,
						 * UserDetails.this); Toast.makeText(UserDetails.this,
						 * "修改成功", 1) .show(); } } catch (JSONException e) {
						 * issucess=true; Toast.makeText(UserDetails.this,
						 * "上传成功", 1) .show(); e.printStackTrace();
						 * 
						 * }
						 */
						}
				});
	}

	public void initdata() {
		// mapt要在for循环中产生对象，这样就不会产生覆盖的现象了
		mapt2 = new HashMap<String, Object>();
		Resources res = getResources();
		Bitmap bmp = BitmapFactory
				.decodeResource(res, R.drawable.forum_add_pic);
		mapt2.put("image1", bmp);
		map2.add(mapt2);
	}
	public List<NameValuePair> add() {
	Map<String, String> map=new HashMap<String, String>();
	map.put("user_id", bean.getId()+"");
	//map.put("create_date", TextUtil.getData());
	map.put("building_name", sell_address.getText().toString());//楼盘名称
	map.put("house_type", sell_huxing.getText().toString());//户型
	map.put("house_area", sell_area.getText().toString());//面积
   //楼层信息
	map.put("house_floor", sell_floower.getText().toString()+"/"+sell_allflower.getText().toString()+"层");
	map.put("house_decorate", sell_cx.getText().toString());//朝向
	map.put("house_price", sell_money.getText().toString());//价钱
	map.put("house_personal", sell_name.getText().toString());//联系人
	map.put("telephone", sell_tepn.getText().toString());//电话
	map.put("tel_time", sell_time.getText().toString());//联系时间段
	map.put("house_description", sell_descript.getText().toString());//房屋描述
	
	List<NameValuePair> parameters = new ArrayList<NameValuePair>();
	for (Entry<String, String> entry : map.entrySet()) {
		NameValuePair nameValuePairs = new BasicNameValuePair(
				entry.getKey(), entry.getValue());
		parameters.add(nameValuePairs);
	}
	return parameters;
}
	public String getStringURL(String result){
		String ur="";
		String [] ss=result.split(".jpg");
		for(int n=0;n<ss.length;n++){
		ur+=ss[n]+".jpg,";
		}
		return ur;
	}
}
