package com.example.woweather_new.recyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.woweather_new.R;
import com.example.woweather_new.activity.WeatherInfoActivity;
import com.example.woweather_new.bean.CollectionData;
import com.example.woweather_new.recyclerView.base.BaseRecyclerHolder;
import com.example.woweather_new.util.HttpUtil;
import com.example.woweather_new.util.TranslateUtil;

import butterknife.BindView;

/**
 * Created by 邹永鹏 on 2018/6/15.
 */

public class CollectionWeatherViewHolder extends BaseRecyclerHolder
        implements View.OnClickListener, View.OnLongClickListener {

    private static final String TAG = "MainActivity_ViewHolder";

    CollectionData mCollectionData = new CollectionData();
    @BindView(R.id.weather_image)
    ImageView weather_image;
    @BindView(R.id.place_name)
    TextView place_name;
    @BindView(R.id.card_tmp)
    TextView card_tmp;
    @BindView(R.id.card_cond)
    TextView card_cond;
    @BindView(R.id.card_updateTime)
    TextView card_updateTime;
    @BindView(R.id.weather_loading)
    ProgressBar weather_loading;
    @BindView(R.id.weather_refresh)
    ImageView weather_refresh;
    @BindView(R.id.weather_degree)
    TextView weather_degree;
    @BindView(R.id.card_view)
    CardView card_view;

    public CollectionWeatherViewHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.collection_card_item);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
        weather_refresh.setOnClickListener(this);
    }

    public void bind(Object data) {
        mCollectionData =(CollectionData) data;
        weather_image.setImageResource(TranslateUtil.getIcon(mCollectionData.getCond()));
        place_name.setText(mCollectionData.getCityName() + "·" + mCollectionData.getPlaceName());
        card_tmp.setText(mCollectionData.getTmp() + "℃");
        card_cond.setText(mCollectionData.getCond());
        card_updateTime.setText(TranslateUtil.transUpdateTime(mCollectionData.getUpdateTime()));
        weather_degree.setText(TranslateUtil.transAqiToDgree(mCollectionData.getAqi()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.weather_refresh:
                Log.d(TAG, "onClick: 刷新该卡片 "+mCollectionData.getId());
                weather_refresh.setVisibility(View.INVISIBLE);
                weather_loading.setVisibility(View.VISIBLE);
                int position = Integer.valueOf(mCollectionData.getId());
                HttpUtil.requestWeather(mCollectionData.getWeatherId());//position,
                break;
            default:
                Log.d(TAG, "onClick: 查看 " + mCollectionData.getPlaceName());
                Intent intent = new Intent(itemView.getContext(),WeatherInfoActivity.class);
                Bundle bundle=new Bundle();
//                bundle.putInt("id",mCollectionData.getId());
                bundle.putString("weatherId",mCollectionData.getWeatherId());
                bundle.putString("placeName",mCollectionData.getPlaceName());
                intent.putExtras(bundle);
//                intent.putExtra("collectionData",mCollectionData);
                itemView.getContext().startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}
