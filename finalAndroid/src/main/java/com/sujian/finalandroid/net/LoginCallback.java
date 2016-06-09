package com.sujian.finalandroid.net;

import com.google.gson.Gson;
import com.sujian.finalandroid.entity.LoginCallBackEntiy;
import com.zhy.http.okhttp.callback.Callback;

import org.xutils.common.util.LogUtil;

import okhttp3.Response;

/**
 * 登陆的回掉
 * Created by sujian on 2016/5/25.
 * Mail:121116111@qq.com
 */
public abstract class LoginCallback extends Callback<LoginCallBackEntiy> {
    @Override
    public LoginCallBackEntiy parseNetworkResponse(Response response, int id) throws Exception {
        String s = response.body().string();
        LogUtil.e(s);
        LoginCallBackEntiy loginEntiy = new Gson().fromJson(s, LoginCallBackEntiy.class);
        return loginEntiy;
    }
}
