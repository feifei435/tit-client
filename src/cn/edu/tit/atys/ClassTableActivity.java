package cn.edu.tit.atys;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import cn.edu.tit.MySqLiteParser;
import cn.edu.tit.MyXmlParser;
import cn.edu.tit.R;
import cn.edu.tit.adapter.SpinnerAdapter;
import cn.edu.tit.bean.Semester;
import cn.edu.tit.config.Config;
import cn.edu.tit.swipeback.SwipeBackActivity;
import cn.edu.tit.util.Toaster;
import cn.edu.tit.util.Request;

public class ClassTableActivity extends SwipeBackActivity {
	protected static final int INIT_MSG_SEMESTER_READY = 0x12;
	private Spinner sp_semester;
	private EditText et_class;
	private Button btn_submit;
	private TextView tv_title;
	private ImageView iv_goBack;
	private ImageView iv_showCode;
	private List<Semester> smList ;
	private String postSemester ;
	private String postClass ;
	private EditText et_yzm;
	public static String cookie;
	SpinnerAdapter spAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_class_table);
		init();
	}

	private void init() {
		//实例化控件
		this.sp_semester = (Spinner) this.findViewById(R.id.sp_semester);
		this.sp_semester.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent,
					View view, int position, long id) {
				System.out.println(parent.getItemAtPosition(position));
				Toaster.makeText(getApplicationContext(),
						parent.getItemAtPosition(position) + "", 0).show();
				sp_semester.setSelection(position);
				postSemester = ((Semester) parent
						.getItemAtPosition(position)).getCode() + "";
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});
		spAdapter = new SpinnerAdapter(this);
		this.sp_semester.setAdapter(spAdapter);
		this.tv_title = (TextView) this.findViewById(R.id.tv_title);
		this.tv_title.setText(R.string.easy_query);
		this.iv_goBack = (ImageView) this.findViewById(R.id.iv_goBack);
		this.iv_goBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ClassTableActivity.this.finish();

			}
		});
		this.et_class = (EditText) this.findViewById(R.id.et_class);
		this.btn_submit = (Button) this.findViewById(R.id.btn_submit);
		this.et_yzm = (EditText) this.findViewById(R.id.et_validate_code);
		this.btn_submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				postClass =et_class.getText().toString();
				if(TextUtils.isEmpty(postClass)|TextUtils.isEmpty(postSemester)){
					Toaster.makeText(getApplicationContext(), R.string.select_semester_and_class, 0).show();
					return ;
				}	
				
				try {
					InputStream in = getApplicationContext().getAssets().open("titn.db");
					postClass = MySqLiteParser.findObj(getApplicationContext(), in, "class", 
							"clsname", postClass, "code");
				} catch (IOException e) {
					e.printStackTrace();
				}
		
				if(TextUtils.isEmpty(postClass)){
					Toaster.makeText(getApplicationContext(), R.string.can_not_find_result, 0).show();
					return ;
				}
				//设置post参数
				Map<String, String> params = new HashMap<String, String>();
				params.put("Sel_XNXQ",postSemester);
				params.put("txtxzbj","");		    //这一个参数可以不发送
				params.put("Sel_XZBJ",postClass);
				params.put("type", "1");
				params.put("txt_yzm", et_yzm.getText().toString());
