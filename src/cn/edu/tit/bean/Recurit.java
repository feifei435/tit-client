package cn.edu.tit.bean;

public class Recurit {
	public Recurit(){}
	public Recurit(String url, String title, String date) {
		super();
		this.url = url;
		this.title = title;
		this.date = date;
	}
	private String url ;
	private String title ; 
	private String date ;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "Recurit [url=" + url + ", title=" + title + ", date=" + date
				+ "]";
	} 
	
	

}
