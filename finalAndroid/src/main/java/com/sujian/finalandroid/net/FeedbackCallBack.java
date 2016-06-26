package com.sujian.finalandroid.net;

import android.content.pm.LauncherApps;

import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;
import com.sujian.finalandroid.entity.FeedbackCallBackEntity;

import org.xutils.common.util.LogUtil;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 反馈消息的回调
 * Created by Administrator on 2016/6/26/026.
 */
public abstract class FeedbackCallBack extends Callback<FeedbackCallBackEntity> {
    @Override
    public FeedbackCallBackEntity parseNetworkResponse(Response response, int id) throws Exception {
        String s = response.body().string();
        LogUtil.e(s);
        FeedbackCallBackEntity feedbackCallBackEntity = new Gson().fromJson(s, FeedbackCallBackEntity.class);
        return feedbackCallBackEntity;
    }

}
