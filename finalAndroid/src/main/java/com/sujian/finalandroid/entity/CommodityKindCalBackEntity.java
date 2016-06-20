package com.sujian.finalandroid.entity;

import java.util.List;

/**
 * Created by Admin on 2016/6/2.
 * 主页返回实体类
 */
public class CommodityKindCalBackEntity {
    private boolean success;
    private HomeObject homelist;


    public HomeObject getHomelist() {
        return homelist;
    }

    public void setHomelist(HomeObject homelist) {
        this.homelist = homelist;
    }


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "CommodityKindCalBackEntity{" +
                "homelist=" + homelist +
                ", success=" + success +
                '}';
    }
}
