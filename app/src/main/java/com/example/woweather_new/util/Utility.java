package com.example.woweather_new.util;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 邹永鹏 on 2018/6/13.
 */

public class Utility {

//    /*解析和处理服务器返回的省级数据*/
//    public static boolean handleProvinceResponse(String response){
//        if (!TextUtils.isEmpty(response)){//返回数据不为空
//            try{
//                JSONArray allpProvinces=new JSONArray(response);
//                for (int i=0;i<allpProvinces.length();i++){
//                    JSONObject provinceObject=allpProvinces.getJSONObject(i);
//                    Province province=new Province();
//                    province.setProvinceName(provinceObject.getString("name"));
//                    province.setProvinceCode(provinceObject.getInt("id"));
//                    province.save();
//                    Log.d("local",province.getProvinceName());
//                }
//                return true;
//            }catch (JSONException e){
//                e.printStackTrace();
//            }
//        }
//        return false;
//    }

//    //将返回的json数据解析成weather实体类
//    public static Weather handleWeatherResponse(String response){
//        try{
//            JSONObject jsonObject=new JSONObject(response);
//            JSONArray jsonArray=jsonObject.getJSONArray("HeWeather");
//            String weatherContent=jsonArray.getJSONObject(0).toString();
//            Log.d("success:","JSON解析："+weatherContent);
//            return new Gson().fromJson(weatherContent,Weather.class);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return null;
//    }
}
