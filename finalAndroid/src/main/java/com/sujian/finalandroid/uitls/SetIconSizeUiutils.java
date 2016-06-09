package com.sujian.finalandroid.uitls;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

/**
 * Created by Admin on 2016/5/16.
 */
public abstract class SetIconSizeUiutils {

    /**
     * 设置图标大小
     */
    public SetIconSizeUiutils(Context con, int object, int width, int height) {
        Drawable drawable = ContextCompat.getDrawable(con, object);
        if (drawable != null) {
            drawable.setBounds(0, 0, width, height);// 第一0是距左边距离，第二0是距上边距离，40分别是长宽
            setLocation(drawable);
        }
    }

    public abstract void setLocation(Drawable drawable);
}
