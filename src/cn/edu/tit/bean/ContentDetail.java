package cn.edu.tit.bean;

import java.util.List;

public class ContentDetail {

	private String content; // ÄÚÈİ


	private String title;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

	private String time;
	private List<String> thumbail_urls ;

	public List<String> getThumbail_urls() {
		return thumbail_urls;
	}
	public void setThumbail_urls(List<String> thumbail_urls) {
		this.thumbail_urls = thumbail_urls;
	}
	@Override
	public String toString() {
		return "ContentDetail [content=" + content + ", title=" + title
				+ ", time=" + time + ", thumbail_urls=" + thumbail_urls + "]";
	}
	
}
