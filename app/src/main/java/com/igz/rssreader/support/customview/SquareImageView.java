package com.igz.rssreader.support.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class SquareImageView extends ImageView {

	public SquareImageView(Context context) {
		super(context);
	}

	public SquareImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SquareImageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
		int measuredHeight = MeasureSpec.getSize(heightMeasureSpec);
		if (measuredHeight == 0 && measuredWidth == 0) { //Height and width set to wrap_content
			setMeasuredDimension(measuredWidth, measuredHeight);
		} else if (measuredHeight == 0) { //Height set to wrap_content
			setMeasuredDimension(measuredWidth, measuredWidth);
		} else if (measuredWidth == 0) { //Width set to wrap_content
			setMeasuredDimension(measuredHeight, measuredHeight);
		} else { //Width and height are explicitly set (either to match_parent or to exact value)
			setMeasuredDimension(measuredWidth, measuredHeight);
		}
	}

}
