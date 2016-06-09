package com.sujian.finalandroid.entity;

import java.util.List;

/**
 * Created by Admin on 2016/6/2.
 * 主页返回实体类
 */
public class CommodityKindCalBackEntity {
    private boolean success;
    private HomeObject homelist;
    private CommodityKind kindlist;

    @Override
    public String toString() {
        return "CommodityKindCalBackEntity{" +
                "success=" + success +
                ", homelist=" + homelist +
                ", kindlist=" + kindlist +
                '}';
    }

    public CommodityKind getKindlist() {
        return kindlist;
    }

    public void setKindlist(CommodityKind kindlist) {
        this.kindlist = kindlist;
    }

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


}
