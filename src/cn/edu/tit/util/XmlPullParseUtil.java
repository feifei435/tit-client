package cn.edu.tit.util;

import java.io.ByteArrayInputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import cn.edu.tit.bean.WeatherInfo;

public class XmlPullParseUtil {
	public static WeatherInfo parseWeatherInfo(String result) {
		WeatherInfo allWeather = null;
		try {
			// 获取XmlPullParser的实例
			XmlPullParser xmlPullParser = XmlPullParserFactory.newInstance()
					.newPullParser();
			// 设置输入流 xml文件
			xmlPullParser.setInput(new ByteArrayInputStream(result.getBytes()),
					"UTF-8");
			// 开始解析
			int eventType = xmlPullParser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				String nodeName = xmlPullParser.getName();
				switch (eventType) {

				case XmlPullParser.START_DOCUMENT: // 文档开始
					allWeather = new WeatherInfo();
					break;

				case XmlPullParser.START_TAG:// 开始节点
					if(nodeName.equals("resp")){
//						System.out.println("resp节点");
					}
					if (nodeName.equals("city")) {
						allWeather.setCity(xmlPullParser.nextText());
					} else if (nodeName.equals("updatetime")) {
						allWeather.setUpdatetime(xmlPullParser.nextText());
					} else if (nodeName.equals("wendu")) {
						allWeather.setWendu(xmlPullParser.nextText());
					} else if (nodeName.equals("fengli")) {
						allWeather.setFengli(xmlPullParser.nextText());
					} else if (nodeName.equals("fengxiang")) {
						allWeather.setFengxiang(xmlPullParser.nextText());
					} 
					break;

				case XmlPullParser.END_TAG:// 结束节点
					break;

	
				}
				eventType = xmlPullParser.next();
			}
		} catch (Exception e) {
			//解析出错会返回空
			e.printStackTrace();
		}
		return allWeather;
	}
}
