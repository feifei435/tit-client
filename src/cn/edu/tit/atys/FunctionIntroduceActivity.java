package cn.edu.tit.atys;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import cn.edu.tit.R;
import cn.edu.tit.atys.MyNewsDetailsActivity.JavascriptInterface;

public class FunctionIntroduceActivity extends Activity {
	private WebView wv_function_introduce;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_function_introduce);

		this.findViewById(R.id.iv_goBack).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						FunctionIntroduceActivity.this.finish();
					}
				});
		((TextView) this.findViewById(R.id.tv_title)).setText(R.string.about);
		this.wv_function_introduce = (WebView) this
				.findViewById(R.id.wv_function_introduce);
		this.wv_function_introduce
				.setBackgroundResource(R.color.detail_bgColor);
		// 添加js交互接口类，并起别名 imagelistner
		// wv_function_introduce.addJavascriptInterface(new JavascriptInterface(
		// getApplicationContext()), "imagelistner");
		// wv_function_introduce.setWebChromeClient(new MyWebChromeClient());
		// wv_function_introduce.setWebViewClient(new MyWebViewClient());

		WebSettings settings = wv_function_introduce.getSettings();
		settings.setJavaScriptEnabled(true);// 设置可以运行JS脚本

		settings.setSupportZoom(false);// 用于设置webview放大
		settings.setBuiltInZoomControls(false);
		settings.setLoadsImagesAutomatically(true);
		settings.setDefaultTextEncodingName("utf-8");
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
				try {
					InputStream input = getAssets().open(
							"functionIntroduce.html");
					InputStreamReader isr = new InputStreamReader(input);
					BufferedReader br = new BufferedReader(isr);
					String data = null;
					StringBuffer buffer = new StringBuffer();
					while (((data = br.readLine()) != null)) {
						buffer.append(data);
					}
					wv_function_introduce.loadDataWithBaseURL(null,
							buffer.toString(), "text/html", "utf-8", null);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return null;
			}

			protected void onPostExecute(Void result) {

			};

		}.execute();

	}

}
