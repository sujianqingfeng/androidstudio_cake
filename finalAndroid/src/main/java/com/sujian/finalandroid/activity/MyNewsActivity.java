package com.sujian.finalandroid.activity;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import com.sujian.finalandroid.base.BaseActivity;
import com.sujian.finalandroid.constant.Constants;
import com.sujian.finalandroid.entity.Commodity;
import com.sujian.finalandroid.entity.Message;
import com.sujian.finalandroid.entity.MessageCallBackEntity;
import com.sujian.finalandroid.fragment.MyNewsFragmentFactory;
import com.sujian.finalandroid.fragment.OrderFragmentFactory;
import com.sujian.finalandroid.net.MessageCallback;
import com.sujian.finalandroid.ui.TitleBuilder;
import com.sujian.finalandroid.uitls.ToastUitls;
import com.viewpagerindicator.TabPageIndicator;
import com.zhy.http.okhttp.OkHttpUtils;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import java.util.List;

import okhttp3.Call;

/**
 * @author Sujian  121116111@QQ.COM
 *         MyNewsActivity
 *         TODO(我的消息页面)
 *         2016年4月17日 下午5:59:05
 */
@ContentView(R.layout.activity_mynews)
public class MyNewsActivity extends BaseActivity {

    private static final String[] CONTENT = new String[]{"用户消息", "系统消息"};
    //我的消息的viewpager
    @ViewInject(R.id.pager)
    private ViewPager pager;
    //指示器
    @ViewInject(R.id.indicator)
    private TabPageIndicator indicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void initData() {
        initTitle();
        initViewpager();

    }

    /**
     * 初始化viewpager
     */
    private void initViewpager() {
        NewsAdapter newsAdapter = new NewsAdapter(getSupportFragmentManager());
        pager.setAdapter(newsAdapter);
        indicator.setViewPager(pager);
        //添加滑动监听器  滑动到新页面的时候 重新请求服务器
        pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                MyNewsFragmentFactory.createFragment(position).show();
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
        new TitleBuilder(this).setLeftImageRes(R.drawable.head_top_title_left_icon).setMiddleTitleText("我的消息")
                .setLeftTextOrImageListener(titleListener)
                .initTitle(this);

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

                    break;
            }

        }
    };


    /**
     * 我的消息viewpager的适配器
     *
     * @author 12111
     */
    class NewsAdapter extends FragmentPagerAdapter {
        public NewsAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return MyNewsFragmentFactory.createFragment(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return CONTENT[position % CONTENT.length].toUpperCase();
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

}
