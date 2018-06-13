package com.example.woweather_new.activity;

import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.woweather_new.PlaceDBManager;
import com.example.woweather_new.R;
import com.example.woweather_new.base.BaseActivity;
import com.example.woweather_new.base.WoWeatherApplication;
import com.example.woweather_new.util.PlaceDatabaseHelper;
import com.example.woweather_new.util.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LaunchActivity extends BaseActivity {

    @BindView(R.id.launch_bg)
    ImageView mLaunchBg;
    @BindView(R.id.launch_remind)
    TextView mLaunchRemind;

    private PlaceDBManager mDBManager;

    private static int successCount=0;

    private static final int FIND_PLACE_DONE=1;
    private static final int FIND_PLACE_FAIL=2;
    private static final int SAVE_EXCEL_TO_DB_DONE=3;
    private static final int SAVE_EXCEL_TO_DB_FAIL=4;


    public LocationClient mLocationClient;

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case FIND_PLACE_DONE:
                    mLaunchRemind.setText("成功获取当前位置");
                    startActivity(MainActivity.class,null,false);
                    break;
                case FIND_PLACE_FAIL:
                    mLaunchRemind.setText("获取当前位置失败");
                    startActivity(MainActivity.class,null,false);
                    break;
                case SAVE_EXCEL_TO_DB_DONE:
                    mLaunchRemind.setText("成功获取位置数据");
                    requestLocation();
                    break;
                case SAVE_EXCEL_TO_DB_FAIL:
                    mLaunchRemind.setText("获取位置数据失败");
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取一个全局的context参数并传入
        mLocationClient=new LocationClient(getApplicationContext());
        //注册一个定位监听器，当获取到位置信息时，就会回调这个定位监听器
        mLocationClient.registerLocationListener(new MyLocationListener());
        setContentView(R.layout.activity_launch);
        ButterKnife.bind(this);

        checkPemission();
    }

    //检查是否申请权限
    private void checkPemission(){
        //创建一个新的list集合，以此判断这三个权限有没有授权。没授权就添加
        List<String> permissionList=new ArrayList<>();
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (!permissionList.isEmpty()){
            String[] permissions=permissionList.toArray(new String[permissionList.size()]);
            //将未授权的权限一次性申请
            ActivityCompat.requestPermissions(this,permissions,1);
        }else {
            //开始地理位置定位，定位结果会回到定位监听器mLocationClient
//            requestLocation();
            saveExcelToDB();
        }
    }

    private void requestLocation(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message=new Message();
                try {
                    LocationClientOption option=new LocationClientOption();
//              option.setScanSpan(100);//时间间隔为1小时
                    option.setIsNeedAddress(true);//设置需要获取详细地址信息
                    mLocationClient.setLocOption(option);
                    mLocationClient.start();
                    message.what=FIND_PLACE_DONE;
                    mHandler.sendMessage(message);
                }catch (Exception e){
                    message.what=FIND_PLACE_FAIL;
                    mHandler.sendMessage(message);
                }
            }
        }).start();
    }

    private void saveExcelToDB(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message=new Message();
                try{
                    mDBManager = PlaceDBManager.getInstance(WoWeatherApplication.getContext());
                    message.what=SAVE_EXCEL_TO_DB_DONE;
                    mHandler.sendMessage(message);
                }catch (Exception e){
                    message.what=SAVE_EXCEL_TO_DB_FAIL;
                    mHandler.sendMessage(message);
                }
            }
        }).start();
    }

    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            String localProvinceName=bdLocation.getProvince().replace("省", "");
            String localCityName=bdLocation.getCity().replace("市", "");
            String localCountyName=bdLocation.getDistrict().replace("区", "");
            WoWeatherApplication.setLocalProvinceName(localProvinceName);
            WoWeatherApplication.setLocalCityName(localCityName);
            WoWeatherApplication.setLocalCountyName(localCountyName);
            SharedPreferencesUtil.putString(SharedPreferencesUtil.LOCAL_PROVINCE,localProvinceName);
            SharedPreferencesUtil.putString(SharedPreferencesUtil.LOCAL_CITY,localCityName);
            SharedPreferencesUtil.putString(SharedPreferencesUtil.LOCAL_COUNTY,localCountyName);
            log("baiduLBS获取位置：" +WoWeatherApplication.getLocalProvinceName() + " " + WoWeatherApplication.getLocalCityName() + " " + WoWeatherApplication.getLocalCountyName());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();//停止定位
//        unregisterReceiver(networkChangeReceiver);
    }

    //若未同意权限则再次询问
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length>0){
                    for (int result:grantResults){
                        if (result!=PackageManager.PERMISSION_GRANTED){
                            //有一个权限不同意就会退出
                            toast("必须同意所有权限才能使用本程序");
                            finish();
                            return;
                        }
                        saveExcelToDB();
                    }
                }else {
                    toast("发生未知错误");
                    finish();
                }
                break;
            default:
        }
    }

}
