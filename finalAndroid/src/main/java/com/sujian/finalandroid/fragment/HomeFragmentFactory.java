package com.sujian.finalandroid.fragment;

import java.util.HashMap;
import java.util.Map;

import com.sujian.finalandroid.base.BaseFragment;

/**
 * HomeActivity 中的 fragment 工厂
 *
 * @author 12111
 */

public class HomeFragmentFactory {

    static Map<Integer, BaseFragment> fragments = new HashMap<Integer, BaseFragment>();

    public static BaseFragment createFragment(int position) {
        BaseFragment fragment = null;
        fragment = fragments.get(position);//取出fragment
        if (fragment == null) {

            if (position == 0) {
                fragment = new HomeFragment();
            } else if (position == 1) {
                fragment = new CakeShopFragment();
            } else if (position == 2) {
                fragment = new ShopCartFragment();
            }

            if (fragment != null) {
                fragments.put(position, fragment);//存fragment
            }
        }
        return fragment;

    }


}
