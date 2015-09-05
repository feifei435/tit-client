package cn.edu.tit.test;

import cn.edu.tit.bean.JsoupEntity;
import cn.edu.tit.config.Config;
import android.test.AndroidTestCase;

public class MyTest extends AndroidTestCase{
	
	public void testGetRecuritData() throws Exception{
		
		JsoupEntity.getRecuritData(Config.URL_RECURIT);
		
	}

}
