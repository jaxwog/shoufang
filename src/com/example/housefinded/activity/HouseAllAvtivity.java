package com.example.housefinded.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.Task.UtilDialog;
import com.example.Tools.MyHttpUtil;
import com.example.Tools.SharePreferences;
import com.example.Tools.TextUtil;
import com.example.Tools.MyHttpUtil.MyHttpCallback;
import com.example.housefinded.R;
import com.example.housefinded.adapter.GridViewofNew;
import com.example.housefinded.adapter.ImageViewadapt;
import com.example.housefinded.adapter.TabGridviewAdapt;
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
import android.app.Dialog;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class HouseAllAvtivity extends Activity implements OnClickListener{
	private RelativeLayout rent;
private	LinearLayout all;
ImageView back;
	GridView gridview;
	GridViewofNew gridview2;
	TabGridviewAdapt adapt;
	ImageViewadapt aadapt;
	RelativeLayout layout,alllaout;
	TextView title,phone,detail_house,type_hz;
	private EditText rent_titlename;
	Button bb,fabu;
	private File tempFile;
	private final String IMAGE_TYPE = "image/*";
	private static final int PHOTO_REQUEST_CAMERA = 1;// 照相
	private static final int PHOTO_REQUEST_GALLERY = 2;// 相册
	private static final String PHOTO_FILE_NAME = "temp_photo_get.jpg";
	private String PHOTO_CAMERA;
	AlertDialog ddialog;
	UtilDialog processdialog;
	String URL=HttpUrl.BASE_URL+"rest/tenement/addByMobileClient";
	String URlto=HttpUrl.BASE_URL+"UploadAction";
	String Image_PATH=HttpUrl.BASE_URL+"rest/tenementImage/addByClient";//提交图片路径
	private Map<String, Object> mapt,mapt2;
	private List<Map<String, Object>> map,map2;
	boolean [] ischeck={false,false,false,false,false,false,false,false};
	EditText rent_mj,rent_money,rent_descript,rent_name,rent_hx,rent_s,rent_t;
	String gridtitle[] = { "床", "宽带", "电视", "暖气", "冰箱", "洗衣机",
			"空调", "热水器" };
	Bitmap imagedraw[] = { };
	boolean isfirst=true;//房屋详情是否是第一次设置，不是把设置的值传递给设置界面
	String cx,zx,zf,lc,zl,ld,fh,hzss,hzts,hzws,shi,ting,wei;//朝向，装修，支付方式，楼层，总楼层，几号楼，房间号。
	int typenum;// 判断是整租还是合租方式，1为合租，0为整租。
	String [] rentstring={"主卧","次卧","床位","隔断间"};//合租的方式
	private HttpUtils http;
	private UserBean bean;
	private String live="";
	private String houseid;
	 private List<File> filedate=new ArrayList<File>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.house_all);
		Intent intent1=getIntent();
		typenum=intent1.getIntExtra("type", 1);
		gridview=(GridView)findViewById(R.id.house_gridview);
		gridview2=(GridViewofNew)findViewById(R.id.ppiture);
		
		map=new ArrayList<Map<String,Object>>();
		map2=new ArrayList<Map<String,Object>>();
		initview();
		
		initabdata();
		adapt=new TabGridviewAdapt(HouseAllAvtivity.this, map,ischeck);
		
		gridview.setAdapter(adapt);
		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			}
		});
		
		
		initdata();
	
		aadapt=new ImageViewadapt(HouseAllAvtivity.this, map2,filedate);
		gridview2.setAdapter(aadapt);
		gridview2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if((int) parent.getAdapter().getItemId(position)==0){

					LayoutInflater inflater=LayoutInflater.from(HouseAllAvtivity.this);
					 view=inflater.inflate(R.layout.foruserdetails_touxiang, null);
					ddialog=new AlertDialog.Builder(HouseAllAvtivity.this)
					.setTitle("选择")
					.setView(view)
					.setNegativeButton("取消", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							
							
						}
					}).show();
				
				
				}
			}
		});
		//返回按钮的点击事件
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ddialog=new AlertDialog.Builder(HouseAllAvtivity.this)
				.setMessage("房源未发布,确定放弃发布？")
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				})
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
					finish();
					}
				}).show();
			
				
			}
		});
		//房屋详情数据
		detail_house.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(isfirst){
		Intent intent =new Intent();
		intent.putExtra("typenum", typenum);
		intent.putExtra("isfirst",isfirst);
		intent.setClass(HouseAllAvtivity.this, DetailHouseActivity.class);
		startActivityForResult(intent, 12);
		}
				else{
					
					Intent intent =new Intent();
					intent.putExtra("typenum", typenum);
					intent.putExtra("isfirst",isfirst);
					intent.putExtra("cx", cx);
					intent.putExtra("zx", zx);
					intent.putExtra("zf", zf);
					intent.putExtra("lc", lc);
					intent.putExtra("zl", zl);
					intent.putExtra("ld", ld);
					intent.putExtra("fh", fh);
					intent.putExtra("hzss", hzss);
					intent.putExtra("hzts", hzts);
					intent.putExtra("hzws", hzws);
					intent.setClass(HouseAllAvtivity.this, DetailHouseActivity.class);
					startActivityForResult(intent, 12);
				}
				
			}
		});
	}
