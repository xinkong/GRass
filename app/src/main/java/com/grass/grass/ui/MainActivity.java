package com.grass.grass.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.grass.grass.R;
import com.grass.grass.base.BaseActivity;
import com.grass.grass.utils.LogUtils;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity {

    private TextView mTvLogin;
    private TextView mTvUpload;


    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();

    }

    @Override
    protected void initHead(LinearLayout mHeadBack, TextView mLeft, TextView mTitle, TextView mRight) {
        super.initHead(mHeadBack, mLeft, mTitle, mRight);
        mTitle.setText("主界面");
    }

    private void initView() {
        mTvLogin = (TextView) findViewById(R.id.main_login);
        mTvLogin.setOnClickListener(this);

        mTvUpload = (TextView) findViewById(R.id.main_upLoadpic);
        mTvUpload.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {

            case R.id.main_login:
                doLogin();
                break;
            case R.id.main_upLoadpic:
                doFileUpLoad();
                break;
        }
    }

    private void doFileUpLoad() {
        show("执行文件上传");

        PostFormBuilder postFormBu =  OkHttpUtils.post().url(HttpUrlManage.Msg.sendPicMsg);
        List<String> a = new ArrayList<String>();
        a.add("/storage/emulated/0/DCIM/Camera/IMG_20151210_161752.jpg");
        a.add("/storage/emulated/0/DCIM/Camera/IMG_20151210_161747.jpg");
        for (int i = 0; i < a.size(); i++) {
            postFormBu.addFile("file","name"+i+a.get(i).substring(a.get(i).lastIndexOf("."),a.get(i).length()),new File(a.get(i)));
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

//        OkHttpUtils.post().url(HttpUrlManage.Msg.sendPicMsg)
//                .addFile("file", "IMG_20151210_161752.jpg", new File("/storage/emulated/0/DCIM/Camera/IMG_20151210_161752.jpg"))
//                .addFile("file", "IMG_20151210_161747.jpg", new File("/storage/emulated/0/DCIM/Camera/IMG_20151210_161747.jpg"))
//                .addParams("msgContent", "oooooooooooo")
//                .addParams("userId","1")
//                .build().execute(new StringCallback() {
//
//            @Override
//            public void onError(Request request, Exception e) {
//                LogUtils.i(e.toString());
//            }
//
//            @Override
//            public void onResponse(String response) {
//                LogUtils.i(response);
//            }
//        });

    }

    private void doLogin() {
        show("执行了登陆操作");
        Map<String,String> params = new HashMap<String,String>();
        params.put("userName", "o");
        params.put("pwd", "o");
        sendPost(HttpUrlManage.Login.Login, params).execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                LogUtils.i(response);
                show(response);
            }
        });
    }


}
