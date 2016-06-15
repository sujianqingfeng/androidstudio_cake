package com.sujian.finalandroid.net;

import com.google.gson.Gson;
import com.sujian.finalandroid.entity.ShopCarOrderInfo;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * Created by sujian on 2016/6/15.
 * Mail:121116111@qq.com
 */
public abstract class ShopCarOrderCallBack extends Callback<ShopCarOrderInfo> {
    @Override
    public ShopCarOrderInfo parseNetworkResponse(Response response, int id) throws Exception {
        ShopCarOrderInfo shopCarOrderInfo = new Gson().fromJson(response.body().string(), ShopCarOrderInfo.class);
        return shopCarOrderInfo;
    }
}
