package com.example.woweather_new.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.woweather_new.base.WoWeatherApplication;
import com.example.woweather_new.bean.CollectionData;
import com.example.woweather_new.bean.PlaceData;
import com.example.woweather_new.bean.gson.Weather;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;


/**
 * Created by 邹永鹏 on 2018/6/13.
 */

public class PlaceDBManager {

    private static final String TAG = "PlaceDBManager";
    private static final String EXCEPTION = "exception";
    private PlaceDatabaseHelper mDBHelper = null;
    private static PlaceDBManager instance = null;
    private SQLiteDatabase db;

    public static PlaceDBManager getInstance(Context context) {
        if (instance == null) {
            instance = new PlaceDBManager(context);
        }
        return instance;
    }

    private PlaceDBManager(Context context) {
        mDBHelper = PlaceDatabaseHelper.getInstance(context);
        if (SharedPreferencesUtil.getBoolean(SharedPreferencesUtil.IS_READED_PLACE_EXCEL,true)) {
            readExcelToDB(context);
        }
        db = mDBHelper.getWritableDatabase();
    }

    /**
     * 读取excel数据到数据库里
     * @param context
     */
    private void readExcelToDB(Context context) {
        try {
            InputStream is = context.getAssets().open("WoWeather_Place.xls");
            /*获取工作簿*/
            Workbook book = Workbook.getWorkbook(is);
            book.getNumberOfSheets();
            // 获得第一个工作表对象
            Sheet sheet = book.getSheet(0);
            int Rows = sheet.getRows();
            PlaceData info = null;
            for (int i = 1; i < Rows; ++i) {
                String weatherId = (sheet.getCell(0, i)).getContents();
                String placeEng = (sheet.getCell(1, i)).getContents();
                String placeName = (sheet.getCell(2, i)).getContents();
                String countryCode = (sheet.getCell(3, i)).getContents();
                String countryEng = (sheet.getCell(4, i)).getContents();
                String countryName = (sheet.getCell(5, i)).getContents();
                String provinceEng = (sheet.getCell(6, i)).getContents();
                String provinceName = (sheet.getCell(7, i)).getContents();
                String cityEng = (sheet.getCell(8, i)).getContents();
                String cityName = (sheet.getCell(9, i)).getContents();
                double latitude = Double.parseDouble((sheet.getCell(10, i)).getContents());
                double longitude = Double.parseDouble((sheet.getCell(11, i)).getContents());
                int adCode = Integer.parseInt((sheet.getCell(12, i)).getContents());
                info = new PlaceData(weatherId,placeEng,placeName,countryCode,countryEng,countryName,provinceEng,provinceName,cityEng,cityName,latitude,longitude,adCode);
                Log.d(TAG,info.toString());
                saveInfoToDataBase(info);
            }
            book.close();
            SharedPreferencesUtil.putBoolean(SharedPreferencesUtil.IS_READED_PLACE_EXCEL, false);
        } catch (Exception e) {
            SharedPreferencesUtil.putBoolean(SharedPreferencesUtil.IS_READED_PLACE_EXCEL, true);
            Log.e(TAG, EXCEPTION, e);
        }
    }

