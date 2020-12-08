package com.alexandr.weatherapp.utils.ui;

import android.content.Context;

import com.alexandr.weatherapp.R;
import com.alexandr.weatherapp.ui.App;
import com.alexandr.weatherapp.utils.Indicators;
import com.alexandr.weatherapp.utils.Units;

public class UIUtils {


    public static <T extends Number> String addUnits(T value, Indicators indicators, Units units){
        String formatValue;
        Integer res = 0;
        Context context = App.getInstance();
        switch (units){
            case metric:
                switch (indicators){
                    case Speed: res = R.string.speed_metric; break;
                    case Humidity: res = R.string.humid_metric; break;
                    case Pressure: res = R.string.press_metric; break;
                    case Temperature: res = R.string.temp_metric; break;
                }
                break;
            case imperial:
                switch (indicators){
                    case Speed: res = R.string.speed_imper; break;
                    case Humidity: res = R.string.humid_imper; break;
                    case Pressure: res = R.string.press_imper; break;
                    case Temperature: res = R.string.temp_imper; break;
                }
                break;

            case standard:
                switch (indicators){
                    case Speed: res = R.string.speed_stand; break;
                    case Humidity: res = R.string.humid_stand; break;
                    case Pressure: res = R.string.press_stand; break;
                    case Temperature: res = R.string.temp_stand; break;
                }
            break;
        }

        String template;
        if(value instanceof Float) template = "%.0f %s";
        else if (value instanceof Integer) template = "%d %s";
        else template = "...";
        formatValue = String.format(template, value, context.getString(res));

        return formatValue;
    }


}
