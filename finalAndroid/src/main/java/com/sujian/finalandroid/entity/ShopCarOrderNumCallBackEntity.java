package com.sujian.finalandroid.entity;

/**
 * 订单数量实体
 * Created by sujian on 2016/6/14.
 * Mail:121116111@qq.com
 */
public class ShopCarOrderNumCallBackEntity {
    private boolean success;
    private boolean isZero;
    private int num;


    public boolean isZero() {
        return isZero;
    }

    public void setZero(boolean zero) {
        isZero = zero;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "ShopCarOrderNumCallBackEntity{" +
                "isZero=" + isZero +
                ", success=" + success +
                ", num=" + num +
                '}';
    }
}
