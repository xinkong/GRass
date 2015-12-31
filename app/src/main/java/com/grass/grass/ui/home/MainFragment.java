package com.grass.grass.ui.home;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.grass.grass.R;
import com.grass.grass.base.BaseGrassFragment;
import com.grass.grass.ui.home.selPic.utils.ImageLoader;

public class MainFragment extends BaseGrassFragment {

    private ImageView imageView;

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

        imageView = (ImageView) findViewById(R.id.imageview);
        //http://101.201.150.217:8888/Grass/msgImage/7d9d5c5c-2ba9-408a-b081-512d66ef40ca.png
        ImageLoader.getInstance().loadNetImage(getActivity(),"http://101.201.150.217:8888/Grass/msgImage/7d9d5c5c-2ba9-408a-b081-512d66ef40ca.png",imageView);
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
}
