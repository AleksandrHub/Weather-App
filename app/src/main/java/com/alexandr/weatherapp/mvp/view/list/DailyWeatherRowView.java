package com.alexandr.weatherapp.mvp.view.list;

public interface DailyWeatherRowView {
    int getPos();
    void setWeekday(String title);
    void setDate(String title);
    void setIcon(String url);
    void setTemperatureAfternoon(Float temp);
    void setTemperatureNight(Float temp);
}
