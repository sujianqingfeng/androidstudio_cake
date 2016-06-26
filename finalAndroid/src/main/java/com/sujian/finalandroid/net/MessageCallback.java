package com.sujian.finalandroid.net;

import com.google.gson.Gson;
import com.sujian.finalandroid.entity.CommodityCallBackEntity;
import com.sujian.finalandroid.entity.MessageCallBackEntity;
import com.zhy.http.okhttp.callback.Callback;

import org.xutils.common.util.LogUtil;

import okhttp3.Response;

/**
 * 用户消息的回掉
 * Created by sujian on 2016/5/25.
 * Mail:121116111@qq.com
 */
public abstract class MessageCallback extends Callback<MessageCallBackEntity> {

    @Override
    public MessageCallBackEntity parseNetworkResponse(Response response, int id) throws Exception {
        String s = response.body().string();
        LogUtil.e(s);
        MessageCallBackEntity messageEntiy = new Gson().fromJson(s, MessageCallBackEntity.class);
        return messageEntiy;
    }

}
