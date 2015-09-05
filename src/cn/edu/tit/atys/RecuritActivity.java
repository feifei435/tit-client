package cn.edu.tit.atys;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import cn.edu.tit.R;
import cn.edu.tit.bean.JsoupEntity;
import cn.edu.tit.bean.Recurit;
import cn.edu.tit.config.Config;
import cn.edu.tit.util.Toaster;

public class RecuritActivity extends Activity {

	private ListView lv_recurit;
	private TextView tv_title;
	private ImageView iv_goBack;
	private List<Recurit> list = new ArrayList<Recurit>();
	RecuritAdapter adapter = null ;
	
	private LayoutInflater inflater  ; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recurit);
		this.tv_title = (TextView) this.findViewById(R.id.tv_title);
		this.tv_title.setText(R.string.recurit);
		this.iv_goBack = (ImageView) this.findViewById(R.id.iv_goBack);
		this.iv_goBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				RecuritActivity.this.finish();

			}
		});
		inflater = getLayoutInflater() ;
		this.lv_recurit = (ListView) this.findViewById(R.id.lv_recurit);
		adapter = new RecuritAdapter() ;
		this.lv_recurit.setAdapter(adapter);
		
		new AsyncTask<Void, Void, List<Recurit>>() {

			@Override
			protected List<Recurit> doInBackground(Void... params) {
				// TODO Auto-generated method stub
				return JsoupEntity.getRecuritData(Config.URL_RECURIT);
			}

			@Override
			protected void onPostExecute(List<Recurit> result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				adapter.addAll(result);
			}

			
		}.execute();
		
		this.lv_recurit.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				if (list == null) {
					Toaster.makeText(getApplicationContext(), "listÎª¿Õ", 0).show();

				}

				String url = list.get(position).getUrl();
				Toaster.makeText(getApplicationContext(), url, 0).show();
				Intent intent = new Intent(RecuritActivity.this,
						ShowLoanWebDetailsActivity.class);
				intent.putExtra("url", url);
				startActivity(intent);
			}
		});

	}

	class RecuritAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		public void addAll(List<Recurit> result) {
			// TODO Auto-generated method stub
			list = result ;
			notifyDataSetChanged();
			
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = inflater
						.inflate(R.layout.loan_item_layout, null);
				holder.tv_num = (TextView) convertView.findViewById(R.id.tv_publisher);
				holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
				holder.tv_time=(TextView) convertView.findViewById(R.id.tv_time);
				
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.tv_num.setText(position+"");
			holder.tv_title.setText(list.get(position).getTitle());
			holder.tv_time.setText(list.get(position).getDate());
			
			return convertView;
		}
		
		private class ViewHolder {
			private TextView tv_num;
			private TextView tv_title;
			private TextView tv_time;
		}

	}

}
