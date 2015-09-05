package cn.edu.tit.atys;

import java.io.InputStream;
import java.util.List;

import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import cn.edu.tit.R;
import cn.edu.tit.ReadXmlByPullService;
import cn.edu.tit.bean.RomUtils;
import cn.edu.tit.swipeback.SwipeBackActivity;

public class CollegeIntroActivity extends SwipeBackActivity {
	private TextView rom_name, romer, rom_info;
	private TextView tv_title;
	private ImageView iv_goBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// 设置全屏代码，要注意此代码必须放在setContentView前面,否则报错
		// this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.antivity_college_intro);
		this.rom_name = (TextView) this.findViewById(R.id.rom_name);
		this.romer = (TextView) this.findViewById(R.id.romer);
		this.rom_info = (TextView) this.findViewById(R.id.rom_info);
		this.tv_title = (TextView) this.findViewById(R.id.tv_title);
		this.tv_title.setText(R.string.school_intro);
		this.iv_goBack = (ImageView) this.findViewById(R.id.iv_goBack);
		this.iv_goBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CollegeIntroActivity.this.finish();

			}
		});
		try {
			InputStream is = getAssets().open("college_intro.xml");
			ReadXmlByPullService xml = new ReadXmlByPullService();
			List<RomUtils> all = xml.ReadXmlByPull(is);
			// System.out.println(all.get(0).toString());
			// all.get(0).getAge();
			// all.get(0).getId();
			String enc1 = TextUtils.htmlEncode(all.get(0).getRomName());
			String for1 = getResources().getString(R.string.update_time);
			String resultsText1 = String.format(for1, enc1);
			CharSequence result1 = Html.fromHtml(resultsText1);
			rom_name.setText(result1);
			String enc2 = TextUtils.htmlEncode(all.get(0).getRomerName());
			String for2 = getResources().getString(R.string.title);
			String resultsText2 = String.format(for1, enc2);
			CharSequence result2 = Html.fromHtml(resultsText2);
			romer.setText(result2);

			rom_info.setText(all.get(0).getRomInfo());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
