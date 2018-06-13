package com.example.woweather_new.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by 邹永鹏 on 2018/6/13.
 */

public class BaseActivity extends AppCompatActivity {



    private Toast mToast;

    public void toast(final Object object){
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mToast==null){
                        mToast=Toast.makeText(BaseActivity.this,"",Toast.LENGTH_SHORT);
                    }
                    mToast.setText(object.toString());
                    mToast.show();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void startActivity(Class<? extends Activity> target, Bundle bundle, boolean finished){
        Intent intent=new Intent(this,target);
        if (bundle!=null){
            intent.putExtra(getPackageName(),bundle);
        }
        startActivity(intent);
        if (finished){
            finish();
        }
    }

    public void log(String msg){
        Log.d(getLocalClassName()+"_zyp",msg);
    }

    public boolean doubleExitAppEnable() {
        return false;
    }

    @Override
    public void onBackPressed() {
        if(doubleExitAppEnable()) {//判断是否需要双击退出
            exitAppDoubleClick();
        } else {
            super.onBackPressed();
        }
    }

    /*双击退出函数变量*/
    private long exitTime = 0;

    /*双击退出APP*/
    private void exitAppDoubleClick() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            toast("再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            exitApp();
        }
    }

    /**
     * 退出APP
     */
    private void exitApp() {
        onDestroy();
        super.onBackPressed();
        System.exit(0);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}
