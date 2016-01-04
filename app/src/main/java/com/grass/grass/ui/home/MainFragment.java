package com.grass.grass.ui.home;


import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.grass.grass.R;
import com.grass.grass.adapter.MessageAdapter;
import com.grass.grass.base.BaseApplication;
import com.grass.grass.base.BaseGrassFragment;
import com.grass.grass.entity.MessageInfo;
import com.grass.grass.ui.HttpUrlManage;
import com.grass.grass.utils.GsonQuick;
import com.grass.grass.utils.LogUtils;
import com.grass.grass.utils.MyStringCallBack;

import java.util.HashMap;
import java.util.Map;

public class MainFragment extends BaseGrassFragment {

    private ListView mLvAllMeg;
    private MessageAdapter mMsgAdapter;
    private SimpleDraweeView mSimple;

    @Override
    public View onInflaterView(LayoutInflater inflater) {
        View view =inflater.inflate(R.layout.fragment_message,null);
        return view;
    }

    @Override
    protected void initHeadView(LinearLayout ll_headView, TextView tv_left, TextView tv_title, TextView tv_right) {
        super.initHeadView(ll_headView, tv_left, tv_title, tv_right);
        tv_title.setText("首页");
        tv_right.setVisibility(View.VISIBLE);
        addRightIcon(R.mipmap.home_add_msg);
        tv_right.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();

        getData();

    }

    private void initView() {

        mLvAllMeg = (ListView) findViewById(R.id.msg_lv_allMsg);
//        mMsgAdapter = new MessageAdapter(getActivity());
//        mLvAllMeg.setDividerHeight(0);
//        mLvAllMeg.setAdapter(mMsgAdapter);
        LogUtils.i("用户ID:" + BaseApplication.getSpfinfo("userId"));

        mSimple = (SimpleDraweeView) findViewById(R.id.simple_img);
        mSimple.setImageURI(Uri.parse("http://101.201.150.217:8888/Grass/msgImage/d69d4b48-db1b-4420-b6f8-4e87a39eb876.png"));

//        mSimple.setHierarchy(BaseApplication.getHierarchy());

    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.head_tv_right:
                toActivity(getActivity(),SendMessageActivity.class);
                break;
        }
    }

    public void getData() {

        Map<String,String> params = new HashMap<String,String>();
        params.put("pageNo","0");

        sendPost(HttpUrlManage.Msg.getAllMsgInfo, params).execute(new MyStringCallBack(getActivity()) {
            @Override
            public void onSuccessResponse(String response) {
                MessageInfo msgInfo = GsonQuick.toObject(response, MessageInfo.class);
//                mMsgAdapter.setData(msgInfo.getData());
            }
        });

    }
}
