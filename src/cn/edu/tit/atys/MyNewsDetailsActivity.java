package cn.edu.tit.atys;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import cn.edu.tit.R;
import cn.edu.tit.R.color;
import cn.edu.tit.R.id;
import cn.edu.tit.R.layout;
import cn.edu.tit.bean.MyTask;
import cn.edu.tit.swipeback.SwipeBackActivity;
import cn.edu.tit.util.CommonUtil;

public class MyNewsDetailsActivity extends SwipeBackActivity {
	private static final String TAG = "MainActivity";

	private WebView mWebView;
	private TextView tv_title;
	private ImageView iv_goBack;
	private int screen_width;
	private int mod_width;
	private String webUrl ="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_newsdetails_optimus);
		initView();
		initData();
		// 得到传送过来的字符串
		webUrl = getIntent().getExtras().getString("url");
		tv_title.setText(webUrl);
		if (webUrl.equals("")) {
			TextView tv = new TextView(this);
			tv.setText(R.string.code_error);
			setContentView(tv);
			return;
		}
		MyTask task = new MyTask(mWebView, webUrl, mod_width);
		task.execute();
	}

	private void initData() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		screen_width = dm.widthPixels;
		mod_width = CommonUtil.px2dip(MyNewsDetailsActivity.this,
				screen_width * 9 / 10);
	}

	public void initView() {
		this.tv_title = (TextView) this.findViewById(R.id.tv_title);
		this.iv_goBack = (ImageView) this.findViewById(R.id.iv_goBack);
		this.iv_goBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MyNewsDetailsActivity.this.finish();

			}
		});

		mWebView = (WebView) findViewById(R.id.detail_webView);
		// mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

//		this.mWebView.setBackgroundColor(0);
//		mWebView.setBackgroundColor(R.color.transparent); //ok 不会闪黑屏
		this.mWebView.setBackgroundResource(R.color.detail_bgColor);
		// 添加js交互接口类，并起别名 imagelistner
		mWebView.addJavascriptInterface(new JavascriptInterface(
				getApplicationContext()), "imagelistner");
		mWebView.setWebChromeClient(new MyWebChromeClient());
		mWebView.setWebViewClient(new MyWebViewClient());

		WebSettings settings = mWebView.getSettings();
		settings.setJavaScriptEnabled(true);// 设置可以运行JS脚本
		
		settings.setSupportZoom(false);// 用于设置webview放大
		settings.setBuiltInZoomControls(false);
		settings.setLoadsImagesAutomatically(true);
		settings.setDefaultTextEncodingName("utf-8");
//		   settings.setUseWideViewPort(true); 
//	        settings.setLoadWithOverviewMode(true); 
	}
	
	

	// 注入js函数监听
	private void addImageClickListner() {
		// 这段js函数的功能就是，遍历所有的img几点，并添加onclick函数，在还是执行的时候调用本地接口传递url过去
		mWebView.loadUrl("javascript:(function(){"
				+ "var objs = document.getElementsByTagName(\"img\");"
				+ "var imgurl=''; " + "for(var i=0;i<objs.length;i++)  " + "{"
				+ "imgurl+=objs[i].src+',';"
				+ "    objs[i].onclick=function()  " + "    {  "
				+ "        window.imagelistner.openImage(imgurl);  "
				+ "    }  " + "}" + "})()");
	}

	// js通信接口
	public class JavascriptInterface {

		private Context context;

		public JavascriptInterface(Context context) {
			this.context = context;
		}

		public void openImage(String img) {

			//
			String[] imgs = img.split(",");
			ArrayList<String> imgsUrl = new ArrayList<String>();
			for (String s : imgs) {
				imgsUrl.add(s);
				Log.i("图片的URL>>>>>>>>>>>>>>>>>>>>>>>", s);
			}
			Intent intent = new Intent();
			intent.putStringArrayListExtra("infos", imgsUrl);
			intent.setClass(context, ImageShowActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
		}
	}

	// 监听
	private class MyWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			return super.shouldOverrideUrlLoading(view, url);
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			view.getSettings().setJavaScriptEnabled(true);
			super.onPageFinished(view, url);
			// html加载完成之后，添加监听图片的点击js函数
			addImageClickListner();
			// progressBar.setVisibility(View.GONE);
			mWebView.setVisibility(View.VISIBLE);
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			view.getSettings().setJavaScriptEnabled(true);
			super.onPageStarted(view, url, favicon);
		}

		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			// progressBar.setVisibility(View.GONE);
			super.onReceivedError(view, errorCode, description, failingUrl);
		}
	}

	private class MyWebChromeClient extends WebChromeClient {
		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			// TODO Auto-generated method stub
			if (newProgress != 100) {
				// progressBar.setProgress(newProgress);
			}
			super.onProgressChanged(view, newProgress);
		}
	}
}