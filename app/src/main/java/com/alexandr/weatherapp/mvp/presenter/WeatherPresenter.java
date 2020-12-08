package com.alexandr.weatherapp.mvp.presenter;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.alexandr.weatherapp.StaticData;
import com.alexandr.weatherapp.mvp.model.entity.forecastweather.DailyRestModel;
import com.alexandr.weatherapp.mvp.model.entity.forecastweather.HourlyRestModel;
import com.alexandr.weatherapp.mvp.model.repository.OpenWeatherRepo;
import com.alexandr.weatherapp.mvp.presenter.list.IForecastWeatherPresenter;
import com.alexandr.weatherapp.mvp.view.WeatherView;
import com.alexandr.weatherapp.mvp.view.list.DailyWeatherRowView;
import com.alexandr.weatherapp.mvp.view.list.HourlyWeatherRowView;
import com.alexandr.weatherapp.navigation.Screens;
import com.alexandr.weatherapp.utils.Utils;

import java.io.InputStream;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.subjects.PublishSubject;
import moxy.MvpPresenter;
import ru.terrakok.cicerone.Router;
import timber.log.Timber;

public class WeatherPresenter extends MvpPresenter<WeatherView> {

    List<HourlyRestModel> hourlyForcast;
    List<DailyRestModel> dailyForcast;

    public class HourlyForecastWeatherPresenter implements IForecastWeatherPresenter<HourlyWeatherRowView> {
        PublishSubject<HourlyWeatherRowView> clickSubject = PublishSubject.create();

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");

        @Override
        public void bind(HourlyWeatherRowView view) {
            int i =view.getPos();
            HourlyRestModel model = hourlyForcast.get(i);
            view.setTime(String.valueOf(formatter.format( new Time((model.dt*1000)))));
            view.setTemperature(model.temp);
            view.setIcon(model.weather[0].icon);
        }

        @Override
        public int getCount() {
            return hourlyForcast.size();
        }

        @Override
        public PublishSubject<HourlyWeatherRowView> getClickSubject() {
            return clickSubject;
        }
    }

    public class DailyForecastWeatherPresenter implements IForecastWeatherPresenter<DailyWeatherRowView> {
        PublishSubject<DailyWeatherRowView> clickSubject = PublishSubject.create();

        SimpleDateFormat formatter = new SimpleDateFormat("E");
        SimpleDateFormat formatterD = new SimpleDateFormat("d MMMM");

        @Override
        public void bind(DailyWeatherRowView view) {
            DailyRestModel model = dailyForcast.get(view.getPos());


            view.setWeekday(String.valueOf(formatter.format( new Time((model.dt*1000)))));
            view.setDate(String.valueOf(formatterD.format( new Time((model.dt*1000)))));
            view.setTemperatureAfternoon(model.temp.day);
            view.setTemperatureNight(model.temp.night);
            view.setIcon(model.weather[0].icon);

        }

        @Override
        public int getCount() {
            return dailyForcast.size();
        }

        @Override
        public PublishSubject<DailyWeatherRowView> getClickSubject() {
            return clickSubject;
        }
    }

    private Scheduler mainThreadScheduler;
    private OpenWeatherRepo openWeatherRepo;
    private HourlyForecastWeatherPresenter hourlyPresenter;
    private DailyForecastWeatherPresenter dailyPresenter;
    private Router router;

    public WeatherPresenter(Scheduler scheduler, Router router) {

        mainThreadScheduler = scheduler;
        this.router = router;
        openWeatherRepo = new OpenWeatherRepo();
        hourlyPresenter = new HourlyForecastWeatherPresenter();
        dailyPresenter = new DailyForecastWeatherPresenter();

    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadCurrentWeatherDataModel("");
//        downLoadImage();


    }

    public void goToSettings(){

        System.out.println("----go settings-----");
        router.navigateTo(new Screens.SettingsScreen());
    }

    public HourlyForecastWeatherPresenter getHourlyPresenter() {return hourlyPresenter;}

    public DailyForecastWeatherPresenter getDailyPresenter() {return dailyPresenter;}

    @SuppressLint("CheckResult")
    public void loadCurrentWeatherDataModel(String city) {
        getViewState().showLoading();
        Utils.setDefaultCity(city);
        String city2 = (city != null && !city.isEmpty())?city:Utils.getDefaultCity();
        System.out.println("city in presenter = "+city2);
        openWeatherRepo.getForecastWeather(
                55.75f, 37.60f, city2, false, new String[]{}, StaticData.KEY, Utils.getUnitsString()
        )
                .observeOn(mainThreadScheduler)
                .subscribe(forecastWeather -> {
                            System.out.println("city = "+ forecastWeather.timezone);
                            HourlyRestModel[] hourly  = forecastWeather.hourly;
                            DailyRestModel[] daily = forecastWeather.daily;
                            hourlyForcast = Arrays.asList(hourly);
                            dailyForcast = Arrays.asList(daily);

                            getViewState().setCurrentWeather(forecastWeather);
                            getViewState().hideLoading();
                            getViewState().setCity(forecastWeather.timezone);
                            getViewState().init();
                        },
                        t->{
                            System.out.println("exeption = "+t);
                        });

    }

    public boolean backClicked(){
        router.exit();
        return true;
    }








    Bitmap bitmap1;

    @SuppressLint("CheckResult")
    public Bitmap downLoadImage(){

        Bitmap bitmap=null;

        openWeatherRepo.getIconWeather("https://openweathermap.org/img/wn/10d@2x.png")
                .observeOn(mainThreadScheduler)
                .flatMap(responseBody -> {


                    InputStream in = null;

                    assert responseBody != null;
                    in = responseBody.byteStream();
                    bitmap1 = BitmapFactory.decodeStream(in);

//                    int c;
//                    List<Byte> arrays = new ArrayList<Byte>();
//                    boolean result;
//
//                    while (true) {
//                        try {
//                            if ((c = in.read()) != -1) {
//                                arrays.add(Integer.valueOf(c).byteValue());
//                                System.out.print(c);
//                                System.out.println("длина arrays = "+ arrays.size() +" . arrays[" + (arrays.size()-1) + "] = " + arrays.get(arrays.size()-1));
//                            }
//                            else {
//                                System.out.println("вышли из цикла");
//                                break;
//                            }
//
//
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                            result = false;
//                            System.out.println("сработал catch");
//                            break;
//                        }
//                    }
//
//
//
//                    if (!arrays.isEmpty()) {
//                        System.out.println("arrays не пустой");
//                        byte[] arraysbyte = new byte[arrays.size()];
//
//                        for(int i=0; i<arrays.size(); i++ )
//                            arraysbyte[i] = arrays.get(i);
//
//                        System.out.println("arraysbyte длина = " + arraysbyte.length);
//                        bitmap1 = getBitmapfromByteArray(arraysbyte);
//                        System.out.println("hight bitmap =" + bitmap1.getHeight());
//                    }

                    return Single.create(e -> {
                        e.onSuccess(bitmap1);
                    });
                })
                .subscribe(icon -> {

//                    getViewState().setimage((Bitmap) icon);



                }, t -> {Timber.e(t);
                    System.out.println(t); });

//        System.out.println("length bitmap " + bitmap1.getByteCount());

        System.out.println("-------return");
        return bitmap1;
    }



    public Bitmap getBitmapfromByteArray(byte[] bitmap) {
        return BitmapFactory.decodeByteArray(bitmap, 0, bitmap.length);
    }




}
