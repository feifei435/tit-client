package cn.edu.tit.bean;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import cn.edu.tit.R;
import cn.edu.tit.atys.LoginActivity;
import cn.edu.tit.util.Toaster;


public class LoginTask extends AsyncTask<String, Integer, String> {
	
	private static final String TAG = "LoginTask";
	//public boolean mUseCache;
	//private WebView mWebView;
	private String webUrl;
	//private int width; // 修改后的屏幕宽度

//	public LoginTask(WebView mWebView, String webUrl, int width) {
//		//this.mWebView = mWebView;
//		//this.width = width;
//		this.webUrl = webUrl;
//		//mUseCache = true;
//	}

	public LoginTask(String webUrl) {
		this.webUrl = webUrl;
	}
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		//mWebView.setVisibility(View.GONE);
	}

	@Override
	protected String doInBackground(String... params) {
		try {
			//StringBuffer sb = new StringBuffer();
			//URL url = new URL(webUrl);
			//Document doc = Jsoup.parse(url, 5000); // 获取到doc文档
			
			Connection conn = Jsoup.connect(webUrl) 
					  //.data("query", "Java")   // 请求参数
					  .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.124 Safari/537.36") // 设置 User-Agent 
					  .cookie("ASP.NET_SessionId", LoginActivity.cookie.split("=")[1]) // 设置 cookie 
					  .header("Host", "jwxt.tit.edu.cn")
					  .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
					  .header("Referer", "http://jwxt.tit.edu.cn/default.aspx")
					  .header("Accept-Language", "zh-CN,zh;q=0.8")
					  .timeout(5000);           // 设置连接超时时间
					  //.get();
			conn.method(Method.GET);
			conn.followRedirects(false);
			Response response = conn.execute();
			
			Document doc = response.parse();

			Element inputTag = doc.select("input").first();
			String viewState = inputTag.attr("value");
			
			return viewState;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		
		if (result != null) {
			Log.e("viewState", result);
			Log.e("viewState_end", result.substring(result.length() - 100, result.length()));
			Log.e("ASP.NET_SessionId", LoginActivity.cookie);
			LoginActivity.viewState = result;
		} else {
			//mWebView.setVisibility(View.GONE);
		}
	}
}
