package com.alexandr.weatherapp.mvp.view.list;

public interface HourlyWeatherRowView {
    int getPos();
    void setTime(String title);
    void setIcon(String url);
    void setTemperature(Float temp);
}
