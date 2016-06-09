package com.sujian.finalandroid.base;

import android.view.View;

import java.util.Map;

/**
 * holder的基类
 * Created by sujian on 2016/5/19.
 * Mail:121116111@qq.com
 */
public abstract class BaseHolder<T> {
    protected View view;
    protected T data;

    public BaseHolder() {
        view = initView();
        view.setTag(this);
    }


    public View getView() {
        return view;
    }

    public void setData(T data) {
        this.data = data;
        refreshView();
    }

    /**
     * setData方法之后会调用本方法
     */
    protected abstract void refreshView();


    /**
     * 子类实现view的初始化
     *
     * @return view
     */
    public abstract View initView();

}
