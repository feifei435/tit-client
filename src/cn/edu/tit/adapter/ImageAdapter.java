/*
 * Copyright (C) 2011 Patrik �kerfeldt
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.edu.tit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import cn.edu.tit.R;

public class ImageAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater mInflater;
	private static final int[] ids = { R.drawable.binder_01,
			R.drawable.binder_02, R.drawable.binder_03,R.drawable.binder_04 };

	public ImageAdapter(Context context) {
		mContext = context;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return Integer.MAX_VALUE;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.settings_image_item, null);
		}
		ImageView iv = ((ImageView) convertView.findViewById(R.id.imgView));
		iv.setBackgroundResource(ids[position % ids.length]);
		;
//		iv.setAdjustViewBounds(true);
		iv.setScaleType(ImageView.ScaleType.CENTER);
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Intent intent = new Intent(mContext,
				// ImageShowActivity.class);
				// Bundle bundle = new Bundle();
				// bundle.putInt("image_id", ids[position % ids.length]);
				// intent.putExtras(bundle);
				// mContext.startActivity(intent);
			}
		});
		return convertView;
	}

}
