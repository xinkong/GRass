package com.grass.grass.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.grass.grass.R;
import com.grass.grass.base.BaseApplication;
import com.grass.grass.base.BaseGrassActivity;
import com.grass.grass.entity.ImageInfo;
import com.grass.grass.entity.MessageInfo;
import com.grass.grass.entity.SimpleCommend;
import com.grass.grass.ui.HttpUrlManage;
import com.grass.grass.utils.AppGlobal;
import com.grass.grass.utils.GrassBaseAdapter;
import com.grass.grass.utils.MyStringCallBack;
import com.grass.grass.utils.ViewHolder;
import com.grass.grass.view.MyNoScrollGridView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huchao on 2016/1/4.
 */
public class MessageAdapter extends GrassBaseAdapter<MessageInfo.Message> implements View.OnClickListener {

    private BaseGrassActivity baseActivity;

    public MessageAdapter(Context context) {
        super(context);
        this.baseActivity = (BaseGrassActivity) context;
    }

    @Override
    public int getItemResource() {
        return R.layout.item__message_content;
    }

    @Override
    public View getItemView(int position, View convertView) {

//        CircleImageView headPic = ViewHolder.get(convertView, R.id.item_msg_iv_headPic);
        SimpleDraweeView headPic = ViewHolder.get(convertView, R.id.item_msg_sd_headPic);
        TextView name = ViewHolder.get(convertView, R.id.item_msg_tv_name);

        TextView sex = ViewHolder.get(convertView, R.id.item_msg_tv_sex);
        TextView contetn = ViewHolder.get(convertView, R.id.item_msg_tv_content);
        TextView time = ViewHolder.get(convertView, R.id.item_msg_tv_time);
        RadioButton zan = ViewHolder.get(convertView, R.id.item_msg_rb_zan);
        MyNoScrollGridView imgs = ViewHolder.get(convertView, R.id.msg_gv_imgs);

        MessageInfo.Message message = mListData.get(position);

        headPic.setImageURI(Uri.parse(HttpUrlManage.baseHeadPic + message.getUser().getUserHeadPath()));
//        ImageLoader.getInstance().displayImage(HttpUrlManage.baseHeadPic + message.getUser().getUserHeadPath(),headPic,BaseApplication.getOptions());
        name.setText(message.getUser().getUserName());
        AppGlobal.fillerSexTextView(mContext,message.getUser().getSex(),sex);
//        sex.setText(message.getUser().getSex());
        contetn.setText(message.getMsgContent());
        time.setText(message.getMsgTime());
        List<SimpleCommend> simpleCommends = message.getSimpleCommends();
        for (SimpleCommend simpleComm : simpleCommends) {
            if (String.valueOf(simpleComm.getUserId()).equals(BaseApplication.getSpfinfo("userId")) && simpleComm.isZan()) {
//                zan.setChecked(true);
                message.setIsMyZan(true);
                break;
            }
        }

        zan.setText(simpleCommends.size() + "");
        //我是否点赞了该条消息
        if(message.isMyZan()){
            zan.setChecked(true);
        }else{
            zan.setChecked(false);
        }
        zan.setOnClickListener(this);
        zan.setTag(message);
        //设置图片
        MyImagsAdapter imgsAdapter = new MyImagsAdapter(mContext);
        imgsAdapter.setData(message.getImgs());
        imgs.setAdapter(imgsAdapter);
        return convertView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.item_msg_rb_zan:
                //判断是否是点赞状态
                final MessageInfo.Message message = (MessageInfo.Message) v.getTag();
                if(message.isMyZan()){
                    baseActivity.show("谢谢你的支持,多次点赞是不对的奥");
                }else {
                    Map<String,String> params = new HashMap<String,String>();
                    params.put("userId",BaseApplication.getSpfinfo("userId"));
                    params.put("msgId",message.getMsgId()+"");
                    baseActivity.sendPost(HttpUrlManage.Msg.zan,params).execute(new MyStringCallBack(mContext) {
                        @Override
                        public void onSuccessResponse(String response) {
                            message.setIsMyZan(true);
                            message.getSimpleCommends().add(new SimpleCommend(Integer.valueOf(BaseApplication.getSpfinfo("userId")),true,false));
                            baseActivity.show("谢谢你的支持^-^");
                            MessageAdapter.this.notifyDataSetChanged();
                        }
                    });
                }
                break;

        }
    }

    class MyImagsAdapter extends GrassBaseAdapter<ImageInfo> {

        public MyImagsAdapter(Context context) {
            super(context);
        }

        @Override
        public int getItemResource() {
            return R.layout.item_msg_image;
        }

        @Override
        public View getItemView(int position, View convertView) {
            SimpleDraweeView imgs = ViewHolder.get(convertView, R.id.item_msgimg_iv_img);
            ImageInfo imageInfo = mListData.get(position);
            imgs.setImageURI(Uri.parse(HttpUrlManage.baseImagePic +imageInfo.getImgUrl()));
//            ImageLoader.getInstance().displayImage(HttpUrlManage.baseImagePic + imageInfo.getImgUrl(), imgs, BaseApplication.getOptions());
            return convertView;
        }
    }

}
