package com.alexandr.weatherapp.ui.activity;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import com.alexandr.weatherapp.R;
import com.alexandr.weatherapp.mvp.presenter.MainPresenter;
import com.alexandr.weatherapp.mvp.view.MainView;
import com.alexandr.weatherapp.ui.App;
import com.alexandr.weatherapp.ui.BackButtonListener;
import com.alexandr.weatherapp.utils.Utils;
import com.alexandr.weatherapp.utils.Units;

import java.util.List;

import butterknife.ButterKnife;
import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.android.support.SupportAppNavigator;

import static java.lang.Math.round;


public class MainActivity extends MvpAppCompatActivity implements MainView {
    private static final String TAG = "test";
    private LocationManager locationManager;
    private Location location;

    private Navigator navigator = new SupportAppNavigator(this, R.id.fl_container);

    @InjectPresenter
    MainPresenter presenter;

    @ProvidePresenter
    public MainPresenter createPresenter() {
        return new MainPresenter(App.getInstance().getRouter());
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        System.out.println("activ pref = "+ Utils.getUnitsString());

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        checkPermission();

//        asd

//        sss
//        aaa

    }

    private void checkPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        } else
        {

            if(true)
            locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER,
                    3000L, 0, locationListener);
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            System.out.println("Latitude = "+location.getLatitude());
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100 && grantResults[0]==RESULT_OK)
        {
            checkPermission();
        }
    }

    private LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            System.out.println("Location changed" +location.toString());
        }

        @Override
        public void onProviderDisabled(String provider) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
    };


    @Override
    public void init() {


    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        App.getInstance().getNavigatorHolder().setNavigator(navigator);
    }

    @Override
    protected void onPause() {
        super.onPause();
        App.getInstance().getNavigatorHolder().removeNavigator();
    }

    @Override
    public void onBackPressed() {
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        for(Fragment f: fragmentList){
            if(f instanceof BackButtonListener && ((BackButtonListener) f).backClicked()){
                return;
            }
        }
        presenter.backClicked();
    }
}