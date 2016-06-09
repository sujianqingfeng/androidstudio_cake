package com.sujian.finalandroid.ui;


import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sujian.finalandroid.activity.R;
import com.sujian.finalandroid.uitls.UIUtils;

import org.xutils.common.util.LogUtil;

public class EditTextTitleBuilder {
    /**
     * title栏根布局
     */
    private View titleView;
    private TextView left_textview;
    private ImageView left_imageview;
    private EditText middle_editext;
    private TextView right_textview;
    private ImageView right_imageview;

    /**
     * 第一种  初始化方式
     * 这里是直接引用进文件的初始化方式
     *
     * @param context
     */
    public EditTextTitleBuilder(Activity context) {

        try {

            titleView = context.findViewById(R.id.edittext_title_bar);

            left_textview = (TextView) titleView.findViewById(R.id.title_left_textview);
            left_imageview = (ImageView) titleView.findViewById(R.id.title_left_imageview);
            middle_editext = (EditText) titleView.findViewById(R.id.title_middle_edittext);
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
    public EditTextTitleBuilder(View context) {
        try {
            titleView = context.findViewById(R.id.edittext_title_bar);

            left_textview = (TextView) titleView.findViewById(R.id.title_left_textview);
            left_imageview = (ImageView) titleView.findViewById(R.id.title_left_imageview);
            middle_editext = (EditText) titleView.findViewById(R.id.title_middle_edittext);
            right_textview = (TextView) titleView.findViewById(R.id.title_right_textview);
            right_imageview = (ImageView) titleView.findViewById(R.id.title_right_imageview);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置状态栏 透明和高度
     */
    public EditTextTitleBuilder initTitle(Activity activity) {

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
                    LogUtil.e(statusbarHeight + "-----" + height);
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
     * edittext 的设置
     */
    public EditTextTitleBuilder setMiddleTitleHintTextAndHintColor(String hinttext) {
        if (middle_editext != null) {
            middle_editext.setVisibility(TextUtils.isEmpty(hinttext) ? View.GONE
                    : View.VISIBLE);
            middle_editext.setHint(hinttext);
//            middle_editext.setHintTextColor(hintcolor);
//            middle_editext.setTextColor(textcolor);
            middle_editext.clearFocus();
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
    public EditTextTitleBuilder setLeftImageRes(int resId) {
        if (left_imageview != null) {
            left_imageview.setVisibility(resId > 0 ? View.VISIBLE : View.GONE);
            left_imageview.setImageResource(resId);
        }
        return this;
    }

    /**
     * 左边文字按钮
     */
    public EditTextTitleBuilder setLeftText(String text) {

        left_textview.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
        left_textview.setText(text);

        return this;
    }

    /**
     * 设置左边的事件
     */
    public EditTextTitleBuilder setLeftTextOrImageListener(final EditTextTitleBuilder.EditTextTitleListener listener) {

        if (left_imageview.getVisibility() == View.VISIBLE) {

            left_imageview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String editText = middle_editext.getText().toString().trim();
                    listener.onClick(v, editText);
                }
            });

        } else if (left_textview.getVisibility() == View.VISIBLE) {

            left_textview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String editText = middle_editext.getText().toString().trim();
                    listener.onClick(v, editText);
                }
            });

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
    public EditTextTitleBuilder setRightImageRes(int resId) {

        right_imageview.setVisibility(resId > 0 ? View.VISIBLE : View.GONE);
        right_imageview.setImageResource(resId);

        return this;
    }

    /**
     * 设置右边的图片是否显示
     */
    public EditTextTitleBuilder setRightIconVisiable(boolean b) {
        right_imageview.setVisibility(b ? View.VISIBLE : View.GONE);
        return this;
    }

    /**
     * 右边文字按钮
     */
    public EditTextTitleBuilder setRightText(String text) {

        right_textview.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
        right_textview.setText(text);

        return this;
    }

    /**
     * 设置右边的事件
     */
    public EditTextTitleBuilder setRightTextOrImageListener(final EditTextTitleBuilder.EditTextTitleListener listener) {

        if (right_imageview.getVisibility() == View.VISIBLE) {

            right_imageview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String editText = middle_editext.getText().toString().trim();
                    listener.onClick(v, editText);
                }
            });

        } else if (right_textview.getVisibility() == View.VISIBLE) {

            right_textview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String editText = middle_editext.getText().toString().trim();
                    listener.onClick(v, editText);
                }
            });
        }

        return this;
    }

    public View build() {
        return titleView;
    }


    public interface EditTextTitleListener {
        public void onClick(View v, String text);
    }
}