package com.grass.grass.ui;

/**
 * Created by huchao on 2015/12/22.
 */
public class HttpUrlManage {

    /**
     * 基础url
     */
    private static String BASEURL = "http://101.201.150.217:8888/Grass/";//阿里
//    private static String BASEURL = "http://192.168.0.100:8080/Grass/";//本地


    public interface Login {
        /**用户登陆*/
        String Login = BASEURL + "userLogin";
        /**用户注册*/
        String REGISTERUSER = BASEURL + "registerUser";
    }

    public interface Msg{
        /**发送带图片的消息*/
        String sendPicMsg = BASEURL + "sendMsgHavePic";
        /**发送无图片消息*/
        String sendMsgNoPic = BASEURL + "sendMsgNoPic";

        /**得到所有的消息*/
        String getAllMsgInfo = BASEURL + "getAllMsgInfo";
    }

}
