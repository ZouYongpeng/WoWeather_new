package com.example.woweather_new.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.woweather_new.R;
import com.example.woweather_new.base.BaseActivity;
import com.example.woweather_new.base.WoWeatherApplication;
import com.example.woweather_new.bean.CollectionData;
import com.example.woweather_new.bean.gson.Forecast;
import com.example.woweather_new.bean.gson.Weather;
import com.example.woweather_new.db.PlaceDBManager;
import com.example.woweather_new.db.SharedPreferencesUtil;
import com.example.woweather_new.util.HttpUtil;
import com.example.woweather_new.util.TranslateUtil;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherInfoActivity extends BaseActivity {

    @BindView(R.id.bing_pic_img)
    ImageView mBingPicImg;
    @BindView(R.id.title_back)
    ImageView mTitleBack;
    @BindView(R.id.title_collect)
    ImageView mTitleCollect;
    @BindView(R.id.title_city)
    TextView mTitleCity;
    @BindView(R.id.title_update_time)
    TextView mTitleUpdateTime;
    @BindView(R.id.tirle_share)
    ImageView mTirleShare;
    @BindView(R.id.degree_text)
    TextView mDegreeText;
    @BindView(R.id.weather_info_text)
    TextView mWeatherInfoText;
    @BindView(R.id.forecast_layout)
    LinearLayout mForecastLayout;
    @BindView(R.id.aqi_text)
    TextView mAqiText;
    @BindView(R.id.pm25_text)
    TextView mPm25Text;
    @BindView(R.id.comfort_text)
    TextView mComfortText;
    @BindView(R.id.car_wash_text)
    TextView mCarWashText;
    @BindView(R.id.sport_text)
    TextView mSportText;
    @BindView(R.id.weather_layout)
    ScrollView mWeatherLayout;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.goToWEB)
    TextView mGoToWEB;

    CollectionData collectionData;
    private int id;
    private String weatherId;
    private String placeName;
    ProgressDialog mDialog;

    private String weatherUrl;
    private boolean existing;
    private Weather mWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*融合背景图与状态栏*/
        if (Build.VERSION.SDK_INT>=21){
            View decorView=getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_weather_info);
        ButterKnife.bind(this);

        Bundle bundle=getIntent().getExtras();
