package com.grass.grass.base;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.grass.grass.entity.UserInfo;
import com.grass.grass.utils.LogUtils;

/**
 * Created by huchao on 2015/12/23.
 */
public class BaseApplication extends Application{

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor edit;

    @Override
    public void onCreate() {
        super.onCreate();
        //做一些初始化操作

        sharedPreferences = getSharedPreferences("grass", Context.MODE_PRIVATE);
        edit = sharedPreferences.edit();

        //是否打印log日志
        LogUtils.setDebug(true);
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
