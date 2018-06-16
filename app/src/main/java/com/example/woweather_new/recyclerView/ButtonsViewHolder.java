package com.example.woweather_new.recyclerView;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.example.woweather_new.R;
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

    private static final String TAG = "MainActivity_ButtonsViewHolder";

    @BindView(R.id.button_search)
    ImageView mButtonSearch;
    @BindView(R.id.edit_search)
    ClearEditText mEditSearch;

    boolean editSearchVisible=false;

    public ButtonsViewHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.collection_card_button);
        mEditSearch.setVisibility(View.GONE);
        mButtonSearch.setOnClickListener(this);
    }

    public void bind(Object data) {
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_search:
                toast("搜索");
                if (editSearchVisible){
                    mEditSearch.setVisibility(View.GONE);
                    editSearchVisible=false;
                    //获取mEditSearch的值并搜索

                }else{
                    mEditSearch.setVisibility(View.VISIBLE);
                    editSearchVisible=true;
                    openSelector();
                }
                break;
            case R.id.edit_search:
                openSelector();
                break;
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
                //省份
                String province = citySelected[0];
                //城市
                String city = citySelected[1];
                //区县（如果设定了两级联动，那么该项返回空）
                String district = citySelected[2];
                //邮编
                String code = citySelected[3];
                //为TextView赋值
                String provinceName=province.trim().replace("省","").trim();
                String cityName=province.trim().replace("市","").trim();
                String placeName=province.trim().replace("区","").trim();
//                mEditSearch.setText(province.trim() + "-" + city.trim() + "-" + district.trim());
                mEditSearch.setText(district.trim());
            }
        });
    }
}