    /**
     * 保存该条数据到数据库
     * @param info excel中的某条数据
     */
    private void saveInfoToDataBase(PlaceData info) {
        if (mDBHelper == null) {
            return;
        }
//        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("weatherId", info.getWeatherId());
            values.put("placeEng", info.getPlaceEng());
            values.put("placeName", info.getPlaceName());
            values.put("countryCode", info.getCountryCode());
            values.put("countryEng", info.getCountryEng());
            values.put("countryName", info.getCountryName());
            values.put("provinceEng", info.getProvinceEng());
            values.put("provinceName", info.getProvinceName());
            values.put("cityEng", info.getCityEng());
            values.put("cityName", info.getCityName());
            values.put("latitude", info.getLatitude());
            values.put("longitude", info.getLongitude());
            values.put("adCode", info.getAdCode());
            db.insert(PlaceDatabaseHelper.TABLE_PLACE_NAME, null, values);
        } catch (SQLiteException e) {
            Log.e(TAG, EXCEPTION, e);
        } catch (Exception e){
            Log.e(TAG, EXCEPTION, e);
        } finally {
//            if (db != null) {
//                db.close();
//            }
        }
    }

    /**
     * 根据内容获取 整条数据(ExtraVoiceInfo)
     * @param placeName
     * @return
     */
    public PlaceData getPlaceData(String placeName) {
        PlaceData info = null;
        if (mDBHelper == null) {
            return info;
        }
//        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        if (db == null) {
            return info;
        }
        Cursor cursor = db.rawQuery("select * from "+PlaceDatabaseHelper.TABLE_PLACE_NAME+" where placeName = ?", new String[] { placeName });

        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String weatherId = cursor.getString(cursor.getColumnIndex("weatherId"));
                    String placeEng = cursor.getString(cursor.getColumnIndex("placeEng"));
                    String name = cursor.getString(cursor.getColumnIndex("placeName"));
                    String countryCode = cursor.getString(cursor.getColumnIndex("countryCode"));
                    String countryEng = cursor.getString(cursor.getColumnIndex("countryEng"));
                    String countryName = cursor.getString(cursor.getColumnIndex("countryName"));
                    String provinceEng = cursor.getString(cursor.getColumnIndex("provinceEng"));
                    String provinceName = cursor.getString(cursor.getColumnIndex("provinceName"));
                    String cityEng = cursor.getString(cursor.getColumnIndex("cityEng"));
                    String cityName = cursor.getString(cursor.getColumnIndex("cityName"));
                    double latitude = cursor.getDouble(cursor.getColumnIndex("latitude"));
                    double longitude = cursor.getDouble(cursor.getColumnIndex("longitude"));
                    int adCode = cursor.getInt(cursor.getColumnIndex("adCode"));
                    info = new PlaceData(weatherId,placeEng,name,countryCode,countryEng,countryName,provinceEng,provinceName,cityEng,cityName,latitude,longitude,adCode);
                } while (cursor.moveToNext());
            }

        } catch (SQLiteException e) {
            Log.e(TAG, EXCEPTION, e);
        }  catch (Exception e){
            Log.e(TAG, EXCEPTION, e);
        } finally {
//            if (cursor != null) {
//                cursor.close();
//                cursor = null;
//            }
//            if (db != null) {
//                db.close();
//            }
        }
        return info;
    }

    /*根据地点选择器的值进行模糊查询*/
    public PlaceData getPlaceDataBySelector(String placeName) {
        PlaceData info = null;
        if (mDBHelper == null) {
            return info;
        }
//        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        if (db == null) {
            return info;
        }
        placeName=placeName+"%";
        Cursor cursor = db.rawQuery("select * from "+PlaceDatabaseHelper.TABLE_PLACE_NAME+" where placeName like ?", new String[] { placeName });
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String weatherId = cursor.getString(cursor.getColumnIndex("weatherId"));
                    String placeEng = cursor.getString(cursor.getColumnIndex("placeEng"));
                    String name = cursor.getString(cursor.getColumnIndex("placeName"));
                    String countryCode = cursor.getString(cursor.getColumnIndex("countryCode"));
                    String countryEng = cursor.getString(cursor.getColumnIndex("countryEng"));
                    String countryName = cursor.getString(cursor.getColumnIndex("countryName"));
                    String provinceEng = cursor.getString(cursor.getColumnIndex("provinceEng"));
                    String provinceName = cursor.getString(cursor.getColumnIndex("provinceName"));
                    String cityEng = cursor.getString(cursor.getColumnIndex("cityEng"));
                    String cityName = cursor.getString(cursor.getColumnIndex("cityName"));
                    double latitude = cursor.getDouble(cursor.getColumnIndex("latitude"));
                    double longitude = cursor.getDouble(cursor.getColumnIndex("longitude"));
                    int adCode = cursor.getInt(cursor.getColumnIndex("adCode"));
                    info = new PlaceData(weatherId,placeEng,name,countryCode,countryEng,countryName,provinceEng,provinceName,cityEng,cityName,latitude,longitude,adCode);
                } while (cursor.moveToNext());
            }

        } catch (SQLiteException e) {
            Log.e(TAG, EXCEPTION, e);
        }  catch (Exception e){
            Log.e(TAG, EXCEPTION, e);
        } finally {
//            if (cursor != null) {
//                cursor.close();
//                cursor = null;
//            }
//            if (db != null) {
//                db.close();
//            }
        }
        return info;
    }


    /*打开应用时将当前位置保存至收藏表第position项*/
    public void saveLocalToCollection(PlaceData localPlace,int position){
        if (mDBHelper == null) {
            return;
        }
//        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        String i=Integer.toString(position);
        try {
            ContentValues values = new ContentValues();
            values.put("weatherId", localPlace.getWeatherId());
            values.put("placeName", localPlace.getPlaceName());
            values.put("provinceName", localPlace.getProvinceName());
            values.put("cityName", localPlace.getCityName());
            db.update(PlaceDatabaseHelper.TABLE_COLLECTION_NAME, values,"id = ?",new String[]{i});
        } catch (SQLiteException e) {
            Log.e(TAG, EXCEPTION, e);
        } catch (Exception e){
            Log.e(TAG, EXCEPTION, e);
        } finally {
//            if (db != null) {
//                db.close();
//            }
        }
    }

    /*通过发送本地广播通知activity数据更新*/
    public void sendUpdateBroadcast(){
        Intent collectionUpdateIntent=new Intent("com.example.woweather_new.collection_data_update");
        LocalBroadcastManager.getInstance(WoWeatherApplication.getContext()).sendBroadcast(collectionUpdateIntent);
    }

    /*将解析完成的weather保存至收藏表第position项*/
    public void saveWeatherToCollection(Weather weather,String weatherId){
        if (mDBHelper == null) {
            return;
        }
//        SQLiteDatabase db = mDBHelper.getWritableDatabase();
//        String i=Integer.toString(position);
        try {
            ContentValues values = new ContentValues();
            values.put("tmp", weather.now.temperature);
            values.put("cond", weather.now.more.info);
            values.put("aqi", weather.aqi.city.aqi);
            values.put("pm25", weather.aqi.city.pm25);
            values.put("updateTime", weather.basic.update.updateTime);
            Log.d(TAG, "saveWeatherToCollection: "+values.toString());
            db.update(PlaceDatabaseHelper.TABLE_COLLECTION_NAME, values,"weatherId = ?",new String[]{weatherId});
            sendUpdateBroadcast();
        }catch (SQLiteException e) {
            Log.e(TAG, EXCEPTION, e);
        } catch (Exception e){
            Log.e(TAG, EXCEPTION, e);
        } finally {
//            if (db != null) {
//                db.close();
//            }
        }
    }

    /*显示收藏表内容并返回*/
    public synchronized List<CollectionData> showCollectTable(){
        List<CollectionData> CollectionDatas=new ArrayList<CollectionData>();
        if (mDBHelper == null) {
            return null;
        }
//        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        Cursor cursor=db.query(PlaceDatabaseHelper.TABLE_COLLECTION_NAME,null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                /*如果收藏表中第一项之后的数据中存在与当前位置相同的数据，
                那么就说明当前位置已定位到一个已经收藏的位置，所以不会添加*/
                int id=cursor.getInt(cursor.getColumnIndex("id"));
                String placeName=cursor.getString(cursor.getColumnIndex("placeName"));
                if (id>1 && WoWeatherApplication.getLocalCountyName().equals(placeName)){

                }else{
                    CollectionData collectionData=new CollectionData();
                    collectionData.setId(id);
                    collectionData.setWeatherId(cursor.getString(cursor.getColumnIndex("weatherId")));
                    collectionData.setPlaceName(placeName);
                    collectionData.setProvinceName(cursor.getString(cursor.getColumnIndex("provinceName")));
                    collectionData.setCityName(cursor.getString(cursor.getColumnIndex("cityName")));
                    collectionData.setTmp(cursor.getString(cursor.getColumnIndex("tmp")));
                    collectionData.setCond(cursor.getString(cursor.getColumnIndex("cond")));
                    collectionData.setAqi(cursor.getString(cursor.getColumnIndex("aqi")));
                    collectionData.setPm25(cursor.getString(cursor.getColumnIndex("pm25")));
                    collectionData.setUpdateTime(cursor.getString(cursor.getColumnIndex("updateTime")));
                    CollectionDatas.add(collectionData);
                    Log.d(TAG,"showCollectTable: "+collectionData.toString());
                }
            }while (cursor.moveToNext());
        }
