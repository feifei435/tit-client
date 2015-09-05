package cn.edu.tit;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;
import android.widget.TextView;
import cn.edu.tit.atys.LoginSystemActivity;
import cn.edu.tit.atys.NewsActivity;
import cn.edu.tit.atys.SettingActivity;
import cn.edu.tit.ui.stub.AnimationTabHost;

public class TabHostActivity extends TabActivity implements OnTabChangeListener {
	private GestureDetector gestureDetector;
	private FrameLayout frameLayout;

	private AnimationTabHost mTabHost;
	private TabWidget mTabWidget;

	/** 记录当前分页ID */
	private int currentTabID = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_host_test_view);

		mTabHost = (AnimationTabHost) findViewById(android.R.id.tabhost);
		mTabWidget = (TabWidget) findViewById(android.R.id.tabs);
		mTabHost.setOnTabChangedListener(this);

		init();

		gestureDetector = new GestureDetector(new TabHostTouch());
		new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (gestureDetector.onTouchEvent(event)) {
					return true;
				}
				return false;
			}
		};

		frameLayout = mTabHost.getTabContentView();
		Log.i("TabHostActivity",
				"TabHostActivity====>>>onCreate()===>>>>frameLayout: "
						+ frameLayout.getChildCount());
	}

	private void init() {
		setIndicator(getString(R.string.tab_news), 0, new Intent(this, NewsActivity.class));
		setIndicator(getString(R.string.tab_edu), 1, new Intent(this, LoginSystemActivity.class));
		setIndicator(getString(R.string.tab_settings), 2, new Intent(this, SettingActivity.class));

		mTabHost.setOpenAnimation(true);
	}

	private void setIndicator(String text, int tabId, Intent intent) {

		View localView = LayoutInflater.from(this.mTabHost.getContext())
				.inflate(R.layout.tab_widget_view, null);
		((TextView) localView.findViewById(R.id.main_activity_tab_image))
				.setText(text);

		String str = String.valueOf(tabId);

		TabHost.TabSpec localTabSpec = mTabHost.newTabSpec(str)
				.setIndicator(localView).setContent(intent);
		mTabHost.addTab(localTabSpec);
	}

	@Override
	public void onTabChanged(String tabId) {
		int tabID = Integer.valueOf(tabId);

		for (int i = 0; i < mTabWidget.getChildCount(); i++) {
			if (i == tabID) {
				mTabWidget.getChildAt(Integer.valueOf(i));
			} else {
				mTabWidget.getChildAt(Integer.valueOf(i))
						.setBackgroundResource(R.drawable.main_meun_bg);
			}
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {

		if (gestureDetector.onTouchEvent(event)) {
			event.setAction(MotionEvent.ACTION_CANCEL);
		}

		return super.dispatchTouchEvent(event);

	}

	private class TabHostTouch extends SimpleOnGestureListener {
		/** 滑动翻页所需距离 */
		private static final int ON_TOUCH_DISTANCE = 30;

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
			return false;
		}
	}
}