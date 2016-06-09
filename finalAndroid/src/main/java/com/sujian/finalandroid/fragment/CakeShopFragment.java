package com.sujian.finalandroid.fragment;

import java.util.List;

import org.xutils.view.annotation.ViewInject;

import com.sujian.finalandroid.activity.R;
import com.sujian.finalandroid.base.BaseFragment;
import com.sujian.finalandroid.ui.LoadingPage;
import com.viewpagerindicator.TabPageIndicator;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * 蛋糕铺 fragment
 */
public class CakeShopFragment extends BaseFragment {

    private static final String[] CONTENT = new String[]{"奶酪蛋糕", "重油蛋糕", "天使蛋糕", "戚风蛋糕", "海绵蛋糕", "慕斯蛋糕 "};

    //蛋糕铺的viewpager
    @ViewInject(R.id.pager)
    private ViewPager pager;
    //指示器
    @ViewInject(R.id.indicator)
    private TabPageIndicator indicator;
    //放置Fragment的集合
    List<Fragment> list_datas;


    @Override
    public void initDatas(View view) {

        initViewpager();
    }

    /**
     * 初始化viewpager
     */
    private void initViewpager() {
        CakeShopAdapter cakeShopAdapter = new CakeShopAdapter(getFragmentManager());
        pager.setAdapter(cakeShopAdapter);
        indicator.setViewPager(pager);
        //滑动页面的时候加载数据
        pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                CakeShopFragmentFactory.createFragment(position).show();
            }
        });
    }


    @Override
    protected View createSuccessView() {
        View view = View.inflate(getActivity(), R.layout.cakeshop_fragment, null);
        return view;
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }


    /**
     * 面包tab的适配器
     *
     * @author 12111
     */
    class CakeShopAdapter extends FragmentPagerAdapter {
        public CakeShopAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return CakeShopFragmentFactory.createFragment(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return CONTENT[position % CONTENT.length].toUpperCase();
        }

        @Override
        public int getCount() {
            return 6;
        }
    }


}
