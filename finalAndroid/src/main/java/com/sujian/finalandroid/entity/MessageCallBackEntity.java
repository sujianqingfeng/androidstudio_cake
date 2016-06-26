package com.sujian.finalandroid.entity;


import java.util.List;

/**
 * 用户消息返回实体类
 */
public class MessageCallBackEntity {
    private boolean success;
    private List<Message> message;

    @Override
    public String toString() {
        return "MessageCallBackEntity{" +
                "success=" + success +
                ", message=" + message +
                '}';
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Message> getMessage() {
        return message;
    }

    public void setMessage(List<Message> message) {
        this.message = message;
    }
}
