package com.sujian.finalandroid.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author Sujian  121116111@QQ.COM
 * @ClassName: TabViewPager
 * @Description: TODO(自定义viewpager)
 * @date 2016年4月13日 下午11:49:46
 */
public class TabViewPager extends ViewPager {

    private boolean scrollble = false;

    public TabViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TabViewPager(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!scrollble) {
            return true;
        }
        return super.onTouchEvent(ev);
    }


    public boolean isScrollble() {
        return scrollble;
    }

    public void setScrollble(boolean scrollble) {
        this.scrollble = scrollble;
    }


}
