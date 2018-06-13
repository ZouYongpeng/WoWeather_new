package com.example.woweather_new.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.woweather_new.base.WoWeatherApplication;

/**
 * Created by 邹永鹏 on 2018/6/14.
 */

public class SharedPreferencesUtil {

    public static final String IS_READED_PLACE_EXCEL="isReadedPlaceExcel";

    public static final String LOCAL_PROVINCE="local_province";
    public static final String LOCAL_CITY="local_city";
    public static final String LOCAL_COUNTY="local_county";

    private static SharedPreferences sp;

    /*static{}(即static块)，会在类被加载的时候执行且仅会被执行一次，一般用来初始化静态变量和调用静态方法
    * 参考：https://www.cnblogs.com/caolaoshi/p/7824748.html*/
    static {
        sp = PreferenceManager.getDefaultSharedPreferences(WoWeatherApplication.getContext());
    }

    public static void putBoolean(String key,boolean value){
        sp.edit().putBoolean(key, value).commit();
    }

    public static Boolean getBoolean(String key,boolean value){
        return sp.getBoolean(key,value);
    }

    public static void putString(String key,String value){
        sp.edit().putString(key, value).commit();
    }

    public static String getString(String key,String value){
        return sp.getString(key,value);
    }
}
