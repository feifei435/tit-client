package cn.edu.tit.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Color;
import android.provider.SyncStateContract.Constants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.edu.tit.R;
import cn.edu.tit.bean.NewsCategory;
import cn.edu.tit.config.Config;

public class NewsCategoryAdapter extends BaseAdapter {

	private List<NewsCategory> menus;
	private LayoutInflater inflater;
	private int margin;
	public static final String[] COLORS = Config.ColorPanel.COLORS;
	List<Map<String, String>> list;

	// Constants.ColorPanel.COLORS[position]
	public NewsCategoryAdapter(Context context, List<NewsCategory> menus, int margin) {
		inflater = LayoutInflater.from(context);
		this.menus = menus;
		this.margin = margin;
		list = new ArrayList<Map<String, String>>();

		for (int i = 0; i < COLORS.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("color", COLORS[i]);
			list.add(map);
		}
	}
	
	private final int getRamdon(){
		return (int) (Math.random()*(list.size()));
	}
//	(int)(Math.random()*(max-min+1)) + min;

	@Override
	public int getCount() {
		return menus.size();
	}

	@Override
	public Object getItem(int position) {
		return menus.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int height = parent.getHeight() / 3 - margin;
		AbsListView.LayoutParams param = new AbsListView.LayoutParams(
				LayoutParams.MATCH_PARENT, height);
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.news_category_item, parent,
					false);

			
			convertView.setBackgroundColor(Color
					.parseColor(list.get(position).get("color"))); // 此处可以设置为主题
//			list.remove(num);
			
			holder = new ViewHolder();
			holder.iv_menuIcon = (ImageView) convertView
					.findViewById(R.id.iv_menu_icon);
			holder.tv_menuTitle = (TextView) convertView
					.findViewById(R.id.tv_menu_title);
			holder.tv_menuMsg = (TextView) convertView
					.findViewById(R.id.tv_menu_msg);
			convertView.setTag(holder);
		} else {
			convertView.setBackgroundColor(Color
					.parseColor(list.get(position).get("color"))); // 此处可以设置为主题
//			list.remove(num);
			
			holder = (ViewHolder) convertView.getTag();
		}
		NewsCategory item = menus.get(position);
		holder.iv_menuIcon.setImageResource(item.menuIconRes);
		holder.tv_menuTitle.setText(item.menuTitle);
		if (item.menuMsg.length() == 0) {
			holder.tv_menuMsg.setVisibility(View.GONE);
		} else {
			holder.tv_menuMsg.setVisibility(View.VISIBLE);
			holder.tv_menuMsg.setText(item.menuMsg);
		}
		convertView.setLayoutParams(param);
		return convertView;
	}

	private class ViewHolder {
		ImageView iv_menuIcon;
		TextView tv_menuTitle;
		TextView tv_menuMsg;
	}

}