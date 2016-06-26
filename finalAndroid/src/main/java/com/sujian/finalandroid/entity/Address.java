package com.sujian.finalandroid.entity;


/**
 * 地址表的实体类
 *
 * @author 12111
 */
public class Address {
    private int address_id;
    private int user_id;
    private String address_name;
    private int address_sex;
    private long address_phone;
    private String address_city;
    private String address_content;
    private int ischeck;

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getIscheck() {
        return ischeck;
    }

    public void setIscheck(int ischeck) {
        this.ischeck = ischeck;
    }

    public String getAddress_name() {
        return address_name;
    }

    public void setAddress_name(String address_name) {
        this.address_name = address_name;
    }

    public int getAddress_sex() {
        return address_sex;
    }

    public void setAddress_sex(int address_sex) {
        this.address_sex = address_sex;
    }

    public long getAddress_phone() {
        return address_phone;
    }

    public void setAddress_phone(long address_phone) {
        this.address_phone = address_phone;
    }

    public String getAddress_city() {
        return address_city;
    }

    public void setAddress_city(String address_city) {
        this.address_city = address_city;
    }

    public String getAddress_content() {
        return address_content;
    }

    public void setAddress_content(String address_content) {
        this.address_content = address_content;
    }

    @Override
    public String toString() {
        return "Address [address_id=" + address_id + ", user_id=" + user_id
                + ", address_name=" + address_name + ", address_sex="
                + address_sex + ", address_phone=" + address_phone
                + ", address_city=" + address_city + ", address_content="
                + address_content + ", ischeck=" + ischeck + "]";
    }


}