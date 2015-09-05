package cn.edu.tit.atys;

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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import cn.edu.tit.MySqLiteParser;
import cn.edu.tit.MyXmlParser;
import cn.edu.tit.R;
import cn.edu.tit.adapter.MyActvAdapter;
import cn.edu.tit.adapter.SpinnerAdapter;
import cn.edu.tit.bean.Semester;
import cn.edu.tit.bean.Subject;
import cn.edu.tit.config.Config;
import cn.edu.tit.swipeback.SwipeBackActivity;
import cn.edu.tit.util.Toaster;
import cn.edu.tit.util.Request;
/**
 * 查询课程课表
 * @author pjm
 *
 */
public class LessonTableActivity extends SwipeBackActivity{
	protected static final int INIT_MSG_SEMESTER_READY = 0x12;
	protected static final int INIT_MSG_SUBJECT_READY = 0x13;
	private Spinner sp_semester;
	private AutoCompleteTextView actv_selSubject;
	private Button btn_submit;
	private TextView tv_title;
	private ImageView iv_goBack;
	private ImageView iv_showCode;
	private List<Semester> smList;
	private List<Subject> sbjList;
	private String postSemester;
	private String postSubject;
	private EditText et_yzm;
	public static String cookie;
	SpinnerAdapter spAdapter;
	MyActvAdapter actvAdapter;

