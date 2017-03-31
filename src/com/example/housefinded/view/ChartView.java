package com.example.housefinded.view;

import com.example.housefinded.activity.SecondHouseDetailsActivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class ChartView extends View{
   private int width;         //屏幕宽度
   private int height;
   public int YPoint;     //原点的Y坐标
   public int XPoint;     //原点的X坐标
   
   public int XLength;        //X轴的长度
    public int YLength;        //Y轴的长度
    public int XScale;     //X的刻度长度(width*8/(10*7))
    public int YScale;     //Y的刻度长度
    private double PPI;       //屏幕分辨率     
    public String[] XLabel=new String[]{"15-2","15-3","15-4","15-5","15-6","15-7","15-8"};
    public String[] YLabel= new String[]{"8500","9500","10500","11500","12500","13500"}; 
    public String[] Data ;
    public String Title="";
    public ChartView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		width=((Activity) context).getWindowManager().getDefaultDisplay()
				.getWidth();
		height=((Activity) context).getWindowManager().getDefaultDisplay()
				.getHeight();
		YPoint=height*3/12;
		YLength=height*3/12;
		YScale=height*3/(12*6);
		XPoint=width/10;
		XLength=width*8/10;
		XScale=width*8/(10*7);
		PPI =  Math.sqrt((Math.pow(width, 2)+Math.pow(height, 2)));  
	}
    public void SetInfo(String[] XLabels,String[] YLabels,String[] AllData,String strTitle)
    {
        XLabel=XLabels;
        YLabel=YLabels;
        Data=AllData;
        Title=strTitle;
    }
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);//重写onDraw方法

        //canvas.drawColor(Color.WHITE);//设置背景颜色
        Paint paint= new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);//去锯齿
        paint.setColor(Color.GRAY);//颜色
        Paint paint1=new Paint();
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setAntiAlias(true);//去锯齿
        paint1.setColor(Color.BLUE);
        paint.setTextSize(18F);  //设置轴文字大小
        //设置Y轴
        canvas.drawLine(XPoint, YPoint-YLength, XPoint, YPoint, paint);   //轴线
        for(int i=0;i*YScale<YLength ;i++)                
        {
            canvas.drawLine(XPoint,YPoint-i*YScale, XPoint+5, YPoint-i*YScale, paint);  //刻度
            try
            {
                canvas.drawText(YLabel[i] , XPoint-35, YPoint-i*YScale+5, paint);  //文字
            }
            catch(Exception e)
            {
            }
        }
        canvas.drawLine(XPoint,YPoint-YLength,XPoint-3,YPoint-YLength+6,paint);  //箭头
        canvas.drawLine(XPoint,YPoint-YLength,XPoint+3,YPoint-YLength+6,paint);            
        //设置X轴
        canvas.drawLine(XPoint,YPoint,XPoint+XLength,YPoint,paint);   //轴线
        for(int i=0;i*XScale<XLength;i++)    
        {
            canvas.drawLine(XPoint+i*XScale, YPoint, XPoint+i*XScale, YPoint-5, paint);  //刻度
            try
            {
                canvas.drawText(XLabel[i] , XPoint+i*XScale-10, YPoint+20, paint);  //文字
                //数据值
                    if(i>0&&YCoord(SecondHouseDetailsActivity.HOUSE_PRICE[i-1])!=-999&&YCoord(SecondHouseDetailsActivity.HOUSE_PRICE[i])!=-999)  //保证有效数据
                        canvas.drawLine(XPoint+(i-1)*XScale, YCoord(SecondHouseDetailsActivity.HOUSE_PRICE[i-1]),  XPoint+i*XScale, YCoord(SecondHouseDetailsActivity.HOUSE_PRICE[i]), paint1);
                    canvas.drawCircle(XPoint+i*XScale,YCoord(SecondHouseDetailsActivity.HOUSE_PRICE[i]), 2, paint1);
           }
            catch(Exception e)
            {
            }
        }
        canvas.drawLine(XPoint+XLength,YPoint,XPoint+XLength-6,YPoint-3,paint);    //箭头
        canvas.drawLine(XPoint+XLength,YPoint,XPoint+XLength-6,YPoint+3,paint);  
        canvas.drawText(Title, 150, 50, paint);
    }
    private int YCoord(String y0)  //计算绘制时的Y坐标，无数据时返回-999
    {
        int y;
        try
        {
            y=Integer.parseInt(y0);
        }
        catch(Exception e)
        {
            return -999;    //出错则返回-999
        }
        try
        {
            return YPoint-y*YScale/Integer.parseInt(YLabel[1]);
        }
        catch(Exception e)
        {
        }
        return y;
    }
}