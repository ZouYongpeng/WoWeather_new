package com.example.woweather_new.activity;

import android.os.Bundle;

import com.example.woweather_new.db.PlaceDBManager;
import com.example.woweather_new.R;
import com.example.woweather_new.base.BaseActivity;
import com.example.woweather_new.base.WoWeatherApplication;

import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {


    private PlaceDBManager mDBManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mDBManager = PlaceDBManager.getInstance(WoWeatherApplication.getContext());
//        PlaceData placeData=mDBManager.getPlaceData("南海");
//        mainText.setText(placeData.getWeatherId());
//        log(placeData.toString());
    }

    /*RecyclerView适配器*/
//    public class CollectionCardAdapter extends RecyclerView.Adapter
}
