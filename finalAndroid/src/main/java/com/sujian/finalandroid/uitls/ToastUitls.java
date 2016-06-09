package com.sujian.finalandroid.uitls;

import android.widget.Toast;

import org.xutils.x;

/**
 * Toast工具
 * Created by sujian on 2016/5/25.
 * Mail:121116111@qq.com
 */
public class ToastUitls {
    public static void show(String string) {
        Toast.makeText(x.app(), string, Toast.LENGTH_SHORT).show();
    }
}
