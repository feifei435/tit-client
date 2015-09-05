package cn.edu.tit.atys;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;
import cn.edu.tit.R;
import cn.edu.tit.R.id;
import cn.edu.tit.R.layout;
import cn.edu.tit.ui.stub.AnimationTabHost;
import cn.edu.tit.util.Toaster;

public class TitActivity extends TabActivity implements OnTabChangeListener {
//	private GestureDetector gestureDetector;
//	private FrameLayout frameLayout;

	private AnimationTabHost mTabHost;
	private TabWidget mTabWidget;
	private ImageView iv_goBack;
	private TextView tv_title;
	private RadioGroup radioGroup;

	/** 记录当前分页ID */
	private int currentTabID = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		iv_goBack = (ImageView) this.findViewById(R.id.iv_goBack);
		iv_goBack.setVisibility(View.GONE);
		tv_title = (TextView) this.findViewById(R.id.tv_title);

		tv_title.setText(R.string.app_name);
		mTabHost = (AnimationTabHost) findViewById(android.R.id.tabhost);
		mTabWidget = (TabWidget) findViewById(android.R.id.tabs);
		mTabWidget.setGravity(Gravity.CENTER);
		mTabHost.setOnTabChangedListener(this);
		initView();

//		gestureDetector = new GestureDetector(new TabHostTouch());
//		new View.OnTouchListener() {
//			public boolean onTouch(View v, MotionEvent event) {
//				if (gestureDetector.onTouchEvent(event)) {
//					
//					return true;
//				}
//				return false;
//			}
//		};

//		frameLayout = mTabHost.getTabContentView();
//		Log.i("TabHostActivity",
//				"TabHostActivity====>>>onCreate()===>>>>frameLayout: "
//						+ frameLayout.getChildCount());
	}

	private void initView() {
		TabSpec tabspec1 = mTabHost.newTabSpec("0");
		tabspec1.setIndicator(getString(R.string.tab_news));
		tabspec1.setContent(new Intent(this, NewsActivity.class));
		mTabHost.addTab(tabspec1);
		// TabSpec条目
		TabSpec tabspec2 = mTabHost.newTabSpec("1");
		tabspec2.setIndicator(getString(R.string.tab_edu));
		tabspec2.setContent(new Intent(this, LoginSystemActivity.class));
		mTabHost.addTab(tabspec2);
		// TabSpec条目
		TabSpec tabspec3 = mTabHost.newTabSpec("2");
		tabspec3.setIndicator(getString(R.string.tab_settings));
		tabspec3.setContent(new Intent(this, SettingActivity.class));
		mTabHost.addTab(tabspec3);
		mTabHost.setOpenAnimation(true);

		radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
		radioGroup.setOnCheckedChangeListener(checkedChangeListener);

	}

	@Override
	public void onTabChanged(String tabId) {
		int tabID = Integer.valueOf(tabId);

		for (int i = 0; i < mTabWidget.getChildCount(); i++) {
			if (i == tabID) {
				mTabWidget.getChildAt(Integer.valueOf(i));
				// .setBackgroundResource(R.drawable.topic_subscribe_btn_pressed);
				// //设置TAB背景
			} else {
				mTabWidget.getChildAt(Integer.valueOf(i));
			}
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {

//		if (gestureDetector.onTouchEvent(event)) {
//			event.setAction(MotionEvent.ACTION_CANCEL);
//		}

		return super.dispatchTouchEvent(event);

	}

	private class TabHostTouch extends SimpleOnGestureListener {
		/** 滑动翻页所需距离 */
		private static final int ON_TOUCH_DISTANCE = 500;

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			if (e1.getX() - e2.getX() <= (-ON_TOUCH_DISTANCE)) {
				currentTabID = mTabHost.getCurrentTab() - 1;

				if (currentTabID < 0) {
					currentTabID = mTabHost.getTabCount() - 1;
				}
			} else if (e1.getX() - e2.getX() >= ON_TOUCH_DISTANCE) {
				currentTabID = mTabHost.getCurrentTab() + 1;

				if (currentTabID >= mTabHost.getTabCount()) {
					currentTabID = 0;
				}
			}
			mTabHost.setCurrentTab(currentTabID);
			if (currentTabID == 0) {
				radioGroup.check(R.id.radio_location);
			} else if (currentTabID == 1) {
				radioGroup.check(R.id.radio_map);
			} else if (currentTabID == 2) {
				radioGroup.check(R.id.radio_setting);
			}
			return true;
		}
	}

	private OnCheckedChangeListener checkedChangeListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (checkedId) {
			case R.id.radio_location:
				// Toaster.makeText(getApplicationContext(), "news", 0).show();
				mTabHost.setCurrentTabByTag("0");
				break;

			case R.id.radio_map:
				// Toaster.makeText(getApplicationContext(), "search",
				// 0).show();
				mTabHost.setCurrentTabByTag("1");
				break;
			case R.id.radio_setting:
				// Toaster.makeText(getApplicationContext(), "setting",
				// 0).show();
				mTabHost.setCurrentTabByTag("2");
				break;

			}
		}
	};

}