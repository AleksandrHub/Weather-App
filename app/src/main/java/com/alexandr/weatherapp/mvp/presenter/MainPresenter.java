package com.alexandr.weatherapp.mvp.presenter;

import com.alexandr.weatherapp.mvp.model.entity.forecastweather.DailyRestModel;
import com.alexandr.weatherapp.mvp.model.entity.forecastweather.HourlyRestModel;
import com.alexandr.weatherapp.mvp.model.repository.OpenWeatherRepo;
import com.alexandr.weatherapp.mvp.view.MainView;
import com.alexandr.weatherapp.navigation.Screens;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import moxy.MvpPresenter;
import ru.terrakok.cicerone.Router;

public class MainPresenter extends MvpPresenter<MainView> {

    List<HourlyRestModel> hourlyForcast = new ArrayList<>();
    List<DailyRestModel> dailyForcast = new ArrayList<>();





    private Scheduler mainThreadScheduler;
    private OpenWeatherRepo openWeatherRepo;
    private Router router;


    public MainPresenter(Router router) {

        this.router = router;
        openWeatherRepo = new OpenWeatherRepo();


    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        getViewState().init();
        router.replaceScreen(new Screens.WeatherScreen());


    }

    public void backClicked(){
        router.exit();
    }



}
