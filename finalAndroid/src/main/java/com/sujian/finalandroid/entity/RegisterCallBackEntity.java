package com.sujian.finalandroid.entity;

/**
 * Created by sujian on 2016/5/25.
 * Mail:121116111@qq.com
 * 注册返回实体类
 */
public class RegisterCallBackEntity {
    private int resuleCode;
    private boolean success;

    public void setResuleCode(int resuleCode) {
        this.resuleCode = resuleCode;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getResuleCode() {
        return resuleCode;
    }

    public boolean getSuccess() {
        return success;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
