package com.sujian.finalandroid.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.provider.Contacts;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sujian.finalandroid.activity.R;
import com.sujian.finalandroid.uitls.UIUtils;

import org.xutils.common.util.LogUtil;

/**
 * tab 标题栏的创建
 * Created by sujian on 2016/5/16.
 */
public class TabTitleBuilder {

    /**
     * title栏根布局
     */
    private View titleView;
    private TextView left_textview;
    private ImageView left_imageview;
    private RadioGroup middle_radioGroup;
    private TextView right_textview;
    private ImageView right_imageview;
    private RadioButton rb_left;
    private RadioButton rb_right;


    /**
     * 第一种  初始化方式
     * 这里是直接引用进文件的初始化方式
     *
     * @param context
     */
    public TabTitleBuilder(Activity context) {
        try {
            titleView = context.findViewById(R.id.tab_title_bar);

            left_textview = (TextView) titleView.findViewById(R.id.title_left_textview);
            left_imageview = (ImageView) titleView.findViewById(R.id.title_left_imageview);
            middle_radioGroup = (RadioGroup) titleView.findViewById(R.id.title_middle_radiogroup);
            right_textview = (TextView) titleView.findViewById(R.id.title_right_textview);
            right_imageview = (ImageView) titleView.findViewById(R.id.title_right_imageview);
            rb_left = (RadioButton) titleView.findViewById(R.id.rb_left);
            rb_right = (RadioButton) titleView.findViewById(R.id.rb_right);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 第二种初始化方式
     * 这里是比如你用代码创建布局的时候使用
     */
    public TabTitleBuilder(View context) {
        try {
            titleView = context.findViewById(R.id.tab_title_bar);

            left_textview = (TextView) titleView.findViewById(R.id.title_left_textview);
            left_imageview = (ImageView) titleView.findViewById(R.id.title_left_imageview);
            middle_radioGroup = (RadioGroup) titleView.findViewById(R.id.title_middle_radiogroup);
            right_textview = (TextView) titleView.findViewById(R.id.title_right_textview);
            right_imageview = (ImageView) titleView.findViewById(R.id.title_right_imageview);
            rb_left = (RadioButton) titleView.findViewById(R.id.rb_left);
            rb_right = (RadioButton) titleView.findViewById(R.id.rb_right);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 设置状态栏 透明和高度
     */
    public TabTitleBuilder initTitle(Activity activity) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 获取状态栏高度
            final int statusbarHeight = UIUtils.getStatusBarHeight(activity);

            // 获取activity和menu中的占位view。两者代码一致
            titleView.post(new Runnable() {
                @Override
                public void run() {
                    int height = titleView.getHeight();
                    // 设置占位view的高度为状态栏高度

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            statusbarHeight + height);
                    titleView.setLayoutParams(params);
                    titleView.setPadding(0, statusbarHeight, 0, 0);
                }
            });
        }
        return this;
    }


    /**
     * title 的背景色
     */

    public TabTitleBuilder setMiddleTitleBgRes(int resid) {

        middle_radioGroup.setBackgroundResource(resid);

        return this;
    }

    /**
     * 设置左边Radiobutton 的文字
     *
     * @param text
     * @return TabTitleBuilder
     */

    public TabTitleBuilder setLeftRadioButtonTextAndColorAndBackground(String text, int background, boolean b) {
        rb_left.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
        rb_left.setText(text);
        rb_left.setBackgroundResource(background);
        rb_left.setChecked(b);

        return this;
    }

    /**
     * 设置左边Radiobutton 的文字
     *
     * @param text
     * @return TabTitleBuilder
     */
    public TabTitleBuilder setRightRadioButtonTextAndColorAndBackground(String text, int background, boolean b) {
        rb_right.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
        rb_right.setText(text);
        rb_right.setBackgroundResource(background);
        rb_right.setChecked(b);

        return this;
    }

    /**
     * 设置标题栏tab的监听事件
     *
     * @param listener
     */
    public TabTitleBuilder setTabSelectedListener(RadioGroup.OnCheckedChangeListener listener) {
        if (rb_left.getVisibility() == View.VISIBLE && rb_right.getVisibility() == View.VISIBLE) {
            middle_radioGroup.setOnCheckedChangeListener(listener);
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
    public TabTitleBuilder setLeftImageRes(int resId) {

        left_imageview.setVisibility(resId > 0 ? View.VISIBLE : View.GONE);
        left_imageview.setImageResource(resId);

        return this;
    }

    /**
     * 左边文字按钮
     */
    public TabTitleBuilder setLeftText(String text) {

        left_textview.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
        left_textview.setText(text);

        return this;
    }

    /**
     * 设置左边的事件
     */
    public TabTitleBuilder setLeftTextOrImageListener(View.OnClickListener listener) {

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
    public TabTitleBuilder setRightImageRes(int resId) {

        right_imageview.setVisibility(resId > 0 ? View.VISIBLE : View.GONE);
        right_imageview.setImageResource(resId);

        return this;
    }

    /**
     * 设置右边的图片是否显示
     */
    public TabTitleBuilder setRightIconVisiable(boolean b) {
        right_imageview.setVisibility(b ? View.VISIBLE : View.GONE);
        return this;
    }

    /**
     * 右边文字按钮
     */
    public TabTitleBuilder setRightText(String text) {

        right_textview.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
        right_textview.setText(text);

        return this;
    }

    /**
     * 设置右边的事件
     */
    public TabTitleBuilder setRightTextOrImageListener(View.OnClickListener listener) {

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
