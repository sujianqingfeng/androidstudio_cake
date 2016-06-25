package com.sujian.finalandroid.entity;

import java.util.Date;

/**
 * 返回订单的数据
 *
 * @author 12111
 */
public class OrderInfo {
    //订单的时间
    private String order_time;
    //商品的数量
    private int commodity_num;
    //商品的价格
    private float commodity_price;
    //订单的状态
    private int order_state;
    //商品的名字
    private String commodity_name;
    //商品的描述图片地址
    private String pic_url;


    public String getCommodity_name() {
        return commodity_name;
    }

    public void setCommodity_name(String commodity_name) {
        this.commodity_name = commodity_name;
    }

    public int getCommodity_num() {
        return commodity_num;
    }

    public void setCommodity_num(int commodity_num) {
        this.commodity_num = commodity_num;
    }

    public float getCommodity_price() {
        return commodity_price;
    }

    public void setCommodity_price(float commodity_price) {
        this.commodity_price = commodity_price;
    }

    public int getOrder_state() {
        return order_state;
    }

    public void setOrder_state(int order_state) {
        this.order_state = order_state;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    @Override
    public String toString() {
        return "OrderInfo{" +
                "commodity_name='" + commodity_name + '\'' +
                ", order_time='" + order_time + '\'' +
                ", commodity_num=" + commodity_num +
                ", commodity_price=" + commodity_price +
                ", order_state=" + order_state +
                ", pic_url='" + pic_url + '\'' +
                '}';
    }
}
