package com.alexandr.weatherapp.navigation;

import androidx.fragment.app.Fragment;

import com.alexandr.weatherapp.ui.fragments.SettingsFragment;
import com.alexandr.weatherapp.ui.fragments.WeatherFragment;

import ru.terrakok.cicerone.android.support.SupportAppScreen;

public class Screens {

    public static class WeatherScreen extends SupportAppScreen{
        @Override
        public Fragment getFragment() {
            return WeatherFragment.newInstance();
        }
    }

    public static class SettingsScreen extends SupportAppScreen{
        @Override
        public Fragment getFragment() {
            return SettingsFragment.newInstance();
        }
    }

}
