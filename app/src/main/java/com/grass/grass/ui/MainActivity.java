package com.grass.grass.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;

import com.grass.grass.R;
import com.grass.grass.base.BaseGrassActivity;
import com.grass.grass.pageAdapter.MainPageAdapter;
import com.grass.grass.ui.find.FindFragment;
import com.grass.grass.ui.home.MainFragment;
import com.grass.grass.ui.my.MyFragment;
import com.grass.grass.utils.AppManager;
import com.grass.grass.utils.LogUtils;
import com.grass.grass.view.GrassChangeIconAndText;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseGrassActivity {

    private ViewPager mVpMainPage;
    private GrassChangeIconAndText mGCvVMain, mGCVFind, mGCVMy;

    private MainPageAdapter pageAdapter;

    private List<GrassChangeIconAndText> mTabIndicator;

    private boolean isExitApp = false;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
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
        mVpMainPage = (ViewPager) findViewById(R.id.mian_vp_page);
        mGCvVMain = (GrassChangeIconAndText) findViewById(R.id.main_gv_main);
        mGCvVMain.setOnClickListener(this);
        mGCVFind = (GrassChangeIconAndText) findViewById(R.id.main_gv_find);
        mGCVFind.setOnClickListener(this);
        mGCVMy = (GrassChangeIconAndText) findViewById(R.id.main_gv_my);
        mGCVMy.setOnClickListener(this);

        mTabIndicator = new ArrayList<GrassChangeIconAndText>();
        mTabIndicator.add(mGCvVMain);
        mTabIndicator.add(mGCVFind);
        mTabIndicator.add(mGCVMy);

        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new MainFragment());
        fragments.add(new FindFragment());
        fragments.add(new MyFragment());

        pageAdapter = new MainPageAdapter(getSupportFragmentManager(), fragments);
        mVpMainPage.setAdapter(pageAdapter);
        mVpMainPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffset > 0) {
                    GrassChangeIconAndText left = mTabIndicator.get(position);
                    GrassChangeIconAndText right = mTabIndicator.get(position + 1);

                    left.setIconAlpha(1 - positionOffset);
                    right.setIconAlpha(positionOffset);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mGCvVMain.setIconAlpha(1.0f);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.main_gv_main:
                mVpMainPage.setCurrentItem(0,false);
                changIocnAlpha(0);
                break;
            case R.id.main_gv_find:
                mVpMainPage.setCurrentItem(1,false);
                changIocnAlpha(1);
                break;
            case R.id.main_gv_my:
                mVpMainPage.setCurrentItem(2,false);
                changIocnAlpha(2);
                break;
        }
    }

    /**
     * 修改alpha的值
     * @param index
     */
    private void changIocnAlpha(int index) {
        for (int i = 0; i < mTabIndicator.size() ; i++) {
            if(index == i){
                mTabIndicator.get(i).setIconAlpha(1.0f);
            }else{
                mTabIndicator.get(i).setIconAlpha(0.0f);
            }
        }
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
