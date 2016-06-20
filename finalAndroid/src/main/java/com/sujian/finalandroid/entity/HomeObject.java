package com.sujian.finalandroid.entity;

import java.util.List;

/**
 * 主页实体类
 * Created by Admin on 2016/6/2.
 */
public class HomeObject {
    List<Commodity> headList;
    List<Commodity> gridList;
    List<CommodityKind> kindList;

    @Override
    public String toString() {
        return "HomeObject{" +
                "headList=" + headList +
                ", gridList=" + gridList +
                ", KindList=" + kindList +
                '}';
    }

    public List<Commodity> getHeadList() {
        return headList;
    }

    public void setHeadList(List<Commodity> headList) {
        this.headList = headList;
    }

    public List<Commodity> getGridList() {
        return gridList;
    }

    public void setGridList(List<Commodity> gridList) {
        this.gridList = gridList;
    }

    public List<CommodityKind> getKindList() {
        return kindList;
    }

    public void setKindList(List<CommodityKind> kindList) {
        this.kindList = kindList;
    }

}
