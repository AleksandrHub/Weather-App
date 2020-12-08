package com.alexandr.weatherapp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alexandr.weatherapp.R;
import com.alexandr.weatherapp.mvp.presenter.SettingsPresenter;
import com.alexandr.weatherapp.mvp.view.MainView;
import com.alexandr.weatherapp.mvp.view.SettingsView;
import com.alexandr.weatherapp.ui.App;
import com.alexandr.weatherapp.ui.BackButtonListener;
import com.alexandr.weatherapp.utils.Units;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;

public class SettingsFragment extends MvpAppCompatFragment implements SettingsView, BackButtonListener {
    private static final String TAG = "test";

    private Unbinder unbinder;

    @BindView(R.id.cb_default_city)
    CheckBox defaultCityCB;

    @OnClick(R.id.cb_default_city)
    public void onClickDefaultCity(){
        System.out.println("SSSSSSSSS");
        presenter.setDefaultCity(defaultCityIET.getText().toString());
    }

    @BindView(R.id.iet_default_city)
    TextInputEditText defaultCityIET;

    @BindView(R.id.cb_gps_default)
    CheckBox gpsDefaultCityCB;

    @OnClick(R.id.cb_gps_default)
    public void onClickGpsDefault(){
        System.out.println("WWWWW");
    }

    @BindView(R.id.rg_units)
    RadioGroup unitsRG;

    @BindView(R.id.rb_imperial_units)
    RadioButton imperialUnitsRB;

    @OnClick(R.id.rb_imperial_units)
    public void onClickImperial(){
        System.out.println("imperial");

    }

    @BindView(R.id.rb_metric_units)
    RadioButton metricUnitsRB;

    @OnClick(R.id.rb_metric_units)
    public void onClickMetric(){
        System.out.println("metric");

    }

    @BindView(R.id.rb_standard_units)
    RadioButton standardUnitsRB;

    @OnClick(R.id.rb_standard_units)
    public void onClickStandard(){
        System.out.println("standard");

    }

    @BindView(R.id.btn_ok)
    Button okBTN;

    @BindView(R.id.iv_back)
    ImageView backIV;

    @OnClick(R.id.iv_back)
    public void clickSettings(){
        presenter.goToWeather();
    }

    @OnClick(R.id.btn_ok)
    public void onClickOk(){
        setPrefAndGoBack();
    }

    @InjectPresenter
    SettingsPresenter presenter;

    @ProvidePresenter
    public SettingsPresenter createPresenter() {
        return new SettingsPresenter(App.getInstance().getRouter());
    }



    public static SettingsFragment newInstance(){
        return new SettingsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View layout = View.inflate(getContext(), R.layout.fragment_settings_layout, null);
        unbinder = ButterKnife.bind(this, layout);

        return layout;
    }


    @Override
    public void init() {


    }

    @Override
    public void setDefaultCity(String city) {
        defaultCityIET.setText(city);
    }

    @Override
    public void setGpsDefault(boolean value) {
        gpsDefaultCityCB.setChecked(value);
    }

    @Override
    public void setDefaultUnits(Units units) {
        System.out.println("in fragment void setdefault, unit = "+units.getValue());
        switch (units){
            case standard:  {unitsRG.check(R.id.rb_standard_units);System.out.println("fearst atach, checked standard");} break;
            case imperial: {unitsRG.check(R.id.rb_imperial_units);System.out.println("fearst atach, checked imperial");} break;
            case metric:  {unitsRG.check(R.id.rb_metric_units);System.out.println("fearst atach, checked metric");} break;
        }

    }


    @Override
    public boolean backClicked() {
        return presenter.backClicked();
    }

    private void setPrefAndGoBack(){
        presenter.setDefaultCity(defaultCityIET.getText().toString());

        if(gpsDefaultCityCB.isChecked())  presenter.setGpsDefault(true);
        else presenter.setGpsDefault(false);

        switch (unitsRG.getCheckedRadioButtonId()){
            case R.id.rb_imperial_units: { System.out.println("RG checked =imperial"); presenter.setImperial();} break;
            case R.id.rb_metric_units: {System.out.println("RG checked =metric");presenter.setMetric();} break;
            case R.id.rb_standard_units: {System.out.println("RG checked =standard");presenter.setStandard();} break;
        }

        presenter.goToWeather();
    }


}
