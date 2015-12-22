package com.grass.grass.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
/**
 * 基类adapter
 * 
 * @author bamboo
 * 
 * @param <T>
 */
public abstract class GrassBaseAdapter<T> extends BaseAdapter {
	protected Context mContext;
	protected List<T> mListData;

	public GrassBaseAdapter(Context context) {
		this.mContext = context;
		mListData = new ArrayList<T>();
	}

	/**
	 * 功能:设置数据
	 * @param mListData 
	 * @author: huchao
	 * @date:2015-5-14下午3:14:45
	 */
	public void setData(List<T> mListData){
		this.mListData = mListData; 
		this.notifyDataSetChanged();
	}
	
	public void addAll(List<T> lists) {
		this.mListData.addAll(lists);
		this.notifyDataSetChanged();
	}

	
	public void remove(int position) {
		this.mListData.remove(position);
		this.notifyDataSetChanged();
	}

	public void remove(T obj) {
		this.mListData.remove(obj);
		this.notifyDataSetChanged();
	}
	
	public void removeAll() {
		this.mListData.clear();
		this.notifyDataSetChanged();
	}
	public void removeAll(List<T> T) {
		this.mListData.removeAll(T);
		this.notifyDataSetChanged();
	}

	public void clear() {
		this.mListData.clear();
	}
	@Override
	public int getCount() {
		if(mListData!=null){
			return mListData.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if (position > mListData.size()) {
			return null;
		}
		return mListData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = LayoutInflater.from(mContext).inflate(getItemResource(), null);
		}
		return getItemView(position, convertView);
	}

	/**
	 * 
	 * 功能:条目布局
	 * @return 
	 * @author: huchao
	 * @date:2015-5-14下午2:46:00
	 */
	public abstract int getItemResource();

	public abstract View getItemView(int position, View convertView);
}