	/*
	 * private Handler handler = new Handler() { public void
	 * handleMessage(android.os.Message msg) { switch (msg.what) { case
	 * INIT_MSG_SEMESTER_READY: SpinnerAdapter spAdapter = new
	 * SpinnerAdapter(smList); sp_semester.setAdapter(spAdapter); sp_semester
	 * .setOnItemSelectedListener(new OnItemSelectedListener() {
	 * 
	 * @Override public void onItemSelected(AdapterView<?> parent, View view,
	 * int position, long id) { // TODO Auto-generated method stub
	 * System.out.println(parent .getItemAtPosition(position)); Toaster.makeText(
	 * getApplicationContext(), parent.getItemAtPosition(position) + "",
	 * 0).show(); sp_semester.setSelection(position); postSemester = ((Semester)
	 * parent .getItemAtPosition(position)).getCode() + ""; }
	 * 
	 * @Override public void onNothingSelected(AdapterView<?> parent) { // TODO
	 * Auto-generated method stub
	 * 
	 * } }); break; case INIT_MSG_SUBJECT_READY: actv_selSubject
	 * .setOnItemClickListener(new OnItemClickListener() {
	 * 
	 * @Override public void onItemClick(AdapterView<?> parent, View arg1, int
	 * position, long id) { // TODO Auto-generated method stub Log.d("TAG",
	 * "onItemClick:" + position); String name = ((Subject) parent
	 * .getItemAtPosition(position)) .getSbjname();
	 * actv_selSubject.setText(name); postSubject = ((Subject) parent
	 * .getItemAtPosition(position)).getMark();
	 * 
	 * } }); actv_selSubject.setThreshold(1); MyActvAdapter adapter = new
	 * MyActvAdapter( getApplicationContext(), sbjList, MyActvAdapter.ALL); //
	 * SelSubjectAdapter adapter = new
	 * SelSubjectAdapter(getApplicationContext(),sbjList,
	 * SelSubjectAdapter.ALL); actv_selSubject.setAdapter(adapter); break; } };
	 * };
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lesson_table);
		init();
		// Intent intent = new
		// Intent(KebiaochaxunActivity.this,BanjikebiaoActivity.class);
		// startActivity(intent);
	}

	private void init() {
		this.sp_semester = (Spinner) this.findViewById(R.id.sp_semester);
		this.sp_semester.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent,
					View view, int position, long id) {
				// TODO Auto-generated method stub
				//System.out.println(parent.getItemAtPosition(position));
				Toaster.makeText(getApplicationContext(),
						parent.getItemAtPosition(position) + "", 0).show();
				sp_semester.setSelection(position);
				postSemester = ((Semester) parent
						.getItemAtPosition(position)).getCode() + "";
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});
		spAdapter = new SpinnerAdapter(this);
		this.sp_semester.setAdapter(spAdapter);

		this.actv_selSubject = (AutoCompleteTextView) this
				.findViewById(R.id.actv_selSubject);
		this.actv_selSubject.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View arg1,
					int position, long id) {
				Log.d("TAG", "onItemClick:" + position);
				String name = ((Subject) parent.getItemAtPosition(position))
						.getSbjname();
				actv_selSubject.setText(name);
				postSubject = ((Subject) parent.getItemAtPosition(position))
						.getMark();

			}
		});
		this.actv_selSubject.setThreshold(1);
		actvAdapter= new MyActvAdapter(getApplicationContext(),
				 MyActvAdapter.ALL); //
		this.actv_selSubject.setAdapter(actvAdapter);
		this.tv_title = (TextView) this.findViewById(R.id.tv_title);
		this.tv_title.setText(R.string.lesson_table);
		this.iv_goBack = (ImageView) this.findViewById(R.id.iv_goBack);
		this.iv_goBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LessonTableActivity.this.finish();

			}
		});
		this.btn_submit = (Button) this.findViewById(R.id.btn_submit);
		this.et_yzm = (EditText) this.findViewById(R.id.et_validate_code);
		this.btn_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// String postSemester = getSemester();
				// String tmpstr =actv_SelSubject.getText().toString();
				// String postSubject =getSubject(tmpstr);

				if (TextUtils.isEmpty(postSubject) | TextUtils.isEmpty(postSemester)) {
					Toaster.makeText(getApplicationContext(), R.string.select_semester_and_subject, 0)
							.show();
					return;
				}

				Map<String, String> params;
				params = new HashMap<String, String>();
				params.put("Sel_XNXQ", postSemester);
				params.put("Sel_KC", postSubject);
				params.put("gs", "1");
				params.put("txt_yzm",et_yzm.getText().toString());
				Bundle bundle = new Bundle();
				Set<String> keySet = params.keySet();
				Iterator<String> iter = keySet.iterator();
				while (iter.hasNext()) {
					String key = iter.next();
					bundle.putString(key, params.get(key));
				}

				Map<String, String> headers = new HashMap<String, String>();
				headers.put("Referer", Config.URL_LESSON_SELECT);
				headers.put("Host", Config.URL_HOST);
				headers.put("Cache-Control", "max-age=0");
				headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
				headers.put("Origin", "http://jwxt.tit.edu.cn");
				headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.90 Safari/537.36");
				headers.put("Content-Type", "application/x-www-form-urlencoded");
				//headers.put("Accept-Encoding", "gzip, deflate");
				headers.put("Accept-Language", "zh-CN,zh;q=0.8");
				headers.put("Cookie", LessonTableActivity.cookie);
				
				Bundle bundle2 = new Bundle();
				Set<String> keySet2 = headers.keySet();
				Iterator<String> iter2 = keySet2.iterator();
				while (iter2.hasNext()) {
					String key = iter2.next();
					bundle2.putString(key, headers.get(key));
				}

				Intent intent = new Intent(LessonTableActivity.this,
						ShowWebDetailsActivity.class);
				intent.putExtra("params", bundle);
				intent.putExtra("url", Config.URL_KECHENGKEBIAO);
				intent.putExtra("webview_method", "post");
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
		
		
		 // 开启线程加载数据 new Thread() {
/*		 @Override public void run() { // TODO Auto-generated method stub
		 super.run();
		 
		 try { // 加载学期列表 InputStream input =
		 getApplicationContext().getResources().openRawResource(R.raw.semester); smList =
		 MyXmlParser.getSemester(input); Message msg = handler.obtainMessage(); msg.what = INIT_MSG_SEMESTER_READY;
		 handler.sendMessage(msg); // 加载科目列表 InputStream in =
		 getApplicationContext().getAssets().open( "subject.db"); sbjList =
		 MySqLiteParser.getAllSubject( getApplicationContext(), in);
		 
		 Message msg2 = handler.obtainMessage(); msg2.what =
		 INIT_MSG_SUBJECT_READY; handler.sendMessage(msg2); } catch (Exception
		 e) { // TODO Auto-generated catch block e.printStackTrace(); } }
		 
		 }.start();
*/		 
	}

	class AsyncData extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			try {
				InputStream input = getApplicationContext().getResources()
						.openRawResource(R.raw.semester);
				smList = MyXmlParser.getSemester(input);
				spAdapter.addAll(smList);

				InputStream in = getApplicationContext().getAssets().open("subject.db");
				sbjList = MySqLiteParser.getAllSubject(getApplicationContext(), in);
				actvAdapter.addAll(sbjList);
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
			
			Bitmap bitmap = BitmapFactory.decodeStream(Request.handlerDataWithHeader(url, LessonTableActivity.cookie));
			if(bitmap == null){
				Toaster.makeText(getApplicationContext(), "获取验证码失败", 0).show();
				return null;
			}
			bean.setImage(bitmap);
			if (LessonTableActivity.cookie != null) {
				Log.d("header", LessonTableActivity.cookie);
			}
			
			publishProgress(); // 通知去更新UI
			return null;
		}

		public void onProgressUpdate(Void... voids) {
			if (isCancelled())
				return;
			// 更新UI
			LessonTableActivity.this.iv_showCode.setImageBitmap(this.bean.getImage());
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
