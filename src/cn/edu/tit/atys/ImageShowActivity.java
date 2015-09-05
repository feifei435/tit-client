package cn.edu.tit.atys;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import cn.edu.tit.BaseActivity;
import cn.edu.tit.R;
import cn.edu.tit.R.id;
import cn.edu.tit.R.layout;
import cn.edu.tit.adapter.ImagePagerAdapter;
import cn.edu.tit.ui.stub.ImageShowViewPager;

public class ImageShowActivity extends BaseActivity {
	/** 图片展示 */
	private ImageShowViewPager image_pager;
	private TextView page_number;
	/** 图片下载按钮 */
	private ImageView download;
	/** 图片列表 */
	private ArrayList<String> imgsUrl;
	/** PagerAdapter */
	private ImagePagerAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.details_imageshow);
		initView();
		initData();
		initViewPager();
	}

	private void initData() {
		imgsUrl = getIntent().getStringArrayListExtra("infos");
		page_number.setText("1" + "/" + imgsUrl.size());
	}

	private void initView() {
		image_pager = (ImageShowViewPager) findViewById(R.id.image_pager);
		page_number = (TextView) findViewById(R.id.page_number);
		download = (ImageView) findViewById(R.id.download);
		image_pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				page_number.setText((arg0 + 1) + "/" + imgsUrl.size());
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void initViewPager() {
		if (imgsUrl != null && imgsUrl.size() != 0) {
			mAdapter = new ImagePagerAdapter(getApplicationContext(), imgsUrl);
			image_pager.setAdapter(mAdapter);
		}
	}
}
