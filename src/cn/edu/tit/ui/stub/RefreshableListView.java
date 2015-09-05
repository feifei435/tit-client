package cn.edu.tit.ui.stub;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import cn.edu.tit.R;

public class RefreshableListView extends ListView implements OnScrollListener {

	private final static int RELEASE_To_REFRESH = 0;// 发布刷新
	private final static int PULL_To_REFRESH = 1;// 下拉更新
	private final static int REFRESHING = 2;// 刷新
	private final static int DONE = 3;// 完成
	private final static int LOADING = 4;// 加载中

	private final static int ENDINT_DONE = 0;// 完成/等待刷新
	private final static int ENDINT_LOADING = 1;// 加载中

	// 实际的padding的距离与界面上偏移距离的比例
	private final static int RATIO = 3;

	// 用于保证startY的值在一个完整的touch事件中只被记录一次
	private boolean isRecored;

	private int startY;
	private int firstItemIndex;
	private boolean canloadmore = true;// 可以加载更多？
	private boolean canrefresh = true;// 可以刷新？
	private boolean isBack;
	private Context mContext;
	private RotateAnimation animation;
	private RotateAnimation reverseAnimation;
	private boolean isRefreshable;
	private LayoutInflater inflater;
	private LinearLayout headView;
	private ImageView head_arrowImageView;
	private ProgressBar head_progressBar;
	private TextView head_lastUpdatedTextView;
	private int headContentHeight;
	private int headContentWidth;

	private int headstate;
	private int endingstate;

	private LinearLayout endingView;
	private TextView head_tipsTextView;
	private ProgressBar ending_progressBar;
	private int count;
	private boolean enougcount;
	private int lastItemIndex;

	private OnRefreshListener refreshListener;
	private OnLoadMoreListener loadMoreListener;
	private TextView ending_tipsTextView;

	public RefreshableListView(Context context) {
		super(context);
		mContext = context;
		initView();
		// TODO Auto-generated constructor stub
	}

