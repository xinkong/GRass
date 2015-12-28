package com.grass.grass.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;

import com.grass.grass.R;
import com.grass.grass.base.BaseGrassActivity;
import com.grass.grass.utils.AppManager;
import com.grass.grass.utils.LogUtils;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseGrassActivity{

    private boolean isExitApp = false;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1){
                isExitApp = false;
            }

        }
    };

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();

    }

    private void initView() {
    }


    private void doFileUpLoad() {
        show("执行文件上传");

        PostFormBuilder postFormBu = OkHttpUtils.post().url(HttpUrlManage.Msg.sendPicMsg);
        List<String> a = new ArrayList<String>();
        a.add("/storage/emulated/0/DCIM/Camera/IMG_20151210_161752.jpg");
        a.add("/storage/emulated/0/DCIM/Camera/IMG_20151210_161747.jpg");
        for (int i = 0; i < a.size(); i++) {
            postFormBu.addFile("file", "name" + i + a.get(i).substring(a.get(i).lastIndexOf("."), a.get(i).length()), new File(a.get(i)));
        }
        postFormBu.addParams("msgContent", "哈哈哈哈哈")
                .addParams("userId", "1").build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Request request, Exception e) {
                        LogUtils.i(e.toString());
                    }

                    @Override
                    public void onResponse(String response) {
                        LogUtils.i(response);
                    }
                });

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isExitApp == true) {
                AppManager.getInstance().appExit(this);
            } else {
                isExitApp = true;
                handler.sendEmptyMessageDelayed(1, 2000);
                show("再次按下返回键就退出应用程序");
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
