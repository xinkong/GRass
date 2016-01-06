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
import com.nostra13.universalimageloader.cache.disc.impl.LimitedAgeDiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

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
//        initImageLoad();

    }

    private void initImageLoad() {
//		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
//				.diskCacheFileNameGenerator(new Md5FileNameGenerator()).diskCacheSize(50 * 1024 * 1024).diskCache(new UnlimitedDiscCache(new File(BitmapHelp.getCacheRoot(this))))
//				// 50 Mb
//				.tasksProcessingOrder(QueueProcessingType.LIFO).writeDebugLogs().build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(this)
                .memoryCacheExtraOptions(720, 1280) // maxwidth, max height，即保存的每个缓存文件的最大长宽
                .threadPoolSize(3)//线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCacheSize(2 * 1024 * 1024)
                .discCacheSize(30 * 1024 * 1024)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .discCacheFileCount(50) //缓存的文件数量
                .discCache(new LimitedAgeDiskCache(new File(AppGlobal.getCacheRoot(this)),1*60*60*24*30L))//自定义缓存路径,保存一个月时间
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(new BaseImageDownloader(this, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
                .writeDebugLogs() // Remove for releaseapp
                .build();

        ImageLoader.getInstance().init(config);

    }

    public static DisplayImageOptions getOptions(){
        DisplayImageOptions options = new DisplayImageOptions.Builder().showImageOnFail(R.mipmap.app_default_img).showImageForEmptyUri(R.mipmap.app_default_img)
                .showImageOnLoading(R.mipmap.app_default_img).cacheOnDisk(true).cacheInMemory(true).build();
        return options;
    }


    private void initFresco() {
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(mContext)
                .setMainDiskCacheConfig(DiskCacheConfig.newBuilder().setBaseDirectoryName("grass").setBaseDirectoryPath(new File(AppGlobal.getCacheRoot(mContext))).build())
                .build();
        Fresco.initialize(mContext);
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
