package com.alexandr.weatherapp.mvp.model.api;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiHolder {
    private static ApiHolder singleton = null;
    private IOpenWeather API;

    private ApiHolder() {
        API = createAdapter();
    }

    public static ApiHolder getSingleton() {
        if(singleton == null) {
            singleton = new ApiHolder();
        }

        return singleton;
    }

    public IOpenWeather getAPI() {
        return API;
    }

    private IOpenWeather createAdapter() {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        IOpenWeather api = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(IOpenWeather.class);

        return api;
    }
}
