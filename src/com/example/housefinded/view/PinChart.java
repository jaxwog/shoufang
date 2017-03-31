package com.example.housefinded.view;

import com.example.housefinded.activity.SecondHouseDetailsActivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;

public class PinChart extends View {

	static Canvas c;
	private Paint[] mPaints;
	private Paint mTextPaint;
	private RectF mBigOval;
	int width;
	float[] mSweep = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	private static final float SWEEP_INC = 1;

	public PinChart(Context context) {
		super(context);

	}

	public PinChart(Context context, AttributeSet atr) {
		super(context, atr);
		width = ((Activity) context).getWindowManager().getDefaultDisplay()
				.getWidth();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.TRANSPARENT);// 设置背景颜色(透明)

		mPaints = new Paint[SecondHouseDetailsActivity.FEE_DATA.length];
		for (int i = 0; i < SecondHouseDetailsActivity.FEE_DATA.length; i++) {
			mPaints[i] = new Paint();
			mPaints[i].setAntiAlias(true);
			mPaints[i].setStyle(Paint.Style.FILL);
			if (i == SecondHouseDetailsActivity.FEE_DATA.length - 1||i==0) {
 
				mPaints[i].setColor(Color.TRANSPARENT);
			} else {
				mPaints[i].setColor(0x880FF000 + i * 0x019e8860);

			}
		}

		mBigOval = new RectF(width/12, 0, width/3, width/4);// 饼图的四周边界

		mTextPaint = new Paint();// 绘制文本
		mTextPaint.setAntiAlias(true);
		mTextPaint.setColor(Color.BLACK);

		mTextPaint.setTextSize(20F);// 设置温度值的字体大小
		float start = 0;
		for (int i = 0; i < SecondHouseDetailsActivity.FEE_DATA.length; i++) {
			canvas.drawArc(mBigOval, start, mSweep[i], true, mPaints[i]);
			start += SecondHouseDetailsActivity.FEE_DATA[i];
			if (mSweep[i] < SecondHouseDetailsActivity.FEE_DATA[i]) {
				mSweep[i] += SWEEP_INC;
			}
			canvas.drawRect(new RectF(width / 2 - 40, 30 + 25 * i,
					width / 2 - 28, 42 + 25 * i), mPaints[i]);
			canvas.drawText(SecondHouseDetailsActivity.FEE_TEXT[i],
					width / 2 - 20, 40 + 25 * i, mTextPaint);
		}
		invalidate();
	}

}
