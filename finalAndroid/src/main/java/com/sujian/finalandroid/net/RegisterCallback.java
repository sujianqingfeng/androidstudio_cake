package com.sujian.finalandroid.net;

import com.google.gson.Gson;
import com.sujian.finalandroid.entity.RegisterCallBackEntity;
import com.zhy.http.okhttp.callback.Callback;

import org.xutils.common.util.LogUtil;

import okhttp3.Response;

/**
 * 注册的回掉
 * Created by sujian on 2016/5/25.
 * Mail:121116111@qq.com
 */
public abstract class RegisterCallback extends Callback<RegisterCallBackEntity> {
    @Override
    public RegisterCallBackEntity parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        LogUtil.e(string);
        RegisterCallBackEntity registerEntity = new Gson().fromJson(string, RegisterCallBackEntity.class);
        return registerEntity;
    }
}
