package com.sujian.finalandroid.entity;


import java.util.HashMap;
import java.util.Map;


/**
 * 消息实体类
 *
 * @author 一 直 灬 メ 不 回 头  453485453@QQ.COM
 * @ClassName: Message
 * @Description: TODO(描述)
 * @date 2016年6月22日 下午8:27:55
 */
public class Message {
    /**
     * 消息id
     */
    private long message_id;
    /**
     * 用户id
     */
    private long user_id;
    /**
     * 消息状态
     */
    private int message_status;
    /**
     * 消息标题
     */
    private String message_title;
    /**
     * 消息内容
     */
    private String message_content;


    @Override
    public String toString() {
        return "Message [message_id=" + message_id + ", user_id=" + user_id
                + ", message_status=" + message_status + ", message_title="
                + message_title + ", message_content=" + message_content
                + ", jsonRoot=" + jsonRoot + "]";
    }

    public long getMessage_id() {
        return message_id;
    }

    public void setMessage_id(long message_id) {
        this.message_id = message_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public float getMessage_status() {
        return message_status;
    }


    public String getMessage_title() {
        return message_title;
    }

    public void setMessage_title(String message_title) {
        this.message_title = message_title;
    }

    public String getMessage_content() {
        return message_content;
    }

    public void setMessage_content(String message_content) {
        this.message_content = message_content;
    }

    public void setMessage_status(int message_status) {
        this.message_status = message_status;
    }

    public Map<String, Object> getJsonRoot() {
        return jsonRoot;
    }

    public void setJsonRoot(Map<String, Object> jsonRoot) {
        this.jsonRoot = jsonRoot;
    }

    private Map<String, Object> jsonRoot = new HashMap<String, Object>();


}
