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
import cn.edu.tit.bean.Semester;

public class SpinnerAdapter extends BaseAdapter {
	List<Semester> list = new ArrayList<Semester>();
	LayoutInflater inflater = null;

	public SpinnerAdapter(Context mContext) {
		inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list==null?0:list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list==null?null:list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	public void addAll(List<Semester> data){
		this.list.addAll(data);
		notifyDataSetChanged();
	}
	
	public void clear(){
		list.clear();
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater
					.inflate(R.layout.adapter_view_item, null);
			holder.tv = (TextView) convertView.findViewById(R.id.tv_title);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv.setText(list.get(position).getSemester());
		return convertView;
	}

	private class ViewHolder {
		private TextView tv;
	}

}
