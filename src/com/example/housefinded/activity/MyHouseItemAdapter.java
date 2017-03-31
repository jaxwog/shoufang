package com.example.housefinded.activity;

import com.example.housefinded.R;
import com.example.housefinded.net.HttpUrl;
import com.example.javabean.House;
import com.example.javabean.SecondHouseBean;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyHouseItemAdapter extends BaseAdapter {

	
	private SecondHouseBean secondHouseBean;
	private Context context;
	private Gson gson;
	private BitmapUtils bitmapUtils;
	HttpUrl BaseUrl=new HttpUrl();
	
	public MyHouseItemAdapter(SecondHouseBean secondHouseBean, Context context) {
		this.secondHouseBean = secondHouseBean;
		this.context = context;
		bitmapUtils=new BitmapUtils(context);
	}

	@Override
	public int getCount() {
		return secondHouseBean.getList().size();
	}

	@Override
	public Object getItem(int position) {
		return secondHouseBean.getList().get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_sechouse, null);
			viewHolder.title = (TextView) convertView.findViewById(R.id.title);
			viewHolder.house_area = (TextView) convertView
					.findViewById(R.id.tv_tentype);
			viewHolder.house_type = (TextView) convertView
					.findViewById(R.id.house_type);
			viewHolder.distance = (TextView) convertView
					.findViewById(R.id.distance);
			viewHolder.house_address = (TextView) convertView
					.findViewById(R.id.house_address);
			viewHolder.house_price = (TextView) convertView
					.findViewById(R.id.house_price);
			viewHolder.house_1 = (TextView) convertView
					.findViewById(R.id.house_1);
			viewHolder.house_2 = (TextView) convertView
					.findViewById(R.id.house_2);
			viewHolder.house_3 = (TextView) convertView
					.findViewById(R.id.house_3);
			viewHolder.house_image = (ImageView) convertView
					.findViewById(R.id.house_image);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		House house = secondHouseBean.getList().get(position);
		viewHolder.title.setText(house.getTitle());
		viewHolder.house_area.setText(house.getHouseArea()+"m²");
		viewHolder.house_type.setText(house.getHouseType());

		viewHolder.house_address.setText(house.getBuildingAdress());
		System.out.println("BuildingAddress"+house.getBuildingAdress());
		viewHolder.house_price.setText(house.getHousePrice()+"万");
		String ss=house.getImagePath();
		System.out.print(ss);
		bitmapUtils.display(viewHolder.house_image, BaseUrl.BASE_URL+house.getImagePath());
	System.out.println("BaseUrl.BASE_URL"+BaseUrl.BASE_URL+house.getImagePath());
		return convertView;
	}

	private class ViewHolder {
		TextView title;
		TextView house_area;
		TextView house_type;
		TextView distance;
		TextView house_address;
		TextView house_price;
		TextView house_1;
		TextView house_2;
		TextView house_3;
		ImageView house_image;

	}
}
