package com.example.woweather_new.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by 邹永鹏 on 2018/6/13.
 */

public class PlaceDatabaseHelper extends SQLiteOpenHelper {

    public static final String TAG="PlaceDatabaseHelper";

    public static String DATA_BASE_NAME = "WoWeather.db";

    public static String TABLE_PLACE_NAME="place";

    public static String TABLE_COLLECTION_NAME="collection";

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

    public static final String CREATE_COLLECITON="create table if not exists "
            +TABLE_COLLECTION_NAME
            +" ("
            +"id integer primary key autoincrement, "
            +"weatherId text, "
            +"placeName text, "
            +"provinceName text, "
            +"cityName text, "
            +"tmp text, "   //气温
            +"cond text, "  //具体内容
            +"aqi text, "   //aqi指数
            +"pm25 text, "  //pm2.5指数
            +"updateTime text)";//更新时间

    /*在收藏表创建第一条数据*/
    public static final String INSERT_FIRST_LOCAL_TO_COLLECTION="insert into "
            +TABLE_COLLECTION_NAME
            +" (weatherId,placeName,provinceName,cityName,tmp,cond,aqi,pm25,updateTime)"
            +" values(?,?,?,?,?,?,?,?,?)";

    private Context mContext;

    public PlaceDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PLACE);
        db.execSQL(CREATE_COLLECITON);
        db.execSQL(INSERT_FIRST_LOCAL_TO_COLLECTION,new String[]{"","","","","","","","",""});
        Log.d(TAG,"sql create success");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if ( oldVersion != newVersion ){
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_PLACE_NAME);
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_COLLECTION_NAME);
            onCreate(db);
        }
    }
}
