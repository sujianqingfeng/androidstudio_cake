package com.sujian.finalandroid.entity;

import java.util.List;

/**
 * 解析订单的实体
 * Created by sujian on 2016/6/25.
 * Mail:121116111@qq.com
 */
public class OrderInfoCallbackEntity {

    //订单数据集合
    private List<OrderInfo> list;
    //状态
    private boolean success;

    public List<OrderInfo> getList() {
        return list;
    }

    public void setList(List<OrderInfo> list) {
        this.list = list;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "OrderInfoCallbackEntity{" +
                "list=" + list +
                ", success=" + success +
                '}';
    }
}
