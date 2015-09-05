package cn.edu.tit;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import cn.edu.tit.bean.Semester;
import cn.edu.tit.config.Config;

/**
 * 通过xml解析操作获取键值对
 * 
 * @author Administrator
 * 
 */
public class MyXmlParser {

	public static List<Semester> getSemester(InputStream input)
			throws Exception {
		List<Semester> list = null;
		Semester semester = null;
		// 得到解析器
		XmlPullParser xmlPull = XmlPullParserFactory.newInstance()
				.newPullParser();
		// 设置输入流和编码格式
		xmlPull.setInput(input, Config.CHARSET_UTF8);
		int eventType = xmlPull.getEventType(); // 得到事件码
		// 循环遍历每个元素直到文档的末尾
		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			// 文档的开始，只执行一遍
			case XmlPullParser.START_DOCUMENT:
				// 实例化list
				list = new ArrayList<Semester>();
				break;
			case XmlPullParser.START_TAG:
				if (xmlPull.getName().equals("term")) {
					semester = new Semester();

				}
				if (xmlPull.getName().equals("code")) {
					eventType = xmlPull.next();
					semester.setCode(Integer.parseInt(xmlPull.getText()));
					System.out.println("MyXmlParser Code:" + xmlPull.getText());
				} else if (xmlPull.getName().equals("name")) {
					eventType = xmlPull.next();
					semester.setSemester(xmlPull.getText());
					// System.out.println("MyXmlParser:"+xmlPull.getText());
				}
				break;
			case XmlPullParser.END_TAG:
				if (xmlPull.getName().equals("term")) {
					list.add(semester);
					semester = null;
				}
				break;
			}
			eventType = xmlPull.next();
		}
		return list;
	}
}