public void initabdata(){
	for (int i = 0; i < gridtitle.length; i++) {
		// mapt要在for循环中产生对象，这样就不会产生覆盖的现象了
		mapt = new HashMap<String, Object>();
		mapt.put("title1", gridtitle[i]);
		map.add(mapt);
	}

	
}
public void initview(){
	bean=SharePreferences.readuserbean(HouseAllAvtivity.this);
	fabu=(Button)findViewById(R.id.rent_fabu);
	rent_mj=(EditText)findViewById(R.id.rent_mj);//面积
	rent_money=(EditText)findViewById(R.id.rent_money);//租金
	rent_descript=(EditText)findViewById(R.id.rent_descript);
	rent_name=(EditText)findViewById(R.id.rent_name);
	phone=(TextView)findViewById(R.id.telephone_pub);
	phone.setText(bean.getTelephone());
	rent_titlename=(EditText)findViewById(R.id.rent_titlename);
	fabu.setOnClickListener(this);
	
	if(typenum==0){
	layout=(RelativeLayout)findViewById(R.id.house_title);
	title=(TextView)layout.findViewById(R.id.titlename);
	back= (ImageView) layout.findViewById(R.id.btnback);
	bb= (Button) layout.findViewById(R.id.btnswitch);
	title.setText("发布整租");
	bb.setVisibility(View.GONE);
	detail_house=(TextView)findViewById(R.id.detail_house);
	all=(LinearLayout)findViewById(R.id.all_type);
	rent_hx=(EditText)findViewById(R.id.rent_hx);
	rent_s=(EditText)findViewById(R.id.rent_s);
	rent_t=(EditText)findViewById(R.id.rent_t);
	all.setVisibility(View.VISIBLE);
	}
	else if(typenum==1)
	{
		layout=(RelativeLayout)findViewById(R.id.house_title);
		title=(TextView)layout.findViewById(R.id.titlename);
		back= (ImageView) layout.findViewById(R.id.btnback);
		bb= (Button) layout.findViewById(R.id.btnswitch);
		title.setText("发布合租");
		bb.setVisibility(View.GONE);
		
		detail_house=(TextView)findViewById(R.id.detail_house);
		rent=(RelativeLayout)findViewById(R.id.rent_type);
		rent.setVisibility(View.VISIBLE);
		type_hz=(TextView)findViewById(R.id.ht_house);
		
		
	}
}
//初始化map2的数据
public void initdata() {
	// mapt要在for循环中产生对象，这样就不会产生覆盖的现象了
	mapt2=new HashMap<String, Object>();
	Resources res = getResources();   
	Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.forum_add_pic);   
	mapt2.put("image1", bmp);
    map2.add(mapt2);
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
	Bitmap bm = null;
	ContentResolver resolver = getContentResolver();
	if(requestCode==PHOTO_REQUEST_GALLERY){
	if(data!=null){
		Uri uri = data.getData();
		try {
			//从相册中读取图片，并上传到服务器
			bm = MediaStore.Images.Media.getBitmap(resolver, uri);
			mapt2=new HashMap<String, Object>();
			mapt2.put("image1", bm);
			map2.add(mapt2);
			aadapt.notifyDataSetChanged();
			//bitmap = data.getParcelableExtra("data");
			tempFile=savaMyBitmap(bm,"temppit");
			filedate.add(tempFile);
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	}else if(requestCode==PHOTO_REQUEST_CAMERA){
//通过照相获取照片，并上传到服务器
		if (hasSdcard()) {
			tempFile = new File(Environment.getExternalStorageDirectory(),
					PHOTO_CAMERA);
			if(tempFile.exists()){
			//tempFile.getAbsolutePath();
		//	imageview.setImageBitmap(BitmapFactory.decodeFile(tempFile.getAbsolutePath()));
			mapt2=new HashMap<String, Object>();
			//Bitmap bit=BitmapFactory.decodeFile(tempFile.getAbsolutePath());
			mapt2.put("image1", decodeSampledBitmapFromResource(tempFile.getAbsolutePath(),100,100));
			map2.add(mapt2);
			aadapt.notifyDataSetChanged();//刷新操作
			filedate.add(tempFile);
			}} else {
			Toast.makeText(HouseAllAvtivity.this, "未找到图片信息", 0).show();
		}
	}else if(requestCode==12){
		if(resultCode==12){
			detail_house.setText(data.getStringExtra("detail"));
			isfirst=data.getBooleanExtra("isfirst", true);
			cx=data.getStringExtra("cx");
			zx=data.getStringExtra("zx");
			zf=data.getStringExtra("zf");
			lc=data.getStringExtra("lc");
			zl=data.getStringExtra("zl");
			ld=data.getStringExtra("ld");
			fh=data.getStringExtra("fh");
			hzss=data.getStringExtra("hzss");
			hzts=data.getStringExtra("hzts");
			hzws=data.getStringExtra("hzws");
			shi=data.getStringExtra("hzs");
			ting=data.getStringExtra("hzt");
			wei=data.getStringExtra("hzw");
		}
	}
	super.onActivityResult(requestCode, resultCode, data);
}
public void camera(View view) {
	Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
	// 从照相机中截取图片
	if (hasSdcard()) {
		PHOTO_CAMERA=UUID.randomUUID()+ PHOTO_FILE_NAME;
		intent.putExtra(MediaStore.EXTRA_OUTPUT,
				Uri.fromFile(new File(Environment.getExternalStorageDirectory(),PHOTO_CAMERA)));
	}
	startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
}
//压缩图片防止内存溢出
//BitmapFactory提供多种Decode方法，大家根据需要来使用 
public static Bitmap decodeSampledBitmapFromResource(String res,
		 int reqWidth, int reqHeight) { 

	
		 final BitmapFactory.Options options = new BitmapFactory.Options(); 
		 options.inJustDecodeBounds = true; 
		 BitmapFactory.decodeFile(res,options); 

	
		 options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight); 
		 options.inJustDecodeBounds = false; 
		 return BitmapFactory.decodeFile(res, options); 
		 } 

