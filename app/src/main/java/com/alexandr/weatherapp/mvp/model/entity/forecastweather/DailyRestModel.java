package com.alexandr.weatherapp.mvp.model.entity.forecastweather;

import com.google.gson.annotations.SerializedName;

public class DailyRestModel {

    @SerializedName("dt") public long dt;
    @SerializedName("sunrise") public long sunrise;
    @SerializedName("sunset") public long sunset;
    @SerializedName("temp") public TempRestModel temp;
    @SerializedName("feels_like") public FeelsLikeRestModel feelsLike;
    @SerializedName("pressure") public int pressure;
    @SerializedName("humidity") public int humidity;
    @SerializedName("dew_point") public float dewPoint;
    @SerializedName("wind_speed") public float windSpeed;
    @SerializedName("wind_deg") public int windDeg;
    @SerializedName("weather") public WeatherRestModel[] weather;
    @SerializedName("clouds") public int clouds;
    @SerializedName("pop") public float pop;
    @SerializedName("rain") public float rain;
    @SerializedName("uvi") public float middleUVIndex;

}
