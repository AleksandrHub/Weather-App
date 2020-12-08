package com.alexandr.weatherapp.mvp.view;

import com.alexandr.weatherapp.mvp.model.entity.currentweather.CurrentWeatherDataModel;
import com.alexandr.weatherapp.mvp.model.entity.forecastweather.ForecastWeatherDataModel;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.OneExecutionStateStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface WeatherView extends MvpView {
    void init();
    void updateList();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showMessage(String text);

    void showLoading();
    void hideLoading();

    void setCurrentWeather(ForecastWeatherDataModel forecastWeatherDataModel);
    void loadImage(String url);

    void setCity(String city);

    void searchWeather();
}
