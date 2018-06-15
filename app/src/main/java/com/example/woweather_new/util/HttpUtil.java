package com.example.woweather_new.util;

import android.util.Log;

import com.example.woweather_new.base.WoWeatherApplication;
import com.example.woweather_new.bean.CollectionData;
import com.example.woweather_new.bean.gson.Weather;
import com.example.woweather_new.db.PlaceDBManager;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 邹永鹏 on 2018/6/13.
 */

public class HttpUtil {

    private static final String TAG="MainActivity_";

    //向服务器发送请求
    public static void sendOkHttpRequest(String address,okhttp3.Callback callback){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }

    public static void requestWeather(final int position,final String weatherId){
        String weatherUrl="http://guolin.tech/api/weather?cityid=" +weatherId
                +"&key=a41e6909fcad45289af37f6344d0580e";
        Log.d(TAG, "requestWeather: 正在访问"+weatherUrl);
        sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: 无法获取"+weatherId+"的天气信息");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText=response.body().string();
                Log.d(TAG, "onResponse: "+responseText);
                /*OkHttpClient已经自动打开子线程、不要自己再开一个子线程*/
                Weather weather= handleWeatherResponse(responseText);
                PlaceDBManager.getInstance(WoWeatherApplication.getContext()).saveWeatherToCollection(weather,position+1);
            }
        });
    }

    //将返回的json数据解析成weather实体类
    public static Weather handleWeatherResponse(String response){
        try{
            JSONObject jsonObject=new JSONObject(response);
            JSONArray jsonArray=jsonObject.getJSONArray("HeWeather");
            String weatherContent=jsonArray.getJSONObject(0).toString();
            Log.d("success:","JSON解析："+weatherContent);
            return new Gson().fromJson(weatherContent,Weather.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
