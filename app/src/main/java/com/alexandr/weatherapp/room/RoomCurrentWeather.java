package com.alexandr.weatherapp.room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class RoomCurrentWeather {

    @NonNull
    @PrimaryKey
    private String timeZone;
    private long dt;
    private float temp;
    private int pressure;
    private int humidity;
    private float lat;
    private float lon;
    private String icon;
    private String main;
    private String description;
    private int id;


    public RoomCurrentWeather(String timeZone, float lat, float lon, long dt, float temp, int pressure, int humidity, String icon, String main, String description, int id){
        this.timeZone=timeZone;
        this.lat=lat;
        this.lon=lon;
        this.dt=dt;
        this.temp=temp;
        this.pressure=pressure;
        this.humidity=humidity;
        this.icon=icon;
        this.main=main;
        this.description=description;
        this.id=id;
    }

    @NonNull
    public String getTimeZone() {
        return timeZone;
    }
    public long getDt() {
        return dt;
    }
    public float getTemp() {
        return temp;
    }
    public int getPressure() {
        return pressure;
    }
    public int getHumidity() {
        return humidity;
    }
    public float getLat() {
        return lat;
    }
    public float getLon() {
        return lon;
    }
    public String getIcon() {
        return icon;
    }

    public String getMain() {
        return main;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }
}
