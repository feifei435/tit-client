package cn.edu.tit.atys;

import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.edu.tit.R;
import cn.edu.tit.config.Config;
import cn.edu.tit.util.Toaster;

public class FeedBackActivity extends Activity{
	EditText content ;
	SmsManager sManager; 
	EditText contact ;
	TextView titleBar ;
	private ImageView iv_goBack;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.activity_feedback);
		this.titleBar = (TextView) this.findViewById(R.id.tv_title);
		this.titleBar.setText(R.string.feedback);
		iv_goBack = (ImageView) this.findViewById(R.id.iv_goBack);
		iv_goBack.setVisibility(View.VISIBLE);
		this.iv_goBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FeedBackActivity.this.finish();

			}
		});
		content = (EditText)this.findViewById(R.id.content);
		contact = (EditText)this.findViewById(R.id.contact);
		sManager = SmsManager.getDefault();
	}
	

	
	public void toGo(View view ){
		String contentStr ;
		String contactStr ;
		StringBuilder sb =new StringBuilder() ;
		contentStr = content.getText().toString().trim();
		contactStr = contact.getText().toString().trim();
		if(contentStr.equals("") ){
			Toaster.makeText(FeedBackActivity.this, R.string.input_feedback_content, 1000).show();
			return;
		}
		if(contactStr.equals("")){
			Toaster.makeText(FeedBackActivity.this, R.string.input_connect_method, 1000).show();
			return;
		}
//		sb.append("智能公交发来回馈:");
//		sb.append(contentStr);
//		sb.append(",联系方式:");
//		sb.append(contactStr);
//		Toaster.makeText(FeedBackActivity.this, sb.toString(), 1000).show();
//		System.out.println(sb.toString());
		//创建一个pendingIntent对象
		PendingIntent pi =  PendingIntent.getActivity(FeedBackActivity.this, 0, new Intent(), 0);
		 //如果字数超过70,需拆分成多条短信发送
       

            List<String> all=sManager.divideMessage(sb.toString());  //短信的内容是有限的，要根据短信长度截取。逐条发送    
            Iterator<String> it=all.iterator();   
            while(it.hasNext()){   
                sManager.sendTextMessage(Config.PHONE_NUM, null, it.next(), null, null);  //逐条发送短息    
                   
            }   
            Toaster.makeText(FeedBackActivity.this, R.string.message_sent, Toast.LENGTH_LONG).show();   
           

        }
	}
	