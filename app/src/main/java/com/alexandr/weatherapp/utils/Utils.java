package com.alexandr.weatherapp.utils;

import com.alexandr.weatherapp.R;
import com.alexandr.weatherapp.utils.ui.MyPreferences;

public class Utils {

    public static Integer getIconRes(String id){



        Integer res = null;
        IconId[] array = IconId.values();
        for (IconId o: array) {
            if(o.compare(id)) {
                res = o.getRes();
                break;
            }
        }
        return (res != null)? res : R.drawable.cloud_error;
    }

    public static String getUnitsString(){
        Integer unitsPref = MyPreferences.getIntPref(MyPreferences.UNITS_PREFERENCES);
        return (unitsPref ==0)? Units.metric.getValue():Units.intToUnits(unitsPref).getValue() ;
    }

    public static Units getUnits(){
        Integer unitsPref = MyPreferences.getIntPref(MyPreferences.UNITS_PREFERENCES);
        return (unitsPref ==0)? Units.metric: Units.intToUnits(unitsPref);
    }

    public static void setUnitsPref(Units units){
        MyPreferences.setIntPref(MyPreferences.UNITS_PREFERENCES, units.getId());
        System.out.println("set unit in utils, units = "+units.getValue());
    }

    public static void setDefaultCity(String city){
        MyPreferences.setStrPref(MyPreferences.DEFAULT_CITY_PREFERENCES, city);
    }

    public static String getDefaultCity(){
        String city = MyPreferences.getStrPref(MyPreferences.DEFAULT_CITY_PREFERENCES);
        if(city.isEmpty()) {
            city = "moscow";
        }
        return city;
    }

    public static void setDefaultGps(boolean value){
        MyPreferences.setBooleanPref(MyPreferences.DEFAULT_GPS_PREFERENCES, value);
    }

    public static boolean getDefaultGps(){
        boolean gpsDefault = MyPreferences.getBooleanPref(MyPreferences.DEFAULT_GPS_PREFERENCES);
        return gpsDefault;
    }

}
