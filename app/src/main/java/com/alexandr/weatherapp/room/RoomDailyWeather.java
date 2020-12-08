package com.alexandr.weatherapp.room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(primaryKeys = {"dt", "timeZone"}, foreignKeys = @ForeignKey(entity = RoomCurrentWeather.class, parentColumns = "timeZone", childColumns = "timeZone", onDelete = CASCADE))
public class RoomDailyWeather {

    public long dt;
    public float tempDay;
    public float  tempNight;
    public int id;
    public String description;
    public String icon;
    @NonNull
    private String timeZone;

    public RoomDailyWeather(String timeZone, long dt, float tempDay, float tempNight, int id, String description, String icon) {
        this.dt = dt;
        this.tempDay = tempDay;
        this.tempNight = tempNight;
        this.id = id;
        this.description = description;
        this.icon = icon;
        this.timeZone=timeZone;
    }

    public long getDt() {
        return dt;
    }

    public float getTempDay() {
        return tempDay;
    }

    public float getTempNight() {
        return tempNight;
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
