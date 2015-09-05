package cn.edu.tit.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.edu.tit.R;
import cn.edu.tit.bean.LoanFree;

public class LoanAdapter extends BaseAdapter {
	Context mContext;
	LayoutInflater inflater;
	List<LoanFree> mList = new ArrayList<LoanFree>();

	public LoanAdapter(Context mContext) {
		this.mContext = mContext;
		inflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public void addAll(List<LoanFree> data) {
		this.mList.addAll(data);
		notifyDataSetChanged();
	}

	public void clear() {
		this.mList.clear();
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
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
		holder.tv_num.setText(mList.get(position).getPublisher());
		holder.tv_title.setText(mList.get(position).getTitle());
		holder.tv_time.setText(mList.get(position).getPubTime());
		
		return convertView;
	}

	private class ViewHolder {
		private TextView tv_num;
		private TextView tv_title;
		private TextView tv_time;
	}
}