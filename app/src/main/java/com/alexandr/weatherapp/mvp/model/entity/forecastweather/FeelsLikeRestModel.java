package com.alexandr.weatherapp.mvp.model.entity.forecastweather;

import com.google.gson.annotations.SerializedName;

public class FeelsLikeRestModel {

    @SerializedName("day") public float day;
    @SerializedName("night") public float night;
    @SerializedName("eve") public float eve;
    @SerializedName("morn") public float morn;


}
