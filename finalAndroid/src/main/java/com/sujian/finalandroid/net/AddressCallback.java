package com.sujian.finalandroid.net;

import com.google.gson.Gson;
import com.sujian.finalandroid.entity.AddressCallBackEntity;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * Created by sujian on 2016/6/26.
 * Mail:121116111@qq.com
 */
public abstract class AddressCallback extends Callback<AddressCallBackEntity> {
    @Override
    public AddressCallBackEntity parseNetworkResponse(Response response, int id) throws Exception {
        AddressCallBackEntity addressCallBackEntity = new Gson().fromJson(response.body().string(), AddressCallBackEntity.class);
        return addressCallBackEntity;
    }
}
