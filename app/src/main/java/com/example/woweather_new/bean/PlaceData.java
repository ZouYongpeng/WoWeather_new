package com.example.woweather_new.bean;

/**
 * Created by 邹永鹏 on 2018/6/13.
 */

public class PlaceData {

    private String weatherId;//城市、地区编码

    private String placeEng;//英文

    private String placeName;//中文

    private String countryCode;//国家代码

    private String countryEng;//国家英文

    private String countryName;//国家中文

    private String provinceEng;//省英文

    private String provinceName;//省中文

    private String cityEng;//市英文

    private String cityName;//市中文

    private double latitude;//纬度

    private double longitude;//经度

    private int adCode;//adcode

    public PlaceData(String weatherId, String placeEng, String placeName, String countryCode, String countryEng, String countryName, String provinceEng, String provinceName, String cityEng, String cityName, double latitude, double longitude, int adCode) {
        this.weatherId = weatherId;
        this.placeEng = placeEng;
        this.placeName = placeName;
        this.countryCode = countryCode;
        this.countryEng = countryEng;
        this.countryName = countryName;
        this.provinceEng = provinceEng;
        this.provinceName = provinceName;
        this.cityEng = cityEng;
        this.cityName = cityName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.adCode = adCode;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public void setPlaceCode(String weatherId) {
        this.weatherId = weatherId;
    }

    public String getPlaceEng() {
        return placeEng;
    }

    public void setPlaceEng(String placeEng) {
        this.placeEng = placeEng;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryEng() {
        return countryEng;
    }

    public void setCountryEng(String countryEng) {
        this.countryEng = countryEng;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getProvinceEng() {
        return provinceEng;
    }

    public void setProvinceEng(String provinceEng) {
        this.provinceEng = provinceEng;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityEng() {
        return cityEng;
    }

    public void setCityEng(String cityEng) {
        this.cityEng = cityEng;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getAdCode() {
        return adCode;
    }

    public void setAdCode(int adCode) {
        this.adCode = adCode;
    }

    @Override
    public String toString() {
        return "PlaceData{" +
                "weatherId='" + weatherId + '\'' +
                ", placeEng='" + placeEng + '\'' +
                ", placeName='" + placeName + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", countryEng='" + countryEng + '\'' +
                ", countryName='" + countryName + '\'' +
                ", provinceEng='" + provinceEng + '\'' +
                ", provinceName='" + provinceName + '\'' +
                ", cityEng='" + cityEng + '\'' +
                ", cityName='" + cityName + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", adCode=" + adCode +
                '}';
    }
}
