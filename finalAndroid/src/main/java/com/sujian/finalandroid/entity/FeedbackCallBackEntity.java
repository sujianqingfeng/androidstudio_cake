package com.sujian.finalandroid.entity;

/**
 * Created by Administrator on 2016/6/26/026.
 */
public class FeedbackCallBackEntity {
    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "FeedbackCallBackEntity{" +
                "success=" + success +
                '}';
    }
}
