package com.sujian.finalandroid.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.baidu.mapapi.SDKInitializer;
import com.sujian.finalandroid.uitls.UIUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import org.xutils.x;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * @author Sujian  121116111@QQ.COM
 *         BaseApplication
 *         TODO(应用)
 *         2016年4月10日 下午6:31:27
 */
public class BaseApplication extends Application {

    private static Application context;
    private static int mainTid;
    private static Handler handler;
    //用户的id
    private static long uid = 1;

    @Override
    public void onCreate() {
        context = this;
        mainTid = android.os.Process.myTid();
        handler = new Handler();
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(true); // 是否输出debug日志, 开启debug会影响性能.
        //初始化地图
        SDKInitializer.initialize(this);

        //okhttp
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                //.addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);

    }


    public static Context getApplication() {
        return context;
    }

    public static int getMainTid() {
        return mainTid;
    }

    public static Handler getHandler() {
        return handler;
    }

    public static Long getUid() {
        return uid;
    }

    public static void setUid(Long uid) {
        BaseApplication.uid = uid;
    }

}
