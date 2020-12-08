package com.alexandr.weatherapp.mvp.view;


import android.graphics.Bitmap;

import com.alexandr.weatherapp.mvp.model.entity.currentweather.CurrentWeatherDataModel;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.OneExecutionStateStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface MainView extends MvpView {
    void init();

}
