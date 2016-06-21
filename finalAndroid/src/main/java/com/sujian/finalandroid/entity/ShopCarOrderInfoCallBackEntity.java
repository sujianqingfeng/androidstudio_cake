package com.sujian.finalandroid.entity;

import java.util.List;

/**
 * Created by sujian on 2016/6/15.
 * Mail:121116111@qq.com
 */
public class ShopCarOrderInfoCallBackEntity {

    private boolean success;
    private List<ShopCarOrderInfo> shopCarOrderInfo;

    public List<ShopCarOrderInfo> getShopCarOrderInfo() {
        return shopCarOrderInfo;
    }

    public void setShopCarOrderInfo(List<ShopCarOrderInfo> shopCarOrderInfo) {
        this.shopCarOrderInfo = shopCarOrderInfo;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "ShopCarOrderInfoCallBackEntity{" +
                "shopCarOrderInfo=" + shopCarOrderInfo +
                ", success=" + success +
                '}';
    }
}
