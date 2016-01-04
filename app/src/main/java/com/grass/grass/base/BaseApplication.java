package com.grass.grass.base;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.grass.grass.R;
import com.grass.grass.entity.UserInfo;
import com.grass.grass.utils.AppGlobal;
import com.grass.grass.utils.LogUtils;

import java.io.File;

/**
 * Created by huchao on 2015/12/23.
 */
public class BaseApplication extends Application{

    private static Context mContext;

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor edit;


    @Override
    public void onCreate() {
        super.onCreate();
        //做一些初始化操作
        mContext = this;
        sharedPreferences = getSharedPreferences("grass", Context.MODE_PRIVATE);
        edit = sharedPreferences.edit();

        //是否打印log日志
        LogUtils.setDebug(true);

        initFresco();

    }

    private void initFresco() {
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(mContext)
                .setMainDiskCacheConfig(DiskCacheConfig.newBuilder().setBaseDirectoryName("grass").setBaseDirectoryPath(new File(AppGlobal.getCacheRoot(mContext))).build())
                .build();
        Fresco.initialize(mContext, config);
    }

    public static GenericDraweeHierarchy getHierarchy(){
        Drawable bg = mContext.getResources().getDrawable(R.drawable.tips_bg);
        GenericDraweeHierarchyBuilder builder =
                new GenericDraweeHierarchyBuilder(mContext.getResources());
        GenericDraweeHierarchy hierarchy = builder
                .setFadeDuration(300)
                .setPlaceholderImage(bg)
                .build();
        hierarchy.setProgressBarImage(new ProgressBarDrawable());
       return hierarchy;
    }


    public static boolean saveUserInfo(UserInfo.User user){
        edit.putString("userId",user.getUserId()+"");
        edit.putString("userName",user.getUserName());
        edit.putString("userPwd",user.getPwd());
        edit.putString("userSex", user.getSex());

       return edit.commit();
    }

    public static String getSpfinfo(String key){
        return sharedPreferences.getString(key,"");
    }

}