//        id=bundle.getInt("id");
        weatherId=bundle.getString("weatherId");
        placeName=bundle.getString("placeName");
        mTitleCity.setText(placeName);

        mDialog=new ProgressDialog(this);
        mDialog.setMessage("正在获取"+placeName+"的资料");
        mSwipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestWeather(weatherId);
            }
        });

        requestWeather(weatherId);
    }

    public void requestWeather(final String weatherId){
        mDialog.show();
//        /*如果为当前定位地点，那么不显示收藏*/
        checkCollection();
//        if (placeName.equals(WoWeatherApplication.getLocalCountyName())){
//            mTitleCollect.setVisibility(View.GONE);
//            mTitleCollect.setClickable(false);
//        }else {
//            if (PlaceDBManager.getInstance(WoWeatherApplication.getContext()).isExisting(weatherId)){
//                //是收藏夹的内容
//                mTitleCollect.setImageResource(R.drawable.ic_collect);
//                existing=true;
//            }else {
//                mTitleCollect.setImageResource(R.drawable.ic_collect_no);
//                existing=false;
//            }
//        }

        if (!TextUtils.isEmpty(weatherId)){
            weatherUrl="http://guolin.tech/api/weather?cityid=" +weatherId
                    +"&key=a41e6909fcad45289af37f6344d0580e";
            log("requestWeather: 正在访问"+weatherUrl);
            HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    mDialog.dismiss();
                    toast("无法获取数据,请检查网络");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String responseText=response.body().string();
                    log(responseText);
                    final Weather weather= HttpUtil.handleWeatherResponse(responseText);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (weather!=null && weather.status.equals("ok")){
                                showWeatherInfo(weatherId,weather);
                            }else {
                                Toast.makeText(WeatherInfoActivity.this,"无法获取天气信息",Toast.LENGTH_SHORT).show();
                                String imageBGUrl= SharedPreferencesUtil.getString(SharedPreferencesUtil.IMAGE_BG,null);
                                Glide.with(WeatherInfoActivity.this).load(imageBGUrl).into(mBingPicImg);
                            }
                            mDialog.dismiss();
                            mSwipeRefresh.setRefreshing(false);
                        }
                    });
                }
            });

        }
    }

    @OnClick({R.id.title_back, R.id.title_collect, R.id.tirle_share,R.id.goToWEB})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_collect:
                if (existing){
                    //取消收藏
                    try{
                        PlaceDBManager.getInstance(WoWeatherApplication.getContext()).deleteCollectionData(weatherId);
                        toast("成功取消收藏");
                        checkCollection();
                    }catch (Exception e){
                        toast("取消收藏失败");
                    }
                }else {
                    //加入收藏
                    PlaceDBManager.getInstance(WoWeatherApplication.getContext()).insertCollectionData(mWeather);
                    toast("成功添加收藏");
                    checkCollection();
                }
                break;
            case R.id.tirle_share:
                break;
            case R.id.goToWEB:
                String pingying=PlaceDBManager.getInstance(WoWeatherApplication.getContext()).searchPinYin(weatherId);
                if (TextUtils.isEmpty(pingying)){
                    toast("该地点不支持外站查询");
                }else {
                    String WEBUrl="https://www.tianqi.com/"+pingying+"/";
                    Intent intent=new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(WEBUrl));
                    startActivity(intent);
                }

                break;
        }
    }

    private void checkCollection(){
        /*如果为当前定位地点，那么不显示收藏*/
        if (placeName.equals(WoWeatherApplication.getLocalCountyName())){
            mTitleCollect.setVisibility(View.GONE);
            mTitleCollect.setClickable(false);
        }else {
            if (PlaceDBManager.getInstance(WoWeatherApplication.getContext()).isExisting(weatherId)){
                //是收藏夹的内容
                mTitleCollect.setImageResource(R.drawable.ic_collect);
                existing=true;
            }else {
                mTitleCollect.setImageResource(R.drawable.ic_collect_no);
                existing=false;
            }
        }
    }

    private void showWeatherInfo(String weatherId,Weather weather){
        mWeather=weather;
        String cityName=weather.basic.cityName;
        String updateTime= TranslateUtil.transUpdateTime(weather.basic.update.updateTime);
        String degree=weather.now.temperature+"℃";
        String weatherInfo=weather.now.more.info;

        mTitleUpdateTime.setText(updateTime);
        mDegreeText.setText(degree);
        mWeatherInfoText.setText(weatherInfo);
        //显示收藏按钮

        mForecastLayout.removeAllViews();
        for (Forecast forecast:weather.forecastList){
            View view= LayoutInflater.from(this).inflate(R.layout.forecast_item,mForecastLayout,false);
            TextView dateText=(TextView) view.findViewById(R.id.date_text);
            TextView infoText=(TextView) view.findViewById(R.id.info_text);
            TextView maxText=(TextView) view.findViewById(R.id.max_text);
            TextView minText=(TextView) view.findViewById(R.id.min_text);

            dateText.setText(forecast.date);
            infoText.setText(forecast.more.info);
            maxText.setText(forecast.temperature.max);
            minText.setText(forecast.temperature.min);
            mForecastLayout.addView(view);
        }

        if (weather.aqi!=null){
            mAqiText.setText(weather.aqi.city.aqi);
            mPm25Text.setText(weather.aqi.city.pm25);
        }
        String comfort="舒适度："+weather.suggestion.comfort.info;
        String carWash="洗车指数："+weather.suggestion.carwash.info;
        String sport="运动建议："+weather.suggestion.sport.info;

        mComfortText.setText(comfort);
        mCarWashText.setText(carWash);
        mSportText.setText(sport);
        mWeatherInfoText.setVisibility(View.VISIBLE);

        //根据天气加载图片
        int weatherPicUrl=TranslateUtil.getImage(weatherInfo);
        Glide.with(WeatherInfoActivity.this).load(weatherPicUrl).into(mBingPicImg);
    }
}
