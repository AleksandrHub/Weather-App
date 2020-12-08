package com.alexandr.weatherapp.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.alexandr.weatherapp.room.RoomDailyWeather;
import com.alexandr.weatherapp.room.RoomHourlyWeather;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface DailyWeatherDao {

    @Insert(onConflict = REPLACE)
    void insert(RoomDailyWeather dailyWeather);

    @Insert(onConflict = REPLACE)
    void insert(List<RoomDailyWeather> dailyWeather);

    @Query("SELECT * FROM roomdailyweather WHERE timeZone = :timeZone")
    List<RoomDailyWeather> findDailyWeathersByCity(String timeZone);

    @Query("SELECT * FROM roomdailyweather WHERE timeZone = :timeZone AND dt = :dt")
    RoomDailyWeather findDailyWeatherByCity(String timeZone, long dt);


    @Query("DELETE FROM roomdailyweather WHERE timeZone = :timeZone")
    void deleteDailyWeathersByCity(String timeZone);
}
