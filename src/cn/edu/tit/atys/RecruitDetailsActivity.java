package cn.edu.tit.atys;

import cn.edu.tit.R;
import cn.edu.tit.bean.JsoupEntity;
import cn.edu.tit.config.Config;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebView;

public class RecruitDetailsActivity extends Activity {

	private WebView wv_recurit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recruit_details);
		this.wv_recurit = (WebView) this.findViewById(R.id.wv_recurit);
		new AsyncTask<Void, Void, String>() {

			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				JsoupEntity.getRecuritData(Config.URL_RECURIT);
				
				return null;
			}

			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				wv_recurit.loadDataWithBaseURL(null, result, "text/html",
						"utf-8", null);
			}

		}.execute();

	}

}
