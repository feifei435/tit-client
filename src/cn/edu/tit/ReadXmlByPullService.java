package cn.edu.tit;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;
import cn.edu.tit.bean.RomUtils;

/**
 * 使用pull解析xml 2010-04-26
 * 
 * @author leequer
 * 
 */
public class ReadXmlByPullService {

	public static List<RomUtils> ReadXmlByPull(InputStream inputStream)
			throws Exception {
		List<RomUtils> rosteam = null;
		/**
		 * android给我们提供了xml 用来得到xmlpull解析器
		 */
		XmlPullParser xmlpull = Xml.newPullParser();
		/**
		 * 将输入流传入 设定编码方式
		 * 
		 */
		xmlpull.setInput(inputStream, "utf-8");
		/**
		 * pull读到xml后 返回数字 读取到xml的声明返回数字0 START_DOCUMENT; 读取到xml的结束返回数字1
		 * END_DOCUMENT ; 读取到xml的开始标签返回数字2 START_TAG 读取到xml的结束标签返回数字3 END_TAG
		 * 读取到xml的文本返回数字4 TEXT
		 */
		int eventCode = xmlpull.getEventType();
		/**
		 * 只要这个事件返回的不是1 我们就一直读取xml文件
		 */
		RomUtils romUtils = null;
		while (eventCode != XmlPullParser.END_DOCUMENT) {

			switch (eventCode) {
			case XmlPullParser.START_DOCUMENT: {// 开始文档 new数组
				rosteam = new ArrayList<RomUtils>();
				break;
			}
			case XmlPullParser.START_TAG: {
				if ("rom".equals(xmlpull.getName())) {
					romUtils = new RomUtils();
					// person.setId(xmlpull.getAttributeValue(0));
				} else if (romUtils != null) {
					if (("romname".equals(xmlpull.getName()))) {
						/**
						 * 如果当前元素的下一个元素是文本节点 就可以直接用nextText()这个方法来得到文本节点的内容
						 */
						romUtils.setRomName(xmlpull.nextText());
					} else if ("rominfo".equals(xmlpull.getName())) {
						/**
						 * 如果当前元素的下一个元素是文本节点 就可以直接用nextText()这个方法来得到文本节点的内容
						 */
						romUtils.setRomInfo(xmlpull.nextText());
					} else if ("romername".equals(xmlpull.getName())) {
						/**
						 * 如果当前元素的下一个元素是文本节点 就可以直接用nextText()这个方法来得到文本节点的内容
						 */
						romUtils.setRomerName(xmlpull.nextText());
					}
				}
				break;
			}

			case XmlPullParser.END_TAG: {
				if ("rom".equals(xmlpull.getName()) && romUtils != null) {
					rosteam.add(romUtils);
					romUtils = null;
				}
				break;
			}
			}

			eventCode = xmlpull.next();// 没有结束xml文件就推到下个进行解析

		}

		return rosteam;
	}

}
