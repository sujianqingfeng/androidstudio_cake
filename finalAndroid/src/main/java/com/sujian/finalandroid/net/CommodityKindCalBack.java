package com.sujian.finalandroid.net;

import com.google.gson.Gson;

import com.sujian.finalandroid.entity.CommodityKindCalBackEntity;
import com.zhy.http.okhttp.callback.Callback;

import org.xutils.common.util.LogUtil;

import okhttp3.Response;

/**
 * Created by Admin on 2016/6/2.
 */
public abstract class CommodityKindCalBack extends Callback<CommodityKindCalBackEntity> {
    @Override
    public CommodityKindCalBackEntity parseNetworkResponse(Response response, int id) throws Exception {
        String s = response.body().string();
        LogUtil.e("首页解析的字符串" + s);
        CommodityKindCalBackEntity commoditykindCallBackEntity = new Gson().fromJson(s, CommodityKindCalBackEntity.class);
        return commoditykindCallBackEntity;
    }
}
