package com.example.woweather_new.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by 邹永鹏 on 2018/6/13.
 */

public class PlaceDatabaseHelper extends SQLiteOpenHelper {

    public static final String TAG="PlaceDatabaseHelper";

    public static String DATA_BASE_NAME = "WoWeather.db";

    public static String TABLE_PLACE_NAME="place";

    private static final int VERSION = 1;

    private static PlaceDatabaseHelper instance;

    public static PlaceDatabaseHelper getInstance(Context context ) {
        if (instance == null) {
            instance = new PlaceDatabaseHelper(context);
        }
        return instance;
    }

    public PlaceDatabaseHelper(Context context){
        super(context, DATA_BASE_NAME, null, VERSION);
    }

    public static final String CREATE_PLACE="create table if not exists "
            +TABLE_PLACE_NAME
            +" ("
            +"weatherId text, "
            +"placeEng text, "
            +"placeName text, "
            +"countryCode text, "
            +"countryEng text, "
            +"countryName text, "
            +"provinceEng text, "
            +"provinceName text, "
            +"cityEng text, "
            +"cityName text, "
            +"latitude real, "
            +"longitude real, "
            +"adCode integer)";

    private Context mContext;

    public PlaceDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PLACE);
        Log.d(TAG,"sql create success");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if ( oldVersion != newVersion ){
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_PLACE_NAME);
            onCreate(db);
        }
    }
}
