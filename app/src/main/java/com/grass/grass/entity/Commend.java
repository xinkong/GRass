package com.grass.grass.entity;

/**
 * Created by huchao on 2016/1/4.
 */
public class Commend {

    private int id;
    private int userId;
    private String commendTime;
    private String commendContent;

    public String getCommendContent() {
        return commendContent;
    }

    public void setCommendContent(String commendContent) {
        this.commendContent = commendContent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCommendTime() {
        return commendTime;
    }

    public void setCommendTime(String commendTime) {
        this.commendTime = commendTime;
    }
}
