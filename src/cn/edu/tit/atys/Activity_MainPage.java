package cn.edu.tit.atys;
import cn.edu.tit.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class Activity_MainPage extends Activity {
	private WebView wv_MainPage;
	private String url;
	ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainpage);
		
		Intent intent = this.getIntent();        //获取已有的intent对象   
		Bundle bundle = intent.getExtras();      //获取intent里面的bundle对象   
		url = bundle.getString("intentFlag");    //获取Bundle里面的字符串 
		
		
		wv_MainPage = (WebView) findViewById(R.id.activity_mainpage_wv);
		// 1.先判断手机是否联网

		wv_MainPage.loadUrl(url);
		wv_MainPage.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				if (pd == null) {
					pd = ProgressDialog.show(Activity_MainPage.this, "尊敬的用户",
							"网页加载中！请您稍等。。。");
				} else {
					pd.show();
				}
				super.onPageStarted(view, url, favicon);
			}
			@Override
			public void onPageFinished(WebView view, String url) {
				if (pd != null) {
					pd.hide();
					super.onPageFinished(view, url);
				}
			}
		});

		wv_MainPage.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onReceivedTitle(WebView view, String title) {
				super.onReceivedTitle(view, title);
			}

			public void onProgressChanged(WebView view, int progress) {
				Activity_MainPage.this.setProgress(progress * 1000);
			}
		});
		wv_MainPage.setOnTouchListener(onTouchListener);
	}

	private OnTouchListener onTouchListener = new OnTouchListener() {
		private float OldX1, OldY1, OldX2, OldY2;
		private float NewX1, NewY1, NewX2, NewY2;

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_POINTER_2_DOWN:
				if (event.getPointerCount() == 2) {
					for (int i = 0; i < event.getPointerCount(); i++) {
						if (i == 0) {
							OldX1 = event.getX(i);
							OldY1 = event.getY(i);
						} else if (i == 1) {
							OldX2 = event.getX(i);
							OldY2 = event.getY(i);
						}
					}
				}
				break;
			case MotionEvent.ACTION_MOVE:
				if (event.getPointerCount() == 2) {
					for (int i = 0; i < event.getPointerCount(); i++) {
						if (i == 0) {
							NewX1 = event.getX(i);
							NewY1 = event.getY(i);
						} else if (i == 1) {
							NewX2 = event.getX(i);
							NewY2 = event.getY(i);
						}
					}
					float disOld = (float) Math
							.sqrt((Math.pow(OldX2 - OldX1, 2) + Math.pow(OldY2
									- OldY1, 2)));
					float disNew = (float) Math
							.sqrt((Math.pow(NewX2 - NewX1, 2) + Math.pow(NewY2
									- NewY1, 2)));
					Log.d("onTouch", "disOld=" + disOld + "|disNew=" + disNew);
					if (disOld - disNew >= 25) {
						// 缩小
						wv_MainPage.zoomOut();

					} else if (disNew - disOld >= 25) {
						// 放大
						wv_MainPage.zoomIn();
					}
					OldX1 = NewX1;
					OldX2 = NewX2;
					OldY1 = NewY1;
					OldY2 = NewY2;
				}
			}

			return false;
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			Activity_MainPage.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
