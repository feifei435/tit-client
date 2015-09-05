package cn.edu.tit.ui.stub;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;
import cn.edu.tit.Application;
import cn.edu.tit.R;
import cn.edu.tit.bean.City;
import cn.edu.tit.bean.GetWeatherTask;
import cn.edu.tit.bean.WeatherInfo;
import cn.edu.tit.util.BaseTools;
import cn.edu.tit.util.Toaster;

/**
 * 窗帘拉开自定义布局
 * 
 * @author Ra blog : http://blog.csdn.net/vipzjyno1/
 */
public class CurtainView extends RelativeLayout implements OnTouchListener {
	public static final int GET_WEATHER_SCUESS = 3;
	public static final int GET_WEATHER_FAIL = 4;
	private static String TAG = "CurtainView";
	private Context mContext;
	/** Scroller 拖动类 */
	private Scroller mScroller;
	/** 屏幕高度 */
	private int mScreenHeigh = 0;
	/** 屏幕宽度 */
	private int mScreenWidth = 0;
	/** 点击时候Y的坐标 */
	private int downY = 0;
	/** 拖动时候Y的坐标 */
	private int moveY = 0;
	/** 拖动时候Y的方向距离 */
	private int scrollY = 0;
	/** 松开时候Y的坐标 */
	private int upY = 0;
	/** 广告幕布的高度 */
	private int curtainHeigh = 0;
	/** 是否 打开 */
	private boolean isOpen = false;
	/** 是否在动画 */
	private boolean isMove = false;
	/** 绳子的图片 */
	private ImageView img_curtain_rope;
	/** 广告的图片 */
	// private ImageView img_curtain_ad;

	private View layout_curtain_ad;
	/** 上升动画时间 */
	private int upDuration = 1000;
	/** 下落动画时间 */
	private int downDuration = 500;
	private Application mApplication = Application.getInstance();

	public CurtainView(Context context) {
		super(context);
		init(context);
	}

