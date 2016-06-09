package com.sujian.finalandroid.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.sujian.finalandroid.base.BaseActivity;
import com.sujian.finalandroid.base.BaseFragment;
import com.sujian.finalandroid.fragment.OrderFragmentFactory;
import com.sujian.finalandroid.ui.TitleBuilder;
import com.viewpagerindicator.TabPageIndicator;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * @author Sujian  121116111@QQ.COM
 *         MyOrderActivity
 *         TODO(我的订单页面)
 *         2016年4月17日 下午5:52:39
 */

@ContentView(R.layout.activity_myorder)
public class MyOrderActivity extends BaseActivity {


    private static final String[] CONTENT = new String[]{"全部订单", "待付款", "待收货", "待评论"};

    //蛋糕铺的viewpager
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
     * 初始化viewpager    已经修改为工厂模式
     */
    private void initViewpager() {
        OrderAdapter cakeShopAdapter = new OrderAdapter(getSupportFragmentManager());
        pager.setAdapter(cakeShopAdapter);
        indicator.setViewPager(pager);
        //添加滑动监听器  滑动到新页面的时候 重新请求服务器
        pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                OrderFragmentFactory.createFragment(position).show();
            }
        });
    }


    /**
     * 初始化标题
     */
    private void initTitle() {
        /**
         * 1.设置左边的图片按钮显示，以及事件 2.设置中间TextView显示的文字
         */
        new TitleBuilder(this).setLeftImageRes(R.drawable.head_top_title_left_icon).setMiddleTitleText("我的订单")
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
                    Toast.makeText(getApplicationContext(), "右边被点击", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };


    /**
     * 订单的适配器
     *
     * @author 12111
     */
    class OrderAdapter extends FragmentPagerAdapter {
        public OrderAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return OrderFragmentFactory.createFragment(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return CONTENT[position % CONTENT.length].toUpperCase();
        }

        @Override
        public int getCount() {
            return 4;
        }
    }

}
