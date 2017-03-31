package com.example.housefinded.adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.housefinded.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;

public class ImageViewadapt extends BaseAdapter {
	List<Map<String, Object>> listItems;
	LayoutInflater inflater;
	Context context;
	List<File> file;
int ss=0;
	public ImageViewadapt(Context context, List<Map<String, Object>> listItems,List<File> file) {
		inflater = LayoutInflater.from(context);
		this.listItems = listItems;
		this.context = context;
		this.file=file;
	}
	public ImageViewadapt(Context context, List<Map<String, Object>> listItems) {
		inflater = LayoutInflater.from(context);
		this.listItems = listItems;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listItems.size();//返回listiview数目加1
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
	final int nn = position;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.image_adapt, null);
			hold = new Holdview();
			hold.imageview = (ImageView) convertView
					.findViewById(R.id.piture_set);
			hold.imagebutton = (ImageButton) convertView
					.findViewById(R.id.deletepiture);
			convertView.setTag(hold);
		} else {
			hold = (Holdview) convertView.getTag();
		}
		
		
		
		int b=listItems.size();
	
		if ( listItems != null && listItems.size()> 1) {
			hold.imageview.setImageBitmap((Bitmap) listItems.get(position).get("image1"));
			hold.imagebutton.setVisibility(View.VISIBLE);
		}else {
			hold.imageview.setImageBitmap((Bitmap) listItems.get(position).get("image1"));
			hold.imagebutton.setVisibility(View.GONE);
		}
		
		if (position ==0) {
			hold.imagebutton.setVisibility(View.GONE);
		}

    
//		Intent intent=new Intent();
//		intent.setClass(context, MineActivity.class);
		
		//删除按钮点击事件
		hold.imagebutton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				file.remove(nn-1);
				listItems.remove(nn);
				notifyDataSetChanged();
			}
		});
		return convertView;
	}

	public class Holdview {
		ImageView imageview;
		ImageButton imagebutton;
	}
	

}
