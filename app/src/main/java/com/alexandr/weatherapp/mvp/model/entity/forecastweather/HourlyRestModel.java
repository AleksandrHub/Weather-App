package com.alexandr.weatherapp.mvp.model.entity.forecastweather;

import com.google.gson.annotations.SerializedName;

public class HourlyRestModel {

    @SerializedName("dt") public long dt;
    @SerializedName("temp") public float temp;
    @SerializedName("feels_like") public float feelsLike;
    @SerializedName("pressure") public int pressure;
    @SerializedName("humidity") public int humidity;
    @SerializedName("dew_point") public float dewPoint;
    @SerializedName("uvi") public float middleUVIndex;
    @SerializedName("clouds") public int clouds;
    @SerializedName("visibility") public int visibility;
    @SerializedName("wind_speed") public float windSpeed;
    @SerializedName("wind_deg") public int windDeg;
    @SerializedName("weather") public WeatherRestModel[] weather;
    @SerializedName("pop") public float pop;
//    @SerializedName("rain") public RainRestModel rain;
//    @SerializedName("snow") public SnowRestModel snow;
}
