package com.example.woweather_new.bean;

/**
 * Created by 邹永鹏 on 2018/6/15.
 */

public class CollectionData {

    private int id;
    private String weatherId;
    private String placeName;
    private String provinceName;
    private String cityName;
    private String tmp;
    private String cond;
    private String aqi;
    private String pm25;
    private String updateTime;

    public CollectionData(){

    }

    public CollectionData(int id,String weatherId,String placeName,String provinceName,String cityName){
        this.id=id;
        this.weatherId=weatherId;
        this.placeName=placeName;
        this.provinceName=provinceName;
        this.cityName=cityName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getTmp() {
        return tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }

    public String getCond() {
        return cond;
    }

    public void setCond(String cond) {
        this.cond = cond;
    }

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "CollectionData{" +
                "id=" + id +
                ", weatherId='" + weatherId + '\'' +
                ", placeName='" + placeName + '\'' +
                ", provinceName='" + provinceName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", tmp='" + tmp + '\'' +
                ", cond='" + cond + '\'' +
                ", aqi='" + aqi + '\'' +
                ", pm25='" + pm25 + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
