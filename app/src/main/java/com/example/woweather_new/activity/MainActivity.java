package com.example.woweather_new.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.woweather_new.R;
import com.example.woweather_new.base.BaseActivity;
import com.example.woweather_new.base.WoWeatherApplication;
import com.example.woweather_new.db.PlaceDBManager;
import com.example.woweather_new.db.SharedPreferencesUtil;
import com.example.woweather_new.recyclerView.CollectionCardAdapter;
import com.example.woweather_new.service.AutoUpdateService;
import com.example.woweather_new.service.updateCollectionService;
import com.example.woweather_new.util.HttpUtil;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends BaseActivity {

    @BindView(R.id.image_bg)
    ImageView imageBG;
    @BindView(R.id.collect_card)
    RecyclerView mCardRecyclerView;
    @BindView(R.id.collect_card_refresh)
    SwipeRefreshLayout mCollectCardRefresh;

    private CollectionCardAdapter mAdapter;

    Intent updateCollectionService;

    private LocalBroadcastManager mLocalBroadcastManager;
    private upadteDataReceiver mUpadteDataReceiver;
    private IntentFilter mIntentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*融合背景图与状态栏*/
        if (Build.VERSION.SDK_INT>=21){
            View decorView=getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        updateCollectionService=new Intent(this,updateCollectionService.class);
//        startService(updateCollectionService);

        mLocalBroadcastManager=LocalBroadcastManager.getInstance(WoWeatherApplication.getContext());//获取实例
        mIntentFilter=new IntentFilter();
        mIntentFilter.addAction("com.example.woweather_new.collection_data_update");
        mUpadteDataReceiver=new upadteDataReceiver();
        mLocalBroadcastManager.registerReceiver(mUpadteDataReceiver,mIntentFilter);
;
        mCollectCardRefresh.setColorSchemeColors(getResources().getColor(R.color.colorAccent)); // 进度动画颜色
        mCollectCardRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startService(updateCollectionService);
                mCollectCardRefresh.setRefreshing(false);
            }
        });
        mCardRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        updateCollectionCardList();
        initImageBG();
    }

    public void initImageBG(){
        log("initImageBG()");
        String imageBGUrl= SharedPreferencesUtil.getString(SharedPreferencesUtil.IMAGE_BG,null);
        if (!TextUtils.isEmpty(imageBGUrl)){
            Glide.with(this).load(imageBGUrl).into(imageBG);
        }else {
            log("loadImageBG()");
            loadImageBG();
        }
    }

    private void loadImageBG(){
        String requestImageURL="http://guolin.tech/api/bing_pic";
        log("访问"+requestImageURL);
        HttpUtil.sendOkHttpRequest(requestImageURL, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String image=response.body().string();
                log("image= "+image);
                SharedPreferencesUtil.putString(SharedPreferencesUtil.IMAGE_BG,image);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(MainActivity.this).load(image).into(imageBG);
                    }
                });
            }
        });
    }

    class upadteDataReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateCollectionCardList();
        }
    }

    private void updateCollectionCardList(){
        mAdapter=new CollectionCardAdapter(PlaceDBManager.getInstance(WoWeatherApplication.getContext()).showCollectTable());
        mCardRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onDestroy() {
        /*激活自动更新服务*/
        Intent intent=new Intent(this, AutoUpdateService.class);
        startService(intent);
        super.onDestroy();
        mLocalBroadcastManager.unregisterReceiver(mUpadteDataReceiver);
        PlaceDBManager.getInstance(WoWeatherApplication.getContext()).close();
//        android.os.Process.killProcess(android.os.Process.myPid());
    }



}
