package com.example.housefinded.view;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class PinChartview extends View {

	static Canvas c;
	private Paint[] mPaints;
	private Paint mTextPaint;
	private RectF mBigOval;
	float[] mSweep = { 0 };
	private static final float SWEEP_INC = 1;
	private static float[] humidity = { 360 };
	private static String[] titles = { "test" };
	private int color[]={Color.BLUE,Color.GREEN,Color.YELLOW,Color.LTGRAY,Color.RED,Color.MAGENTA,Color.CYAN};
	int width;
	public PinChartview(Context context) {
		super(context);

	}

	public PinChartview(Context context, AttributeSet atr) {
		super(context, atr);
		width = ((Activity) context).getWindowManager().getDefaultDisplay()
				.getWidth();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.TRANSPARENT);// ���ñ�����ɫ(͸��)

		mPaints = new Paint[humidity.length];
		for (int i = 0; i < humidity.length; i++) {
			mPaints[i] = new Paint();
			mPaints[i].setAntiAlias(true);
			mPaints[i].setStyle(Paint.Style.FILL);
			//mPaints[i].setColor(0x880FF000 + i * 0x019e8860);
			mPaints[i].setColor(color[i]);
		}

	//	mBigOval = new RectF(40, 20, 520, 500);// ��ͼ�����ܱ߽�
		mBigOval = new RectF(width/12, 0, width/3, width/4);
		mTextPaint = new Paint();// �����ı�
		mTextPaint.setAntiAlias(true);
		mTextPaint.setColor(Color.WHITE);
		mTextPaint.setTextSize(12F);// �����¶�ֵ�������С
		float start = 0;
		for (int i = 0; i < humidity.length; i++) {
			canvas.drawArc(mBigOval, start, mSweep[i], true, mPaints[i]);
			start += humidity[i];
			if (mSweep[i] < humidity[i]) {
				mSweep[i] += SWEEP_INC;
			}
			canvas.drawRect(new RectF(500, 30 + 15 * i,499, 42 + 15 * i),
					mPaints[i]);
			canvas.drawText(titles[i], 510, 40 + 15 * i, mTextPaint);
		}
		invalidate();
	}

	public void setData(List<PieDataItem> data) {
		if (data == null || data.size() == 0) {
			return;
		}
		humidity = new float[data.size()];
		titles = new String[data.size()];
		mSweep = new float[data.size()];
		for (int i = 0; i < data.size(); i++) {
			humidity[i] = data.get(i).getHumidity();
			titles[i] = data.get(i).getTitle();
			mSweep[i] = 0;
		}
		postInvalidate();
	}

}