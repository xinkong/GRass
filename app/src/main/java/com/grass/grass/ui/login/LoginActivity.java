package com.grass.grass.ui.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

public class LoginActivity extends BaseGrassActivity{

    private EditText mEtUserName, mEtUserPwd;
    private TextView mTvUserRegister, mTvUserForgetPwd;
    private Button mBtnLogin;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
    }

    private void initView() {
        mEtUserName = (EditText) findViewById(R.id.login_et_userName);
        mEtUserPwd = (EditText) findViewById(R.id.login_et_userPwd);

        mTvUserForgetPwd = (TextView) findViewById(R.id.login_tv_userForgetPwd);
        mTvUserForgetPwd.setOnClickListener(this);
        mTvUserRegister = (TextView) findViewById(R.id.login_tv_userRegister);
        mTvUserRegister.setOnClickListener(this);

        mBtnLogin = (Button) findViewById(R.id.login_btn_login);
        mBtnLogin.setOnClickListener(this);
    }

    @Override
    protected void initHead(LinearLayout mHeadBack, TextView mLeft, TextView mTitle, TextView mRight) {
        super.initHead(mHeadBack, mLeft, mTitle, mRight);
        mLeft.setVisibility(View.INVISIBLE);
        mLeft.setClickable(false);
        mTitle.setText("登录");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.login_tv_userRegister:
                toActivity(RegisterActivity.class);
                break;
            case R.id.login_tv_userForgetPwd:
                show("该应用无法验证用户信息,无法找回密码");
                break;
            case R.id.login_btn_login:
                doLogin();
                break;
        }
    }

    private void doLogin() {
        String userName = mEtUserName.getText().toString().trim();
        String userPwd = mEtUserPwd.getText().toString().trim();
        Map<String,String> params = new HashMap<String,String>();
        params.put("userName",userName);
        params.put("pwd",userPwd);
        sendPost(HttpUrlManage.Login.Login,params).execute(new MyStringCallBack(this) {
            @Override
            public void onSuccessResponse(String response) {
                UserInfo userInfo = GsonQuick.toObject(response,UserInfo.class);
                if(userInfo.isSuccess()){
                    //保存用户信息
                    BaseApplication.saveUserInfo(userInfo.getData());
                    toActivity(MainActivity.class);
                }else {
                    show(userInfo.getMessage());
                }
            }
        });
    }
}
