package com.example.woweather_new.util;

import android.text.TextUtils;
import android.util.Log;

import com.example.woweather_new.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 邹永鹏 on 2018/6/15.
 */

public class TranslateUtil {

    private static final String TAG="trans";

    public static int getIcon(String condData){
        int weatherPicUrl= R.drawable.weather_default;
        switch (condData){
            case "多云":
                weatherPicUrl=R.drawable.cloudy;
                break;
            case "晴":
                weatherPicUrl=R.drawable.sunny;
                break;
            case "小雨":
                weatherPicUrl=R.drawable.small_rain;
                break;
            case "阵雨":
                weatherPicUrl=R.drawable.heavy_rain;
                break;
            case "晴间多云":
                weatherPicUrl=R.drawable.partly_cloudy;
                break;
            case "阴":
                weatherPicUrl=R.drawable.overcast;
                break;
            case "雾":
                weatherPicUrl=R.drawable.foggy;
                break;
            default:
                break;
        }
        return weatherPicUrl;
    }

    public static String transAqiToDgree(String aqiData){
        String dgree="NA";
        if (TextUtils.isEmpty(aqiData)||aqiData.equals("NA")){
            return dgree;
        }
        int aqi=Integer.parseInt(aqiData);
        if (aqi<=50) {
            dgree = "优";
        }else if (aqi<=100){
            dgree = "良";
        }else if (aqi<=150){
            dgree = "轻度污染";
        }else if (aqi<=200){
            dgree = "中度污染";
        }else if (aqi<=300){
            dgree = "重度污染";
        }else {
            dgree = "严重污染";
        }
        return dgree;
    }

    /*事件转换，如2018-6-16 00:21转化为00:21*/
    public static String transUpdateTime(String time){
        SimpleDateFormat formatter=new SimpleDateFormat("HH:mm");
        Date curDate=new Date(System.currentTimeMillis());//获取当前时间
        String transTime=formatter.format(curDate);
        Log.d(TAG, "transUpdateTime: currentTime="+transTime);
        if (TextUtils.isEmpty(time)||time.equals("NA")){
            return transTime;
        }else {
            String []a=time.split(" ");
            if (a.length>1){
                time=a[1];
            }
            return time;
        }
    }

    public static int getImage(String weatherInfo){
        int weatherPicUrl=R.drawable.default_weather;
        switch (weatherInfo){
            case "多云":
                weatherPicUrl=R.drawable.image_cloud;
                break;
            case "晴":
                weatherPicUrl=R.drawable.image_sunny;
                break;
            case "小雨":
                weatherPicUrl=R.drawable.image_rain;
                break;
            case "阵雨":
                weatherPicUrl=R.drawable.image_rain;
                break;
            case "晴间多云":
                weatherPicUrl=R.drawable.image_sunny_cloud;
                break;
            case "阴":
                weatherPicUrl=R.drawable.image_overcast;
                break;
            case "雾":
                weatherPicUrl=R.drawable.image_fog;
                break;
            default:
                break;
        }
        return weatherPicUrl;
    }

//    public static String changePlaceName(String name){
//        String[] word={"区","市","镇","","","","","","","","","","","","","","","",};
//    }
}
