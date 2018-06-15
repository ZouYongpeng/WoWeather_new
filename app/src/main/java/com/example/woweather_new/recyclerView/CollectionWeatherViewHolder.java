package com.example.woweather_new.recyclerView;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.woweather_new.R;
import com.example.woweather_new.bean.CollectionData;
import com.example.woweather_new.util.TranslateUtil;

/**
 * Created by 邹永鹏 on 2018/6/15.
 */

public class CollectionWeatherViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener,View.OnLongClickListener{

    private static final String TAG="MainActivity_ViewHolder";

    CollectionData mCollectionData=new CollectionData();

    private ImageView weather_image;
    private ImageView weather_refresh;
    private TextView place_name;
    private TextView card_tmp;
    private TextView card_cond;
    private TextView card_updateTime;
    private TextView weather_degree;

    public CollectionWeatherViewHolder(View view){
        super(view);
        weather_image=(ImageView)view.findViewById(R.id.weather_image);
        weather_refresh=(ImageView)view.findViewById(R.id.weather_refresh);
        place_name=(TextView)view.findViewById(R.id.place_name);
        card_tmp=(TextView)view.findViewById(R.id.card_tmp);
        card_cond=(TextView)view.findViewById(R.id.card_cond);
        card_updateTime=(TextView)view.findViewById(R.id.card_updateTime);
        weather_degree=(TextView)view.findViewById(R.id.weather_degree);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        weather_refresh.setOnClickListener(this);
    }

    public void bind(CollectionData data){
        mCollectionData=data;
        weather_image.setImageResource(TranslateUtil.getImage(data.getCond()));
        place_name.setText(data.getCityName()+"·"+data.getPlaceName());
        card_tmp.setText(data.getTmp()+"℃");
        card_cond.setText(data.getCond());
        card_updateTime.setText(TranslateUtil.transUpdateTime(data.getUpdateTime()));
        weather_degree.setText(TranslateUtil.transAqiToDgree(data.getAqi()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.weather_refresh:
                Log.d(TAG, "onClick: 刷新该卡片");
                break;
            default:
                Log.d(TAG, "onClick: 查看 "+mCollectionData.getPlaceName());
                break;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}
