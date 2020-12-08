package com.alexandr.weatherapp.mvp.model.entity.forecastweather;

import com.google.gson.annotations.SerializedName;

public class MinutelyRestModel {

    @SerializedName("dt") public long dt;
    @SerializedName("precipitation") public float precipitation;

}
