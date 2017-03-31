package com.example.housefinded.adapter;

import java.util.List;
import java.util.Map;

import com.example.housefinded.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class TabGridviewAdapt extends BaseAdapter {
	List<Map<String, Object>> listItems;
	LayoutInflater inflater;
Context context;
boolean [] ischeck ;
	public TabGridviewAdapt(Context context, List<Map<String, Object>> listItems,boolean [] ischeck ) {
		inflater = LayoutInflater.from(context);
		this.listItems = listItems;
		this.context=context;
		this.ischeck=ischeck;
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
		final int ss=position;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.house_checkbox, null);
			hold = new Holdview();
			hold.box = (CheckBox) convertView.findViewById(R.id.check_select);

			convertView.setTag(hold);
		} else {
			hold = (Holdview) convertView.getTag();
		}
		hold.box.setText(listItems.get(position).get("title1").toString());
hold.box.setOnCheckedChangeListener(new OnCheckedChangeListener() {
	
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		if(isChecked){
			ischeck[ss]=true;
		}else{
			ischeck[ss]=false;
		}
	}
});
		return convertView;
	}

	public class Holdview {
		CheckBox box;

	}

}