/*				Sel_XNXQ  20140       
				txtxzbj   1120271     
				Sel_XZBJ  2011020401  
				type      1           
*/
				Bundle bundle = new Bundle();
				Set<String> keySet = params.keySet();
				Iterator<String> iter = keySet.iterator();
				while (iter.hasNext()) {
					String key = iter.next();
					bundle.putString(key, params.get(key));
				}
				
				//设置header
				Map<String, String> headers = new HashMap<String, String>();
				headers.put("Referer", Config.URL_CLASS_SELECT);
				headers.put("Host", Config.URL_HOST);
				//headers.put("Host", "codesrocks.com");
				headers.put("Cache-Control", "max-age=0");
				headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
				headers.put("Origin", "http://jwxt.tit.edu.cn");
				headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.90 Safari/537.36");
				headers.put("Accept-Language", "zh-CN,zh;q=0.8");
				headers.put("Content-Type", "application/x-www-form-urlencoded");
				//headers.put("Accept-Encoding", "gzip, deflate");
				headers.put("Cookie", ClassTableActivity.cookie);
				Bundle bundle2 = new Bundle();
				Set<String> keySet2 = headers.keySet();
				Iterator<String> iter2 = keySet2.iterator();
				while (iter2.hasNext()) {
					String key = iter2.next();
					bundle2.putString(key, headers.get(key));
				}
				
				Intent intent = new Intent(ClassTableActivity.this, 
						ShowWebDetailsActivity.class);
				intent.putExtra("params", bundle);
				intent.putExtra("url", Config.URL_BANGJIKEBIAO);
				intent.putExtra("webview_method", "post");
				//intent.putExtra("url", "http://codesrocks.com/lesson_origin.html");
				//intent.putExtra("url", "http://jwxt.tit.edu.cn/ZNPK/drawkbimg.aspx?type=1&w=1110&h=280&xn=2015&xq=0&bjdm=2012050401");
				intent.putExtra("url", "http://jwxt.tit.edu.cn/ZNPK/KBFB_ClassSel_rpt.aspx");
				intent.putExtra("headers", bundle2);
				//intent.putExtra("cookies", "");
				startActivity(intent);
			}
		});
		
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
		new AsyncData().execute();
		try {
			String[] params = {
					"http://jwxt.tit.edu.cn/sys/ValidateCode.aspx", "p2"};
					//"http://59.49.33.178/sys/ValidateCode.aspx", "p2"};
			new ImageLoadTask(getApplicationContext()).execute(params);
		} catch (Exception e) {
			//e.printStackTrace();
			Toaster.makeText(getApplicationContext(), "获取验证码失败", 0).show();
        }
	}

//	class SpinnerAdapter extends BaseAdapter {
//		List<Semester> list;
//		LayoutInflater inflater = null;
//
//		public SpinnerAdapter(List<Semester> list) {
//			this.list = list;
//			inflater = (LayoutInflater) getApplicationContext()
//					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		}
//
//		@Override
//		public int getCount() {
//			return list==null?0:list.size();
//		}
//
//		@Override
//		public Object getItem(int position) {
//			// TODO Auto-generated method stub
//			return list==null?null:list.get(position);
//		}
//
//		@Override
//		public long getItemId(int position) {
//			// TODO Auto-generated method stub
//			return position;
//		}
//
//		@Override
//		public View getView(int position, View convertView, ViewGroup parent) {
//			// TODO Auto-generated method stub
//			ViewHolder holder = null;
//			if (convertView == null) {
//				holder = new ViewHolder();
//				convertView = inflater
//						.inflate(R.layout.adapter_view_item, null);
//				holder.tv = (TextView) convertView.findViewById(R.id.tv_title);
//				convertView.setTag(holder);
//			} else {
//				holder = (ViewHolder) convertView.getTag();
//			}
//			holder.tv.setText(list.get(position).getSemester());
//			return convertView;
//		}
//
//		private class ViewHolder {
//			private TextView tv;
//		}
//
//	}
	
	class AsyncData extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {

			try {
				InputStream input = getApplicationContext().getResources()
						.openRawResource(R.raw.semester);
				smList = MyXmlParser.getSemester(input);
				spAdapter.addAll(smList);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
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
			Bitmap bitmap = BitmapFactory.decodeStream(Request.handlerDataWithHeader(url, ClassTableActivity.cookie));
			if(bitmap == null){
				Toaster.makeText(getApplicationContext(), "获取验证码失败", 0).show();
				return null;
			}
			bean.setImage(bitmap);
			if (ClassTableActivity.cookie != null) {
				Log.d("header", ClassTableActivity.cookie);
			}
			publishProgress(); // 通知去更新UI
			return null;
		}

		public void onProgressUpdate(Void... voids) {
			if (isCancelled())
				return;
			// 更新UI
			ClassTableActivity.this.iv_showCode.setImageBitmap(this.bean.getImage());
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
