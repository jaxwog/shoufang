package com.example.housefinded.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.housefinded.R;
import com.example.housefinded.net.HttpUrl;

import com.example.javabean.House;
import com.example.javabean.RentHouse;
import com.example.javabean.RentHouseBean;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RentHouseAdapter extends BaseAdapter {

	private RentHouseBean rentHouseBean;
	private Context context;
	private BitmapUtils bitmapUtils;
	private HttpUrl baseUrl = new HttpUrl();
	private List<RentHouse> data = new ArrayList<RentHouse>();

	public RentHouseAdapter(List<RentHouse> data2, Context context) {
		this.context = context;
		bitmapUtils = new BitmapUtils(context);
		data = data2;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
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
			viewHolder.tv_releaser = (TextView) convertView.findViewById(R.id.tv_releaser);
			viewHolder.title = (TextView) convertView.findViewById(R.id.title);
			viewHolder.house_mode = (TextView) convertView
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
		RentHouse rentHouse = data.get(position);
		if("0".equals(rentHouse.getUserType())){
			viewHolder.tv_releaser.setText("中介房源");
		}else if("1".equals(rentHouse.getUserType())){
			viewHolder.tv_releaser.setText("个人房源");
			
		}
		viewHolder.title.setText(rentHouse.getTitle());
		viewHolder.house_type.setText(rentHouse.getHouseType());
		if ("0".equals(rentHouse.getTenType())) {

			viewHolder.house_mode.setText("整租");
		} else if ("1".equals(rentHouse.getTenType())) {
			viewHolder.house_mode.setText("合租");
		} else {
			viewHolder.house_mode.setText("求租");
		}
		viewHolder.house_address.setText(rentHouse.getCellName());
		viewHolder.house_price.setText(rentHouse.getRent() + "元/月");
		if ("1".equals(rentHouse.getUserType())) {

			viewHolder.house_1.setText("个人房源");
		}

		bitmapUtils.display(viewHolder.house_image, baseUrl.BASE_URL
				+ rentHouse.getImagePath());
		return convertView;
	}

	private class ViewHolder {
		TextView tv_releaser;
		TextView title;
		TextView house_mode;
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
