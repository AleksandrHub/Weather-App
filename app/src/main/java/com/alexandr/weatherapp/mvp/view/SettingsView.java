package com.alexandr.weatherapp.mvp.view;

import com.alexandr.weatherapp.mvp.model.entity.currentweather.CurrentWeatherDataModel;
import com.alexandr.weatherapp.utils.Units;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.OneExecutionStateStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface SettingsView extends MvpView {
    void init();

    void setDefaultCity(String city);
    void setGpsDefault(boolean value);
    void setDefaultUnits(Units units);



}
