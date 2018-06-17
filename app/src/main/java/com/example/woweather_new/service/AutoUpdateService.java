package com.example.woweather_new.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

import com.bumptech.glide.Glide;
import com.example.woweather_new.activity.MainActivity;
import com.example.woweather_new.bean.CollectionData;
import com.example.woweather_new.db.PlaceDBManager;
import com.example.woweather_new.db.SharedPreferencesUtil;
import com.example.woweather_new.util.HttpUtil;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AutoUpdateService extends Service {

    public AutoUpdateService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        updateWeather();
        updateBingBG();
        AlarmManager manager=(AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour=2*60*60*1000;//每两小时更新一次
        long triggerAtTime= SystemClock.elapsedRealtime()+anHour;
        Intent otherIntent=new Intent(this,AutoUpdateService.class);
        PendingIntent pi=PendingIntent.getService(this,0,otherIntent,0);
        manager.cancel(pi);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /*更新天气信息*/
    private void updateWeather(){
        List<CollectionData> CollectionDatas= PlaceDBManager.getInstance(getApplicationContext()).showCollectTable();
        int listSize=CollectionDatas.size();
        for (int i=0;i<listSize;i++){
            CollectionData data=CollectionDatas.get(i);
            HttpUtil.requestWeather(data.getWeatherId());
        }
    }

    /*更新每日一图*/
    private void updateBingBG(){
        String requestImageURL="http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestImageURL, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String image=response.body().string();
                SharedPreferencesUtil.putString(SharedPreferencesUtil.IMAGE_BG,image);
            }
        });
    }
}
