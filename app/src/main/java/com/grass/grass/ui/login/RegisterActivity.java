package com.grass.grass.ui.login;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.grass.grass.R;
import com.grass.grass.base.BaseActivity;

public class RegisterActivity extends BaseActivity {

    @Override
    public int getLayoutResId() {
        return R.layout.activity_register;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //执行注册操作

        initView();
    }

    private void initView() {
    }

    @Override
    protected void initHead(LinearLayout mHeadBack, TextView mLeft, TextView mTitle, TextView mRight) {
        super.initHead(mHeadBack, mLeft, mTitle, mRight);
        mTitle.setText("用户注册");
    }
}
