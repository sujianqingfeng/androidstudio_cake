package com.sujian.finalandroid.net;

import com.google.gson.Gson;
import com.sujian.finalandroid.entity.AddressInfoEntity;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * 解析单个的地址信息
 * Created by sujian on 2016/6/26.
 * Mail:121116111@qq.com
 */
public abstract class AddressInfoCallback extends Callback<AddressInfoEntity> {
    @Override
    public AddressInfoEntity parseNetworkResponse(Response response, int id) throws Exception {
        AddressInfoEntity addressInfoEntity = new Gson().fromJson(response.body().string(), AddressInfoEntity.class);
        return addressInfoEntity;
    }
}
