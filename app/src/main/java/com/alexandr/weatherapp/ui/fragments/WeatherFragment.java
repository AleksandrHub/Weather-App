package com.alexandr.weatherapp.ui.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexandr.weatherapp.R;
import com.alexandr.weatherapp.mvp.model.entity.currentweather.CurrentWeatherDataModel;
import com.alexandr.weatherapp.mvp.model.entity.forecastweather.ForecastWeatherDataModel;
import com.alexandr.weatherapp.mvp.presenter.WeatherPresenter;
import com.alexandr.weatherapp.mvp.view.WeatherView;
import com.alexandr.weatherapp.ui.App;
import com.alexandr.weatherapp.ui.BackButtonListener;
import com.alexandr.weatherapp.ui.adapters.DailyForecastRVAdapter;
import com.alexandr.weatherapp.ui.adapters.HourlyForecastRVAdapter;
import com.alexandr.weatherapp.utils.Indicators;
import com.alexandr.weatherapp.utils.Utils;
import com.alexandr.weatherapp.utils.ui.UIUtils;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;

public class WeatherFragment extends MvpAppCompatFragment implements WeatherView, BackButtonListener {

    @InjectPresenter
    WeatherPresenter presenter;

    @ProvidePresenter
    public WeatherPresenter createPresenter() {
        return new WeatherPresenter(AndroidSchedulers.mainThread(), App.getInstance().getRouter());
    }

    private Unbinder unbinder;

    @BindView(R.id.tv_temperature)
    TextView temperatureTV;

    @BindView(R.id.tv_humidity)
    TextView humidityTV;

    @BindView(R.id.tv_speed)
    TextView speedTV;

    @BindView(R.id.tv_pressure)
    TextView pressureTV;

    @BindView(R.id.iv_weatherIcon)
    ImageView weatherIconIV;

    @BindView(R.id.iv_speed)
    ImageView imageView;

    @BindView(R.id.rv_hourly)
    RecyclerView hoourlyRV;

    @BindView(R.id.rv_daily)
    RecyclerView dailyRV;

    @BindView(R.id.progress_bar_round)
    ProgressBar progressBar;

    @BindView(R.id.card_view)
    CardView cardView;

    @BindView(R.id.cv_daily_forecast)
    CardView dailyForecastCV;

    @BindView(R.id.iv_settings)
    ImageView settingsIV;

    @BindView(R.id.iet_search_city)
    TextInputLayout searchCityIET;

    @BindView(R.id.fab_search)
    ImageView searchFAB;

    @BindView(R.id.tv_description)
    TextView descriptionTV;

    @BindView(R.id.tv_city)
    TextView cityTV;

    @OnClick(R.id.iv_settings)
    public void clickSettings(){
        presenter.goToSettings();
    }

    @OnClick(R.id.fab_search)
    public void clickFabSearch(){
        searchWeather();
    }

    public static WeatherFragment newInstance(){
        return new WeatherFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View layout = View.inflate(getContext(), R.layout.fragment_weather_layout, null);
        unbinder = ButterKnife.bind(this, layout);
        cardView.setBackgroundResource(R.drawable.custom_corner);
        dailyForecastCV.setBackgroundResource(R.drawable.custom_corner);

        return layout;
    }


    @Override
    public void init() {
        Toast.makeText(getActivity(), "init() count =" + presenter.getHourlyPresenter().getCount(), Toast.LENGTH_LONG).show();
        hoourlyRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        hoourlyRV.setAdapter(new HourlyForecastRVAdapter(presenter.getHourlyPresenter()));

        dailyRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        dailyRV.setAdapter(new DailyForecastRVAdapter(presenter.getDailyPresenter()));

    }

    @Override
    public void updateList() {

    }

    @Override
    public void showMessage(String text) {

    }

    @Override
    public void showLoading() {
        cardView.setVisibility(View.GONE);
        hoourlyRV.setVisibility(View.GONE);
        dailyRV.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        cardView.setVisibility(View.VISIBLE);
        hoourlyRV.setVisibility(View.VISIBLE);
        dailyRV.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setCurrentWeather(ForecastWeatherDataModel forecastWeatherDataModel) {

        temperatureTV.setText(UIUtils.addUnits(forecastWeatherDataModel.current.temp, Indicators.Temperature, Utils.getUnits()));
        pressureTV.setText(UIUtils.addUnits(forecastWeatherDataModel.current.pressure, Indicators.Pressure,Utils.getUnits()));
        speedTV.setText(UIUtils.addUnits(forecastWeatherDataModel.current.windSpeed, Indicators.Speed,Utils.getUnits()));
        humidityTV.setText(UIUtils.addUnits(forecastWeatherDataModel.current.humidity, Indicators.Humidity,Utils.getUnits()));
        weatherIconIV.setImageDrawable(getContext().getDrawable(Utils.getIconRes(forecastWeatherDataModel.current.weather[0].icon)));
        descriptionTV.setText(forecastWeatherDataModel.current.weather[0].description);

    }


    @Override
    public void setCity(String city) {
        cityTV.setText(city);
    }

    @Override
    public void searchWeather() {
        System.out.println("click on search, city = "+ searchCityIET.getEditText().getText().toString());
        presenter.loadCurrentWeatherDataModel(searchCityIET.getEditText().getText().toString());

    }

    @Override
    public void loadImage(String url) {
//        picassoImageLoader.loadInto(url,  weatherIconIV);

    }

    @Override
    public boolean backClicked() {
        return presenter.backClicked();
    }
}
