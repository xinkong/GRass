package com.grass.grass.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.grass.grass.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.Map;

/**
 *
 */
public abstract class BaseActivity extends Activity implements View.OnClickListener {

    /**
     * 顶部返回
     */
    private TextView mLeft;
    /**
     * 标题
     */
    private TextView mTitle;
    /**
     * 顶部右侧操作
     */
    private TextView mRight;

    /**
     * 头部背景
     */
    private LinearLayout mHeadBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutResId());

        initHeadView();

        initHead(mHeadBack, mLeft, mTitle, mRight);
    }


    /**
     * 初始化头部控件
     */
    protected void initHeadView() {
        mLeft = (TextView) findViewById(R.id.head_tv_left);
        mTitle = (TextView) findViewById(R.id.head_tv_title);
        mRight = (TextView) findViewById(R.id.head_tv_right);

        if (mLeft != null) {
            mLeft.setOnClickListener(this);
        }
    }

    /**
     * 设置头部控件属性
     *
     * @param mHeadBack
     * @param mLeft
     * @param mTitle
     * @param mRight
     */
    protected void initHead(LinearLayout mHeadBack, TextView mLeft, TextView mTitle, TextView mRight) {

    }

    public void toActivity(Class<?> toClsActivity) {
        toActivity(toClsActivity, null);
    }

    public void toActivity(Class<?> toClsActivity, Bundle bundle) {
        Intent intent = new Intent(this, toClsActivity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }


    /**
     * 界面弹出提示消息
     * @param msg
     */
    protected void show(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_tv_left:
                onBackPressed();
                break;
        }
    }

    public abstract int getLayoutResId();

    /**
     * 发送post请求
     * @param url
     * @param params
     */
    public RequestCall sendPost(String url,Map<String,String> params){

        PostFormBuilder posetRequset = OkHttpUtils.post();
        posetRequset.url(url);
        //迭代Map
        if(params!=null){
            for(String key :params.keySet()){
                posetRequset.addParams(key,params.get(key));
            }
        }
       return posetRequset.build();
    }
}
