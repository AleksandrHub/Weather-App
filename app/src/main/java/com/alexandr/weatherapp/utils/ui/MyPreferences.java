package com.alexandr.weatherapp.utils.ui;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;

import com.alexandr.weatherapp.ui.App;

public class MyPreferences {

    public static final String APP_PREFERENCES = "mysettings";
    public static final String UNITS_PREFERENCES = "units_settings";
    public static final String DEFAULT_CITY_PREFERENCES = "default_city_settings";
    public static final String DEFAULT_GPS_PREFERENCES = "default_gps_settings";

    private static MyPreferences instance = new MyPreferences(App.getInstance());
    private static SharedPreferences preferences;

    private MyPreferences(Context context){
        preferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        System.out.println("create MyPref()");
    }

    public static MyPreferences getInstance(){
        if(instance == null) instance = new MyPreferences(App.getInstance());
        return instance;
    }

    public static void setIntPref(String key, Integer units){
        instance.preferences.edit().putInt(key, units).apply();
        System.out.println("set in pref, key ="+key+", units ="+units);
    }

    public static Integer getIntPref(String key){

        System.out.println("get in pref, key ="+key +", value ="+instance.preferences.getInt(key, 0));
        return instance.preferences.getInt(key, 0);
    }

    public static void setStrPref(String key, String units){
        instance.preferences.edit().putString(key, units).apply();
    }

    public static String getStrPref(String key){
        return instance.preferences.getString(key, "");
    }

    public static void setBooleanPref(String key, boolean units){
        instance.preferences.edit().putBoolean(key, units).apply();
    }

    public static boolean getBooleanPref(String key){
        return instance.preferences.getBoolean(key, false);
    }

}

