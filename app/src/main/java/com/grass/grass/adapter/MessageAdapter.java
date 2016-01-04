package com.grass.grass.adapter;

import android.content.Context;
import android.view.View;

import com.grass.grass.entity.MessageInfo;
import com.grass.grass.utils.GrassBaseAdapter;

/**
 * Created by huchao on 2016/1/4.
 */
public class MessageAdapter extends GrassBaseAdapter<MessageInfo.Message>{


    public MessageAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemResource() {
        return 0;
    }

    @Override
    public View getItemView(int position, View convertView) {
        return null;
    }
}
