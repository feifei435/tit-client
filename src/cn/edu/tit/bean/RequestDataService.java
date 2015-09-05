package cn.edu.tit.bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 获取网页数据的操作
 * 
 * @author Administrator
 * 
 */
public class RequestDataService {

	private static final String TAG = "RequestDataService";
	private static String a42787PAGE;

	// 获取指定标签的信息,title，time，imageurl
	public static List<Map<String, Object>> NewsTagInfos(String html) {
		List<Map<String, Object>> datas = null;
		datas = new ArrayList<Map<String, Object>>();
		StringBuilder tinyWebUrl = new StringBuilder();
		try {
			// 使用jsoup解析
			// URL url = new URL(weburl);
			// Document doc = Jsoup.parse(url, 5000); // 获取到doc文档

			Document doc = Jsoup.parse(html);

			// try {
			//
			// File file = new File("/sdcard/修改到的源网页02" + ".html");
			// FileOutputStream fos = new FileOutputStream(file);
			// fos.write(doc.html().getBytes());
			// } catch (Exception e) {
			// // TODO: handle exception
			// }

			Elements links = doc.getElementsByClass("winstyle981422905_42787")
					.select("TR");
			for (Element link : links) {
				Map<String, Object> map = new HashMap<String, Object>();
				// 取得标题
				String title = link.getElementsByClass(
						"titlestyle981422905_42787").text();
				if (!title.equals("")) {
					map.put("title", title);
				}

				String href = link.select("td a").attr("href");
				if (!href.equals("")) {
					//判断href是否包含有http://www.tit.edu.cn/
					if(href.contains("http://www.tit.edu.cn")){
						map.put("href", href);
						System.out.println(href);
					}else{
					map.put("href", "http://www.tit.edu.cn"+href);
					}
				}

				String time = link.getElementsByClass(
						"timestyle981422905_42787").text();
				// Log.e(TAG, "time:" + time);
				if (!time.equals("")) {
					map.put("time", time);
					datas.add(map);
				}

			}
			return datas;

		} catch (Exception e) {
			// TODO: 一旦解析过程出现错误立即返回
			e.printStackTrace();
			return null;
		}

	}

	// 通过post操作获取到html
	public static String RequestPost(String weburl) {
		StringBuffer sb = new StringBuffer();
		URL url = null;
		HttpURLConnection conn = null;
		try {
			url = new URL(weburl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setReadTimeout(5000);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			// Content-Type application/x-www-form-urlencoded
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			// 发送请求参数
			OutputStreamWriter osw = new OutputStreamWriter(
					conn.getOutputStream(), "utf-8");
			// String params = "a42787CountNo=" + countno + "&&a42787CURURI="
			// + cururl + "&&a42787PAGE=" + page + "&&actiontype="
			// + actiontype;
			// a42787rowCount：新闻条目总数

			String params = "a42787CountNo=20"
					+ "&&a42787CURURI=4572F7F67878C89D2617A1A8E8998753"
					+ "&&a42787NOWPAGE=" + "&&a42787ORDER=desc"
					+ "&&a42787ORDERKEY=wbtop" + "&&a42787PAGE=60"
					+ "&&a42787rowCount=298" + "&&actiontype=NextPage";
			// osw.write("a42787CountNo=20&&a42787CURURI=4572F7F67878C89D2617A1A8E8998753a42787PAGE=60&&actiontype=NextPage");
			// System.out.println(params);
			osw.write(params);
			osw.flush();
			osw.close();
			// InputStream is =conn.getInputStream();
			// byte[] buffer = new byte[8192];
			// int len ;
			// while((len=is.read(buffer))!=-1){
			// System.out.println(new String(buffer));
			// sb.append(new String(buffer));
			// }

			// 一旦发送成功，用以下方法就可以得到服务器的回应：
			String sCurrentLine;
			InputStream l_urlStream;
			l_urlStream = conn.getInputStream();
			// 传说中的三层包装阿！
			BufferedReader l_reader = new BufferedReader(new InputStreamReader(
					l_urlStream, "utf-8"));
			while ((sCurrentLine = l_reader.readLine()) != null) {
				// sTotalString += sCurrentLine;
				sb.append(sCurrentLine);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return sb.toString();
	}

	public static String RequestPostInAndroid(String weburl, Context context,List<NameValuePair> params) {
		String result = null;
		SharedPreferences sp = context.getSharedPreferences("config",
				Context.MODE_PRIVATE);

		// 第一步，创建HttpPost对象
		HttpPost httpPost = new HttpPost(weburl);

//		// 设置HTTP POST请求参数必须用NameValuePair对象
//		List<NameValuePair> params = new ArrayList<NameValuePair>();
//		params.add(new BasicNameValuePair("a42787CountNo", "20"));
//		params.add(new BasicNameValuePair("a42787CURURI",
//				"4572F7F67878C89D2617A1A8E8998753"));
//
//		params.add(new BasicNameValuePair("a42787NOWPAGE", ""));
//		params.add(new BasicNameValuePair("a42787ORDER", "desc"));
//
//		params.add(new BasicNameValuePair("a42787ORDERKEY", "wbtop"));
//
//		params.add(new BasicNameValuePair("a42787PAGE", sp.getString("page",
//				"20")));
//
//		params.add(new BasicNameValuePair("a42787rowCount", "298"));
//
//		params.add(new BasicNameValuePair("actiontype", "NextPage"));

		HttpResponse httpResponse = null;
		try {
			// 设置httpPost请求参数
			httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			httpResponse = new DefaultHttpClient().execute(httpPost);
			// System.out.println(httpResponse.getStatusLine().getStatusCode());
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				// 第三步，使用getEntity方法活得返回结果
				result = EntityUtils.toString(httpResponse.getEntity());
//				System.out.println("result:" + result);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;

	}
}
