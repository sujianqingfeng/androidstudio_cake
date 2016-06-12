package com.sujian.finalandroid.net;

import com.google.gson.Gson;
import com.sujian.finalandroid.entity.CommodityCallBackEntity;
import com.sujian.finalandroid.entity.CommodityDetailCallBackEntity;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * Created by sujian on 2016/6/12.
 * Mail:121116111@qq.com
 * 商品详细界面返回的回调
 */
public abstract class CommodityDetailCallBack extends Callback<CommodityDetailCallBackEntity> {
    @Override
    public CommodityDetailCallBackEntity parseNetworkResponse(Response response, int id) throws Exception {
        CommodityDetailCallBackEntity commodityCallBackEntity = new Gson().fromJson(response.body().string(), CommodityDetailCallBackEntity.class);
        return commodityCallBackEntity;
    }
}
