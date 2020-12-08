package com.alexandr.weatherapp.room.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.alexandr.weatherapp.room.RoomCurrentWeather;
import com.alexandr.weatherapp.room.RoomDailyWeather;
import com.alexandr.weatherapp.room.RoomHourlyWeather;
import com.alexandr.weatherapp.room.dao.CurrentWeatherDao;
import com.alexandr.weatherapp.room.dao.DailyWeatherDao;
import com.alexandr.weatherapp.room.dao.HourlyWeatherDao;

@Database(entities = {RoomCurrentWeather.class, RoomHourlyWeather.class, RoomDailyWeather.class}, version = 1)
public abstract class WeatherDataBase extends RoomDatabase {

    private static final String DB_NAME = "weatherDatabase.db";
    private static volatile WeatherDataBase instance;

    public static synchronized WeatherDataBase getInstance(){
        if(instance == null){
            throw new RuntimeException("Database has not been created. Please call create()");
        }
        return instance;
    }

    public static void create(Context context){
        if(instance == null){
            instance =  Room.databaseBuilder(context, WeatherDataBase.class, DB_NAME).build();
        }
    }

    public abstract CurrentWeatherDao getCurrentWeatherDao();

    public abstract HourlyWeatherDao getHourlyWeatherDao();

    public abstract DailyWeatherDao getDailyWeatherDao();

}