//        if (cursor != null) {
//            cursor.close();
//            cursor = null;
//        }
//        if (db != null) {
//            db.close();
//        }
        return CollectionDatas;
    }

    /*判断某一个地点是否已经收藏*/
    public boolean isExisting(String weatherId){
        if (mDBHelper == null) {
            return false;
        }
//        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        Cursor cursor=db.query(PlaceDatabaseHelper.TABLE_COLLECTION_NAME,null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                if(weatherId.equals(cursor.getString(cursor.getColumnIndex("weatherId")))){
                    return true;
                }

            }while (cursor.moveToNext());
        }
//        if (cursor != null) {
//            cursor.close();
//            cursor = null;
//        }
//        if (db != null) {
//            db.close();
//        }
        return false;
    }

    /*取消收藏*/
    public void deleteCollectionData(String weatherId){
        if (mDBHelper == null) {
            return;
        }
//        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        db.delete(PlaceDatabaseHelper.TABLE_COLLECTION_NAME,"weatherId = ?",new String[]{weatherId});
        sendUpdateBroadcast();
    }

    /*添加收藏*/
    public void insertCollectionData(Weather weather){
        if (mDBHelper == null) {
            return;
        }
//        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("weatherId", weather.basic.weatherId);
            values.put("placeName", weather.basic.countyName);
            values.put("provinceName", weather.basic.provinceName);
            values.put("cityName", weather.basic.cityName);
            values.put("tmp", weather.now.temperature);
            values.put("cond", weather.now.more.info);
            values.put("aqi", weather.aqi.city.aqi);
            values.put("pm25", weather.aqi.city.pm25);
            values.put("updateTime", weather.basic.update.updateTime);
            Log.d(TAG, "insertCollectionData: "+values.toString());
            db.insert(PlaceDatabaseHelper.TABLE_COLLECTION_NAME, null,values);
            sendUpdateBroadcast();
        }catch (SQLiteException e) {
            Log.e(TAG, EXCEPTION, e);
        } catch (Exception e){
            Log.e(TAG, EXCEPTION, e);
        } finally {
//            if (db != null) {
//                db.close();
//            }
        }
    }

    public String searchPinYin(String id){
        if (mDBHelper == null || db == null) {
            return "";
        }
        Cursor cursor = db.rawQuery("select * from "+PlaceDatabaseHelper.TABLE_PLACE_NAME+" where weatherId = ?", new String[] { id });
        try {
            if (cursor != null && cursor.moveToFirst()) {
                return cursor.getString(cursor.getColumnIndex("placeEng"));
//                do {
//                    String weatherId = cursor.getString(cursor.getColumnIndex("weatherId"));
//                    String placeEng = cursor.getString(cursor.getColumnIndex("placeEng"));
//                    String name = cursor.getString(cursor.getColumnIndex("placeName"));
//                    String countryCode = cursor.getString(cursor.getColumnIndex("countryCode"));
//                    String countryEng = cursor.getString(cursor.getColumnIndex("countryEng"));
//                    String countryName = cursor.getString(cursor.getColumnIndex("countryName"));
//                    String provinceEng = cursor.getString(cursor.getColumnIndex("provinceEng"));
//                    String provinceName = cursor.getString(cursor.getColumnIndex("provinceName"));
//                    String cityEng = cursor.getString(cursor.getColumnIndex("cityEng"));
//                    String cityName = cursor.getString(cursor.getColumnIndex("cityName"));
//                    double latitude = cursor.getDouble(cursor.getColumnIndex("latitude"));
//                    double longitude = cursor.getDouble(cursor.getColumnIndex("longitude"));
//                    int adCode = cursor.getInt(cursor.getColumnIndex("adCode"));
//                    info = new PlaceData(weatherId,placeEng,name,countryCode,countryEng,countryName,provinceEng,provinceName,cityEng,cityName,latitude,longitude,adCode);
//                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e) {
            Log.e(TAG, EXCEPTION, e);
        }  catch (Exception e){
            Log.e(TAG, EXCEPTION, e);
        } finally {
//            if (cursor != null) {
//                cursor.close();
//                cursor = null;
//            }
//            if (db != null) {
//                db.close();
//            }
        }
        return "";
    }

    public void close(){
        if (mDBHelper == null) {
            return;
        }
        db.close();
    }
}
