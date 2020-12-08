package com.alexandr.weatherapp.mvp.presenter.list;

import com.alexandr.weatherapp.mvp.view.list.HourlyWeatherRowView;
import io.reactivex.subjects.PublishSubject;


public interface IForecastWeatherPresenter<T> {
    void bind(T view);
    int getCount();
    PublishSubject<T> getClickSubject();
}
