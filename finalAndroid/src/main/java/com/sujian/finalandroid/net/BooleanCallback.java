package com.sujian.finalandroid.net;

import com.google.gson.Gson;
import com.sujian.finalandroid.entity.BooleabEntity;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * boolean值得回调
 * Created by sujian on 2016/6/24.
 * Mail:121116111@qq.com
 */
public abstract class BooleanCallback extends Callback<BooleabEntity> {
    @Override
    public BooleabEntity parseNetworkResponse(Response response, int id) throws Exception {
        BooleabEntity booleabEntity = new Gson().fromJson(response.body().string(), BooleabEntity.class);
        return booleabEntity;
    }
}
