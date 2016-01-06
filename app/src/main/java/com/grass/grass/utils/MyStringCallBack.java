package com.grass.grass.utils;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.grass.grass.entity.BaseEntity;
import com.grass.grass.ui.login.LoginActivity;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.callback.StringCallback;

/**
 * Created by huchao on 2015/12/28.
 */
public abstract class MyStringCallBack extends StringCallback{

    private Context mContenxt;

    public MyStringCallBack(Context context){
        this.mContenxt = context;
    }

    @Override
    public void onError(Request request, Exception e) {
        LogUtils.e(e.toString());
        Toast.makeText(mContenxt,"网络异常,请稍后尝试",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(String response) {
        LogUtils.i("网络请求数据" + response);
        BaseEntity baseEntity = GsonQuick.toObject(response,BaseEntity.class);
        if(baseEntity.isSuccess()){
            onSuccessResponse(response);
        }else{
            if(baseEntity.getMessage().equals("请登录")){
                Toast.makeText(mContenxt,"会话超时,请登录",Toast.LENGTH_SHORT).show();
                mContenxt.startActivity(new Intent(mContenxt, LoginActivity.class));
                AppManager.getInstance().finishAllActivity();
            }else {
                Toast.makeText(mContenxt,baseEntity.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    }

    public abstract void onSuccessResponse(String response);
}
