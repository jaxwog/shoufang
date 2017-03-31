package com.example.housefinded.adapter;

import java.util.List;

import com.example.housefinded.R;
import com.example.housefinded.bean.ProductBean;



import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyPopNearAdapter extends BaseAdapter{
	private List<ProductBean> list;
	private Context context;

	public MyPopNearAdapter(Context context,  List<ProductBean> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	// 每个convert view都会调用此方法，获得当前�?��要的view样式
	public int getItemViewType(int position, List<ProductBean> list) {
		if (position == (list.size() - 1) && list.size() > 1)
			return 0;
		else if (position == 0 && (list.size() == 1)) {
			return 2;
		} else
			return 1;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		int type = getItemViewType(position, list);
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.gw_popwindow_bg, null);
		}
		
		TextView tv = (TextView) convertView.findViewById(R.id.tv_list_item);
		
		tv.setText(list.get(position).getNear());
		return convertView;
	}

}
