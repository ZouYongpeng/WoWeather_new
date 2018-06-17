package com.example.woweather_new.recyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.example.woweather_new.R;
import com.example.woweather_new.activity.WeatherInfoActivity;
import com.example.woweather_new.base.WoWeatherApplication;
import com.example.woweather_new.bean.PlaceData;
import com.example.woweather_new.db.PlaceDBManager;
import com.example.woweather_new.db.SharedPreferencesUtil;
import com.example.woweather_new.recyclerView.base.BaseRecyclerHolder;
import com.example.woweather_new.ui.ClearEditText;
import com.lljjcoder.citypickerview.widget.CityPicker;

import butterknife.BindView;

/**
 * Created by 邹永鹏 on 2018/6/16.
 * 开源框架citypickerview实现省市区三级联动选择
 * 参考地址：https://blog.csdn.net/panhouye/article/details/60872318
 */

public class ButtonsViewHolder extends BaseRecyclerHolder
        implements View.OnClickListener {

    private static final String TAG = "MainActivity_BtnHolder";

    @BindView(R.id.button_search)
    ImageView mButtonSearch;
//    @BindView(R.id.edit_search)
//    ClearEditText mEditSearch;

    boolean editSearchVisible=false;

    public ButtonsViewHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.collection_card_button);
//        mEditSearch.setVisibility(View.GONE);
        mButtonSearch.setOnClickListener(this);
    }

    public void bind(Object data) {
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_search:
//                toast("搜索");
                openSelector();
//                if (editSearchVisible){
//                    mEditSearch.setVisibility(View.GONE);
//                    editSearchVisible=false;
//                    //获取mEditSearch的值并搜索
//                    if (TextUtils.isEmpty(mEditSearch.getText().toString())){
//
//                    }
//                }else{
//                    mEditSearch.setVisibility(View.VISIBLE);
//                    editSearchVisible=true;
//                    openSelector();
//                }
                break;
//            case R.id.edit_search:
//                openSelector();
//                break;
        }
    }

    private void openSelector(){
        //判断输入法的隐藏状态
        InputMethodManager imm = (InputMethodManager) itemView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(itemView.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
            selectAddress();//调用CityPicker选取区域
        }
    }

    private void selectAddress() {
        CityPicker cityPicker = new CityPicker.Builder(itemView.getContext())
                .textSize(14)
                .title("选择地区")
                .titleBackgroundColor("#FFFFFF")
//                .titleTextColor("#696969")
                .confirTextColor("#696969")
                .cancelTextColor("#696969")
                .province(SharedPreferencesUtil.getString(SharedPreferencesUtil.LOCAL_PROVINCE,""))
                .city(SharedPreferencesUtil.getString(SharedPreferencesUtil.LOCAL_CITY,""))
                .district(SharedPreferencesUtil.getString(SharedPreferencesUtil.LOCAL_COUNTY,""))
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();
        cityPicker.show();
        //监听方法，获取选择结果
        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                String province = citySelected[0];//省份
                String city = citySelected[1];//城市
                String district = citySelected[2];//区县（如果设定了两级联动，那么该项返回空）
                String code = citySelected[3];//邮编
                String placeName=district.trim().replace("区","");
                if (placeName.equals("其他")){
                    toast("请不要选择“其他”");
                }else {
                    openWeatherInfo(placeName);
                }
            }
        });
    }

    private void openWeatherInfo(String countyName){
        countyName=countyName.substring(0,2);
        Log.d(TAG, "openWeatherInfo: 正在搜索"+countyName);
        PlaceData placeData= PlaceDBManager.getInstance(WoWeatherApplication.getContext()).getPlaceDataBySelector(countyName);
        Log.d(TAG, "openWeatherInfo: "+placeData.toString());
        Intent intent = new Intent(itemView.getContext(),WeatherInfoActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("weatherId",placeData.getWeatherId());
        bundle.putString("placeName",placeData.getPlaceName());
        intent.putExtras(bundle);
        itemView.getContext().startActivity(intent);
    }
}
