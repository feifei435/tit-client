package cn.edu.tit.config;

public class Config {

	public static final String CHARSET_UTF8 = "UTF-8";
	public static final String CHARSET_GBK = "GBK";
	public static final String URL_KECHENGKEBIAO="http://jwxt.tit.edu.cn/ZNPK/KBFB_LessonSel_rpt.aspx";
	public static final String URL_BANGJIKEBIAO="http://jwxt.tit.edu.cn/ZNPK/KBFB_ClassSel_rpt.aspx";
	public static final String URL_CLASS_SELECT="http://jwxt.tit.edu.cn/ZNPK/KBFB_ClassSel.aspx";
	public static final String URL_LESSON_SELECT="http://jwxt.tit.edu.cn/ZNPK/KBFB_LessonSel.aspx";
	public static final String URL_HOST="jwxt.tit.edu.cn";
	public static final String URL_RECURIT="http://jyw.tit.edu.cn/Bysjy/Zpxx/Xnzp/Index.shtml";
	
	public static final String KEY_KECHENGKEBIAO_KECHENG="Sel_KC";
	public static final String KEY_KECHENGKEBIAO_GESHI="gs";
	public static final String FORMAT = "^[a-z,A-Z].*$";
	public static final String DEFAULT_DB_PARENT_PATH = "cn.edu.tit";
	public static final String BASE_URL = "http://www.tit.edu.cn";
	
	public static final String XUEYUANYAOWENURL = "http://www.tit.edu.cn/list.jsp?urltype=tree.TreeTempUrl&wbtreeid=1029";
	public static final String XUEYUANTONGZHIURL = "http://www.tit.edu.cn/list.jsp?urltype=tree.TreeTempUrl&wbtreeid=1030";
	public static final String XIAOYUANWENHUAURL = "http://www.tit.edu.cn/list.jsp?urltype=tree.TreeTempUrl&wbtreeid=1154";
	public static final String CHUSHIDONGTAIURL = "http://www.tit.edu.cn/list.jsp?urltype=tree.TreeTempUrl&wbtreeid=1153";
	public static final String XIBUDONGTAIURL = "http://www.tit.edu.cn/list.jsp?urltype=tree.TreeTempUrl&wbtreeid=1118";
	public static final String XUEYUANGONGGAOURL = "http://www.tit.edu.cn/list.jsp?urltype=tree.TreeTempUrl&wbtreeid=1152";
	
	//太原工业学院各系链接
	public static final String XUEYUAN_1_JXX="http://jxx.tit.edu.cn/";//机械系
	public static final String XUEYUAN_2_DZGCX="http://dzx.tit.edu.cn/";//电子工程系
	public static final String XUEYUAN_3_ZDHX="http://www1.tit.edu.cn/3x/";//自动化系
	public static final String XUEYUAN_4_HXYHGX="http://www1.tit.edu.cn/4x/";//化学与化工系
	public static final String XUEYUAN_5_JSJGCX="http://jsjx.tit.edu.cn/";//计算机工程系
	public static final String XUEYUAN_6_HJYAQX="http://hjaq.tit.edu.cn/";//环境与安全工程系
	public static final String XUEYUAN_7_CLGCX="http://www1.tit.edu.cn/7x/";//材料工程系
	public static final String XUEYUAN_8_LXX="http://lxx.tit.edu.cn/";//理学系
	public static final String XUEYUAN_9_JJYGLX="http://jjyglx.tit.edu.cn/";//经济与管理系
	public static final String XUEYUAN_10_WYX="http://wyx.tit.edu.cn/";//外语系
	public static final String XUEYUAN_11_SJYYSX="http://sjysx.tit.edu.cn/portal.php";//设计艺术系
	public static final String XUEYUAN_12_SZBYFXX="http://fxx.tit.edu.cn/";//思政部与法学系系
	public static final String XUEYUAN_13_JXJYX="http://jxjyb.tit.edu.cn/";//继续教育部
	public static final String XUEYUAN_14_TYX="http://www1.tit.edu.cn/tybnew/";//体育系

	public static final class TAGS {
		public static final String NEWS_TAG = "news";
		public static final String BLOG_TAG = "blog";
		public static final String WIKI_TAG = "wiki";
	}

	public static final class DBContentType {
		public static final String Content_list = "list";
		public static final String Content_content = "content";
		public static final String Discuss = "discuss";
	}

	public static final class WebSourceType {
		public static final String Json = "json";
		public static final String Xml = "xml";
	}

	public static final class WebModuleType {
		public static final String xueyuanyaowen = "学院要闻";
		// public static final String xueyuandongtai = "学院动态"; //这个暂时无内容
		public static final String xueyuantongzhi = "学院通知";
		public static final String xueyuangonggao = "学院公告";
		public static final String xibudongtai = "系部动态";
		public static final String chushidongtai = "处室动态";
		public static final String xiaoyuanwenhua = "校园文化";
		// public static final String xiaoneibaozhi = "校内报纸"; //另开板块
		public static final int size = 6;

	}

	public static final class ColorPanel {
		public static final String[] COLORS = new String[] { "#FF6666",
				"#977200", "#01A3E0", "#FF7709", "#1BA602", "#EAD200" };

	}

	public static final String[] COLORS = new String[] { "#FF6666", "#003EE1",
			"#01D882", "#977200", "#01A3E0", "#BD00CC", "#F90064", "#FF7709",
			"#1BA602", "#016940", "#FF2D2D", "#01B4F8", "#662E00", "#75002F",
			"#EAD200", "#780082", "#D59F00", "#FF3366", "#2C007D", "#0F5A01",
			"#015474", "#DE00F0", "#018752", "#B75200", "#3750000" };
	public static final String PHONE_NUM = "15388517136";


}
