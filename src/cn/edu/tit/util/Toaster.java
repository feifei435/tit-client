package cn.edu.tit.util;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import cn.edu.tit.R;

public class Toaster {
	public static int SHOWN = 0;
	public static int HIDDEN = 1;
	private static Animation animation;

	public static Toast makeText(Context context, CharSequence text,
			int duration) {
		Toast toast = Toast.makeText(context, text, duration);
		toast.setGravity(Gravity.CENTER, 0, 0);
		LinearLayout toastView = (LinearLayout) toast.getView();
		// ImageView imageCodeProject = new
		// ImageView(getApplicationContext());
		// imageCodeProject.setImageResource(R.drawable.icon);
		// toastView.addView(imageCodeProject, 0);
		toastView.setOrientation(LinearLayout.HORIZONTAL);
		ImageView image = new ImageView(context);
		if (animation == null) {
			animation = AnimationUtils.loadAnimation(context,
					R.anim.custom_toast_image_anim);
			animation.setDuration(1000L);
		}
		image.startAnimation(animation);
		image.setImageResource(R.drawable.toast_icon);
		// image.setScaleType(ScaleType.MATRIX);
		toastView.addView(image, 0);
		return toast;
		// toast.show();

	}
	
	public static Toast makeText(Context context, int  resId,
			int duration) {
		Toast toast = Toast.makeText(context, context.getString(resId), duration);
		toast.setGravity(Gravity.CENTER, 0, 0);
		LinearLayout toastView = (LinearLayout) toast.getView();
		// ImageView imageCodeProject = new
		// ImageView(getApplicationContext());
		// imageCodeProject.setImageResource(R.drawable.icon);
		// toastView.addView(imageCodeProject, 0);
		toastView.setOrientation(LinearLayout.HORIZONTAL);
		ImageView image = new ImageView(context);
		if (animation == null) {
			animation = AnimationUtils.loadAnimation(context,
					R.anim.custom_toast_image_anim);
			animation.setDuration(1000L);
		}
		image.startAnimation(animation);
		image.setImageResource(R.drawable.toast_icon);
		// image.setScaleType(ScaleType.MATRIX);
		toastView.addView(image, 0);
		return toast;
		// toast.show();

	}

	
	public static Toast makeText(Context context, String text,
			int duration) {
		Toast toast = Toast.makeText(context, text, duration);
		toast.setGravity(Gravity.CENTER, 0, 0);
		LinearLayout toastView = (LinearLayout) toast.getView();
		// ImageView imageCodeProject = new
		// ImageView(getApplicationContext());
		// imageCodeProject.setImageResource(R.drawable.icon);
		// toastView.addView(imageCodeProject, 0);
		toastView.setOrientation(LinearLayout.HORIZONTAL);
		ImageView image = new ImageView(context);
		if (animation == null) {
			animation = AnimationUtils.loadAnimation(context,
					R.anim.custom_toast_image_anim);
			animation.setDuration(1000L);
		}
		image.startAnimation(animation);
		image.setImageResource(R.drawable.toast_icon);
		// image.setScaleType(ScaleType.MATRIX);
		toastView.addView(image, 0);
		return toast;
		// toast.show();

	}


}
