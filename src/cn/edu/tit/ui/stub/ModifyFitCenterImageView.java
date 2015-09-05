package cn.edu.tit.ui.stub;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

public class ModifyFitCenterImageView extends ImageView {
	private boolean isModify = true;

	public ModifyFitCenterImageView(Context context) {
		super(context);
	}

	public ModifyFitCenterImageView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
	}

	public ModifyFitCenterImageView(Context context, AttributeSet attributeSet,
			int param) {
		super(context, attributeSet, param);
	}

	// public final void a() {
	// this.a = true;
	// }

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		if (isModify) {
			Drawable drawable = getDrawable(); // 获取到布局中的imageView图像
			if (drawable != null) {
				int intrinsicWidth = drawable.getIntrinsicWidth(); // 得到图像的固定宽度
				int intrinsicHeight = drawable.getIntrinsicHeight(); // 得到图像的固定高度
				if (intrinsicWidth > 1 && intrinsicHeight > 1) {
					int measureWidth = getMeasuredWidth();
					int measureHeight = getMeasuredHeight();
					int i5 = intrinsicWidth
							* (measureHeight - getPaddingTop() - getPaddingBottom())
							/ intrinsicHeight + getPaddingLeft()
							+ getPaddingRight();
					if (measureWidth != i5) {
						setMeasuredDimension(i5, measureHeight);
					}
				}

				if (getScaleType() == ImageView.ScaleType.FIT_CENTER) {
					Drawable localDrawable1 = getDrawable();
					if (localDrawable1 != null) {
						// continue;
						int i = localDrawable1.getIntrinsicWidth();
						int j = localDrawable1.getIntrinsicHeight();
						if (!(i <= 0) || !(j <= 0)) {
							// continue;
							int k = getMeasuredWidth();
							int m = getMeasuredHeight();
							int n = j
									* (k - getPaddingLeft() - getPaddingRight())
									/ i + getPaddingTop() + getPaddingBottom();

							if ((View.MeasureSpec.getMode(heightMeasureSpec) == 0)
									|| (m <= n)) {
								setMeasuredDimension(k, n);
//								continue;
							}
							setScaleType(ImageView.ScaleType.CENTER_CROP);
						}
					}
				}

			}

		}
	}
}
//
// protected void onMeasure(int paramInt1, int paramInt2)
// {
// super.onMeasure(paramInt1, paramInt2);
// if (this.a)
// {
// Drawable localDrawable2 = getDrawable();
// if (localDrawable2 != null)
// {
// int i1 = localDrawable2.getIntrinsicWidth();
// int i2 = localDrawable2.getIntrinsicHeight();
// if ((i1 > 0) && (i2 > 0))
// {
// int i3 = getMeasuredWidth();
// int i4 = getMeasuredHeight();
// int i5 = i1 * (i4 - getPaddingTop() - getPaddingBottom()) / i2 +
// getPaddingLeft() + getPaddingRight();
// if (i3 != i5)
// setMeasuredDimension(i5, i4);
// }
// }
// }
// while (true)
// {
// return;
// if (getScaleType() == ImageView.ScaleType.FIT_CENTER)
// {
// Drawable localDrawable1 = getDrawable();
// if (localDrawable1 == null)
// continue;
// int i = localDrawable1.getIntrinsicWidth();
// int j = localDrawable1.getIntrinsicHeight();
// if ((i <= 0) || (j <= 0))
// continue;
// int k = getMeasuredWidth();
// int m = getMeasuredHeight();
// int n = j * (k - getPaddingLeft() - getPaddingRight()) / i +
// getPaddingTop() + getPaddingBottom();
// if ((View.MeasureSpec.getMode(paramInt2) == 0) || (m <= n))
// {
// setMeasuredDimension(k, n);
// continue;
// }
// setScaleType(ImageView.ScaleType.CENTER_CROP);
// continue;
// }
// }
// }
// }