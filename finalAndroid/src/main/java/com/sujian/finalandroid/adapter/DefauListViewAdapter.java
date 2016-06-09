package com.sujian.finalandroid.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.sujian.finalandroid.base.BaseHolder;

import java.util.List;
import java.util.Map;

/**
 * 封装listviw的适配器
 * Created by sujian on 2016/5/19.
 * Mail:121116111@qq.com
 */
public abstract class DefauListViewAdapter<T> extends BaseAdapter {
    private List<T> data;

    public DefauListViewAdapter(List<T> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder<T> baseHolder;
        if (convertView == null) {
            baseHolder = getHolder();
        } else {
            baseHolder = (BaseHolder) convertView.getTag();
        }
        T map = data.get(position);
        baseHolder.setData(map);
        return baseHolder.getView();
    }

    public abstract BaseHolder getHolder();
}
