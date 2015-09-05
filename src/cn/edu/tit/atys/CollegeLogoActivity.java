package cn.edu.tit.atys;

import java.io.InputStream;

import org.json.JSONObject;
import org.json.JSONTokener;

import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import cn.edu.tit.R;
import cn.edu.tit.swipeback.SwipeBackActivity;
import cn.edu.tit.util.StreamUtils;

public class CollegeLogoActivity extends SwipeBackActivity {
	private TextView tv_school_motto, tv_school_badge;
	private TextView tv_title;
	private ImageView iv_goBack;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.antivity_college_logo);
		this.tv_school_motto = (TextView) this.findViewById(R.id.tv_school_motto);
		this.tv_school_badge = (TextView) this.findViewById(R.id.tv_school_badge);
		this.tv_title = (TextView) this.findViewById(R.id.tv_title);
		this.tv_title.setText(R.string.school_logo_intro);
		this.iv_goBack = (ImageView) this.findViewById(R.id.iv_goBack);
		this.iv_goBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CollegeLogoActivity.this.finish();

			}
		});
		
		try {
			InputStream is = getAssets().open("college_logo_intro.json");
			String convertData =StreamUtils.inputStreamToString(is);
			JSONTokener jsonParser = new JSONTokener(convertData);  
			JSONObject data = (JSONObject) jsonParser.nextValue();
			String motto =data.getString("school_motto");
			String badge = data.getString("school_badge");
//			System.out.println("motto:"+motto+"\n"+"badge:"+badge);
			// System.out.println(all.get(0).toString());
			// all.get(0).getAge();
			// all.get(0).getId();
			String enc1 = TextUtils.htmlEncode(motto);
			String for1 = getResources().getString(R.string.update_time);
			String resultsText1 = String.format(for1, enc1);
			CharSequence result1 = Html.fromHtml(resultsText1);
			tv_school_motto.setText(result1);
			String enc2 = TextUtils.htmlEncode(badge);
			String for2 = getResources().getString(R.string.title);
			String resultsText2 = String.format(for1, enc2);
			CharSequence result2 = Html.fromHtml(resultsText2);
			tv_school_badge.setText(result2);
			
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
