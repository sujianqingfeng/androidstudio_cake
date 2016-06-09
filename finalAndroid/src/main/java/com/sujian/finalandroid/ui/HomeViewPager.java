package com.sujian.finalandroid.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 图片轮播的viewpager
 *
 * @author 12111
 */
public class HomeViewPager extends ViewPager {

    public HomeViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HomeViewPager(Context context) {
        super(context);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (getCurrentItem() == 0) {
            getParent().requestDisallowInterceptTouchEvent(false);
        } else {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        return super.dispatchTouchEvent(ev);
    }


}
