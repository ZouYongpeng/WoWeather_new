package com.example.woweather_new.recyclerView;

import android.nfc.Tag;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.woweather_new.R;
import com.example.woweather_new.bean.CollectionData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 邹永鹏 on 2018/6/15.
 */

public class CollectionCardAdapter extends RecyclerView.Adapter<CollectionWeatherViewHolder> {

    private static final String TAG="MainActivity_";

    private List<CollectionData> mCollectionDataList=new ArrayList<>();

    public CollectionCardAdapter (List<CollectionData> collectionDataList){
        this.mCollectionDataList=collectionDataList;
        for (CollectionData data:mCollectionDataList){
            Log.d(TAG,data.toString());
        }
    }

    @Override
    public CollectionWeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.collection_card_item,parent,false);
        CollectionWeatherViewHolder collectionWeatherViewHolder=new CollectionWeatherViewHolder(view);
        return collectionWeatherViewHolder;
    }

    @Override
    public void onBindViewHolder(CollectionWeatherViewHolder holder, int position) {
        CollectionData collectionData=mCollectionDataList.get(position);
        holder.bind(collectionData);
    }

    @Override
    public int getItemCount() {
        return mCollectionDataList.size();
    }
}
