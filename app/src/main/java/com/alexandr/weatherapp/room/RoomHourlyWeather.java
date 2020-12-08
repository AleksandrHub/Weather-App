package com.alexandr.weatherapp.room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import com.google.gson.annotations.SerializedName;

import static androidx.room.ForeignKey.CASCADE;

@Entity(primaryKeys = {"dt", "timeZone"}, foreignKeys = @ForeignKey(entity = RoomCurrentWeather.class, parentColumns = "timeZone", childColumns = "timeZone", onDelete = CASCADE))
public class RoomHourlyWeather {

    private long dt;
    private float temp;
    private int id;
    private String description;
    private String icon;
    @NonNull
    private String timeZone;

    public RoomHourlyWeather(String timeZone, long dt, float temp, int id, String description, String icon) {
        this.dt = dt;
        this.temp = temp;
        this.id = id;
        this.description = description;
        this.icon = icon;
        this.timeZone=timeZone;
    }

    public long getDt() {
        return dt;
    }

    public float getTemp() {
        return temp;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    @NonNull
    public String getTimeZone() {
        return timeZone;
    }
}
