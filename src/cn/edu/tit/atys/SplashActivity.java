package cn.edu.tit.atys;

import cn.edu.tit.R;
import cn.edu.tit.R.id;
import cn.edu.tit.R.layout;
import cn.edu.tit.ui.stub.MyScrollLayout;
import cn.edu.tit.ui.stub.OnViewChangeListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class SplashActivity extends Activity implements OnViewChangeListener {
	private SharedPreferences sp;
	private MyScrollLayout mScrollLayout;
	private ImageView[] imgs;
	private int count;
	private int currentItem;
	private RelativeLayout mainRLayout;
	private LinearLayout pointLLayout;
	AnimationDrawable myAnimation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_splash);
		sp = getSharedPreferences("config", Context.MODE_PRIVATE);
		int times = sp.getInt("loadedTimes", 0);
		if (times == 0) { // 第二次或者第二次以上
			Editor editor = sp.edit();
			editor.putInt("loadedTimes", 1);
			editor.commit();
			initView();
		} else {
			GotoMain();

		}

	}

	public void clickToActivity(View v) {
		GotoMain();

	}

	private void GotoMain() {
		Intent intent = new Intent(SplashActivity.this, TitActivity.class);
		SplashActivity.this.startActivity(intent);
		SplashActivity.this.finish();
	}

	private void initView() {
		mScrollLayout = (MyScrollLayout) findViewById(R.id.ScrollLayout);
		pointLLayout = (LinearLayout) findViewById(R.id.llayout);
		mainRLayout = (RelativeLayout) findViewById(R.id.mainRLayout);
		count = mScrollLayout.getChildCount();
		imgs = new ImageView[count];
		for (int i = 0; i < count; i++) {
			imgs[i] = (ImageView) pointLLayout.getChildAt(i);
			imgs[i].setEnabled(true);
			imgs[i].setTag(i);
		}
		currentItem = 0;
		imgs[currentItem].setEnabled(false);
		mScrollLayout.SetOnViewChangeListener(this);
	}

	@Override
	public void OnViewChange(int position) {
		setcurrentPoint(position);
	}

	private void setcurrentPoint(int position) {
		if (position < 0 || position > count - 1 || currentItem == position) {
			return;
		}
		imgs[currentItem].setEnabled(true);
		imgs[position].setEnabled(false);
		currentItem = position;
	}

}
