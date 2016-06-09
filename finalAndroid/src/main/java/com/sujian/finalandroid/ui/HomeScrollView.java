package com.sujian.finalandroid.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * 首页里面的滚动视图
 *
 * @author 12111
 */
public class HomeScrollView extends ScrollView {
    // 开始的x坐标
    int stratX;

    public HomeScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public HomeScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HomeScrollView(Context context) {
        super(context);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // 自己处理

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                stratX = (int) ev.getX();
                break;

            case MotionEvent.ACTION_MOVE:
                // 结束的x坐标
                int endX = (int) ev.getX();
                // System.out.println("====================sx"+stratX);
                // System.out.println("====================ex"+endX);
                // 结束的x坐标
                int dx = stratX - endX;
                // System.out.println("====================dx"+dx);
                if (dx > 0) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                } else {

                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

}
