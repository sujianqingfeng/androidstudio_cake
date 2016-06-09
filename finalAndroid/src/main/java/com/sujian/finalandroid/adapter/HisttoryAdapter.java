package com.sujian.finalandroid.adapter;

import android.content.Context;

import com.fyales.tagcloud.library.TagBaseAdapter;

import java.util.List;

/*
 *历史搜索标签的适配器
 * Created by 12111 on 2016/5/13.
 */
public class HisttoryAdapter extends TagBaseAdapter {
    private List<String> list;

    public HisttoryAdapter(Context context, List<String> list) {
        super(context, list);
        this.list = list;
    }

    /**
     * 清楚数据
     *
     * @return
     */
    public void setData(List<String> list) {
        this.list = list;
    }
}
