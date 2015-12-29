package com.grass.grass.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.grass.grass.R;
import com.grass.grass.base.BaseApplication;
import com.grass.grass.base.BaseGrassActivity;
import com.grass.grass.entity.UserInfo;
import com.grass.grass.ui.login.LoginActivity;
import com.grass.grass.utils.GsonQuick;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

public class StartActivity extends BaseGrassActivity{

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1){
                toActivity(LoginActivity.class);
            }else{
                toActivity(MainActivity.class);
            }
            StartActivity.this.finish();
        }
    };

    @Override
    public int getLayoutResId() {
        return R.layout.activity_start;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String userId = BaseApplication.getSpfinfo("userId");
        if("".equals(userId) || "0".equals(userId)){
            handler.sendEmptyMessageDelayed(1,2000);
        }else{
            doLogin();
        }
    }

    private void doLogin() {

        Map<String,String> params = new HashMap<String,String>();
        params.put("userName",BaseApplication.getSpfinfo("userName"));
        params.put("pwd",BaseApplication.getSpfinfo("userPwd"));
        sendPost(HttpUrlManage.Login.Login,params).execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                show("网络异常");
            }

            @Override
            public void onResponse(String response) {

                UserInfo userInfo = GsonQuick.toObject(response, UserInfo.class);
                if(userInfo.isSuccess()){
                    //保存用户信息
                    BaseApplication.saveUserInfo(userInfo.getData());
                    handler.sendEmptyMessageDelayed(2, 1500);
                }else {
                    show(userInfo.getMessage());
                }
            }
        });

    }

}
