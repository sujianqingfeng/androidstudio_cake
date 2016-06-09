package com.sujian.finalandroid.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.sujian.finalandroid.base.BaseActivity;
import com.sujian.finalandroid.fragment.HomeFragmentFactory;
import com.sujian.finalandroid.fragment.OrderDetailFragmentFactory;
import com.sujian.finalandroid.ui.TabTitleBuilder;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * 订单详情页面
 */
@ContentView(R.layout.activity_order_detail)
public class OrderDetailActivity extends BaseActivity {

    @ViewInject(R.id.vp_orderdetail)
    private ViewPager vp_orderdetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initData() {
        super.initData();
        initTabTitle();
        initViewpager();

    }

    /**
     * 初始化view
     */
    private void initViewpager() {
        vp_orderdetail.setAdapter(new TabPagerAdapter(getSupportFragmentManager()));
    }

    /**
     * 初始化标题栏
     */
    private void initTabTitle() {
        new TabTitleBuilder(this).initTitle(this)
                .setLeftImageRes(R.drawable.head_top_title_left_icon)
                .setRightImageRes(R.drawable.ic_launcher)
                .setLeftRadioButtonTextAndColorAndBackground("订单状态", R.drawable.rb_bg_01, true)
                .setRightRadioButtonTextAndColorAndBackground("订单详情", R.drawable.rb_bg_02, false)
                .setLeftTextOrImageListener(titleListener)
                .setRightTextOrImageListener(titleListener)
                .setTabSelectedListener(tabListener);
    }


    /**
     * 标题栏tab的监听
     */
    private RadioGroup.OnCheckedChangeListener tabListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_left:
                    vp_orderdetail.setCurrentItem(0);
                    break;
                case R.id.rb_right:
                    vp_orderdetail.setCurrentItem(1);
                    break;
            }
        }
    };

    /**
     * 标题栏的监听
     */
    private View.OnClickListener titleListener = new View.OnClickListener() {

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


    // viewPager 的适配器
    class TabPagerAdapter extends FragmentPagerAdapter {

        public TabPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return OrderDetailFragmentFactory.createFragment(position);
        }

        @Override
        public int getCount() {

            return 2;
        }

    }

}
