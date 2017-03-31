package com.example.housefinded.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.example.housefinded.R;
import com.example.housefinded.net.HttpUrl;
import com.example.javabean.PublicHouseBean;
import com.example.javabean.PublicRenthouseBean;

public class ListShowppadapt  extends BaseAdapter{

	PublicRenthouseBean item;
	LayoutInflater inflater;
	Context context;

	public ListShowppadapt(Context context, PublicRenthouseBean item) {
		this.context = context;
		this.item = item;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return item.getList().size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return item.getList().get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder hold = null;
		if (convertView == null) {
			hold = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.publishlist_item, null);
			hold.addres = (TextView) convertView
					.findViewById(R.id.publish_address);
			hold.shi = (TextView) convertView.findViewById(R.id.publish_shi);
			hold.area = (TextView) convertView.findViewById(R.id.publish_area);
			hold.date = (TextView) convertView.findViewById(R.id.publish_data);
			hold.money = (TextView) convertView
					.findViewById(R.id.publish_money);
			hold.image=(ImageView)convertView.findViewById(R.id.publish_image);
			convertView.setTag(hold);
		} else {
			hold = (ViewHolder) convertView.getTag();
		}
		if (item.getList().get(position) != null) {
			hold.addres
					.setText(item.getList().get(position).getCellName());
			hold.area.setText("建筑面积"
					+ item.getList().get(position).getHouseArea() + "㎡");
			hold.date.setText(item.getList().get(position).getCreateDate());
			hold.money.setText(item.getList().get(position).getRent()
					+ "元/月");
			hold.shi.setText("在"+ item.getList().get(position).getFloor()+"层");
			if (item.getList().get(position).getImagePath() != null
					&& item.getList().get(position).getImagePath() != "") {

				RequestQueue mQueue = Volley.newRequestQueue(context);
				final LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(
						20);
				ImageCache imageCache = new ImageCache() {
					@SuppressLint("NewApi")
					@Override
					public void putBitmap(String key, Bitmap value) {
						lruCache.put(key, value);
					}

					@SuppressLint("NewApi")
					@Override
					public Bitmap getBitmap(String key) {
						return lruCache.get(key);
					}
				};
			String ss	=HttpUrl.BASE_URL+ item.getList().get(position).getImagePath();
			System.out.print(ss);
			ImageLoader imageLoader = new ImageLoader(mQueue, imageCache);
			ImageListener listener = ImageLoader.getImageListener(
						hold.image, R.drawable.btn_click_right_end, R.drawable.btn_click_right_end);
			imageLoader.get(HttpUrl.BASE_URL+ item.getList().get(position).getImagePath(), listener);
			}

		}

		return convertView;
	}

	private class ViewHolder {
		TextView addres;
		TextView shi;
		TextView area;
		TextView money;
		TextView date;
		ImageView image;

	}

}
