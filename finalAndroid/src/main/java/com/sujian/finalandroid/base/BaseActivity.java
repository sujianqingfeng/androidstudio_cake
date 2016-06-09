package com.sujian.finalandroid.base;


import org.xutils.x;

import com.sujian.finalandroid.uitls.MyActivityManager;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.WindowManager;

/**
 * @author Sujian  121116111@QQ.COM
 * @ClassName: BaseActivity
 * @Description: TODO(所有activity的基类)
 * @date 2016年4月10日 下午6:33:30
 */
public class BaseActivity extends FragmentActivity {
    public Context mContext;

    /**
     * activity生命周期 创建
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        MyActivityManager.getMyActivityManager().addActivity(this);
        init();

    }


    /**
     * activity生命周期 销毁
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // MyActivityManager.getMyActivityManager().finishActivity(this);
    }

    /**
     * @param
     * @return void
     * @throws
     * @Title: init
     * @Description: TODO(初始化)
     */
    protected void init() {
        x.view().inject(this);
        initWindow();
        initData();
    }

    /**
     * @param
     * @return void
     * @throws
     * @Title: initWindow
     * @Description: TODO(判断sdk版本 并设置状态栏透明)
     */
    @TargetApi(19)
    private void initWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }


    /**
     * @param
     * @return void
     * @throws
     * @Title: initData
     * @Description: TODO(初始化数据)
     */
    protected void initData() {
    }

    ;
}
