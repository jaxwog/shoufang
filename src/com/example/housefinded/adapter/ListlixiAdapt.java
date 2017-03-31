package com.example.housefinded.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.housefinded.R;

public class ListlixiAdapt extends BaseAdapter {
	List<Map<String, Object>> listItems;
	LayoutInflater inflater;

	public ListlixiAdapt(Context context, List<Map<String, Object>> listItems) {
		inflater = LayoutInflater.from(context);
		this.listItems = listItems;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		Holdview hold = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.shangdai_list, null);
			hold = new Holdview();
			hold.textview = (TextView) convertView
					.findViewById(R.id.shangdai_listitem);
			convertView.setTag(hold);
		} else {
			hold = (Holdview) convertView.getTag();
		}
		hold.textview.setText(listItems.get(position).get("title1").toString());
		return convertView;
	}

	public class Holdview {
		TextView textview;
	}
}
