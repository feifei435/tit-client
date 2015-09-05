package cn.edu.tit.atys;


import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import cn.edu.tit.R;
import cn.edu.tit.config.Config;
import cn.edu.tit.util.Toaster;

public class PasternSettingActivity extends Activity implements OnClickListener{
	
	private TextView tv_title;
	private ImageView iv_goBack;
	
	
	private ListView lv;
	private ArrayList<HashMap<String, Object>> dataList;
	SimpleAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
//		ImageView  view = new ImageView(this);
//		LayoutParams params = new LayoutParams();
//		params.height = LayoutParams.FILL_PARENT;
//		params.width = LayoutParams.FILL_PARENT;
//		view.setLayoutParams(params);
//		view.setScaleType(ScaleType.MATRIX);
//		view.setImageResource(R.drawable.pastern);
//		setContentView(view);
		setContentView(R.layout.activity_pastern_setting);
		init();
		
	}

	private void init() {
		
		this.tv_title = (TextView) this.findViewById(R.id.tv_title);
		this.tv_title.setText(R.string.school_xi);
		this.iv_goBack = (ImageView) this.findViewById(R.id.iv_goBack);
		this.iv_goBack.setOnClickListener(this);
		
		lv=(ListView) findViewById(R.id.activity_pastern_setting_lv);
		
		dataList=new ArrayList<HashMap<String,Object>>();
		HashMap<String, Object> map;
		
		final String[] strs={"机械工程系","电子工程系","自动化系","化学与化工系","计算机工程系","环境与安全工程系","材料工程系","理学系","经济与管理系","外语系","设计艺术系","思政部与法学系","继续教育部","体育部"};
		
		for (int i = 0; i < strs.length; i++) {
			map=new HashMap<String, Object>();
			map.put("img", R.drawable.bg_load_default);
			map.put("text", strs[i]);
			
			dataList.add(map);
		}
		
		adapter=new SimpleAdapter(this, dataList,R.layout.simple_adapter_item, new String[]{"img","text"}, new int[]{R.id.simple_adapter_item_iv,R.id.simple_adapter_item_tv});
		lv.setAdapter(adapter);
		
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long id) {
				String url = "";
				switch (position) {
				case 0:
					url=Config.XUEYUAN_1_JXX;
					break;
				case 1:
					url=Config.XUEYUAN_2_DZGCX;
					break;
				case 2:
					url=Config.XUEYUAN_3_ZDHX;
					break;
				case 3:
					url=Config.XUEYUAN_4_HXYHGX;
					break;
				case 4:
					url=Config.XUEYUAN_5_JSJGCX;
					break;
				case 5:
					url=Config.XUEYUAN_6_HJYAQX;
					break;
				case 6:
					url=Config.XUEYUAN_7_CLGCX;
					break;
				case 7:
					url=Config.XUEYUAN_8_LXX;
					break;
				case 8:
					url=Config.XUEYUAN_9_JJYGLX;
					break;
				case 9:
					url=Config.XUEYUAN_10_WYX;
					break;
				case 10:
					url=Config.XUEYUAN_11_SJYYSX;
					break;
				case 11:
					url=Config.XUEYUAN_12_SZBYFXX;
					break;
				case 12:
					url=Config.XUEYUAN_13_JXJYX;
					break;
				case 13:
					url=Config.XUEYUAN_14_TYX;
					break;

				default:
					break;
				}
				Toaster.makeText(getApplicationContext(),strs[position], 0).show();
				Intent intent=new Intent(PasternSettingActivity.this,Activity_MainPage.class);
				intent.putExtra("intentFlag", url);
				startActivity(intent);
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_goBack:
			finish();
			break;

		default:
			break;
		}
	}

}
