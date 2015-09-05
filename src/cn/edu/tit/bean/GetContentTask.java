package cn.edu.tit.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.webkit.WebView;
import cn.edu.tit.net.NetOperation;

public class GetContentTask extends AsyncTask<String, Void, String> {
	Map<String, String> paramsmap;
	Map<String, String> headersmap;
	ProgressDialog pd;
	WebView webView;
	String weburl;
	String cookies;
	String method;

	public GetContentTask(String method, ProgressDialog pd, WebView webView, String weburl,
			String cookies, Map<String, String> paransmap,
			Map<String, String> headersmap) {
		this.pd = pd;
		this.webView = webView;
		this.paramsmap = paransmap;
		this.headersmap = headersmap;
		this.weburl = weburl;
		this.cookies = cookies;
		this.method = method;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		pd.show();
	}

	@Override
	protected String doInBackground(String... params) {
		List<NameValuePair> postparams = new ArrayList<NameValuePair>();
		postparams = new ArrayList<NameValuePair>();
		if (paramsmap != null) {
			Set<String> keys = paramsmap.keySet();
			for (Iterator<String> i = keys.iterator(); i.hasNext();) {
				String key = (String) i.next();
				postparams.add(new BasicNameValuePair(key, paramsmap.get(key)));
			}
		}
		
		
//		Map<String, String> headers = new HashMap<String, String>();
//		headers.put("Referer",
//				"http://jwxt.tit.edu.cn/ZNPK/KBFB_LessonSel.aspx");
//		// headers.put("Accept-Language", "zh-Hans-CN,zh-Hans;q=0.5");
//		// headers.put("Content-Type", "application/x-www-form-urlencoded");
//		// headers.put("Accept-Encoding", "gzip, deflate");
//		headers.put("Host", "jwxt.tit.edu.cn");

		if (this.method.equals("post")) {
			return NetOperation.getContentFromNetByPost(weburl, cookies, headersmap,paramsmap);
		}
		else if(this.method.equals("get")) {
			return NetOperation.getContentFromNetByGet(weburl, cookies, headersmap,paramsmap);
		}
		else {
			return null;
		}
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		// Log.e("Access result:",result);
		// webView.loadData(result, "text/html",null);.
		webView.loadDataWithBaseURL("http://jwxt.tit.edu.cn/ZNPK/", result, "text/html", "utf-8", null);
		pd.dismiss();
		super.onPostExecute(result);
	}

}
