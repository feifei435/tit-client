package cn.edu.tit.atys;

import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import cn.edu.tit.DataOperation;
import cn.edu.tit.R;
import cn.edu.tit.adapter.LoanAdapter;
import cn.edu.tit.bean.LoanFree;
import cn.edu.tit.swipeback.SwipeBackActivity;
import cn.edu.tit.util.Toaster;

public class LoanFreeActivity extends SwipeBackActivity{
	private TextView tv_title;
	private ImageView iv_goBack;
	private ListView lv_loan;
	private LoanAdapter loanAdapter;
	private List<LoanFree> mList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loan);
		this.lv_loan = (ListView) this.findViewById(R.id.lv_loan);
		this.tv_title = (TextView) this.findViewById(R.id.tv_title);
		this.tv_title.setText(R.string.easy_query);
		this.iv_goBack = (ImageView) this.findViewById(R.id.iv_goBack);
		this.iv_goBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LoanFreeActivity.this.finish();

			}
		});

		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				mList = DataOperation.getloadFreeDataFromJson(getApplicationContext());
				return null;
			}

			protected void onPostExecute(Void result) {
				loanAdapter.addAll(mList);
			};

		}.execute();
		loanAdapter = new LoanAdapter(getApplicationContext());
		this.lv_loan.setAdapter(loanAdapter);
		this.lv_loan.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				if (mList == null) {
					Toaster.makeText(getApplicationContext(), "listÎª¿Õ", 0).show();

				}

				String url = mList.get(position).getWeburl();
				Toaster.makeText(getApplicationContext(), url, 0).show();
				Intent intent = new Intent(LoanFreeActivity.this,
						ShowLoanWebDetailsActivity.class);
				intent.putExtra("url", url);
				startActivity(intent);

			}
		});
	}

}
