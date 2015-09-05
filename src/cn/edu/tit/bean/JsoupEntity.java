package cn.edu.tit.bean;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 获取网页文件，解析所需要的内容
 * 
 * @author Administrator
 * 
 */
public class JsoupEntity {

	private static final String TAG = "JsoupEntity";

	private String shortContent;

	public String getShortContent() {
		return shortContent;
	}

	public void setShortContent(String shortContent) {
		this.shortContent = shortContent;
	}

	public String getThumbnail_url() {
		return thumbnail_url;
	}

	public void setThumbnail_url(String thumbnail_url) {
		this.thumbnail_url = thumbnail_url;
	}

	private String thumbnail_url;

	public static ContentDetail AccessDetails(String webUrl) {
		ContentDetail newsDetails = null;
		// 加载网页为document
		URL url;
		StringBuffer sb = new StringBuffer();
		try {
			newsDetails = new ContentDetail();
			url = new URL(webUrl);
			Document doc = Jsoup.parse(url, 5000); // 获取到doc文档

			// 获取标题：
			Elements titlestyle = doc
					.getElementsByClass("titlestyle981422905_42800");
			newsDetails.setTitle(titlestyle.html());
			// sb.append(element.html());
			// sb.append(doc.body());

			// 获取点击次数 timestyle981422905_42800
			Elements timestyle = doc
					.getElementsByClass("timestyle981422905_42800");
			newsDetails.setTime(timestyle.html());

			// 获取内容
			Elements contentstyle = doc
					.getElementsByClass("contentstyle981422905_42800");
			// 挑选element3含有图片的节点元素
			// Elements srcLinks=element3.select("img[src$=.jpg]");
			// for (Element link : srcLinks) {
			// // :剔除标签，只剩链接路径
			//
			// String imagesPath = link.attr("src");
			// element3.set(index, link.attr("src", imagesPath));
			//
			// }
			// System.out.println(element3.html());
			// Elements newEle = new Elements();
			// for(int i = 0 ; i<element3.size();i++){
			// //首先取得含有图片的元素，然后将其替换
			// Element current = element3.get(i);
			// if(current.text().contains("img[src$=.jpg]")){
			// element3.remove(current);
			// }
			// // Element ele = element3.get(i);
			// // if(element3.get(i).hasAttr("src")){
			// //
			// // }
			// // element3.get(i).select("img[src$=.jpg]");
			// // String imagesPath = element3.get(i).attr("src");
			// // ele = element3.get(i).attr("src", imagesPath);
			// // newEle.add(ele);
			//
			// }
			String str = contentstyle.html().replaceAll("src=\"",
					"src=\"http://www.tit.edu.cn/");  //20150609 此处要注意最后面要加一个/，防止图片src的../混到域名里
			
			// .replaceAll("width=\"600\"", "width=\"300\"");
			newsDetails.setContent(str);

			// Elements srcLinks = doc.select("img[src$=.jpg]");
			// List<String> thumbnails = null;
			// for (Element link : srcLinks) {
			// thumbnails = new ArrayList<String>();
			// // :剔除标签，只剩链接路径
			// String imagesPath = link.attr("src");
			// System.out.println("当前访问路径:" + imagesPath);
			// thumbnails.add(imagesPath);
			//
			// }
			// content.setThumbail_urls(thumbnails);

			sb.append(titlestyle.html() + "\n" + timestyle.html() + "\n"
					+ contentstyle.html());

			// Elements element = doc
			// .getElementsByClass("contentstyle981422905_42800");

			System.out.println(sb.toString());

		} catch (Exception e) {
			// TODO: handle exception
		}

		return newsDetails;
	}

