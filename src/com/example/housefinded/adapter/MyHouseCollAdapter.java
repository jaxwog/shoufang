package com.example.housefinded.adapter;

import java.util.List;
import com.example.housefinded.R;
import com.example.housefinded.net.HttpUrl;
import com.example.javabean.CollectBean;
import com.example.javabean.House;
import com.example.javabean.SecondHouseBean;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyHouseCollAdapter extends BaseAdapter{
	private Context context;
	private BitmapUtils bitmapUtils;
	private List<CollectBean> secondHouseBean;
	HttpUrl BaseUrl=new HttpUrl();
	
	public MyHouseCollAdapter( List<CollectBean> secondHouseBean, Context context) {
		this.secondHouseBean = secondHouseBean;
		this.context = context;
		bitmapUtils=new BitmapUtils(context);
	}

	@Override
	public int getCount() {
		return secondHouseBean.size();
	}

	@Override
	public Object getItem(int position) {
		return secondHouseBean.get(position);
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
//		[{"houseId":"65","houseType":"2","title":"aSDAta","imagePath":null,"type":"3室3厅3卫","tenType":"0","houseArea":"东方四大","address":"法第三方","releaseDate":"2015-11-03 05:11:33","price":"333","id":"8"}
//		{"houseId":"48","houseType":"1","title":"稀缺房源，低价出售","imagePath":null,"type":"3室2厅2卫","tenType":null,"houseArea":"二七区","address":"二七区京广南路","releaseDate":"2015-11-03 05:11:24","price":"90","id":"6"},
//		{"houseId":"47","houseType":"1","title":"FASDFASDF","imagePath":null,"type":"3室3厅3卫","tenType":null,"houseArea":"ASDFSD","address":"ASDFASD ","releaseDate":"2015-11-03 05:11:08","price":"33","id":"7"}]
		//房屋类型（“1”表示：“二手房”，“2”表示：“租房”，“3”表示：“新房”）
		viewHolder.title.setText(secondHouseBean.get(position).getTitle());
		viewHolder.house_area.setText(secondHouseBean.get(position).getHouseArea()+"m²");
		
		viewHolder.house_address.setText(secondHouseBean.get(position).getAddress());
		bitmapUtils.display(viewHolder.house_image, BaseUrl.BASE_URL+secondHouseBean.get(position).getImagePath());
		if("1".endsWith(secondHouseBean.get(position).getHouseType())){
		viewHolder.house_price.setText(secondHouseBean.get(position).getPrice()+"万");
		viewHolder.house_type.setText(secondHouseBean.get(position).getType()+"(二手房)");
		}else if("2".endsWith(secondHouseBean.get(position).getHouseType())){
			viewHolder.house_price.setText(secondHouseBean.get(position).getPrice()+"元/月");
			viewHolder.house_type.setText(secondHouseBean.get(position).getType()+"(租房)");
		}else{
			viewHolder.house_price.setText(secondHouseBean.get(position).getPrice()+"万");
		}
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
