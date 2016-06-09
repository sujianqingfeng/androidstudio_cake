package com.sujian.finalandroid.activity;

import org.xutils.x;
import org.xutils.view.annotation.ContentView;

import com.sujian.finalandroid.constant.Constants;
import com.sujian.finalandroid.uitls.MyActivityManager;
import com.sujian.finalandroid.uitls.SharedPreferencesUitls;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * @author Sujian  121116111@QQ.COM
 * @ClassName: SplashActivity
 * @Description: TODO(闪屏页)
 * @date 2016年4月10日 下午3:17:37
 */
@ContentView(R.layout.activity_splash)
public class SplashActivity extends Activity {

    private static boolean isFirst = false;
    private static final int HOME = 0;
    private static final int GUIDE = 1;
    private static final int LOGIN = 2;
    private static final int TIME = 2000;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HOME:
                    goHome();
                    break;
                case GUIDE:
                    goGuide();
                    break;
                case LOGIN:
                    goLogin();
                    break;
            }
        }
    };


    /**
     * android 生命周期 创建
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        MyActivityManager.getMyActivityManager().addActivity(this);
        init();
    }

    /**
     * android 生命周期 销毁
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyActivityManager.getMyActivityManager().finishActivity(this);
    }

    /**
     * TODO(初始化数据 跳转不同页面)
     */
    protected void init() {

        isFirst = SharedPreferencesUitls.getBooleanValue(Constants.USER_INFO_ISFiRST, true);
        if (isFirst) {//判断是否是第一次
            handler.sendEmptyMessageDelayed(GUIDE, TIME);
        } else if (SharedPreferencesUitls.getBooleanValue(Constants.USER_INFO_REMBERPASSWORD, false) == true) {//判断是否记住密码
            handler.sendEmptyMessageDelayed(HOME, TIME);
        } else {
            handler.sendEmptyMessageDelayed(LOGIN, TIME);
        }
    }


    /**
     * TODO(跳转主页)
     */
    private void goHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * TODO(跳转引导页)
     */
    private void goGuide() {
        Intent intent = new Intent(this, GuideAcrivity.class);
        startActivity(intent);
        finish();
    }

    ;


    /**
     * TODO(跳转登陆)
     */
    private void goLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


}
