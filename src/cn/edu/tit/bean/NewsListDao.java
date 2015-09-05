package cn.edu.tit.bean;

public class NewsListDao extends BaseListDao {
	
	
	private String Title;
	private String shortContent;
	public String getShortContent() {
		return shortContent;
	}

	public void setShortContent(String shortContent) {
		this.shortContent = shortContent;
	}

	private String thumbnail_url;

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	

	public String getThumbnail_url() {
		return thumbnail_url;
	}

	public void setThumbnail_url(String thumbnail_url) {
		this.thumbnail_url = thumbnail_url;
	}

	@Override
	public String toString() {
		return "NewsListDao [Title=" + Title + ", shortContent=" + shortContent
				+ ", thumbnail_url=" + thumbnail_url + "]";
	}



}
