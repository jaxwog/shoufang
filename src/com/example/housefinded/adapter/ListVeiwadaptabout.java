package com.example.housefinded.adapter;

import java.util.List;
import java.util.Map;

import com.example.housefinded.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListVeiwadaptabout extends BaseAdapter {
	List<Map<String, Object>> listItems;
	LayoutInflater inflater;

	public ListVeiwadaptabout(Context context,
			List<Map<String, Object>> listItems) {
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
			convertView = inflater.inflate(R.layout.gridview, null);
			hold = new Holdview();
			hold.imageview = (ImageView) convertView
					.findViewById(R.id.imagebook);
			hold.textview = (TextView) convertView.findViewById(R.id.textbook);
			convertView.setTag(hold);
		} else {
			hold = (Holdview) convertView.getTag();
		}
		hold.textview.setText(listItems.get(position).get("title1").toString());
		hold.imageview.setBackgroundResource((Integer) listItems.get(position)
				.get("image1"));

		return convertView;
	}

	public class Holdview {
		ImageView imageview;
		TextView textview;
	}
}
