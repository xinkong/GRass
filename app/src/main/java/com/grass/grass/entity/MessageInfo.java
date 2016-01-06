package com.grass.grass.entity;

import java.util.List;

/**
 * Created by huchao on 2016/1/4.
 */
public class MessageInfo extends BaseEntity{

    private List<Message> data;

    public List<Message> getData() {
        return data;
    }

    public void setData(List<Message> data) {
        this.data = data;
    }

    public class Message{
        private int msgId;
        private String msgTime;
        private String msgContent;
        private UserInfo.User user;
        private List<ImageInfo> imgs;
        private List<SimpleCommend> simpleCommends;
        private List<Commend> commends;
        private boolean isMyZan = false;


        public boolean isMyZan() {
            return isMyZan;
        }

        public void setIsMyZan(boolean isMyZan) {
            this.isMyZan = isMyZan;
        }

        public int getMsgId() {
            return msgId;
        }

        public void setMsgId(int msgId) {
            this.msgId = msgId;
        }

        public String getMsgTime() {
            return msgTime;
        }

        public void setMsgTime(String msgTime) {
            this.msgTime = msgTime;
        }

        public String getMsgContent() {
            return msgContent;
        }

        public void setMsgContent(String msgContent) {
            this.msgContent = msgContent;
        }

        public UserInfo.User getUser() {
            return user;
        }

        public void setUser(UserInfo.User user) {
            this.user = user;
        }

        public List<ImageInfo> getImgs() {
            return imgs;
        }

        public void setImgs(List<ImageInfo> imgs) {
            this.imgs = imgs;
        }

        public List<SimpleCommend> getSimpleCommends() {
            return simpleCommends;
        }

        public void setSimpleCommends(List<SimpleCommend> simpleCommends) {
            this.simpleCommends = simpleCommends;
        }

        public List<Commend> getCommends() {
            return commends;
        }

        public void setCommends(List<Commend> commends) {
            this.commends = commends;
        }

    }
}