public static int calculateInSampleSize( 
		 BitmapFactory.Options options, int reqWidth, int reqHeight) { 
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
//合租类型选择
public void hzType(View view){
	AlertDialog dialog=	 new AlertDialog.Builder(HouseAllAvtivity.this).setItems(
			 rentstring, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					type_hz.setText(rentstring[which]);
					dialog.dismiss();
				}
			}).show();
}
public void Searchhouse(View view){
	
}
@Override
public void onClick(View v) {
	switch (v.getId()) {
	//:"添加失败，用户ID、小区名称、租房类型、租金、姓名、联系电话不能为空"
	case R.id.rent_fabu:
			if(!"".equals(rent_titlename.getText().toString()) && !"".equals(rent_money.getText().toString()) &&
					!"".equals(rent_name.getText().toString())){
		        RequestParams params = new RequestParams();
		         params.addBodyParameter(add());
		         uploadMethod(params,URL);
		         }else{
		        	 Toast.makeText(HouseAllAvtivity.this, "请填写完整信息", 1).show();
		         }
	
		break;

	default:
		break;
	}
	
}

	public List<NameValuePair> add() {
	Map<String, String> map=new HashMap<String, String>();
	
	
	map.put("tenType", typenum+"");//租赁方式
	//map.put("id", "71");//发布信息的ID
	map.put("userId", bean.getId()+"");//用户的ID
	map.put("createDate", TextUtil.getData());//创建的时间
	map.put("cellName",rent_titlename.getText().toString());//小区名称
	map.put("housePtss", getpeitao());//配套设施
	map.put("rent", rent_money.getText().toString());//房租的价钱
	map.put("personalName", rent_name.getText().toString());//用户名称
	map.put("telephone", bean.getTelephone());//用户电话
	map.put("houseArea", rent_mj.getText().toString());//住房面积
	map.put("description",rent_descript.getText().toString());//描述
	//String cx,zx,zf,lc,zl,ld,fh,hzss,hzts,hzws;//朝向，装修，支付方式，楼层，总楼层，几号楼，房间号。
	if(typenum==1){
		map.put("house_type", shi+"室"+ting+"厅"+wei+"卫");//户型
		map.put("houseType", shi+"室"+ting+"厅"+wei+"卫");//户型
	    map.put("jointrentType", type_hz.getText().toString());//合租类型
	}
	if(typenum==0){
		String hxdetail=rent_hx.getText().toString()+"室"+rent_s.getText().toString()+"厅"+rent_t.getText().toString()+"卫";
		map.put("house_type", hxdetail);//户型
		map.put("ban", ld);//楼栋
		map.put("roomnum",fh);//房间号
		map.put("houseType",hxdetail);//户型
	}
	map.put("face", cx);//朝向
	map.put("decorate",zx);//装修
	map.put("payment_method", zf);//支付方式
	map.put("floor", lc);//第几层
	map.put("houseLayer", zl);//共几层
	map.put("type", "2");//写死的
	
	List<NameValuePair> parameters = new ArrayList<NameValuePair>();
	for (Entry<String, String> entry : map.entrySet()) {
		NameValuePair nameValuePairs = new BasicNameValuePair(
				entry.getKey(), entry.getValue());
		parameters.add(nameValuePairs);
	}
	return parameters;
}

