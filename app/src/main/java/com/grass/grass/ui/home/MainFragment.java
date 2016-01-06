package com.grass.grass.ui.home;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.grass.grass.R;
import com.grass.grass.adapter.MessageAdapter;
import com.grass.grass.base.BaseApplication;
import com.grass.grass.base.BaseGrassFragment;
import com.grass.grass.entity.MessageInfo;
import com.grass.grass.ui.HttpUrlManage;
import com.grass.grass.utils.GsonQuick;
import com.grass.grass.utils.LogUtils;
import com.grass.grass.utils.MyStringCallBack;
import com.grass.grass.view.RefreshLayout;

import java.util.HashMap;
import java.util.Map;

public class MainFragment extends BaseGrassFragment {

    private ListView mLvAllMeg;
    private MessageAdapter mMsgAdapter;
    private RefreshLayout mUploadView;
    private int pageNo = 0;
    private int pageSize = 5;
    private String updata = "updata";
    private String add = "add";

    @Override
    public View onInflaterView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_message, null);
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

        getData(pageNo, updata);

    }

    private void initView() {

        mUploadView = (RefreshLayout) findViewById(R.id.msg_rl_uploadView);
//        mUploadView.setColorSchemeResources(R.color.colorAccent,
//                R.color.backcard_color, R.color.business_color,
//                R.color.pull_color);
        mUploadView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNo = 0;
                getData(pageNo, updata);
            }
        });
        mUploadView.setOnLoadListener(new RefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                pageNo = pageNo + pageSize;
                getData(pageNo, add);
            }
        });

        mLvAllMeg = (ListView) findViewById(R.id.msg_lv_allMsg);
        mMsgAdapter = new MessageAdapter(getActivity());
        mLvAllMeg.setDividerHeight(0);
        mLvAllMeg.setAdapter(mMsgAdapter);
        LogUtils.i("用户ID:" + BaseApplication.getSpfinfo("userId"));

    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.head_tv_right:
                toActivity(getActivity(), SendMessageActivity.class);
                break;
        }
    }

    public void getData(int size, final String type) {

        Map<String, String> params = new HashMap<String, String>();
        params.put("pageNo", size + "");
        sendPost(HttpUrlManage.Msg.getAllMsgInfo, params).execute(new MyStringCallBack(getActivity()) {
            @Override
            public void onSuccessResponse(String response) {
                MessageInfo msgInfo = GsonQuick.toObject(response, MessageInfo.class);
                if (add.equals(type)) {
                    mMsgAdapter.addAll(msgInfo.getData());
                    mUploadView.setLoading(false);
                } else {
                    mMsgAdapter.clear();
                    mMsgAdapter.addAll(msgInfo.getData());
                    mUploadView.setRefreshing(false);
                }

            }
        });

    }

}
