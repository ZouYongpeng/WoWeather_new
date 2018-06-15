package com.example.woweather_new.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.woweather_new.bean.CollectionData;
import com.example.woweather_new.db.PlaceDBManager;
import com.example.woweather_new.util.HttpUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 邹永鹏 on 2018/6/15.
 */

public class updateCollectionService extends IntentService {

    private static final String TAG="MainActivity_Service";

    public updateCollectionService(){
        super("updateCollectionService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG, "onHandleIntent: start");
        List<CollectionData> CollectionDatas= PlaceDBManager.getInstance(getApplicationContext()).showCollectTable();
        int listSize=CollectionDatas.size();
        for (int i=0;i<listSize;i++){
            CollectionData data=CollectionDatas.get(i);
            HttpUtil.requestWeather(i,data.getWeatherId());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
