package com.example.housefinded.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.Tools.AsyncImageLoader;
import com.example.Tools.AsyncImageLoader.ImageCallback;
import com.example.housefinded.R;
import com.example.housefinded.net.HttpUrl;
import com.example.javabean.HosueImage;

public class RentHouseViewPagerAdapter extends PagerAdapter {
	private ImageView image;
	private List<HosueImage> list;
	private Context context;
	private List<ImageView> imageViewList = new ArrayList<ImageView>();
	private AsyncImageLoader imageloader;
	private HttpUrl baseUrl=new HttpUrl();

	public RentHouseViewPagerAdapter(List<HosueImage> list, Context context) {
		this.list = list;
		this.context = context;
		for (int i = 0; i < this.list.size(); i++) {
			ImageView iv = new ImageView(context);
			imageViewList.add(iv);
		}
		imageloader = AsyncImageLoader.getInstance();
	}
 
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return imageViewList.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(imageViewList.get(position));
	}

	@Override
	public Object instantiateItem(final ViewGroup container, final int position) {
		Drawable cachedImage = imageloader.loadDrawable(baseUrl.BASE_URL+list.get(position)
				.getSource(), new ImageCallback() {

			public void imageLoaded(Drawable imageDrawable, String imageUrl) {

				image = imageViewList.get(position);
				image.setBackgroundDrawable(imageDrawable);
				container.removeView(imageViewList.get(position));
				container.addView(imageViewList.get(position));
				// adapter.notifyDataSetChanged();

			}
		});

		image = imageViewList.get(position);
		image.setBackgroundDrawable(cachedImage);

		container.removeView(imageViewList.get(position));
		container.addView(imageViewList.get(position));
		// adapter.notifyDataSetChanged();

		return imageViewList.get(position);
	}

}
