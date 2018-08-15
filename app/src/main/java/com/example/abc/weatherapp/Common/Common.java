package com.example.abc.weatherapp.Common;

import android.support.annotation.NonNull;

public class Common {

    public static String API_KEY = "cc078084110423afb7994db6571ee338";
    public static String API_LINK = "http://api.openweathermap.org/data/2.5/weather";

    @NonNull
    public static String apirequest(String CityName){

        StringBuilder sb = new StringBuilder(API_LINK);
        sb.append(String.format("?q=%s&appid=%s" , CityName , API_KEY));
        return sb.toString();
    }

    public static String getImage(String icon){
        return String.format("http://openweathermap.org/img/w/%s.png" , icon);
    }


}
