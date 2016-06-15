package com.sujian.finalandroid.net;

import com.google.gson.Gson;
import com.sujian.finalandroid.entity.ShopCarOrderNumCallBackEntity;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * Created by sujian on 2016/6/14.
 * Mail:121116111@qq.com
 */
public abstract class ShopCarOrderNumCallBack extends Callback<ShopCarOrderNumCallBackEntity> {
    @Override
    public ShopCarOrderNumCallBackEntity parseNetworkResponse(Response response, int id) throws Exception {
        ShopCarOrderNumCallBackEntity shopCarOrderNumCallBackEntity = new Gson().fromJson(response.body().string(), ShopCarOrderNumCallBackEntity.class);
        return shopCarOrderNumCallBackEntity;
    }
}
