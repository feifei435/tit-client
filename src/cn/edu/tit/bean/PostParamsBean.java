package cn.edu.tit.bean;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class PostParamsBean {

	public static List<NameValuePair> getPostParams(HttpPostDao postDao) {
		List<NameValuePair> postparams = new ArrayList<NameValuePair>();
		postparams.add(new BasicNameValuePair("a42787CountNo", postDao
				.getCountNo()));
		postparams.add(new BasicNameValuePair("a42787CURURI", postDao
				.getCurUri()));
		postparams.add(new BasicNameValuePair("a42787NOWPAGE", postDao
				.getNowPage()));
		postparams
				.add(new BasicNameValuePair("a42787ORDER", postDao.getOrder()));

		postparams.add(new BasicNameValuePair("a42787ORDERKEY", postDao
				.getOrderkey()));

		postparams.add(new BasicNameValuePair("a42787PAGE", postDao.getPage()));

		postparams.add(new BasicNameValuePair("a42787rowCount", postDao
				.getRowCount()));

		postparams.add(new BasicNameValuePair("actiontype", postDao
				.getActionType()));

		return postparams;

	}

}
