package com.alexandr.weatherapp.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.alexandr.weatherapp.room.RoomCurrentWeather;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface CurrentWeatherDao {

    @Insert(onConflict = REPLACE)
    void insert(RoomCurrentWeather currentWeather);

    @Query("SELECT * FROM roomcurrentweather WHERE timeZone = :timeZone")
    RoomCurrentWeather findCurrentWeatherByCity(String timeZone);

    @Query("SELECT * FROM roomcurrentweather WHERE lat = :lat AND lon = :lon")
    RoomCurrentWeather findCurrentWeatherByCoordinates(float lat, float lon);
}
