package com.sujian.finalandroid.fragment;

import android.support.v4.app.Fragment;

import com.sujian.finalandroid.base.BaseFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * CakeShopFragment 中的 fragment 工厂
 *
 * @author 12111
 */

public class CakeShopFragmentFactory {

    static Map<Integer, BaseFragment> fragments = new HashMap<Integer, BaseFragment>();

    public static BaseFragment createFragment(int position) {
        BaseFragment fragment = null;
        fragment = fragments.get(position);//取出fragment
        if (fragment == null) {

            if (position == 0) {
                fragment = new CheeseCakeFragment();
            } else if (position == 1) {
                fragment = new PoundCakeFragment();
            } else if (position == 2) {
                fragment = new AngelCakeFragment();
            } else if (position == 3) {
                fragment = new PrestigeCakeFragment();
            } else if (position == 4) {
                fragment = new SpongeCakeFragment();
            } else if (position == 5) {
                fragment = new MousseCakeFragment();
            }

            if (fragment != null) {
                fragments.put(position, fragment);//存fragment
            }
        }
        return fragment;

    }

}
