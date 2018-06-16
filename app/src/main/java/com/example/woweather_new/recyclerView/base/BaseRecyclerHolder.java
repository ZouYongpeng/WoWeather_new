package com.example.woweather_new.recyclerView.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.woweather_new.base.BaseActivity;
import com.example.woweather_new.bean.CollectionData;

import butterknife.ButterKnife;

/**
 * Created by 邹永鹏 on 2018/6/15.
 */

public abstract class BaseRecyclerHolder<T> extends RecyclerView.ViewHolder {

    /*参考资料：https://blog.csdn.net/stzy00/article/details/45035301*/
//    private final SparseArray<View> mViews;
//    public  int layoutId;

    protected Context mContext;
    public BaseRecyclerHolder(Context context, ViewGroup parent,
                              int layoutResId) {
        super(LayoutInflater.from(context).inflate(layoutResId,parent,false));
        this.mContext=context;
        ButterKnife.bind(this,itemView);
//        this.mViews = new SparseArray<>(8);
    }

    public Context getContext(){
//        return mContext;
        return itemView.getContext();
    }

    public abstract void bind(T data);

    private Toast mToast;
    public void toast(final Object obj){
        try {
            ((BaseActivity)mContext).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mToast==null){
                        mToast=Toast.makeText(mContext,"",Toast.LENGTH_SHORT);
                    }
                    if (obj!=null){
                        mToast.setText(obj.toString());
                    }
                    mToast.show();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*启动指定activity*/
    public void startActivity(Class<? extends Activity> target, Bundle bundle){
        Intent intent=new Intent();
        intent.setClass(getContext(),target);
        if (bundle!=null){
            intent.putExtra(getContext().getPackageName(),bundle);
        }
        getContext().startActivity(intent);
    }

//    public BaseRecyclerHolder(int layoutId,View itemView) {
//        super(itemView);
//        this.layoutId =layoutId;
//        this.mViews = new SparseArray<>(8);
//    }
//
//    public SparseArray<View> getAllView() {
//        return mViews;
//    }
//
//    /*根据id获取View*/
//    protected<T extends View> T getView(int viewId){
//        View view = mViews.get(viewId);
//        if (view == null) {
//            view = itemView.findViewById(viewId);
//            mViews.put(viewId, view);
//        }
//        return (T) view;
//    }
//
//    public BaseRecyclerHolder setText(int viewId, String text) {
//        TextView view = getView(viewId);
//        view.setText(text);
//        return this;
//    }
//
//    public BaseRecyclerHolder setEnabled(int viewId,boolean enable){
//        View v = getView(viewId);
//        v.setEnabled(enable);
//        return this;
//    }
//
//    public BaseRecyclerHolder setOnClickListener(int viewId, View.OnClickListener listener){
//        View v = getView(viewId);
//        v.setOnClickListener(listener);
//        return this;
//    }
//
//    public BaseRecyclerHolder setVisible(int viewId,int visibility) {
//        View view = getView(viewId);
//        view.setVisibility(visibility);
//        return this;
//    }
//
//    public BaseRecyclerHolder setImageResource(int viewId, int drawableId) {
//        ImageView view = getView(viewId);
//        view.setImageResource(drawableId);
//        return this;
//    }
//
//    public BaseRecyclerHolder setImageBitmap(int viewId, Bitmap bm) {
//        ImageView view = getView(viewId);
//        view.setImageBitmap(bm);
//        return this;
//    }

//    public BaseRecyclerHolder setImageView(String avatar, int defaultRes, int viewId) {
//        ImageView iv = getView(viewId);
////        ImageLoaderFactory.getLoader().loadAvator(iv,avatar, defaultRes);
//        iv.setBackground(defaultRes);
//        return this;
//    }
}