	public RefreshableListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		initView();
		// TODO Auto-generated constructor stub
	}

	public RefreshableListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		initView();
		// TODO Auto-generated constructor stub
	}

	// initial view attrs
	private void initView() {
		addHeadView();
		addEndingView();
		// listener
		setOnScrollListener(this);
		animation = new RotateAnimation(0, -180,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		animation.setInterpolator(new LinearInterpolator());
		animation.setDuration(250);
		animation.setFillAfter(true);

		reverseAnimation = new RotateAnimation(-180, 0,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		reverseAnimation.setInterpolator(new LinearInterpolator());
		reverseAnimation.setDuration(200);
		reverseAnimation.setFillAfter(true);

		isRefreshable = false;
	}

	private void addHeadView() {
		inflater = LayoutInflater.from(mContext);
		headView = (LinearLayout) inflater.inflate(
				R.layout.view_refreshable_listview_list_head, null);
		head_arrowImageView = (ImageView) headView
				.findViewById(R.id.head_arrowImageView);
		head_arrowImageView.setMinimumWidth(70);
		head_arrowImageView.setMinimumHeight(50);
		head_progressBar = (ProgressBar) headView
				.findViewById(R.id.head_progressBar);
		head_tipsTextView = (TextView) headView
				.findViewById(R.id.head_tipsTextView);
		head_lastUpdatedTextView = (TextView) headView
				.findViewById(R.id.head_lastUpdatedTextView);
		// measure the size of view
		measureView(headView);
		headContentHeight = headView.getMeasuredHeight();
		headContentWidth = headView.getMeasuredWidth();
//		headView.setPadding(0, -1 * headContentHeight, 0, 0);
//		headView.invalidate();

		// invoke the override method
		addHeaderView(headView, null, false);

		// flag to estimate the headview`s state
		headstate = DONE;
	}

	private void addEndingView() {
		inflater = LayoutInflater.from(mContext);
		endingView = (LinearLayout) inflater.inflate(
				R.layout.view_refreshable_listview_list_ending, null);
		ending_progressBar = (ProgressBar) endingView
				.findViewById(R.id.ending_progressBar);
		ending_tipsTextView = (TextView) endingView
				.findViewById(R.id.ending_tipsTextView);
		endingView.invalidate();
		// invoke the override method
		addFooterView(endingView, null, false);
		// flag to estimate the endingview`s state
		endingstate = ENDINT_DONE;
	}
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		firstItemIndex = firstVisibleItem;
		lastItemIndex = firstVisibleItem + visibleItemCount - 2;
		count = totalItemCount - 2;
		if (totalItemCount > visibleItemCount) {
			enougcount = true;
		} else {
			enougcount = false;
		}

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		if (lastItemIndex == count && scrollState == SCROLL_STATE_IDLE) {

			// SCROLL_STATE_IDLE=0，滑动停止
			if (endingstate != ENDINT_LOADING) {
				endingstate = ENDINT_LOADING;
				onLoadmore();
			}
		}
		changeEndingViewByState();
	}


	public boolean onTouchEvent(MotionEvent event) {
		if (canrefresh) {
			if (isRefreshable) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if (firstItemIndex == 0 && !isRecored) {
						isRecored = true;
						startY = (int) event.getY();
						// Log.v(TAG, "在down时候记录当前位置‘");
					}
					break;

				case MotionEvent.ACTION_UP:

					if (headstate != REFRESHING && headstate != LOADING) {
						if (headstate == DONE) {
							// 什么都不做
						}
						if (headstate == PULL_To_REFRESH) {
							headstate = DONE;
							changeHeaderViewByState();

							// Log.v(TAG, "由下拉刷新状态，到done状态");
						}
						if (headstate == RELEASE_To_REFRESH) {
							headstate = REFRESHING;
							changeHeaderViewByState();
							onRefresh();

							// Log.v(TAG, "由松开刷新状态，到done状态");
						}
					}

					isRecored = false;
					isBack = false;

					break;

				case MotionEvent.ACTION_MOVE:
					int tempY = (int) event.getY();

					if (!isRecored && firstItemIndex == 0) {
						// Log.v(TAG, "在move时候记录下位置");
						isRecored = true;
						startY = tempY;
					}

					if (headstate != REFRESHING && isRecored
							&& headstate != LOADING) {

						// 保证在设置padding的过程中，当前的位置一直是在head，否则如果当列表超出屏幕的话，当在上推的时候，列表会同时进行滚动

						// 可以松手去刷新了
						if (headstate == RELEASE_To_REFRESH) {

							setSelection(0);

							// 往上推了，推到了屏幕足够掩盖head的程度，但是还没有推到全部掩盖的地步
							if (((tempY - startY) / RATIO < headContentHeight)
									&& (tempY - startY) > 0) {
								headstate = PULL_To_REFRESH;
								changeHeaderViewByState();

								// Log.v(TAG, "由松开刷新状态转变到下拉刷新状态");
							}
							// 一下子推到顶了
							else if (tempY - startY <= 0) {
								headstate = DONE;
								changeHeaderViewByState();

								// Log.v(TAG, "由松开刷新状态转变到done状态");
							}
							// 往下拉了，或者还没有上推到屏幕顶部掩盖head的地步
							else {
								// 不用进行特别的操作，只用更新paddingTop的值就行了
							}
						}
						// 还没有到达显示松开刷新的时候,DONE或者是PULL_To_REFRESH状态
						if (headstate == PULL_To_REFRESH) {

							setSelection(0);

							// 下拉到可以进入RELEASE_TO_REFRESH的状态
							if ((tempY - startY) / RATIO >= headContentHeight) {
								headstate = RELEASE_To_REFRESH;
								isBack = true;
								changeHeaderViewByState();

								// Log.v(TAG, "由done或者下拉刷新状态转变到松开刷新");
							}
							// 上推到顶了
							else if (tempY - startY <= 0) {
								headstate = DONE;
								changeHeaderViewByState();

								// Log.v(TAG, "由DOne或者下拉刷新状态转变到done状态");
							}
						}

						// done状态下
						if (headstate == DONE) {
							if (tempY - startY > 0) {
								headstate = PULL_To_REFRESH;
								changeHeaderViewByState();
							}
						}

						// 更新headView的size
						if (headstate == PULL_To_REFRESH) {
							headView.setPadding(0, -1 * headContentHeight
									+ (tempY - startY) / RATIO, 0, 0);

						}

						// 更新headView的paddingTop
						if (headstate == RELEASE_To_REFRESH) {
							headView.setPadding(0, (tempY - startY) / RATIO
									- headContentHeight, 0, 0);
						}

					}

					break;
				}
			}
		}
		return super.onTouchEvent(event);
	}
	private void changeHeaderViewByState() {
		switch (headstate) {
		case RELEASE_To_REFRESH:
			head_arrowImageView.setVisibility(View.VISIBLE);
			head_progressBar.setVisibility(View.GONE);
			head_tipsTextView.setVisibility(View.VISIBLE);
			head_lastUpdatedTextView.setVisibility(View.VISIBLE);

			head_arrowImageView.clearAnimation();
			head_arrowImageView.startAnimation(animation);

			head_tipsTextView.setText("松开进行刷新");

			// Log.v(TAG, "当前状态，松开刷新");
			break;
		case PULL_To_REFRESH:
			head_progressBar.setVisibility(View.GONE);
			head_tipsTextView.setVisibility(View.VISIBLE);
			head_lastUpdatedTextView.setVisibility(View.VISIBLE);
			head_arrowImageView.clearAnimation();
			head_arrowImageView.setVisibility(View.VISIBLE);// 可不可视
			// 是由RELEASE_To_REFRESH状态转变来的
			if (isBack) {
				isBack = false;
				head_arrowImageView.clearAnimation();
				head_arrowImageView.startAnimation(reverseAnimation);

				head_tipsTextView.setText("向下拖动进行刷新");
			} else {
				head_tipsTextView.setText("向下拖动进行刷新");
			}
			// Log.v(TAG, "当前状态，下拉刷新");
			break;

		case REFRESHING:

			headView.setPadding(0, 0, 0, 0);

			head_progressBar.setVisibility(View.VISIBLE);
			head_arrowImageView.clearAnimation();
			head_arrowImageView.setVisibility(View.GONE);
			head_tipsTextView.setText("正在刷新...");
			head_lastUpdatedTextView.setVisibility(View.VISIBLE);
			// Log.v(TAG, "当前状态,正在刷新...");
			break;
		case DONE:
			headView.setPadding(0, -1 * headContentHeight, 0, 0);

			head_progressBar.setVisibility(View.GONE);
			head_arrowImageView.clearAnimation();
			head_arrowImageView
					.setImageResource(R.drawable.ic_pulltorefresh_arrow);
			head_tipsTextView.setText("松开进行刷新");
			head_lastUpdatedTextView.setVisibility(View.VISIBLE);

			// Log.v(TAG, "当前状态，done");
			break;
		}
	}


	public void setOnRefreshListener(OnRefreshListener refreshListener) {
		this.refreshListener = refreshListener;
		isRefreshable = true;
	}

	public void setOnLoadMoreListener(OnLoadMoreListener loadMoreListener) {
		this.loadMoreListener = loadMoreListener;
	}

	public interface OnRefreshListener {
		public void onRefresh();
	}

	public interface OnLoadMoreListener {
		public void onLoadMore();
	}
	public void setCanLoadMore(boolean args) {
		canloadmore = args;
		endingstate = ENDINT_DONE;
		changeEndingViewByState();
	}

	public void setCanreFresh(boolean arg) {
		canrefresh = arg;
	}



	/*
	 * 刷新完成
	 */
	public void onRefreshComplete() {
		headstate = DONE;
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日  HH:mm");
		String date = format.format(new Date());
		head_lastUpdatedTextView.setText("上次刷新 ：" + date);
		changeHeaderViewByState();
	}

	/**
	 * 加载更多完成
	 */
	public void onLoadMoreComplete() {
		endingstate = ENDINT_DONE;
		changeEndingViewByState();
	}

	/**
	 * 改变加载更多状态
	 */
	private void changeEndingViewByState() {
		if (canloadmore) {
			// 允许加载更多
			switch (endingstate) {
			case ENDINT_LOADING:// 刷新中
				ending_tipsTextView.setText("加载中...");
				ending_tipsTextView.setVisibility(View.VISIBLE);
				ending_progressBar.setVisibility(View.VISIBLE);
				break;
			case ENDINT_DONE:// 完成、等待刷新
			default:
				if (enougcount) {
					endingView.setVisibility(View.VISIBLE);
				} else {
					endingView.setVisibility(View.GONE);
				}
				ending_tipsTextView.setText("更多");
				ending_tipsTextView.setVisibility(View.VISIBLE);
				ending_progressBar.setVisibility(View.GONE);
				break;
			}
		} else {
			endingView.setVisibility(View.GONE);
			ending_tipsTextView.setVisibility(GONE);
			ending_progressBar.setVisibility(GONE);
		}

	}
	private void onRefresh() {
		if (null != refreshListener) {
			refreshListener.onRefresh();
		}
	}

	private void onLoadmore() {
		if (canloadmore) {
			if (null != loadMoreListener) {
				loadMoreListener.onLoadMore();
			}
		}
	}
	// 此方法直接照搬自网络上的一个下拉刷新的demo，此处是“估计”headView的width以及height
	private void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}

		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}




	public void setAdapter(BaseAdapter adapter) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日  HH:mm");
		String date = format.format(new Date());
		head_lastUpdatedTextView.setText("上次刷新 ：" + date);

		// 初始是隐藏的，因为数据可能不足以充满屏幕
		endingView.setVisibility(View.GONE);

		super.setAdapter(adapter);
	}

}
