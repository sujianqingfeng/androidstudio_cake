package com.sujian.finalandroid.uitls;

import com.sujian.finalandroid.constant.Constants;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import org.xutils.x;

/**
 * SharedPreferences的封装
 *
 * @author 12111
 */
public class SharedPreferencesUitls {
    /**
     * 得到一个字符串
     */
    public static String getStringValue(String key, String defuleValue) {
        SharedPreferences sp = x.app().getSharedPreferences(Constants.USER_INFO, Activity.MODE_PRIVATE);
        return sp.getString(key, defuleValue);
    }

    /**
     * 得到一个boolean值
     */
    public static boolean getBooleanValue(String key, boolean defuleValue) {
        SharedPreferences sp = x.app().getSharedPreferences(Constants.USER_INFO, Activity.MODE_PRIVATE);
        return sp.getBoolean(key, defuleValue);
    }

    /**
     * 设置一个字符串
     */
    public static void setStringValue(String key, String value) {
        SharedPreferences sp = x.app().getSharedPreferences(Constants.USER_INFO, Activity.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 设置一个boolean值
     */
    public static void setBooleanValue(String key, Boolean value) {
        SharedPreferences sp = x.app().getSharedPreferences(Constants.USER_INFO, Activity.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }


    /**
     * 得到一个long值
     */
    public static long getLongValue(String key, long defuleValue) {
        SharedPreferences sp = x.app().getSharedPreferences(Constants.USER_INFO, Activity.MODE_PRIVATE);
        return sp.getLong(key, defuleValue);
    }

    /**
     * 设置一个long
     */
    public static void setLongValue(String key, long value) {
        SharedPreferences sp = x.app().getSharedPreferences(Constants.USER_INFO, Activity.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putLong(key, value);
        editor.commit();
    }


}
