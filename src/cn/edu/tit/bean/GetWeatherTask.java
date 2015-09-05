package cn.edu.tit.bean;

import java.net.URLEncoder;

import android.os.AsyncTask;
import android.os.Handler;
import android.text.TextUtils;
import cn.edu.tit.Application;
import cn.edu.tit.ui.stub.CurtainView;
import cn.edu.tit.util.ApiClient;
import cn.edu.tit.util.XmlPullParseUtil;

public class GetWeatherTask extends AsyncTask<Void, Void, Integer> {
	


	private static final String BASE_URL = "http://wthrcdn.etouch.cn/WeatherApi?city=%s";
	private static final int SUCCESS = 0;
	private static final int FAIL = -1;
	private City city;
	private Handler handler;

	private Application mApplication;

	// private

	public GetWeatherTask(Handler handler, City city) {
		this.city = city;
		this.handler = handler;
		mApplication = Application.getInstance();
	}

	@Override
	protected Integer doInBackground(Void... params) {
		try {
			String url = String.format(BASE_URL,
					URLEncoder.encode(city.getName(), "utf-8"));

			// 这里存在一个不足，每次都从网上下载天气信息,后期添加从内存，从文件读取文件

			String netResult = ApiClient.connServerForResult(url);
//			System.out.println("--->");
//			System.out.println(netResult);
			if (!TextUtils.isEmpty(netResult)) {
				WeatherInfo allWeather = XmlPullParseUtil
						.parseWeatherInfo(netResult);
				mApplication.setAllWeather(allWeather);
					return SUCCESS;
				}
			

		} catch (Exception e) {
			e.printStackTrace();

		}
		return FAIL;
	}

	@Override
	protected void onPostExecute(Integer result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if (result < 0) {
			handler.sendEmptyMessage(CurtainView.GET_WEATHER_FAIL);// 获取天气信息失败
			
		} else {
			handler.sendEmptyMessage(CurtainView.GET_WEATHER_SCUESS);// 获取天气信息成功，通知主线程更新
			System.out.println("获取天气信息成功，通知主线程更新");
			
		
		}
	}

}
