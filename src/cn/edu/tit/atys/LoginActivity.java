package cn.edu.tit.atys;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import cn.edu.tit.R;
import cn.edu.tit.bean.LoginTask;
import cn.edu.tit.bean.MyTask;
import cn.edu.tit.config.Config.TAGS;
import cn.edu.tit.net.NetOperation;
import cn.edu.tit.util.MD5;
import cn.edu.tit.util.Request;
import cn.edu.tit.util.Toaster;

@SuppressLint("ShowToaster")
public class LoginActivity extends Activity implements OnClickListener {

	private AutoCompleteTextView et_account_name;
	private EditText et_account_password;
	private Button btn_login;
	private TextView tv_reg_new;
	private TextView tv_forget_pwd;
	private ImageView iv_showCode;
	public static String cookie;
	public static String viewState;
	private EditText et_yzm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		((TextView) this.findViewById(R.id.tv_title))
				.setText(R.string.login_title);
		this.findViewById(R.id.iv_goBack).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						LoginActivity.this.finish();
					}
				});

		this.et_account_name = (AutoCompleteTextView) this
				.findViewById(R.id.et_account_name);
		this.et_account_password = (EditText) this
				.findViewById(R.id.et_account_password);
		this.et_yzm = (EditText) this.findViewById(R.id.et_validate_code);
		this.tv_forget_pwd = (TextView) this.findViewById(R.id.tv_forget_pwd);
		this.tv_reg_new = (TextView) this.findViewById(R.id.tv_reg_new);
		this.btn_login = (Button) this.findViewById(R.id.btn_login);
		this.tv_forget_pwd.setOnClickListener(this);
		this.tv_reg_new.setOnClickListener(this);
		this.btn_login.setOnClickListener(this);
		
		
		iv_showCode = (ImageView) findViewById(R.id.iv_showCode);
		iv_showCode.setImageBitmap(BitmapFactory.decodeResource(getResources(),
				R.drawable.validate_img_default));
		
		this.iv_showCode.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.iv_showCode:
					String[] params = {
							"http://jwxt.tit.edu.cn/sys/ValidateCode.aspx", "p2"};
							//"http://59.49.33.178/sys/ValidateCode.aspx", "p2"};
					new ImageLoadTask(getApplicationContext()).execute(params);
					break;
				}
			}
		});
		
		try {
			String[] params = {
					"http://jwxt.tit.edu.cn/sys/ValidateCode.aspx", "p2"};
					//"http://59.49.33.178/sys/ValidateCode.aspx", "p2"};
			//第一步，加载验证码
			new ImageLoadTask(getApplicationContext()).execute(params);
		} catch (Exception e) {
			//e.printStackTrace();
			Toaster.makeText(getApplicationContext(), "获取验证码失败", 0).show();
        }

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_forget_pwd: // 忘记密码
			Toaster.makeText(getApplicationContext(), R.string.find_password_in_website, 0).show();
			break;
		case R.id.tv_reg_new:    // 注册
			Toaster.makeText(getApplicationContext(), R.string.create_account_in_website, 0).show();
			break;
		case R.id.btn_login:     // 登录
			if(!TextUtils.isEmpty(et_account_name.getText())&&!TextUtils.isEmpty(et_account_password.getText())){
				Toaster.makeText(getApplicationContext(), R.string.login_now, 0).show();
				
			}else{
				Toaster.makeText(getApplicationContext(), R.string.input_error, 0).show();
			}
			
			//学号
			String param_txt_asmcdefsddsd = this.et_account_name.getText().toString();
			//密码
			String param_txt_pewerwedsdfsdff = this.et_account_password.getText().toString();
			//验证码
			String param_txt_sdertfgsadscxcadsads = this.et_yzm.getText().toString();
			
			//加密参数一
			String param_dsdsdsdsdxcxdfgfg = (param_txt_asmcdefsddsd + MD5.encode(param_txt_pewerwedsdfsdff).substring(0, 30).toUpperCase() + "14101").substring(0, 30).toUpperCase();
			//加密参数二
			String param_fgfggfdgtyuuyyuuckjg = (MD5.encode(MD5.encode(param_txt_sdertfgsadscxcadsads.toUpperCase())).substring(0, 30).toUpperCase() + "14101").substring(0, 30).toUpperCase();
			
			byte[] param_typeNmaeByte = {(byte) 0xD1, (byte) 0xA7, (byte) 0xC9, (byte) 0xFA};
			//InputStream param_typeNmaeByteInputStream = new ByteArrayInputStream(param_typeNmaeByte);
			
			//第三步：设置post参数，开始登录
			String weburl = "http://jwxt.tit.edu.cn/_data/index_LOGIN.aspx";
			Map<String, String> headersmap = new HashMap<String, String>();
			headersmap.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.124 Safari/537.36");
			headersmap.put("Cookie", LoginActivity.cookie);
			headersmap.put("Host", "jwxt.tit.edu.cn");
			headersmap.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			headersmap.put("Referer", "http://jwxt.tit.edu.cn/default.aspx");
			headersmap.put("Accept-Language", "zh-CN,zh;q=0.8");
			
			Map<String, String> parammap = new HashMap<String, String>();
			parammap.put("__VIEWSTATE", LoginActivity.viewState);
			parammap.put("pcInfo", "Mozilla/5.0+(Windows+NT+6.1;+WOW64)+AppleWebKit/537.36+(KHTML,+like+Gecko)+Chrome/43.0.2357.124+Safari/537.36undefined5.0+(Windows+NT+6.1;+WOW64)+AppleWebKit/537.36+(KHTML,+like+Gecko)+Chrome/43.0.2357.124+Safari/537.36+SN:NULL");
			try {
				parammap.put("typeName", new String(param_typeNmaeByte, "ISO-8859-1"));
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			parammap.put("dsdsdsdsdxcxdfgfg", param_dsdsdsdsdxcxdfgfg);
			parammap.put("fgfggfdgtyuuyyuuckjg", param_fgfggfdgtyuuyyuuckjg);
			parammap.put("Sel_Type", "STU");
			parammap.put("txt_asmcdefsddsd", param_txt_asmcdefsddsd);
			parammap.put("txt_pewerwedsdfsdff", param_txt_pewerwedsdfsdff);
			parammap.put("txt_sdertfgsadscxcadsads", param_txt_sdertfgsadscxcadsads);
			parammap.put("sbtState", "");
			
			String result = "aaa";
			result = NetOperation.getContentFromNetByPost(weburl, "", headersmap, parammap);
			//Log.e("login result", result);
			
//			try {
//				Connection conn = Jsoup.connect("http://jwxt.tit.edu.cn/_data/index_LOGIN.aspx") 
//						  .data("__VIEWSTATE", LoginActivity.viewState)   // 请求参数
//						  .data("pcInfo", "Mozilla/5.0+(Windows+NT+6.1;+WOW64)+AppleWebKit/537.36+(KHTML,+like+Gecko)+Chrome/43.0.2357.124+Safari/537.36undefined5.0+(Windows+NT+6.1;+WOW64)+AppleWebKit/537.36+(KHTML,+like+Gecko)+Chrome/43.0.2357.124+Safari/537.36+SN:NULL")
//						  //.data("typeName", URLDecoder.decode("%D1%A7%C9%FA"))
//						  .data("typeName", new String(param_typeNmaeByte, "ISO-8859-1"))
//						  //.data("typeName", URLDecoder.decode("%D1%A7%C9%FA", "UTF-8"))
//						  .data("dsdsdsdsdxcxdfgfg", param_dsdsdsdsdxcxdfgfg)
//						  .data("fgfggfdgtyuuyyuuckjg", param_fgfggfdgtyuuyyuuckjg)
//						  .data("Sel_Type", "STU")
//						  .data("txt_asmcdefsddsd", param_txt_asmcdefsddsd)
//						  .data("txt_pewerwedsdfsdff", param_txt_pewerwedsdfsdff)
//						  .data("txt_sdertfgsadscxcadsads", param_txt_sdertfgsadscxcadsads)
//						  .data("sbtState", "")
//						  .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.124 Safari/537.36") // 设置 User-Agent 
//						  .cookie("ASP.NET_SessionId", LoginActivity.cookie.split("=")[1]) // 设置 cookie 
//						  .header("Host", "jwxt.tit.edu.cn")
//						  .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
//						  .header("Referer", "http://jwxt.tit.edu.cn/default.aspx")
//						  .header("Accept-Language", "zh-CN,zh;q=0.8")
//						  .timeout(5000);           // 设置连接超时时间
//				
//				//Log.e("urldecoder", ""+(char)209+(char)167+(char)201+(char)250);
//				//char[] typename = URLDecoder.decode("%D1%A7%C9%FA", "ISO-8859-1").toCharArray();
//				//for (int i = 0; i < typename.length; i++) {
//				//	Log.e("urldecoder", (int)typename[i]+"");
//				//}
//				//Log.e("urldecoder", Arrays.toString((typename)));
//				
//				conn.method(Method.POST);
//				conn.followRedirects(false);
//				Response response = conn.execute();
//				Document doc = response.parse();
//
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
			
			
			//启动登录成功界面
			Intent intent = new Intent(LoginActivity.this,
					AfterLoginActivity.class);
			//startActivity(intent);
			
			
			//第四步，显示登录后的功能：查看个人学籍资料
			
			break;
		default:
			break;
		}
	}
	
	public class ImageLoadTask extends AsyncTask<String, Void, Void> {
		private ImageEntity bean;
		// 初始化
		public ImageLoadTask(Context context) {
			bean = new ImageEntity();
		}

		@Override
		protected Void doInBackground(String... params) {
			String url = params[0];
			String p2 = params[1];
			Log.d("pulling validate img", url);
			Bitmap bitmap = BitmapFactory.decodeStream(Request.handlerDataWithHeader(url, LoginActivity.cookie));
			if(bitmap == null){
				Toaster.makeText(getApplicationContext(), "获取验证码失败", 0).show();
				return null;
			}
			bean.setImage(bitmap);
			if (LoginActivity.cookie != null) {
				Log.d("header", LoginActivity.cookie);
			}
			publishProgress(); // 通知去更新UI
			return null;
		}

		public void onProgressUpdate(Void... voids) {
			if (isCancelled())
				return;
			// 更新UI
			LoginActivity.this.iv_showCode.setImageBitmap(this.bean.getImage());
			
			//第二步:验证码加载完成，获得cookie，然后使用此cookie拉取一个登录页面
			Log.e("LoginActivity", "验证码加载完成开始拉取viewstat");
			LoginTask task = new LoginTask("http://jwxt.tit.edu.cn/_data/index_LOGIN.aspx");
			task.execute();
		}
	}
	public class ImageEntity {
		private Bitmap image;
		public Bitmap getImage() {
			return image;
		}
		public void setImage(Bitmap image) {
			this.image = image;
		}
	}
}

