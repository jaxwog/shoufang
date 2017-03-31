package com.example.housefinded.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class Viewpageradapter extends PagerAdapter {
	List<ImageView> list;

	public Viewpageradapter(List<ImageView> list) {
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub\
		 
         container.removeView(list.get(position));  
         //view.setImageBitmap(null); 
	}

	@Override
	public int getItemPosition(Object object) {
		// TODO Auto-generated method stub
		return super.getItemPosition(object);
	}

	@Override
	public Object instantiateItem(View container, int position) {
		// TODO Auto-generated method stub

		if (list.get(position % list.size()).getParent() != null) {  
            ((ViewPager) list.get(position % list.size())  
                    .getParent()).removeView(list.get(position  
                    % list.size()));  
        }  
        ((ViewPager) container).addView(  
        		list.get(position % list.size()), 0);  
        return list.get(position % list.size()); 
	}
}
