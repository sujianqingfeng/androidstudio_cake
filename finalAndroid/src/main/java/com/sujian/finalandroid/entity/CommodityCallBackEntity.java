package com.sujian.finalandroid.entity;


import java.util.List;

/**
 * 商品列表返回实体类
 */
public class CommodityCallBackEntity {
    private boolean success;
    private List<Commodity> list;

    @Override
    public String toString() {
        return "CommodityCallBackEntity{" +
                "success=" + success +
                ", list=" + list +
                '}';
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Commodity> getList() {
        return list;
    }

    public void setList(List<Commodity> list) {
        this.list = list;
    }
}