	public static ContentDetail AccessShortDetails(String webUrl) {
		ContentDetail newsDetails = null;
		// 加载网页为document
		URL url;
		StringBuffer sb = new StringBuffer();
		try {
			newsDetails = new ContentDetail();
			url = new URL(webUrl);
			Document doc = Jsoup.parse(url, 5000); // 获取到doc文档

			// 获取标题：
			Elements titlestyle = doc
					.getElementsByClass("titlestyle981422905_42800");
			newsDetails.setTitle(titlestyle.text());
			// sb.append(element.html());
			// sb.append(doc.body());

			// 获取点击次数 timestyle981422905_42800
			Elements timestyle = doc
					.getElementsByClass("timestyle981422905_42800");
			newsDetails.setTime(timestyle.text());

			Elements contentstyle = doc
					.getElementsByClass("contentstyle981422905_42800");
			newsDetails.setContent(contentstyle.text());
			// 获取图片链接
			Elements srcLinks = contentstyle.select("img[src$=.jpg]");

			for (Element link : srcLinks) {
				// :剔除标签，只剩链接路径
				List<String> thumbail_urls = new ArrayList<String>();
				thumbail_urls.add(link.attr("src"));
				newsDetails.setThumbail_urls(thumbail_urls);
			}

			// 挑选element3含有图片的节点元素
			// Elements srcLinks=element3.select("img[src$=.jpg]");
			// for (Element link : srcLinks) {
			// // :剔除标签，只剩链接路径
			//
			// String imagesPath = link.attr("src");
			// element3.set(index, link.attr("src", imagesPath));
			//
			// }
			// System.out.println(element3.html());
			// Elements newEle = new Elements();
			// for(int i = 0 ; i<element3.size();i++){
			// //首先取得含有图片的元素，然后将其替换
			// Element current = element3.get(i);
			// if(current.text().contains("img[src$=.jpg]")){
			// element3.remove(current);
			// }
			// // Element ele = element3.get(i);
			// // if(element3.get(i).hasAttr("src")){
			// //
			// // }
			// // element3.get(i).select("img[src$=.jpg]");
			// // String imagesPath = element3.get(i).attr("src");
			// // ele = element3.get(i).attr("src", imagesPath);
			// // newEle.add(ele);
			//
			// }
			// String str =contentstyle.html().replaceAll("src=\"",
			// "src=\"http://www.tit.edu.cn");
			// // .replaceAll("width=\"600\"", "width=\"300\"");
			// newsDetails.setContent(str);

			// Elements srcLinks = doc.select("img[src$=.jpg]");
			// List<String> thumbnails = null;
			// for (Element link : srcLinks) {
			// thumbnails = new ArrayList<String>();
			// // :剔除标签，只剩链接路径
			// String imagesPath = link.attr("src");
			// System.out.println("当前访问路径:" + imagesPath);
			// thumbnails.add(imagesPath);
			//
			// }
			// content.setThumbail_urls(thumbnails);

			// sb.append(titlestyle.html() + "\n" + timestyle.html() + "\n"
			// + contentstyle.html());

			// Elements element = doc
			// .getElementsByClass("contentstyle981422905_42800");

			// System.out.println(sb.toString());

		} catch (Exception e) {
			// TODO: handle exception
		}

		return newsDetails;
	}

	// 获取新闻页的内容，使用到jsoup操作
	public static ArrayList<NewsListDao> ParserNewsJsp(String html) {
		ArrayList<NewsListDao> mList;
		StringBuffer sb = new StringBuffer();
		Document doc;
		try {
			mList = new ArrayList<NewsListDao>();
			doc = Jsoup.connect(html).get();
			// 其中的参数是从网站上选取出来的
			// 后期建议使用动态的输入
			Elements links = doc.getElementsByClass("f42787").select("a[href]");
			for (Element link : links) {
				NewsListDao dao = new NewsListDao();
				String webUrl = link.attr("abs:href");
				ContentDetail detail = AccessShortDetails(webUrl);
				dao.setUrl(webUrl);
				dao.setShortContent(detail.getContent().substring(0, 50));

				// if (detail.getThumbail_urls() == null) {
				// dao.setThumbnail_url("http://www.baidu.com/img/bdlogo.gif");
				// } else {
				// dao.setThumbnail_url(detail.getThumbail_urls().get(0));
				// // 取第一张图片
				// }
				dao.setThumbnail_url(null);
				dao.setTitle(link.text());
				mList.add(dao);
				// sb.append(link.attr("abs:href") + "\n");

			}
			// Logger.e(TAG, sb.toString());
			// System.out.println(sb.toString());
			return mList;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}

	}

	// 得到以list集合保存的map数据
	public static List<Map<String, Object>> RequestPostData(String html) {
		Document doc = Jsoup.parse(html, null);
		return null;

	}

	public static List<Recurit> getRecuritData(String html) {
		List<Recurit> list = new ArrayList<Recurit>();
		Document doc;
		try {
			doc = Jsoup.connect(html).get();
			Elements links = doc.select("ul");
			for (int i = 0; i < links.size(); i++) {
//				System.out.println("----> className:"+
//						links.get(i).className());
				if(links.get(i).className().equals("list m")){
//					System.out.println("----> respose :"+links.get(i).html());
					Elements children = links.get(i).children();
					Iterator iter = children.iterator();
//					System.out.println("size:");
					while(iter.hasNext()){
//						System.out.println("----> iter:"+ iter.next().toString());
						Element dao = (Element) iter.next();
						String url = dao.select("a[href]").attr("abs:href")
								.toString();
						String title = dao.select("a[title]").text().toString();
						String time = dao.getElementsByClass("r").text();
						Recurit recurit = new Recurit(url,title,time);
						System.out.println("recurit "+recurit.toString());
						list.add(recurit);
						
					}
				}
			}
//					getElementsByClass("list m");		//返回li节点集合
//			System.out.println("----> respose :"+doc.html());
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
		}

		return list;
	}

}
