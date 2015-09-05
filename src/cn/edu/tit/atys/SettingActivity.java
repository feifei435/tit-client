package cn.edu.tit.atys;

import java.io.File;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import cn.edu.tit.R;
import cn.edu.tit.util.Toaster;

public class SettingActivity extends Activity implements OnClickListener {
	/** Called when the activity is first created. */
	// private RelativeLayout rl_about_us;
	ProgressDialog pd;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_settings);
		// Intent intent = new
		// Intent(SettingActivity.this,SelectCtiyActivity.class);
		// startActivity(intent);
		pd = new ProgressDialog(this);
		pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		init();
	}

	public void init() {
		// this.rl_about_us = (RelativeLayout) this
		// .findViewById(R.id.account_policy_list);
		this.findViewById(R.id.rl_about_us).setOnClickListener(this);
		this.findViewById(R.id.rl_feedback).setOnClickListener(this);
		this.findViewById(R.id.rl_update).setOnClickListener(this);
		this.findViewById(R.id.rl_shareapp).setOnClickListener(this);
		this.findViewById(R.id.rl_function_introduce).setOnClickListener(this);
		this.findViewById(R.id.ll_not_login).setOnClickListener(this);
		
		
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
			Toaster.makeText(this,
					getResources().getText(R.string.press_again_exit), 0)
					.show();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.rl_about_us: // about_us
			// Toaster.makeText(getApplicationContext(), "step to AboutActivity",
			// 0)
			// .show();
			startActivity(new Intent(SettingActivity.this, AboutActivity.class));

			break;
		case R.id.rl_feedback: // rl_feedback
			startActivity(new Intent(SettingActivity.this,
					FeedBackActivity.class));

			break;
		case R.id.rl_update: // rl_update
			new AsyncTask<Void, Void, Void>() {

				protected void onPreExecute() {
					pd.show();
				};

				@Override
				protected Void doInBackground(Void... arg0) {
					// TODO Auto-generated method stub
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return null;
				}

				protected void onPostExecute(Void arg0) {
					pd.dismiss();
					Toaster.makeText(SettingActivity.this, R.string.no_update, 0)
							.show();
				};

			}.execute();

			break;
		case R.id.rl_shareapp: // rl_shareapp
//			LayoutInflater inflater = (LayoutInflater) this
//					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//			View view = inflater.inflate(R.layout.gridview_item, null);
//			ImageView imageView = (ImageView) view
//					.findViewById(R.id.imageView1);
//			TextView textView = (TextView) view
//					.findViewById(R.id.textView1);
//			AppInfo info =  PackageUtils.apkInfo(this.getPackageResourcePath(), this);
//			textView.setText(info.appName);
//			imageView.setBackgroundColor(Color.TRANSPARENT);
//			Bitmap iconBitmap = ImageUtils.drawableToBitmap(info.appIcon);
//			imageView.setImageBitmap(ImageUtils.getRoundedCornerBitmap(
//					ImageUtils.zoomBitmap(iconBitmap, 60, 60), 10));
//			imageView.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent=new Intent();
					intent.setAction(Intent.ACTION_SEND);
					intent.setType("*/*");
					intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(getPackageResourcePath())));
					startActivity(intent);
//				}
//			});
//			new AlertDialog.Builder(SettingActivity.this)
//					.setView(view).show();
			break;
			
		case R.id.rl_function_introduce: // rl_function_introduce
			startActivity(new Intent(SettingActivity.this,
					FunctionIntroduceActivity.class));

			break;
			
		case R.id.ll_not_login: // rl_function_introduce
			startActivity(new Intent(SettingActivity.this,
					LoginActivity.class));

			break;
			
		default:
			break;
		}

	}
}