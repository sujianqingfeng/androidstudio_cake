package com.sujian.finalandroid.net;

import com.google.gson.Gson;
import com.sujian.finalandroid.entity.ShopCarOrderInfo;
import com.sujian.finalandroid.entity.ShopCarOrderInfoCallBackEntity;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * Created by sujian on 2016/6/15.
 * Mail:121116111@qq.com
 */
public abstract class ShopCarOrderCallBack extends Callback<ShopCarOrderInfoCallBackEntity> {
    @Override
    public ShopCarOrderInfoCallBackEntity parseNetworkResponse(Response response, int id) throws Exception {
        ShopCarOrderInfoCallBackEntity shopCarOrderInfo = new Gson().fromJson(response.body().string(), ShopCarOrderInfoCallBackEntity.class);
        return shopCarOrderInfo;
    }
}
