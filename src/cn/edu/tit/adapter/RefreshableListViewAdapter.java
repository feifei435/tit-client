package cn.edu.tit.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.edu.tit.R;
import cn.edu.tit.util.ImageUtil;
import cn.edu.tit.util.ImageUtil.ImageCallback;

public class RefreshableListViewAdapter extends BaseAdapter {
	Context mContext;
	LayoutInflater mInflater;
	Typeface typeface;
	private  int curPosition=1 ;
//	List<NewsContentItem> mList = new ArrayList<NewsContentItem>();
	List<Map<String,Object>> mList = new ArrayList<Map<String,Object>>();
	public List<Map<String, Object>> getmList() {
		return mList;
	}

	

	public RefreshableListViewAdapter(Context context) {
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
	}

	public void appendToList(List<Map<String,Object>> lists) { // 通过调用此方法既可以完成数据的添加
		if (lists == null) {
			return;
		}
		mList.addAll(lists);
		notifyDataSetChanged();
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

	@Override
	public View getView(int position, View convertView, final ViewGroup parent) {
		ViewHolder holder;
		Map<String,Object> item = mList.get(position);
		if (convertView == null) {
			holder = new ViewHolder();
			
			convertView = mInflater
					.inflate(R.layout.news_item_layout, null);
			holder.title_ = (TextView) convertView
					.findViewById(R.id.news_title);
			holder.short_ = (TextView) convertView
					.findViewById(R.id.news_short_content);
			holder.textVeiw3 = (TextView) convertView
					.findViewById(R.id.item_textView3);
			holder.img_thu = (ImageView) convertView
					.findViewById(R.id.img_thu);
			convertView.setTag(holder);
			typeface = Typeface.createFromAsset(mContext.getAssets(),
					"font/MgOpenCosmeticaBold.ttf");

			holder.textVeiw3.setTypeface(typeface);
		
		} else {
			holder = (ViewHolder) convertView.getTag();

		}
		holder.title_.setText(item.get("title")+"");
		holder.short_.setText(item.get("time")+"");
		holder.textVeiw3.setText((position + 1) + "");
		String img_url = "";
		if (img_url.equals(null) || img_url.equals("")) {
			holder.img_thu.setVisibility(View.GONE);
		} else {
			holder.img_thu.setVisibility(View.VISIBLE);
			ImageUtil.setThumbnailView(img_url, holder.img_thu, mContext,
					new ImageCallback(){


				@Override
				public void loadImage(Bitmap bitmap,
						String imagePath) {
					// TODO Auto-generated method stub
					try {
						ImageView img = (ImageView) parent
								.findViewWithTag(imagePath);
						img.setImageBitmap(bitmap);
					} catch (NullPointerException ex) {
						Log.e("error", "ImageView = null");
					}
				}
			
				
			}, false);
		}
		return convertView;
	
	}
	
	static class ViewHolder {
		public TextView title_;
		public TextView short_;
		public TextView textVeiw3;
		public ImageView img_thu;
	}

	
}