public void uploadMethod(final RequestParams params, final String URL ) {
	http = new HttpUtils();
	http.send(HttpRequest.HttpMethod.POST, URL, params,
			new RequestCallBack<String>() {

				@Override
				public void onStart() {
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
		Toast.makeText(HouseAllAvtivity.this, "网络错误", 1).show();
				}

				@Override
				public void onSuccess(ResponseInfo<String> arg0) {
					// 服务器返回的数据
					String result = arg0.result;
					System.out.print(result);
					Toast.makeText(HouseAllAvtivity.this,
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
							
							MyHttpUtil http2=new MyHttpUtil(HouseAllAvtivity.this,"正在上传图片中，请稍等*^_^*");	
						
							http2.send(HttpRequest.HttpMethod.POST, URlto, params, new MyHttpCallback() {
								
								@Override
								public void onSuccessResult(String result) {
								if(result!=null && result!=""){
								RequestParams parm = new RequestParams();
								parm.addBodyParameter("tenement",houseid);
								parm.addBodyParameter("source",getStringURL(result));
							//	uploadMethod(parm, PIURE_URL);
								MyHttpUtil http3=new MyHttpUtil(HouseAllAvtivity.this, "正在上传图片中，请稍等*^_^*");
								http3.send(HttpRequest.HttpMethod.POST, Image_PATH, parm, new MyHttpCallback() {
									
									@Override
									public void onSuccessResult(String result) {
										Toast.makeText(HouseAllAvtivity.this, "提交成功", 0).show();
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
//把本地相册图片转化成file文件 上传到服务器。
public File savaMyBitmap(Bitmap map,String bitname){
	File f = new File("/sdcard/Note/" + bitname +UUID.randomUUID()+ ".jpg");
	f.mkdirs();


	FileOutputStream out=null;
	try {
		if(f.exists()){
			f.delete();
		}
		f.createNewFile();
		out=new FileOutputStream(f);
		map.compress(Bitmap.CompressFormat.JPEG, 50, out);
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
//获取配套信息
public  String getpeitao(){
	for(int m=0;m<ischeck.length;m++){
		if(ischeck[m]){
			live+=gridtitle[m]+"  ";
		}
	}
	
	return live;
	
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
