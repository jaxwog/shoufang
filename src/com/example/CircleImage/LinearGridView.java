package com.example.CircleImage;

import com.example.housefinded.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;

public class LinearGridView extends GridView {

	public LinearGridView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public LinearGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public LinearGridView(Context context, AttributeSet attr, int a) {
		super(context, attr, a);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {

		super.dispatchDraw(canvas);
		View localview1 = getChildAt(0);
		int column = getWidth() / localview1.getWidth();
		int childcount = getChildCount();
		Paint localpaint;
		localpaint = new Paint();
		localpaint.setStyle(Paint.Style.STROKE);
		localpaint.setColor(getContext().getResources().getColor(R.color.grey));
		for (int i = 0; i < childcount; i++) {
			View cellView = getChildAt(i);
			if ((i + 1) % column == 0) {
				canvas.drawLine(cellView.getLeft(), cellView.getBottom(),
						cellView.getRight(), cellView.getBottom(), localpaint);

			} else if ((i + 1) > (childcount - (childcount % column))) {
				canvas.drawLine(cellView.getRight(), cellView.getTop(),
						cellView.getRight(), cellView.getBottom(), localpaint);
			} else {
				canvas.drawLine(cellView.getRight(), cellView.getTop(),
						cellView.getRight(), cellView.getBottom(), localpaint);
				canvas.drawLine(cellView.getLeft(), cellView.getBottom(),
						cellView.getRight(), cellView.getBottom(), localpaint);
			}
		}
		if (childcount % column != 0) {
			for (int j = 0; j < (column - childcount % column); j++) {
				View lastView = getChildAt(childcount - 1);
				canvas.drawLine(lastView.getRight() + lastView.getWidth() * j,
						lastView.getTop(),
						lastView.getRight() + lastView.getWidth() * j,
						lastView.getBottom(), localpaint);
			}
		}

	}
}
