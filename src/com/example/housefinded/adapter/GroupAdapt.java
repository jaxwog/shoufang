package com.example.housefinded.adapter;

import java.util.List;

import com.example.housefinded.R;
import com.lidroid.xutils.db.sqlite.CursorUtils.FindCacheSequence;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GroupAdapt extends BaseAdapter{
private Context content;
private List<String> item;
public GroupAdapt( Context content,List<String> item){
	this.content=content;
	this.item=item;
}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return item.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return item.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Viewhold hold = null;
		if (convertView == null) {
			hold = new Viewhold();
			convertView = LayoutInflater.from(content).inflate(
					R.layout.itempopuwindow, null);
			hold.text=(TextView)convertView.findViewById(R.id.groupitem_1);
			convertView.setTag(hold);
		} else {
			hold = (Viewhold) convertView.getTag();
		}
		hold.text.setText(item.get(position));
		return convertView;
	}
class Viewhold{
	TextView text;
}
}
