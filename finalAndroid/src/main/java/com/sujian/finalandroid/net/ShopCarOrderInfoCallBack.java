package com.sujian.finalandroid.net;

import com.google.gson.Gson;
import com.sujian.finalandroid.entity.ShopCarOrderInfoCallBackEntity;
import com.sujian.finalandroid.entity.ShopCarOrderNumCallBackEntity;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * 购物车解析callback
 * Created by sujian on 2016/6/21.
 * Mail:121116111@qq.com
 */
public abstract class ShopCarOrderInfoCallBack extends Callback<ShopCarOrderInfoCallBackEntity> {

    @Override
    public ShopCarOrderInfoCallBackEntity parseNetworkResponse(Response response, int id) throws Exception {
        ShopCarOrderInfoCallBackEntity shopCarOrderInfoCallBackEntity = new Gson().fromJson(response.body().string(), ShopCarOrderInfoCallBackEntity.class);
        return shopCarOrderInfoCallBackEntity;
    }
}
