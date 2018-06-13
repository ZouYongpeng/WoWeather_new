package com.example.woweather_new.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by 邹永鹏 on 2018/6/13.
 * 自定义Application，还要在AndroidManifest.xml中配置
 */

public class WoWeatherApplication extends Application {

    private static Context sContext;

    private static String localProvinceName;
    private static String localCityName;
    private static String localCountyName;

    private static int localProvinceId;
    private static int localCityId;
    private static int localWeatherId;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext=getApplicationContext();
    }

    public static Context getContext(){
        return sContext;
    }

    public static String getLocalProvinceName() {
        return localProvinceName;
    }

    public static void setLocalProvinceName(String localProvinceName) {
        WoWeatherApplication.localProvinceName = localProvinceName;
    }

    public static String getLocalCityName() {
        return localCityName;
    }

    public static void setLocalCityName(String localCityName) {
        WoWeatherApplication.localCityName = localCityName;
    }

    public static String getLocalCountyName() {
        return localCountyName;
    }

    public static void setLocalCountyName(String localCountyName) {
        WoWeatherApplication.localCountyName = localCountyName;
    }

    public static int getLocalProvinceId() {
        return localProvinceId;
    }

    public static void setLocalProvinceId(int localProvinceId) {
        WoWeatherApplication.localProvinceId = localProvinceId;
    }

    public static int getLocalCityId() {
        return localCityId;
    }

    public static void setLocalCityId(int localCityId) {
        WoWeatherApplication.localCityId = localCityId;
    }

    public static int getLocalWeatherId() {
        return localWeatherId;
    }

    public static void setLocalWeatherId(int localWeatherId) {
        WoWeatherApplication.localWeatherId = localWeatherId;
    }
}
