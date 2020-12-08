package com.alexandr.weatherapp.mvp.model.api;



import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

import com.alexandr.weatherapp.mvp.model.entity.currentweather.CurrentWeatherDataModel;
import com.alexandr.weatherapp.mvp.model.entity.forecastweather.ForecastWeatherDataModel;

public interface IOpenWeather {
    @GET("data/2.5/weather")
    Single<CurrentWeatherDataModel> loadCurrentWeather(@Query("q") String city,
                                                       @Query("appid") String keyApi,
                                                       @Query("units") String units);

    @GET("data/2.5/onecall")
    Single<ForecastWeatherDataModel> loadForecastWeather(@Query("lat") float lat,
                                                         @Query("lon") float lon,
                                                         @Query("exclude") String[] exclude,
                                                         @Query("appid") String keyApi,
                                                         @Query("units") String units);

    @GET
    Single<ResponseBody> loadIconWeather(@Url String url);
}
