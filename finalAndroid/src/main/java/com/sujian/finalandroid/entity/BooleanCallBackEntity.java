package com.sujian.finalandroid.entity;

import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * 返回的单个boolean返回
 * Created by sujian on 2016/6/20.
 * Mail:121116111@qq.com
 */
public abstract class BooleanCallBackEntity extends Callback<BooleabEntity> {

    @Override
    public BooleabEntity parseNetworkResponse(Response response, int id) throws Exception {
        BooleabEntity booleabEntity = new Gson().fromJson(response.body().string(), BooleabEntity.class);
        return booleabEntity;
    }
}
