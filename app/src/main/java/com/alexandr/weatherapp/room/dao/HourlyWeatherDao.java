package com.alexandr.weatherapp.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.alexandr.weatherapp.room.RoomCurrentWeather;
import com.alexandr.weatherapp.room.RoomHourlyWeather;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface HourlyWeatherDao {

    @Insert(onConflict = REPLACE)
    void insert(RoomHourlyWeather hourlyWeather);

    @Insert(onConflict = REPLACE)
    void insert(List<RoomHourlyWeather> hourlyWeather);

    @Query("SELECT * FROM roomhourlyweather WHERE timeZone = :timeZone")
    List<RoomHourlyWeather> findHourlyWeathersByCity(String timeZone);

    @Query("SELECT * FROM roomhourlyweather WHERE timeZone = :timeZone AND dt = :dt")
    RoomHourlyWeather findHourlyWeatherByCity(String timeZone, long dt);

    @Query("DELETE FROM roomhourlyweather WHERE timeZone = :timeZone")
    void deleteHourlyWeathersByCity(String timeZone);

}
