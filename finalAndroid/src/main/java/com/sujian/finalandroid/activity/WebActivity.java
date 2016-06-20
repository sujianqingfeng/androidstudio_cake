package com.sujian.finalandroid.activity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import com.sujian.finalandroid.activity.R.layout;
import com.sujian.finalandroid.base.BaseActivity;
import com.sujian.finalandroid.ui.BadgeFloatingActionButton;
import com.sujian.finalandroid.ui.TitleBuilder;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.Toast;

/**
 * web 视图 加载网页
 *
 * @author 12111
 */
@ContentView(R.layout.activity_web)
public class WebActivity extends BaseActivity {

    @ViewInject(R.id.wv_web)
    private WebView web;

    @ViewInject(R.id.bfab_web_shopcar)
    private BadgeFloatingActionButton bfab_web_shopcar;

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getIntent().getStringExtra("url");
        initWeb();
    }

    @Override
    protected void initData() {
        initTitle();
    }

    /**
     * 初始化 web
     */
    private void initWeb() {
        web.loadUrl(url);
        // 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
    }

    /**
     * 初始化标题
     */
    private void initTitle() {
        /**
         * 1.设置左边的图片按钮显示，以及事件 2.设置中间TextView显示的文字 3.设置右边的图片按钮显示，并设置事件
         */
        new TitleBuilder(this).setLeftImageRes(R.drawable.head_top_title_left_icon).setMiddleTitleText("网 页")
                .setRightImageRes(R.drawable.ic_launcher).setLeftTextOrImageListener(titleListener)
                .setRightTextOrImageListener(titleListener).initTitle(this);

    }

    /**
     * 标题栏的监听
     */
    private View.OnClickListener titleListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.title_left_imageview:
                    finish();
                    break;

                case R.id.title_right_imageview:
                    Toast.makeText(getApplicationContext(), "右边被点击", 0).show();
                    break;
            }

        }
    };

    /**
     * 点击购物车按钮
     *
     * @param v
     */
    @Event(R.id.bfab_web_shopcar)
    private void clickCar(View v) {
        setResult(11, getIntent());
        finish();
    }

}