	public CurtainView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public CurtainView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	/** 初始化 */
	private void init(Context context) {
		this.mContext = context;
		// Interpolator 设置为有反弹效果的 （Bounce：反弹）
		Interpolator interpolator = new BounceInterpolator();
		mScroller = new Scroller(context, interpolator);
		mScreenHeigh = BaseTools.getWindowHeigh(context);
		mScreenWidth = BaseTools.getWindowWidth(context);
		// 背景设置成透明
		this.setBackgroundColor(Color.argb(0, 0, 0, 0));
		final View view = LayoutInflater.from(mContext).inflate(
				R.layout.curtain, null);
		layout_curtain_ad = view.findViewById(R.id.layout_curtain_ad);
		// img_curtain_ad = (ImageView)view.findViewById(R.id.img_curtain_ad);
		img_curtain_rope = (ImageView) view.findViewById(R.id.img_curtain_rope);
		addView(view);
		layout_curtain_ad.post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				curtainHeigh = layout_curtain_ad.getHeight();
				Log.d(TAG, "curtainHeigh= " + curtainHeigh);
				CurtainView.this.scrollTo(0, curtainHeigh);
				// 注意scrollBy和scrollTo的区别
			}
		});

		img_curtain_rope.setOnTouchListener(this);
	}

	@Override
	protected void onFinishInflate() {
		// TODO Auto-generated method stub
		super.onFinishInflate();
		tv_curTime = (TextView) this.findViewById(R.id.tv_curTime);
		now_tmp = (TextView) this.findViewById(R.id.now_tmp);
		tv_date = (TextView) this.findViewById(R.id.tv_date);
		tv_city = (TextView) this.findViewById(R.id.tv_city);
		// tv_city.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// Intent intent = new Intent(getContext(),SelectCtiyActivity.class);
		// getContext().startActivity(intent);
		// }
		// });
		weather_summary = (TextView) this.findViewById(R.id.weather_summary);
		timeHandler.sendEmptyMessage(0);
		updateWeatherTask(mApplication.getCity());
	}

	public void updateWeatherTask(City city) {
		// City city = new City("北京", "北京", "101010100", "beijing", "bj");
		// System.out.println("执行了updateWeatherTask");
		new GetWeatherTask(timeHandler, city).execute();

	}

	private Handler timeHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (getVisibility() == VISIBLE) {
				timeHandler.sendEmptyMessageDelayed(0, 1000);
				refreshTime();
				//
			}
			switch (msg.what) {
			case GET_WEATHER_SCUESS:
				updateWeatherUI();

				break;
			case GET_WEATHER_FAIL:
				Toaster.makeText(getContext(), "获取天气失败，请重试", Toast.LENGTH_SHORT);
				break;
			}

		}

	};

	@SuppressWarnings("static-access")
	private void refreshTime() {
		// TODO Auto-generated method stub
		Calendar c = Calendar.getInstance();
		// tv_curTime.setText(new String().format("%d：%d",
		// c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE)));
		tv_curTime.setText(getStrTime_hm(c.getTimeInMillis() + ""));

//		Calendar calendar = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_WEEK);
        String today = null;
        if (day == 2) {
            today = "周一";
        } else if (day == 3) {
            today = "周二";
        } else if (day == 4) {
            today = "周三";
        } else if (day == 5) {
            today = "周四";
        } else if (day == 6) {
            today = "周五";
        } else if (day == 7) {
            today = "周六";
        } else if (day == 1) {
            today = "周日";
        }
    
//        tv_date.setText(today);
		tv_date.setText((new String().format("%s / %d月%d日",
				today, c.get(Calendar.MONTH) + 1,
				c.get(Calendar.DAY_OF_MONTH))));

	};

	/*
	 * 将时间戳转为字符串 ，格式：HH:mm
	 */
	public static String getStrTime_hm(String cc_time) {
		String re_StrTime = null;
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		// 例如：cc_time=1291778220
		long lcc_time = Long.valueOf(cc_time);
		re_StrTime = sdf.format(new Date(lcc_time));
		return re_StrTime;
	}

	@Override
	protected void onVisibilityChanged(View changedView, int visibility) {
		// TODO Auto-generated method stub
		super.onVisibilityChanged(changedView, visibility);
		if (visibility == View.GONE) { // 不可见状态
			timeHandler.removeMessages(0); // 不在更新
		} else {
			timeHandler.sendEmptyMessage(0);
		}

	}

	private TextView tv_curTime;
	private TextView now_tmp;
	private TextView tv_date;
	private TextView tv_city;
	private TextView weather_summary;

	/**
	 * 拖动动画
	 * 
	 * @param startY
	 * @param dy
	 *            垂直距离, 滚动的y距离
	 * @param duration
	 *            时间
	 */
	public void startMoveAnim(int startY, int dy, int duration) {
		isMove = true;
		mScroller.startScroll(0, startY, 0, dy, duration);
		invalidate();// 通知UI线程的更新
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
	}

	@Override
	public void computeScroll() {
		// 判断是否还在滚动，还在滚动为true
		if (mScroller.computeScrollOffset()) {
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			// 更新界面
			postInvalidate();
			isMove = true;
		} else {
			isMove = false;
		}
		super.computeScroll();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		if (!isMove) {
			int offViewY = 0;// 屏幕顶部和该布局顶部的距离
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				downY = (int) event.getRawY();
				offViewY = downY - (int) event.getX();
				return true;
			case MotionEvent.ACTION_MOVE:
				moveY = (int) event.getRawY();
				scrollY = moveY - downY;
				if (scrollY < 0) {
					// 向上滑动
					if (isOpen) {
						if (Math.abs(scrollY) <= layout_curtain_ad.getBottom()
								- offViewY) {
							scrollTo(0, -scrollY);
						}
					}
				} else {
					// 向下滑动
					if (!isOpen) {
						if (scrollY <= curtainHeigh) {
							scrollTo(0, curtainHeigh - scrollY);
						}
					}
				}
				break;
			case MotionEvent.ACTION_UP:
				upY = (int) event.getRawY();
				if (Math.abs(upY - downY) < 10) {
					onRopeClick();
					break;
				}
				if (downY > upY) {
					// 向上滑动
					if (isOpen) {
						if (Math.abs(scrollY) > curtainHeigh / 2) {
							// 向上滑动超过半个屏幕高的时候 开启向上消失动画
							startMoveAnim(this.getScrollY(),
									(curtainHeigh - this.getScrollY()),
									upDuration);
							isOpen = false;
						} else {
							startMoveAnim(this.getScrollY(),
									-this.getScrollY(), upDuration);
							isOpen = true;
						}
					}
				} else {
					// 向下滑动
					if (scrollY > curtainHeigh / 2) {
						// 向上滑动超过半个屏幕高的时候 开启向上消失动画
						startMoveAnim(this.getScrollY(), -this.getScrollY(),
								upDuration);
						isOpen = true;
					} else {
						startMoveAnim(this.getScrollY(),
								(curtainHeigh - this.getScrollY()), upDuration);
						isOpen = false;
					}
				}
				break;
			default:
				break;
			}
		}
		return false;
	}

	/**
	 * 点击绳索开关，会展开关闭 在onToch中使用这个中的方法来当点击事件，避免了点击时候响应onTouch的衔接不完美的影响
	 */
	public void onRopeClick() {
		if (isOpen) {
			CurtainView.this.startMoveAnim(0, curtainHeigh, upDuration);
		} else {
			CurtainView.this.startMoveAnim(curtainHeigh, -curtainHeigh,
					downDuration);
		}
		isOpen = !isOpen;
	}

	public synchronized void updateWeatherUI() {
		WeatherInfo weatherInfo = mApplication.getAllWeather();
		// System.out.println(weatherInfo.toString());
		if (weatherInfo != null) {
			String tmp = weatherInfo.getWendu();
			String city = weatherInfo.getCity();
			String summary = weatherInfo.getWeatherSummary();
			if (now_tmp.equals("°")) {
				now_tmp.setText("0°");
			} else {
				now_tmp.setText(tmp);
			}
			if (TextUtils.isEmpty(city)) {
				tv_city.setText("未知城市");
			} else {
				tv_city.setText(city);
			}
			if (summary.equals("")) {
				weather_summary.setText("暂无信息");
			} else {
				weather_summary.setText(summary);
			}

		} else {
			now_tmp.setText("0°");
			tv_city.setText("未知城市");
			weather_summary.setText("暂无信息");
		}

	}
}
