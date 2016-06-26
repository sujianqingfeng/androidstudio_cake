package com.sujian.finalandroid.entity;

/**
 * 反馈实体类
 */
public class Feedback {

    private int feedback_id;
    private User user;

    private String feedback_content;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getFeedback_id() {
        return feedback_id;
    }

    public void setFeedback_id(int feedback_id) {
        this.feedback_id = feedback_id;
    }


    public String getFeedback_content() {
        return feedback_content;
    }

    public void setFeedback_content(String feedback_content) {
        this.feedback_content = feedback_content;
    }

    @Override
    public String toString() {
        return "Feedback [feedback_id=" + feedback_id + ", user=" + user
                + ", feedvback_content=" + feedback_content + "]";
    }


}

