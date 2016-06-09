package com.sujian.finalandroid.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户实体类
 * Created by Admin on 2016/5/26.
 */
public class User {

    /**
     * 用户id
     */
    private long user_id;
    /**
     * 用户账号
     */
    private long user_acount;
    /**
     * 用户密码
     */
    private String user_password;

    /**
     * 用户性别
     */
    private String user_sex;
    /**
     * 用户头像
     */
    private String user_head;
    /**
     * 用户生日
     */
    private String user_birthday;
    /**
     * 用户家乡
     */
    private String user_hometown;
    /**
     * 用户所在地
     */
    private String user_seat;
    /**
     * 用户职业
     */
    private String user_occupation;

    @Override
    public String toString() {
        return "User [user_id=" + user_id + ", user_acount=" + user_acount
                + ", user_password=" + user_password + ", user_sex=" + user_sex
                + ", user_head=" + user_head + ", user_birthday="
                + user_birthday + ", user_hometown=" + user_hometown
                + ", user_seat=" + user_seat + ", user_occupation="
                + user_occupation + ", jsonRoot=" + jsonRoot + "]";
    }

    public String getUser_sex() {
        return user_sex;
    }

    public void setUser_sex(String user_sex) {
        this.user_sex = user_sex;
    }

    public String getUser_head() {
        return user_head;
    }

    public void setUser_head(String user_head) {
        this.user_head = user_head;
    }

    public String getUser_birthday() {
        return user_birthday;
    }

    public void setUser_birthday(String user_birthday) {
        this.user_birthday = user_birthday;
    }

    public String getUser_hometown() {
        return user_hometown;
    }

    public void setUser_hometown(String user_hometown) {
        this.user_hometown = user_hometown;
    }

    public String getUser_seat() {
        return user_seat;
    }

    public void setUser_seat(String user_seat) {
        this.user_seat = user_seat;
    }

    public String getUser_occupation() {
        return user_occupation;
    }

    public void setUser_occupation(String user_occupation) {
        this.user_occupation = user_occupation;
    }

    private Map<String, Object> jsonRoot = new HashMap<String, Object>();


    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getUser_acount() {
        return user_acount;
    }

    public void setUser_acount(long user_acount) {
        this.user_acount = user_acount;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }


    public Map<String, Object> getJsonRoot() {
        return jsonRoot;
    }

    public void setJsonRoot(Map<String, Object> jsonRoot) {
        this.jsonRoot = jsonRoot;
    }

}
