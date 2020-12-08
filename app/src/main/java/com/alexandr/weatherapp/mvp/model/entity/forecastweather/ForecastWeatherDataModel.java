package com.alexandr.weatherapp.mvp.model.entity.forecastweather;

import com.google.gson.annotations.SerializedName;

public class ForecastWeatherDataModel {

    @SerializedName("lat") public float lat;
    @SerializedName("lon") public float lon;
    @SerializedName("timezone") public String timezone;
    @SerializedName("timezone_offset") public long timezone_offset;
    @SerializedName("current") public CurrentRestModel current;
    @SerializedName("minutely") public MinutelyRestModel[] minutely;
    @SerializedName("hourly") public HourlyRestModel[] hourly;
    @SerializedName("daily") public DailyRestModel[] daily;

}
