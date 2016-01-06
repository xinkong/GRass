package com.grass.grass.utils;

import android.util.SparseArray;
import android.view.View;

/**
 * 
 * @Date:2014-8-24上午9:51:51
 */
public class ViewHolder {

	public static <T extends View> T get(View view, int id) {
		SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
		if (viewHolder == null) {
			viewHolder = new SparseArray<View>();
			view.setTag(viewHolder);
		}
		View childView = viewHolder.get(id);
		if (childView == null) {
			childView = view.findViewById(id);
			viewHolder.put(id, childView);
		}
		return (T) childView;

	}
}
