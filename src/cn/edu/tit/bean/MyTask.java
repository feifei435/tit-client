package cn.edu.tit.bean;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import cn.edu.tit.R;

public class MyTask extends AsyncTask<String, Integer, ContentDetail> {

	private static final String TAG = "MyTask";
	public boolean mUseCache;
	private WebView mWebView;
	private String webUrl;
	private int width; // 修改后的屏幕宽度

	public MyTask(WebView mWebView, String webUrl, int width) {
		this.mWebView = mWebView;
		this.width = width;
		this.webUrl = webUrl;
		mUseCache = true;
	}

	public MyTask(String webUrl) {
		this.webUrl = webUrl;
	}
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		mWebView.setVisibility(View.GONE);
	}

	@Override
	protected ContentDetail doInBackground(String... params) {
		return JsoupEntity.AccessDetails(webUrl);
	}

	@Override
	protected void onPostExecute(ContentDetail result) {
		super.onPostExecute(result);
		if (result != null) {
			String title = "<h2 align=center>" + result.getTitle() + "</h2> \n";
			String baseContent = result.getContent();
			if(baseContent==null){
				baseContent="找不到内容";
			} else{
				baseContent=baseContent.replaceAll("width=\"600\"", "width=\"" + width + "\"");
			}
					
			Log.e(TAG, width + "");
			String clickTime = "<p><font size=\"5\" color=\"orange\">"
					+ result.getTime() + "</font></p>";
			String data = title + clickTime + baseContent;
			mWebView.setVisibility(View.VISIBLE);
			mWebView.setBackgroundResource(R.color.detail_bgColor);
			mWebView.loadDataWithBaseURL(null, data, "text/html", "utf-8", null);

			Log.e(TAG, "onPostExecute:" + data);
			// try {
			//
			// File file = new File("/sdcard/content" + ".html");
			// FileOutputStream fos = new FileOutputStream(file);
			// fos.write(content.getBytes());
			// } catch (Exception e) {
			// // TODO: handle exception
			// }
		} else {
			mWebView.setVisibility(View.GONE);
		}
	}
}
