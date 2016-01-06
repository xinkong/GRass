package com.grass.grass.entity;

/**
 * Created by huchao on 2016/1/4.
 */
public class SimpleCommend {

    private int id;
    private int userId;
    private boolean isZan;
    private boolean isCai;

    public SimpleCommend(){}
    public SimpleCommend(int userId, boolean isCai, boolean isZan) {
        this.userId = userId;
        this.isCai = isCai;
        this.isZan = isZan;
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

    public boolean isZan() {
        return isZan;
    }

    public void setIsZan(boolean isZan) {
        this.isZan = isZan;
    }

    public boolean isCai() {
        return isCai;
    }

    public void setIsCai(boolean isCai) {
        this.isCai = isCai;
    }
}
