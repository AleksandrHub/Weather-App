package com.alexandr.weatherapp.ui;

import android.app.Application;

import com.alexandr.weatherapp.room.db.WeatherDataBase;

import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;
import timber.log.Timber;

public class App extends Application {

    private static App instance;

    private Cicerone<Router> cicerone;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        cicerone = Cicerone.create();
        Timber.plant(new Timber.DebugTree());
        WeatherDataBase.create(this);
    }


    public static App getInstance(){
        return instance;
    }

    public NavigatorHolder getNavigatorHolder() {
        return cicerone.getNavigatorHolder();
    }

    public Router getRouter() {
        return cicerone.getRouter();
    }
}
