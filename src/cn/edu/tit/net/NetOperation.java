package cn.edu.tit.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.text.TextUtils;
import android.util.Log;
import cn.edu.tit.config.Config;

public class NetOperation {

	private static final String TAG = "NetOperation";

	/**
	 * 
	 * @param weburl
	 * @param cookies
	 *            采用，作为分割,k,v
	 * @param header
	 *            采用，作为分割
	 * @param params
	 *            采用，作为分割
	 * @return
	 */
	public static String getContentFromNetByPost(String weburl, String cookies,
			Map<String, String> headers, Map<String, String> paramsmap) {
		String result;
		try {
			// 创建post对象
			HttpPost httpPost = new HttpPost(weburl);
			// 设置headers
			if (headers != null) {
				Set<String> keys = headers.keySet();
				for (Iterator<String> i = keys.iterator(); i.hasNext();) {
					String key = (String) i.next();
					httpPost.addHeader(key, headers.get(key));
				}
			}
			// 设置cookies
			if (!TextUtils.isEmpty(cookies)) {
				httpPost.setHeader("Cookie", cookies);
			}
			HttpResponse httpResponse = null;
			// 设置httpPost请求参数
			List<NameValuePair> postparams = new ArrayList<NameValuePair>();
			postparams = new ArrayList<NameValuePair>();
			if (paramsmap != null) {
				Set<String> keys = paramsmap.keySet();
				for (Iterator<String> i = keys.iterator(); i.hasNext();) {
					String key = (String) i.next();
					postparams.add(new BasicNameValuePair(key, paramsmap.get(key)));
				}
			}
			Log.e("NetOperations", "begin setEntity");
			httpPost.setEntity(new UrlEncodedFormEntity(postparams, Config.CHARSET_GBK));
			Log.e("NetOperations", "begin execute");
			httpResponse = new DefaultHttpClient().execute(httpPost);
			//System.out.println("ResponseCode:"
			//		+ httpResponse.getStatusLine().getStatusCode());
			Log.e("NetOperations", "end execute");
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				// 第三步，使用getEntity方法活得返回结果
				Log.e(TAG, "成功返回");

				result = EntityUtils.toString(httpResponse.getEntity());
				//System.out.println("result:" + result);
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	public static String getContentFromNetByGet(String weburl, String cookies,
			Map<String, String> headers, Map<String, String> paramsmap) {
		
		String result;
		//先将参数放入List，再对参数进行URL编码 
		List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();
		
		// 设置httpPost请求参数
		if (paramsmap != null) {
			Set<String> keys = paramsmap.keySet();
			for (Iterator<String> i = keys.iterator(); i.hasNext();) {
				String key = (String) i.next();
				params.add(new BasicNameValuePair(key, paramsmap.get(key)));
			}
		}
		
		//对参数编码 
		String param = URLEncodedUtils.format(params, "UTF-8"); 
		//将URL与参数拼接 
		HttpGet getMethod = new HttpGet(weburl + "?" + param); 

		// 设置headers
		if (headers != null) {
			Set<String> keys = headers.keySet();
			for (Iterator<String> i = keys.iterator(); i.hasNext();) {
				String key = (String) i.next();
				getMethod.addHeader(key, headers.get(key));
			}
		}
		
		HttpClient httpClient = new DefaultHttpClient(); 
		
		try {
		    HttpResponse response = httpClient.execute(getMethod); //发起GET请求 
		    //Log.e(TAG, "resCode = " + response.getStatusLine().getStatusCode()); //获取响应码 
		    //Log.e(TAG, "result = " + EntityUtils.toString(response.getEntity(), "utf-8"));//获取服务器响应内容
		    
		    BufferedReader in = null;
		    in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));  
            StringBuffer sb = new StringBuffer("");  
            String line = "";
            String NL = System.getProperty("line.separator");  
            while ((line = in.readLine()) != null) {  
                sb.append(line + NL);  
            }  
            in.close();  
            result = sb.toString(); 
            return result;
		    
		} catch (ClientProtocolException e) { 
		    e.printStackTrace(); 
		    return null;
		} catch (IOException e) { 
		    e.printStackTrace();
		    return null;
		}
	}
}
