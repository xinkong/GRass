package com.grass.grass.ui.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.grass.grass.R;
import com.grass.grass.base.BaseApplication;
import com.grass.grass.base.BaseGrassActivity;
import com.grass.grass.entity.BaseEntity;
import com.grass.grass.ui.HttpUrlManage;
import com.grass.grass.ui.home.selPic.imageloader.ShowPhonePicActivity;
import com.grass.grass.ui.home.selPic.utils.ImageLoader;
import com.grass.grass.utils.AppGlobal;
import com.grass.grass.utils.CompressImage;
import com.grass.grass.utils.DensityUtil;
import com.grass.grass.utils.GsonQuick;
import com.grass.grass.utils.LoadingDialog;
import com.grass.grass.utils.LogUtils;
import com.grass.grass.utils.MyStringCallBack;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SendMessageActivity extends BaseGrassActivity {

    private EditText mEtMsgContent;
    private LinearLayout mllImgs;
    private ImageView mIvAddPic;
    public static final int requestCode = 1;
    private ArrayList<String> mSelPic = new ArrayList<String>();
    private ArrayList<String> returnPic = new ArrayList<String>();
    private ArrayList<String> tempImages = new ArrayList<String>();

    private LoadingDialog dialog;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_send_message;
    }

    @Override
    protected void initHead(LinearLayout mHeadBack, TextView mLeft, TextView mTitle, TextView mRight) {
        super.initHead(mHeadBack, mLeft, mTitle, mRight);
        mTitle.setText("发送消息");
        mRight.setVisibility(View.VISIBLE);
        mRight.setText("发送");
        mRight.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
    }

    private void initView() {
        dialog = new LoadingDialog(this,"正在提交");

        mEtMsgContent = (EditText) findViewById(R.id.sendmsg_et_content);
        mllImgs = (LinearLayout) findViewById(R.id.sendMsg_ll_imgs);

        mIvAddPic = (ImageView) findViewById(R.id.sendmsg_iv_addPic);
        mIvAddPic.setOnClickListener(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == resultCode && data!=null){
            mSelPic.addAll(data.getStringArrayListExtra("selPics"));
            returnPic = data.getStringArrayListExtra("selPics");

            showSelPic();
        }
    }

    private void showSelPic() {
        for(String s : returnPic){
            ImageView selPic = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(SendMessageActivity.this, 100),DensityUtil.dip2px(SendMessageActivity.this,100));
            selPic.setLayoutParams(params);
            ImageLoader.getInstance().loadImage(s, selPic);
            mllImgs.addView(selPic,mllImgs.getChildCount()-1);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.sendmsg_iv_addPic:
                //打开相册选图片
                startActivityForResult(new Intent(this, ShowPhonePicActivity.class),requestCode);
                break;
            case R.id.head_tv_right:
                final String content = mEtMsgContent.getText().toString();
                if(mSelPic.size() == 0){
                    if(TextUtils.isEmpty(content)){
                        show("说点什么吧");
                        return;
                    }
                    sendNoPicMsg(content);
                }else{
                    dialog.show();
                    new Thread(){
                        @Override
                        public void run() {
                            sendHavePicMsg(content);
                        }
                    }.start();

                }
                break;
        }
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1 ){
                dialog.dissmisDialog();
                for(String s :tempImages){
                    File f = new File(s);
                    if(f.exists()){
                        f.delete();
                    }
                }
            }else if (msg.what ==2 ){
                dialog.dissmisDialog();
                String response = (String) msg.obj;
                LogUtils.i(response);
                BaseEntity b = GsonQuick.toObject(response, BaseEntity.class);
                for(String s :tempImages){
                    File f = new File(s);
                    if(f.exists()){
                        f.delete();
                    }
                }
                if (b.isSuccess()) {
                    SendMessageActivity.this.finish();
                } else {
                    show(b.getMessage());
                }
            }
        }
    };

    private void sendHavePicMsg(String content) {
        PostFormBuilder postFormBu = OkHttpUtils.post().url(HttpUrlManage.Msg.sendPicMsg);

        for (int i = 0; i < mSelPic.size(); i++) {
            commpressImage(mSelPic.get(i), postFormBu);
//            postFormBu.addFile("file", "name" + i + mSelPic.get(i).substring(mSelPic.get(i).lastIndexOf("."), mSelPic.get(i).length()), new File(mSelPic.get(i)));
        }
        postFormBu.addParams("msgContent", content+"")
                .addParams("userId", BaseApplication.getSpfinfo("userId")).build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Request request, Exception e) {
                        handler.sendEmptyMessage(1);
                    }

                    @Override
                    public void onResponse(String response) {
                        Message msg = Message.obtain();
                        msg.what = 2;
                        msg.obj = response;
                        handler.sendMessage(msg);
                    }
                });


    }

    private void sendNoPicMsg(String content) {

        dialog.show();
        Map<String,String> params = new HashMap<>();
        params.put("userId", BaseApplication.getSpfinfo("userId"));
        params.put("msgContent", content);
        sendPost(HttpUrlManage.Msg.sendMsgNoPic, params).execute(new MyStringCallBack(this) {
            @Override
            public void onSuccessResponse(String response) {
                dialog.dissmisDialog();
                SendMessageActivity.this.finish();
            }
        });
    }


    private void commpressImage(String path,PostFormBuilder postFormBu) {
        Bitmap bitmap = CompressImage.getBitmap(path);
        Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
        int quality = 100;
        OutputStream stream = null;
        String tmpPath = null;
        try {
            tmpPath = AppGlobal.getCacheRoot(this) + File.separator + UUID.randomUUID() + ".png";
            stream = new FileOutputStream(tmpPath);
            bitmap.compress(format, quality, stream);
            // 上传图片
            File file = new File(tmpPath);
            postFormBu.addFile("file", tmpPath.substring(tmpPath.lastIndexOf("."), tmpPath.length()), file);
            tempImages.add(tmpPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
