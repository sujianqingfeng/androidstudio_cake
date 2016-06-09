package com.sujian.finalandroid.fragment;

import com.sujian.finalandroid.base.BaseFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * 订单详情页面 fragment创建工厂
 * Created by sujian on 2016/5/17.
 */
public class OrderDetailFragmentFactory {


    static Map<Integer, BaseFragment> fragments = new HashMap<Integer, BaseFragment>();

    public static BaseFragment createFragment(int position) {
        BaseFragment fragment = null;
        fragment = fragments.get(position);//取出fragment
        if (fragment == null) {

            if (position == 0) {
                fragment = new OrderStateFragment();
            } else if (position == 1) {
                fragment = new OrderDetailFragment();
            }

            if (fragment != null) {
                fragments.put(position, fragment);//存fragment
            }
        }
        return fragment;

    }
}
