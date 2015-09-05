package cn.edu.tit;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.content.Context;
import cn.edu.tit.R;
import cn.edu.tit.bean.LoanFree;
import cn.edu.tit.util.StreamUtils;

public class DataOperation {

	
	public static List<LoanFree> getloadFreeDataFromJson(Context mContext){
		List<LoanFree> mList = new ArrayList<LoanFree>();
		try {
			InputStream is = mContext.getResources().openRawResource(R.raw.loanfree);
			String result =StreamUtils.inputStreamToString(is);
			JSONTokener parser = new JSONTokener(result);
			try {
				JSONObject head = (JSONObject) parser.nextValue();
				JSONArray array = head.getJSONArray("loanfree");
				for(int i = 0 ; i < array.length();i++){
					JSONObject obj =(JSONObject) array.get(i);
					String title =obj.get("title").toString();
					String publisher =obj.get("public").toString();
					String time =obj.get("time").toString();
					String url =obj.get("url").toString();
					LoanFree loanfree = new LoanFree();
					loanfree.setTitle(title);
					loanfree.setPubTime(time);
					loanfree.setPublisher(publisher);
					loanfree.setWeburl(url);
					mList.add(loanfree);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return mList;
	}

}
