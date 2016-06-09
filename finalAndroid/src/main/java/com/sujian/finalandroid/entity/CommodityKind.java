package com.sujian.finalandroid.entity;

import java.util.List;

/**
 * Created by Admin on 2016/6/2.
 * 主页商品分类实体类
 */
public class CommodityKind {
    private String activityUrl;
    private String toppicurl;
    private List<Commodity> commodityList;

    @Override
    public String toString() {
        return "CommodityKind{" +
                "activityUrl='" + activityUrl + '\'' +
                ", toppicurl='" + toppicurl + '\'' +
                ", commodityList=" + commodityList +
                '}';
    }

    public String getActivityUrl() {
        return activityUrl;
    }

    public void setActivityUrl(String activityUrl) {
        this.activityUrl = activityUrl;
    }

    public String getToppicurl() {
        return toppicurl;
    }

    public void setToppicurl(String toppicurl) {
        this.toppicurl = toppicurl;
    }

    public List<Commodity> getCommodityList() {
        return commodityList;
    }

    public void setCommodityList(List<Commodity> commodityList) {
        this.commodityList = commodityList;
    }

}
