package com.sujian.finalandroid.fragment;

import android.support.v4.app.Fragment;

import com.sujian.finalandroid.base.BaseFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * 订单页面中的 fragment 工厂
 *
 * @author 12111
 */

public class OrderFragmentFactory {

    static Map<Integer, BaseFragment> fragments = new HashMap<Integer, BaseFragment>();

    public static BaseFragment createFragment(int position) {
        BaseFragment fragment = null;
        fragment = fragments.get(position);//取出fragment
        if (fragment == null) {

            if (position == 0) {
                fragment = new AllOrderFragment();
            } else if (position == 1) {
                fragment = new PendingPayFragment();
            } else if (position == 2) {
                fragment = new ReceiptGoodsFragment();
            } else if (position == 3) {
                fragment = new CommentedFragment();
            }

            if (fragment != null) {
                fragments.put(position, fragment);//存fragment
            }
        }
        return fragment;

    }

}
