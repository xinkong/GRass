package com.grass.grass.ui;

/**
 * Created by huchao on 2015/12/22.
 */
public class HttpUrlManage {

    /**
     * 基础url
     */
    private static String BASEURL = "http://192.168.0.100:8080/Grass/";

    public interface Login {
        /**用户登陆*/
        String Login = BASEURL + "userLogin";
    }

    public interface Msg{
        /**发送带图片的消息*/
        String sendPicMsg = BASEURL + "sendMsgHavePic";
    }

}
