package com.alexandr.weatherapp.mvp.presenter;

import com.alexandr.weatherapp.mvp.model.entity.forecastweather.DailyRestModel;
import com.alexandr.weatherapp.mvp.model.entity.forecastweather.HourlyRestModel;
import com.alexandr.weatherapp.mvp.model.repository.OpenWeatherRepo;
import com.alexandr.weatherapp.mvp.view.SettingsView;
import com.alexandr.weatherapp.navigation.Screens;
import com.alexandr.weatherapp.utils.Units;
import com.alexandr.weatherapp.utils.Utils;
import com.alexandr.weatherapp.utils.ui.UIUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import moxy.MvpPresenter;
import ru.terrakok.cicerone.Router;

public class SettingsPresenter extends MvpPresenter<SettingsView> {


    private Scheduler mainThreadScheduler;
    private OpenWeatherRepo openWeatherRepo;
    private Router router;


    public SettingsPresenter(Router router) {

        this.router = router;
        openWeatherRepo = new OpenWeatherRepo();


    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().init();
        getViewState().setDefaultCity(Utils.getDefaultCity());
        getViewState().setGpsDefault(Utils.getDefaultGps());
        getViewState().setDefaultUnits(Utils.getUnits());

    }

    public void goToWeather(){
        router.backTo(new Screens.WeatherScreen());
    }

    public boolean backClicked(){
        router.exit();
        return true;
    }

    public void savePref(){

    }

    public void setImperial() {
        Utils.setUnitsPref(Units.imperial);
        System.out.println("set imperial in presenter");
    }

    public void setMetric() {
        Utils.setUnitsPref(Units.metric);
        System.out.println("set metric in presenter");
    }

    public void setStandard() {
        Utils.setUnitsPref(Units.standard);
        System.out.println("set standard in presenter");
    }

    public void setGpsDefault(boolean value) {
        Utils.setDefaultGps(value);
    }

    public void setDefaultCity(String defaultCity) {
        Utils.setDefaultCity(defaultCity);
    }
}
