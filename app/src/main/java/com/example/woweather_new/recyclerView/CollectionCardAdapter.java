package com.example.woweather_new.recyclerView;

import android.nfc.Tag;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.woweather_new.R;
import com.example.woweather_new.bean.CollectionData;
import com.example.woweather_new.recyclerView.base.BaseRecyclerHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 邹永鹏 on 2018/6/15.
 */

public class CollectionCardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG="MainActivity_";

    private List<CollectionData> mCollectionDataList=new ArrayList<>();

    private final int TYPE_WEATHER_CARD=1;
    private final int TYPE_BUTTON=2;

    public CollectionCardAdapter (List<CollectionData> collectionDataList){
        this.mCollectionDataList=collectionDataList;
        for (CollectionData data:mCollectionDataList){
            Log.d(TAG,data.toString());
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_WEATHER_CARD:
                return new CollectionWeatherViewHolder(parent.getContext(),parent);
            case TYPE_BUTTON:
                return new ButtonsViewHolder(parent.getContext(),parent);
            default:
                return null;
        }
//        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.collection_card_item,parent,false);
//        CollectionWeatherViewHolder collectionWeatherViewHolder=new CollectionWeatherViewHolder(view);
//        return collectionWeatherViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position<mCollectionDataList.size()){
            CollectionData collectionData=mCollectionDataList.get(position);
            ((BaseRecyclerHolder)holder).bind(collectionData);
        }else {
            ((BaseRecyclerHolder)holder).bind(null);
        }

    }

    @Override
    public int getItemCount() {
        return mCollectionDataList.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position<mCollectionDataList.size()){
            return TYPE_WEATHER_CARD;
        }
        else {
            return TYPE_BUTTON;
        }
    }
}
