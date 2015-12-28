package com.grass.grass.ui.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.grass.grass.R;
import com.grass.grass.base.BaseApplication;
import com.grass.grass.base.BaseGrassActivity;
import com.grass.grass.entity.UserInfo;
import com.grass.grass.ui.HttpUrlManage;
import com.grass.grass.ui.MainActivity;
import com.grass.grass.utils.GsonQuick;
import com.grass.grass.utils.MyStringCallBack;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends BaseGrassActivity{

    private EditText mEtUserName, mEtUserPwd1, mEtUserPwd2;
    private RadioButton mRbman, mRbwoman;
    private Button mBtnRegister;

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
        mEtUserName = (EditText) findViewById(R.id.register_et_userName);
        mEtUserPwd1 = (EditText) findViewById(R.id.register_et_userPwd1);
        mEtUserPwd2 = (EditText) findViewById(R.id.register_et_userPwd2);

        mRbman = (RadioButton) findViewById(R.id.register_rb_man);
        mRbwoman = (RadioButton) findViewById(R.id.register_rb_woman);

        mBtnRegister = (Button) findViewById(R.id.register_btn_register);
        mBtnRegister.setOnClickListener(this);
    }

    @Override
    protected void initHead(LinearLayout mHeadBack, TextView mLeft, TextView mTitle, TextView mRight) {
        super.initHead(mHeadBack, mLeft, mTitle, mRight);
        mTitle.setText("用户注册");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.register_btn_register:

                doRegister();
                break;
        }

    }

    private void doRegister() {
        final String userName = mEtUserName.getText().toString().trim();
        final String userPwd1 = mEtUserPwd1.getText().toString().trim();
        String userPwd2 = mEtUserPwd2.getText().toString().trim();
        //非空判断
        if (TextUtils.isEmpty(userName)) {
            show("用户名不能为空");
            return;
        }
        if (TextUtils.isEmpty(userPwd1)) {
            show("密码不能为空");
            return;
        }
        if (!userPwd1.equals(userPwd2)) {
            show("两次密码不一致,请重新输入");
            return;
        }

        Map<String, String> params = new HashMap<String, String>();
        params.put("userName", userName);
        params.put("pwd", userPwd1);
        if (mRbman.isChecked()) {
            params.put("sex", "男");
        } else {
            params.put("sex", "女");
        }
        sendPost(HttpUrlManage.Login.REGISTERUSER, params).execute(new MyStringCallBack(this) {
            @Override
            public void onSuccessResponse(String response) {
                //注册成功,执行登陆,去首页
                doLogin(userName, userPwd1);
            }


        });
    }

    private void doLogin(String userName, String userPwd1) {

        Map<String, String> params = new HashMap<String, String>();
        params.put("userName", userName);
        params.put("pwd", userPwd1);
        sendPost(HttpUrlManage.Login.Login, params).execute(new MyStringCallBack(this) {
            @Override
            public void onSuccessResponse(String response) {
                UserInfo userInfo = GsonQuick.toObject(response, UserInfo.class);
                BaseApplication.saveUserInfo(userInfo.getData());
                toActivity(MainActivity.class);
            }
        });

    }
}
