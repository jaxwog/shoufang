package com.example.housefinded.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class GridViewofNew extends GridView{

	public GridViewofNew(Context context) {
		super(context);
		
	}
	 public GridViewofNew(Context context, AttributeSet attrs) {
		 
         super(context, attrs);
     }
	 public GridViewofNew(Context context, AttributeSet attrs, int defStyle) {
         super(context, attrs, defStyle);
     }

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		
		   int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,  
                   MeasureSpec.AT_MOST);  
        super.onMeasure(widthMeasureSpec, expandSpec);
	}
	     
	/* @Override   
	    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)  
	       {  
	           int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,  
	                   MeasureSpec.AT_MOST);  
	        super.onMeasure(widthMeasureSpec, expandSpec);  
	     
	      }  
*/
}
