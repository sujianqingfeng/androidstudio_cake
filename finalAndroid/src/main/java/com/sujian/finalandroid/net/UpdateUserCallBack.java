package com.sujian.finalandroid.net;

import com.google.gson.Gson;
import com.sujian.finalandroid.entity.UpdateUserCallBackEntity;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * Created by Admin on 2016/5/26.
 */
public abstract class UpdateUserCallBack extends Callback<UpdateUserCallBackEntity> {


    @Override
    public UpdateUserCallBackEntity parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        UpdateUserCallBackEntity up = new Gson().fromJson(string, UpdateUserCallBackEntity.class);
        return up;
    }
}
