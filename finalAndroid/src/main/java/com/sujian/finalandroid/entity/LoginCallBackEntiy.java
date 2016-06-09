package com.sujian.finalandroid.entity;

/**
 * Created by sujian on 2016/5/25.
 * Mail:121116111@qq.com
 * 登陆返回实体类
 */
public class LoginCallBackEntiy {

    private boolean success;
    private int resuleCode;
    private long id;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getResuleCode() {
        return resuleCode;
    }

    public void setResuleCode(int resuleCode) {
        this.resuleCode = resuleCode;
    }

    @Override
    public String toString() {
        return "LoginCallBackEntiy{" +
                "resuleCode=" + resuleCode +
                ", success=" + success +
                '}';
    }
}
