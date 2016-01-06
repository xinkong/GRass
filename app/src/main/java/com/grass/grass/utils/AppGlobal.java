package com.grass.grass.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.widget.TextView;

import com.grass.grass.R;

import java.io.File;

/**
 * Created by huchao on 2015/12/30.
 */
public class AppGlobal {

    /***
     * 得到文件缓存的位置
     *
     * @param appContext
     * @return
     * @update 2014-5-15 下午3:03:30
     */
    public static String getCacheRoot(Context appContext) {
        String root = Environment.getExternalStorageDirectory()
                .getAbsolutePath()
                + appContext.getString(R.string.dir)
                + appContext.getString(R.string.app_dir)
                + appContext.getString(R.string.cache);
        File f = new File(root);
        if(!f.exists()){
            f.mkdirs();
        }
        return root;
    }

    public static void fillerSexTextView(Context context,String sex, TextView tv_sex) {
        Drawable drawable;
        if (sex.endsWith("女")) {
            drawable = context.getResources().getDrawable(R.drawable.woman);
            tv_sex.setBackgroundResource(R.drawable.woman_bg);
        } else {
            drawable = context.getResources().getDrawable(R.drawable.man);
            tv_sex.setBackgroundResource(R.drawable.man_bg);
        }
        tv_sex.setTextSize(context.getResources().getDimension(R.dimen.sex_size));
        tv_sex.setTextColor(0xffffffff);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tv_sex.setCompoundDrawables(drawable, null, null, null);
        tv_sex.setCompoundDrawablePadding(10);
        tv_sex.setText(sex);
    }
}
