package com.sujian.finalandroid.fragment;

import com.sujian.finalandroid.base.BaseFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * 我的消息页面 fragment创建工厂
 * Created by sujian on 2016/5/17.
 */
public class MyNewsFragmentFactory {


    static Map<Integer, BaseFragment> fragments = new HashMap<Integer, BaseFragment>();

    public static BaseFragment createFragment(int position) {
        BaseFragment fragment = null;
        fragment = fragments.get(position);//取出fragment
        if (fragment == null) {

            if (position == 0) {
                fragment = new UserNewFragment();
            } else if (position == 1) {
                fragment = new SystemNewFragment();
            }

            if (fragment != null) {
                fragments.put(position, fragment);//存fragment
            }
        }
        return fragment;

    }
}
