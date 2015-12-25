package com.grass.grass.base;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.grass.grass.R;

/**
 * Created by huchao on 2015/12/23.
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    private LinearLayout ll_headView;
    private TextView tv_left;
    private TextView tv_title;
    private TextView tv_right;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return onInflaterView(inflater);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        findHeadView();
        initHeadView(ll_headView, tv_left, tv_title, tv_right);
    }

    private void findHeadView(){
        ll_headView = (LinearLayout) findViewById(R.id.base_headview);
        tv_left = (TextView) findViewById(R.id.head_tv_left);
        tv_title = (TextView) findViewById(R.id.head_tv_title);
        tv_right = (TextView) findViewById(R.id.head_tv_right);
        tv_left.setOnClickListener(this);
    }

    protected  void initHeadView(LinearLayout ll_headView, TextView tv_left, TextView tv_title, TextView tv_right){

    }

    /**
     * fragment To activity
     **/
    public void toActivity(Activity activity, Class<?> toClsActivity) {
        Intent intent = new Intent(activity, toClsActivity);
        startActivity(intent);
    }

    public void toActivity(Class<?> toClsActivity, Bundle bundle) {
        Intent intent = new Intent(mContext, toClsActivity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void show(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 功能:加载布局文件
     *
     * @param inflater
     * @return
     * @author: huchao
     * @date:2015-10-27上午9:12:54
     */
    public abstract View onInflaterView(LayoutInflater inflater);

    /**
     * 功能:activity 公共方法
     *
     * @param id
     * @return
     * @author: huchao
     * @date:2015-5-14上午9:09:58
     */
    public View findViewById(int id) {
        return getView().findViewById(id);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.head_tv_left){
            getActivity().finish();
        }
    }
}
