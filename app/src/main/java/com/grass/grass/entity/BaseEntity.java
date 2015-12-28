package com.grass.grass.entity;

import java.io.Serializable;

/**
 * Created by huchao on 2015/12/28.
 */
public class BaseEntity implements Serializable{

    private boolean isSuccess;
    private String message;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
