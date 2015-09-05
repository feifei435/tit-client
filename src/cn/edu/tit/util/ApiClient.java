package cn.edu.tit.util;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.edu.tit.Application;

public class ApiClient {

	public static String  connServerForResult(String url) {
		if (NetUtil.getNetworkState(Application.getInstance()) == NetUtil.NETWORN_NONE) {// 判断网络是否可以使用
			return null;
		}
		// HttpGet httpRequest = new HttpGet(url);
//		String result = null;
		StringBuffer sb = new StringBuffer();
		try {
			URL myurl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();
			conn.setReadTimeout(5000);
			conn.setRequestMethod("GET");
			InputStreamReader isr = new InputStreamReader(conn.getInputStream(),"utf-8");
			int len ;
			char buf[] = new char[8192];
			while((len =isr.read(buf))!=-1){
				sb.append(new String(buf));
			}
			return sb.toString() ;
		
			// // httpClient对象
			// HttpClient client = new DefaultHttpClient();
			// // 获取HttpResponse对象
			// HttpResponse response = client.execute(httpRequest);
			// if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
			// { // 成功获取到信息
			// // 得到返回的数据
			// // strResult = EntityUtils.toString(response.getEntity());
			// strResult = EntityUtils.toString(response.getEntity(),"utf-8");
			// }
			//
		} catch (Exception e) {
			e.printStackTrace();

		}
		return sb.toString();
	}

}
