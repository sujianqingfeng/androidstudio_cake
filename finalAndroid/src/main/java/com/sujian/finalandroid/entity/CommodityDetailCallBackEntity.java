package com.sujian.finalandroid.entity;

/**
 * 商品详细实体类
 * Created by sujian on 2016/6/12.
 * Mail:121116111@qq.com
 */
public class CommodityDetailCallBackEntity {
    private Commodity commodity;
    private boolean success;


    @Override
    public String toString() {
        return "CommodityDetailCallBackEntity{" +
                "commodity=" + commodity +
                ", success='" + success + '\'' +
                '}';
    }

    public Commodity getCommodity() {
        return commodity;
    }

    public void setCommodity(Commodity commodity) {
        this.commodity = commodity;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
