package cn.edu.tit.bean;

public class LoanFree {

	private String title;
	private String pubTime;
	private String publisher ;
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	private String weburl ;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPubTime() {
		return pubTime;
	}
	public void setPubTime(String pubTime) {
		this.pubTime = pubTime;
	}
	public String getWeburl() {
		return weburl;
	}
	public void setWeburl(String weburl) {
		this.weburl = weburl;
	}
	@Override
	public String toString() {
		return "LoanFree [title=" + title + ", pubTime=" + pubTime
				+ ", publisher=" + publisher + ", weburl=" + weburl + "]";
	}
	
	

}
