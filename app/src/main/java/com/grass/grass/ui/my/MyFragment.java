package com.grass.grass.ui.my;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.grass.grass.R;
import com.grass.grass.base.BaseApplication;
import com.grass.grass.base.BaseGrassFragment;
import com.grass.grass.entity.UserInfo;
import com.grass.grass.ui.login.LoginActivity;
import com.grass.grass.utils.AppManager;

public class MyFragment extends BaseGrassFragment {

    private RelativeLayout mRlExitApp;

    @Override
    public View onInflaterView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_my,null);
    }

    @Override
    protected void initHeadView(LinearLayout ll_headView, TextView tv_left, TextView tv_title, TextView tv_right) {
        super.initHeadView(ll_headView, tv_left, tv_title, tv_right);
        tv_title.setText("我");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
    }

    private void initView() {
        mRlExitApp = (RelativeLayout) findViewById(R.id.my_rl_exitLogin);
        mRlExitApp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.my_rl_exitLogin:
                new UserInfo().new User();
                BaseApplication.saveUserInfo(new UserInfo().new User());
                toActivity(getActivity(), LoginActivity.class);
                AppManager.getInstance().finishAllActivity();
                break;
        }
    }
}
