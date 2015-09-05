package cn.edu.tit.bean;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class HttpPostDao {

	private String CountNo;
	private String curUri;
	private String nowPage;
	private String order;
	private String orderkey;
	private String page;
	private String rowCount;
	private String actionType;

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	// 设置HTTP POST请求参数必须用NameValuePair对象
	public String getCountNo() {
		if (CountNo == null) {
			return "20";
		}
		return CountNo;
	}

	public void setCountNo(String countNo) {
		CountNo = countNo;
	}

	public String getCurUri() {
		if (curUri == null) {
			return "4572F7F67878C89D2617A1A8E8998753";
		}
		return curUri;
	}

	public void setCurUri(String curUri) {
		this.curUri = curUri;
	}

	public String getNowPage() {
		if (CountNo == null) {
			return "";
		}
		return nowPage;
	}

	public void setNowPage(String nowPage) {
		this.nowPage = nowPage;
	}

	public String getOrder() {
		if (CountNo == null) {
			return "desc";
		}
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getOrderkey() {
		if (CountNo == null) {
			return "wbtop";
		}
		return orderkey;
	}

	public void setOrderkey(String orderkey) {
		this.orderkey = orderkey;
	}

	public String getPage() {
		if (CountNo == null) {
			return "0";
		}
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getRowCount() {
		if (CountNo == null) {
			return "298";
		}
		return rowCount;
	}

	public void setRowCount(String rowCount) {
		this.rowCount = rowCount;
	}

	

}
