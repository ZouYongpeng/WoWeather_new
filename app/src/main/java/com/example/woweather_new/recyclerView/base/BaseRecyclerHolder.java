package com.example.woweather_new.recyclerView.base;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by 邹永鹏 on 2018/6/15.
 */

public class BaseRecyclerHolder extends RecyclerView.ViewHolder {

    /*参考资料：https://blog.csdn.net/stzy00/article/details/45035301*/
    private final SparseArray<View> mViews;
    public  int layoutId;

    public BaseRecyclerHolder(int layoutId,View itemView) {
        super(itemView);
        this.layoutId =layoutId;
        this.mViews = new SparseArray<>(8);
    }

    public SparseArray<View> getAllView() {
        return mViews;
    }

    /*根据id获取View*/
    protected<T extends View> T getView(int viewId){
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public BaseRecyclerHolder setText(int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    public BaseRecyclerHolder setEnabled(int viewId,boolean enable){
        View v = getView(viewId);
        v.setEnabled(enable);
        return this;
    }

    public BaseRecyclerHolder setOnClickListener(int viewId, View.OnClickListener listener){
        View v = getView(viewId);
        v.setOnClickListener(listener);
        return this;
    }

    public BaseRecyclerHolder setVisible(int viewId,int visibility) {
        View view = getView(viewId);
        view.setVisibility(visibility);
        return this;
    }

    public BaseRecyclerHolder setImageResource(int viewId, int drawableId) {
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);
        return this;
    }

    public BaseRecyclerHolder setImageBitmap(int viewId, Bitmap bm) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bm);
        return this;
    }

//    public BaseRecyclerHolder setImageView(String avatar, int defaultRes, int viewId) {
//        ImageView iv = getView(viewId);
////        ImageLoaderFactory.getLoader().loadAvator(iv,avatar, defaultRes);
//        iv.setBackground(defaultRes);
//        return this;
//    }
}
