package com.sujian.finalandroid.ui;


import com.sujian.finalandroid.activity.R;
import com.sujian.finalandroid.uitls.UIUtils;

import android.app.Activity;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TitleBuilder {
    /**
     * title栏根布局
     */
    private View titleView;
    private TextView left_textview;
    private ImageView left_imageview;
    private TextView middle_textview;
    private TextView right_textview;
    private ImageView right_imageview;

    /**
     * 第一种  初始化方式
     * 这里是直接引用进文件的初始化方式
     *
     * @param context
     */
    public TitleBuilder(Activity context) {

        try {

            titleView = context.findViewById(R.id.title_bar);

            left_textview = (TextView) titleView.findViewById(R.id.title_left_textview);
            left_imageview = (ImageView) titleView.findViewById(R.id.title_left_imageview);
            middle_textview = (TextView) titleView.findViewById(R.id.title_middle_textview);
            right_textview = (TextView) titleView.findViewById(R.id.title_right_textview);
            right_imageview = (ImageView) titleView.findViewById(R.id.title_right_imageview);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 第二种初始化方式
     * 这里是比如你用代码创建布局的时候使用
     */
    public TitleBuilder(View context) {
        try {
            titleView = context.findViewById(R.id.title_bar);

            left_textview = (TextView) titleView.findViewById(R.id.title_left_textview);
            left_imageview = (ImageView) titleView.findViewById(R.id.title_left_imageview);
            middle_textview = (TextView) titleView.findViewById(R.id.title_middle_textview);
            right_textview = (TextView) titleView.findViewById(R.id.title_right_textview);
            right_imageview = (ImageView) titleView.findViewById(R.id.title_right_imageview);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置状态栏 透明和高度
     */
    public TitleBuilder initTitle(final Activity activity) {

        if (titleView == null) {
            return this;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 获取状态栏高度
            final int statusbarHeight = UIUtils.getStatusBarHeight(activity);

            // 获取activity和menu中的占位view。两者代码一致
            titleView.post(new Runnable() {
                @Override
                public void run() {
                    int height = titleView.getHeight();
                    // 设置占位view的高度为状态栏高度


                    ViewParent parent = titleView.getParent();
                    if (parent instanceof LinearLayout) {
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                statusbarHeight + height);
                        titleView.setLayoutParams(params);
                    } else {
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                statusbarHeight + height);
                        titleView.setLayoutParams(params);
                    }

                    titleView.setPadding(0, statusbarHeight, 0, 0);
                }
            });
        }
        return this;
    }

    /**
     * title 的背景色
     */

    public TitleBuilder setMiddleTitleBgRes(int resid) {

        middle_textview.setBackgroundResource(resid);

        return this;
    }

    /**
     * title的文本
     *
     * @param text
     * @return
     */
    public TitleBuilder setMiddleTitleText(String text) {
        if (middle_textview != null) {
            middle_textview.setVisibility(TextUtils.isEmpty(text) ? View.GONE
                    : View.VISIBLE);
            middle_textview.setText(text);
        }
        return this;
    }

    /**
     * left
     */
    /**
     * 图片按钮
     *
     * @param resId
     * @return
     */
    public TitleBuilder setLeftImageRes(int resId) {
        if (left_imageview != null) {
            left_imageview.setVisibility(resId > 0 ? View.VISIBLE : View.GONE);
            left_imageview.setImageResource(resId);
        }
        return this;
    }

    /**
     * 左边文字按钮
     */
    public TitleBuilder setLeftText(String text) {

        left_textview.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
        left_textview.setText(text);

        return this;
    }

    /**
     * 设置左边的事件
     */
    public TitleBuilder setLeftTextOrImageListener(View.OnClickListener listener) {

        if (left_imageview.getVisibility() == View.VISIBLE) {

            left_imageview.setOnClickListener(listener);

        } else if (left_textview.getVisibility() == View.VISIBLE) {

            left_textview.setOnClickListener(listener);

        }

        return this;
    }

    /**
     * right
     */
    /**
     * 右边图片按钮
     *
     * @param resId
     * @return
     */
    public TitleBuilder setRightImageRes(int resId) {

        right_imageview.setVisibility(resId > 0 ? View.VISIBLE : View.GONE);
        right_imageview.setImageResource(resId);

        return this;
    }

    /**
     * 设置右边的图片是否显示
     */
    public TitleBuilder setRightIconVisiable(boolean b) {
        right_imageview.setVisibility(b ? View.VISIBLE : View.GONE);
        return this;
    }

    /**
     * 右边文字按钮
     */
    public TitleBuilder setRightText(String text) {

        right_textview.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
        right_textview.setText(text);

        return this;
    }

    /**
     * 设置右边的事件
     */
    public TitleBuilder setRightTextOrImageListener(View.OnClickListener listener) {

        if (right_imageview.getVisibility() == View.VISIBLE) {

            right_imageview.setOnClickListener(listener);

        } else if (right_textview.getVisibility() == View.VISIBLE) {

            right_textview.setOnClickListener(listener);
        }

        return this;
    }

    public View build() {
        return titleView;
    }
}