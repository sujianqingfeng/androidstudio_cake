package com.sujian.finalandroid.net;

import com.google.gson.Gson;
import com.sujian.finalandroid.entity.CommodityCallBackEntity;

import com.zhy.http.okhttp.callback.Callback;

import org.xutils.common.util.LogUtil;

import okhttp3.Response;

/**
 * 商品列表的回掉
 * Created by sujian on 2016/5/25.
 * Mail:121116111@qq.com
 */
public abstract class CommodityCallback extends Callback<CommodityCallBackEntity> {

    @Override
    public CommodityCallBackEntity parseNetworkResponse(Response response, int id) throws Exception {
        String s = response.body().string();
        LogUtil.e(s);
        CommodityCallBackEntity commodityEntiy = new Gson().fromJson(s, CommodityCallBackEntity.class);
        return commodityEntiy;
    }

}
