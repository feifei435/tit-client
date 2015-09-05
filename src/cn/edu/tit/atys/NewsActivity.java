package cn.edu.tit.atys;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import cn.edu.tit.R;
import cn.edu.tit.adapter.NewsCategoryAdapter;
import cn.edu.tit.bean.NewsCategory;
import cn.edu.tit.config.Config;
import cn.edu.tit.util.NetUtil;
import cn.edu.tit.util.Toaster;

public class NewsActivity extends Activity {
	private static final int XUEYUANYAOWEN = 0x0;
	private static final int XUEYUANTONGZHI = 0x1;
	private static final int XUEYUANGONGGAO = 0x2;
	private static final int XIBUDONGTAI = 0x3;
	private static final int CHUSHIDONGTAI = 0x4;
	private static final int XIAOYUANWENHUA = 0x5;

	private GridView gridview;
	// MyGridAdapter adapter;
	NewsCategoryAdapter adapter;
	List<NewsCategory> menus;
	

	private static String[] names = { Config.WebModuleType.xueyuanyaowen,
		Config.WebModuleType.xueyuantongzhi,
		Config.WebModuleType.xueyuangonggao,
		Config.WebModuleType.xibudongtai,
		Config.WebModuleType.chushidongtai,
		Config.WebModuleType.xiaoyuanwenhua

	};
	private static int[] icons = { R.drawable.icon_shortcut_01,
			R.drawable.icon_shortcut_02, R.drawable.icon_shortcut_03,
			R.drawable.icon_shortcut_04, R.drawable.icon_shortcut_05,
			R.drawable.icon_shortcut_06 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_news_category);
		this.gridview = (GridView) findViewById(R.id.gv_menu);
	
		// adapter = new MyGridAdapter(this);
		menus = new ArrayList<NewsCategory>();

		for (int i = 0; i < names.length; i++) {
			NewsCategory item = new NewsCategory(icons[i], names[i], "");
			menus.add(item);
		}
		if (menus.size() != 0) {
			// 计算margin
			int margin = (int) (getResources().getDisplayMetrics().density * 14 * 13 / 9);
			adapter = new NewsCategoryAdapter(this, menus, margin);
			this.gridview.setAdapter(adapter);
			this.gridview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {

					try {
						// 网络不可用
						if (NetUtil.getNetworkState(getApplicationContext())<0) {
							Toaster.makeText(
									getApplicationContext(),
									getString(R.string.network_error) + names[position]
											, 0).show();
							return;
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// Toaster.makeText(getApplicationContext(),
					// "position:" + position, 0).show();
					Intent intent = new Intent();
					intent.setClass(NewsActivity.this, MyNewsListActivity.class);
					switch (position) {

					case XUEYUANYAOWEN:

						intent.putExtra("url", Config.XUEYUANYAOWENURL);
						intent.putExtra("name", names[position]);
						startActivity(intent);
						break;
					case XUEYUANTONGZHI:

						intent.putExtra("url", Config.XUEYUANTONGZHIURL);
						intent.putExtra("name", names[position]);
						startActivity(intent);
						break;
					case XUEYUANGONGGAO:
						intent.putExtra("url", Config.XUEYUANGONGGAOURL);
						intent.putExtra("name", names[position]);
						startActivity(intent);
						break;
					case XIBUDONGTAI:
						intent.putExtra("url", Config.XIBUDONGTAIURL);
						intent.putExtra("name", names[position]);
						startActivity(intent);
						break;
					case CHUSHIDONGTAI:
						intent.putExtra("url", Config.CHUSHIDONGTAIURL);
						intent.putExtra("name", names[position]);
						startActivity(intent);
						break;
					case XIAOYUANWENHUA:
						intent.putExtra("url", Config.XIAOYUANWENHUAURL);
						intent.putExtra("name", names[position]);
						startActivity(intent);
						break;

					}

				}
			});

		} else {
			TextView tv = new TextView(this);
			tv.setText(R.string.system_error);
			setContentView(tv);
		}
	}
	
	/**
	 * 连续按两次返回键就退出
	 */
	private long firstTime;

	@Override
	public void onBackPressed() {
		if (System.currentTimeMillis() - firstTime < 3000) {
			finish();
		} else {
			firstTime = System.currentTimeMillis();
			Toaster.makeText(this,getResources().getText(R.string.press_again_exit), 0).show();
		}
	}
}
