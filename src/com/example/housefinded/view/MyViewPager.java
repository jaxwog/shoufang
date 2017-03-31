package com.example.housefinded.view;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.example.housefinded.R;
import com.example.housefinded.activity.SecondHouseDetailsActivity;
import com.example.housefinded.net.HttpUrl;
import com.example.javabean.HosueImage;
import com.example.javabean.HouseImageBean;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class MyViewPager extends FrameLayout {

	
	private ImageLoader imageLoader = ImageLoader.getInstance();

	// 图片数量
	private final static int IMAGE_COUNT = 5;
	
	private HttpUrl BASE_PATH=new HttpUrl();
	
	// 图片集合
	private List<ImageView> imageViewsList;
	
	private ViewPager viewPager;
	// 当前位置
	private int currentItem = 0;

	private TextView tv_pager;

	private Context context;
	private HouseImageBean houseImageBean;
	private String urlPath;
	private String[] imageUrls;
	private SecondHouseDetailsActivity secondDetails;
	// Handler
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			viewPager.setCurrentItem(currentItem);
		}

	};

	public MyViewPager(Context context) {
		this(context, null);
	
		// TODO Auto-generated constructor stub
		
		
	}
	
	public MyViewPager(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}

	public MyViewPager(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
			
			this.context = context;

			initImageLoader(context);
			
			initData();
		
	}
	

	public void setUrlPath(String urlPath){
		this.urlPath=urlPath;
		
	}
	public String getUrlPath(){
		return urlPath;
	}

	

	/**
	 * 初始化Data
	 */
	private void initData() {
		imageViewsList = new ArrayList<ImageView>();
		
		new GetListTask().execute("");
	}

	/**
	 * 初始化UI
	 */
	private void initUI(Context context) {
		if (imageUrls == null || imageUrls.length == 0)
			return;

		LayoutInflater.from(context).inflate(R.layout.house_pager_activity, this,
				true);

		for (int i = 0; i < imageUrls.length; i++) {
			ImageView view = new ImageView(context);
			view.setTag(imageUrls[i]);
			if (i == 0)// ��һ��Ĭ��ͼ
				view.setBackgroundResource(R.drawable.appmain_subject_1);
			view.setScaleType(ScaleType.FIT_XY);
			imageViewsList.add(view);

		}
		tv_pager=(TextView) findViewById(R.id.tv_pager);
		tv_pager.setText(currentItem+1+"/"+imageViewsList.size());
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		viewPager.setFocusable(true);

		viewPager.setAdapter(new MyPagerAdapter());
		viewPager.setOnPageChangeListener(new MyPageChangeListener());
	}

	/**
	 * 定义ViewPager适配器
	 * 
	 */
	private class MyPagerAdapter extends PagerAdapter {

		@Override
		public void destroyItem(View container, int position, Object object) {
			// TODO Auto-generated method stub
			// ((ViewPag.er)container).removeView((View)object);
			((ViewPager) container).removeView(imageViewsList.get(position));
		}

		@Override
		public Object instantiateItem(View container, int position) {
			ImageView imageView = imageViewsList.get(position);

			imageLoader.displayImage(imageView.getTag() + "", imageView);

			((ViewPager) container).addView(imageViewsList.get(position));
			return imageViewsList.get(position);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return imageViewsList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
			// TODO Auto-generated method stub

		}

		@Override
		public Parcelable saveState() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void finishUpdate(View arg0) {
			// TODO Auto-generated method stub

		}

	}

	/**
	 * ViewPager监听器
	 * 
	 * 
	 */
	private class MyPageChangeListener implements OnPageChangeListener {

	

		@Override
		
		 public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
		
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageSelected(int pos) {
			// TODO Auto-generated method stub
			tv_pager.setText(pos+1+"/"+imageViewsList.size());
			
		}

	}

	/**
	 * ִ���ֲ�ͼ�л�����
	 * 
	 */
	private class SlideShowTask implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			synchronized (viewPager) {
				currentItem = (currentItem + 1) % imageViewsList.size();
				handler.obtainMessage().sendToTarget();
			}
		}

	}

	
	private void destoryBitmaps() {

		for (int i = 0; i < IMAGE_COUNT; i++) {
			ImageView imageView = imageViewsList.get(i);
			Drawable drawable = imageView.getDrawable();
			if (drawable != null) {
				// ���drawable��view������
				drawable.setCallback(null);
			}
		}
	}

	
	class GetListTask extends AsyncTask<String, Integer, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			
			try {
				
				imageUrls = new String[] {
						BASE_PATH.BASE_URL+getUrlPath()	};
				System.out.println("0123"+BASE_PATH.BASE_URL+getUrlPath()	);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			} 
		}
		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if (result) {
				initUI(context);
			}
		}
	}
 
	/**
	 * ImageLoader ͼƬ�����ʼ��
	 * 
	 * @param context
	 */
	public static void initImageLoader(Context context) {
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove
									// for
									// release
									// app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}
}