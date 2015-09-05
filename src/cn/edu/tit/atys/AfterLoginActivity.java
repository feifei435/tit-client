package cn.edu.tit.atys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import android.R.string;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import cn.edu.tit.Application;
import cn.edu.tit.R;
import cn.edu.tit.bean.GetContentTask;
import cn.edu.tit.swipeback.SwipeBackActivity;
import cn.edu.tit.util.Toaster;

public class AfterLoginActivity extends SwipeBackActivity {
	private static final String TAG = "AfterLoginActivity";
	private TextView tv_title;
	private ImageView iv_goBack;
	private ProgressDialog pd;
	private Button btn_submit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_after_login);
		initView();
		initData();
		// 得到传送过来的字符串
		tv_title.setText("登录成功");
	}

	private void initData() {
	}

	public void initView() {
		this.btn_submit = (Button) this.findViewById(R.id.btn_submit);
		this.tv_title = (TextView) this.findViewById(R.id.tv_title);
		this.iv_goBack = (ImageView) this.findViewById(R.id.iv_goBack);
		this.iv_goBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AfterLoginActivity.this.finish();
			}
		});
		
		this.btn_submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toaster.makeText(getApplicationContext(), LoginActivity.cookie, 0).show();
			}
		});
	}
}
