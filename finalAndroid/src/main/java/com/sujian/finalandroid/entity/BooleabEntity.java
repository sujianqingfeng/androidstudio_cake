package com.sujian.finalandroid.entity;

/**
 * Created by sujian on 2016/6/20.
 * Mail:121116111@qq.com
 */
public class BooleabEntity {
    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "BooleabEntity{" +
                "success=" + success +
                '}';
    }
}
