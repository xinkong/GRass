package com.grass.grass.entity;

/**
 * Created by huchao on 2015/12/28.
 */
public class UserInfo extends BaseEntity{

    private User data;

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }

    public class  User{
        private int userId;
        private String userName;
        private String pwd;
        private String sex;
        private String userHeadPath;


        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPwd() {
            return pwd;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getUserHeadPath() {
            return userHeadPath;
        }

        public void setUserHeadPath(String userHeadPath) {
            this.userHeadPath = userHeadPath;
        }
    }
}
