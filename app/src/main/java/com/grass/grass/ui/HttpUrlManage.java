package com.grass.grass.ui;

/**
 * Created by huchao on 2015/12/22.
 */
public class HttpUrlManage {

    /**
     * 基础url
     */
    private static String BASEURL = "http://101.201.150.217:8888/Grass/";

    public interface Login {
        /**用户登陆*/
        String Login = BASEURL + "userLogin";
        /**用户注册*/
        String REGISTERUSER = BASEURL + "registerUser";
    }

    public interface Msg{
        /**发送带图片的消息*/
        String sendPicMsg = BASEURL + "sendMsgHavePic";
    }

}
