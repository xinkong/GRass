package com.grass.grass.utils;

import android.content.Context;
import android.os.Environment;

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
}
