package com.alexandr.weatherapp.mvp.model.repository;

import com.alexandr.weatherapp.mvp.model.api.ApiHolder;
import com.alexandr.weatherapp.mvp.model.api.INetworkStatus;
import com.alexandr.weatherapp.mvp.model.entity.currentweather.CurrentWeatherDataModel;
import com.alexandr.weatherapp.mvp.model.entity.forecastweather.CurrentRestModel;
import com.alexandr.weatherapp.mvp.model.entity.forecastweather.DailyRestModel;
import com.alexandr.weatherapp.mvp.model.entity.forecastweather.ForecastWeatherDataModel;
import com.alexandr.weatherapp.mvp.model.entity.forecastweather.HourlyRestModel;
import com.alexandr.weatherapp.mvp.model.entity.forecastweather.TempRestModel;
import com.alexandr.weatherapp.mvp.model.entity.forecastweather.WeatherRestModel;
import com.alexandr.weatherapp.room.RoomCurrentWeather;
import com.alexandr.weatherapp.room.RoomDailyWeather;
import com.alexandr.weatherapp.room.RoomHourlyWeather;
import com.alexandr.weatherapp.room.db.WeatherDataBase;
import com.alexandr.weatherapp.ui.NetworkStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;


public class OpenWeatherRepo {

    INetworkStatus networkStatus = new NetworkStatus();

    public Single<CurrentWeatherDataModel> getCurrentWeather(String... param) {

        return ApiHolder.getSingleton().getAPI().loadCurrentWeather(param[0], param[1], param[2])
                .subscribeOn(Schedulers.io());
    }


    public Single<ForecastWeatherDataModel> getForecastWeather(
            float lat, float lon, String _city, boolean coord, String[] exclude, String keyApi, String units) {


        String city = _city.substring(0,1).toUpperCase() + _city.substring(1);
        System.out.println("city in repo ="+city);
        System.out.println("into getForecastWeather");
        if (networkStatus.isOnline()) {
            System.out.println("into getForecastWeather online");
            if (coord == true) return getForecast(lat, lon, exclude, keyApi, units);
            else  return ApiHolder.getSingleton().getAPI().loadCurrentWeather(city, keyApi,units)
                    .subscribeOn(Schedulers.io())
                    .flatMap(new Function<CurrentWeatherDataModel, SingleSource<? extends ForecastWeatherDataModel>>() {
                        @Override
                        public SingleSource<? extends ForecastWeatherDataModel> apply(
                                CurrentWeatherDataModel currentWeatherDataModel) throws Exception {
                            CurrentWeatherDataModel current = currentWeatherDataModel;
                            System.out.println("into getForecastWeather_city, current name = "+ current.name);
                            return getForecast(current.coordinates.lat, current.coordinates.lon, exclude, keyApi, units);
                        }
                    });


        } else {
            System.out.println("into getForecastWeather offline");
            return Single.create(emitter -> {

                RoomCurrentWeather current;
                String city2;
                if(coord == true){
                    System.out.println("coord = true");
                     current = WeatherDataBase.getInstance().getCurrentWeatherDao()
                            .findCurrentWeatherByCoordinates(lat, lon);
                    System.out.println("coord = true, current city in room = "+current.getTimeZone());
                     if(current != null) {
                         city2 = current.getTimeZone();
                     } else new RuntimeException("No such weather in cache");
                } else{
                    System.out.println("coord = false, city = " +city);
                    current = WeatherDataBase.getInstance().getCurrentWeatherDao()
                            .findCurrentWeatherByCity(city);
                    System.out.println("coord = false, current city in room = "+current.getTimeZone());
                }

                System.out.println("current in room city ="+current.getTimeZone());

                List<RoomHourlyWeather> hourlyWeathers = WeatherDataBase.getInstance().getHourlyWeatherDao()
                        .findHourlyWeathersByCity(current.getTimeZone());

                List<RoomDailyWeather> dailyWeathers = WeatherDataBase.getInstance().getDailyWeatherDao()
                        .findDailyWeathersByCity(current.getTimeZone());

                if (current == null || hourlyWeathers == null || dailyWeathers == null) {
                    emitter.onError(new RuntimeException("No such weather in cache"));
                } else {
                    emitter.onSuccess(
                            convertToForecastWeatherDataModel(current, hourlyWeathers, dailyWeathers)
                    );
                }
            }).subscribeOn(Schedulers.io()).cast(ForecastWeatherDataModel.class);
        }
    }


    private Single<ForecastWeatherDataModel> getForecast(
            float lat, float lon, String[] exclude, String keyApi, String units){
        return ApiHolder.getSingleton().getAPI().loadForecastWeather(lat,lon, exclude, keyApi, units)
                .subscribeOn(Schedulers.io()).map(forecast -> {

                    String[] text = Pattern.compile("/").split(forecast.timezone);
                    String city = text[text.length-1];
                    System.out.println("into convert, city = "+city);
                    forecast.timezone = city;

            WeatherDataBase.getInstance().getCurrentWeatherDao().insert(convertToRoomCurrentWeather(forecast));


            WeatherDataBase.getInstance().getHourlyWeatherDao()
                    .insert(convertToRoomHourlyWeathers(forecast));

            WeatherDataBase.getInstance().getDailyWeatherDao().insert(convertToRoomDailyWeathers(forecast));
            return forecast;
        });
    }

    public Single<ResponseBody> getIconWeather(String url) {
        return ApiHolder.getSingleton().getAPI().loadIconWeather(url).subscribeOn(Schedulers.io());
    }


    private RoomCurrentWeather convertToRoomCurrentWeather(ForecastWeatherDataModel forecast) {
        RoomCurrentWeather current = new RoomCurrentWeather(
                forecast.timezone,
                forecast.lat,
                forecast.lon,
                forecast.current.dt,
                forecast.current.temp,
                forecast.current.pressure,
                forecast.current.humidity,
                forecast.current.weather[0].icon,
                forecast.current.weather[0].main,
                forecast.current.weather[0].description,
                forecast.current.weather[0].id
        );
        System.out.println("//current = " + current.getTimeZone() + "\n temp ="+current.getTemp());
        return current;
    }

    private List<RoomHourlyWeather> convertToRoomHourlyWeathers(ForecastWeatherDataModel forecast) {
        List<RoomHourlyWeather> hourlyWeathers = new ArrayList<>();

        for (HourlyRestModel o: forecast.hourly) {
            RoomHourlyWeather hourlyWeather = new RoomHourlyWeather(
                forecast.timezone,
                o.dt,
                o.temp,
                o.weather[0].id,
                o.weather[0].description,
                o.weather[0].icon
            );
            hourlyWeathers.add(hourlyWeather);
        }

        return hourlyWeathers;
    }

    private List<RoomDailyWeather> convertToRoomDailyWeathers(ForecastWeatherDataModel forecast) {
        List<RoomDailyWeather> dailyWeathers = new ArrayList<>();

        for (DailyRestModel o: forecast.daily) {
            RoomDailyWeather dailyWeather = new RoomDailyWeather(
                    forecast.timezone,
                    o.dt,
                    o.temp.day,
                    o.temp.night,
                    o.weather[0].id,
                    o.weather[0].description,
                    o.weather[0].icon
            );
            dailyWeathers.add(dailyWeather);
        }

        return dailyWeathers;
    }

    private ForecastWeatherDataModel convertToForecastWeatherDataModel(RoomCurrentWeather _current,
        List<RoomHourlyWeather> _hourlyWeathers, List<RoomDailyWeather> _dailyWeathers){
        ForecastWeatherDataModel forecast = new ForecastWeatherDataModel();
        forecast.lat = _current.getLat();
        forecast.lon = _current.getLon();
        forecast.timezone = _current.getTimeZone();
        forecast.current = convertToCurrentRestModel(_current);
        forecast.hourly = convertToHourlyRestModels(_hourlyWeathers);
        forecast.daily = convertToDailyRestModels(_dailyWeathers);

        return forecast;
    }

    private CurrentRestModel convertToCurrentRestModel(RoomCurrentWeather _current){
        CurrentRestModel current = new CurrentRestModel();
        current.dt = _current.getDt();
        current.temp = _current.getTemp();
        current.pressure = _current.getPressure();
        current.humidity = _current.getHumidity();
        current.weather = convertToWeatherRestModels(_current);

        System.out.println("current.weather ="+current.weather+"\n weather.icon="+current.weather[0].icon);

        return current;
    }

    private WeatherRestModel[] convertToWeatherRestModels(RoomCurrentWeather _current){
        WeatherRestModel[] weatherRestModels = new WeatherRestModel[]{new WeatherRestModel(
              _current.getId(),
              _current. getMain() ,
                _current.getDescription(),
                _current.getIcon()
        )};
        return weatherRestModels;
    }

    private WeatherRestModel[] convertToWeatherRestModels(RoomDailyWeather _daily){
        WeatherRestModel[] weatherRestModels = new WeatherRestModel[]{new WeatherRestModel(
                _daily.getId(),
                _daily. getDescription() ,
                _daily.getDescription(),
                _daily.getIcon()
        )};
        return weatherRestModels;
    }

    private WeatherRestModel[] convertToWeatherRestModels(RoomHourlyWeather _hourly){
        WeatherRestModel[] weatherRestModels = new WeatherRestModel[]{new WeatherRestModel(
                _hourly.getId(),
                _hourly. getDescription() ,
                _hourly.getDescription(),
                _hourly.getIcon()
        )};
        return weatherRestModels;
    }

    private HourlyRestModel[] convertToHourlyRestModels(List<RoomHourlyWeather> _hourlyWeathers){
        List<HourlyRestModel> hourlyWeathers = new ArrayList<>();

        for (RoomHourlyWeather o:_hourlyWeathers) {
            HourlyRestModel hourly = new HourlyRestModel();
            hourly.dt = o.getDt();
            hourly.temp = o.getTemp();
            hourly.weather = new WeatherRestModel[]{
                    new WeatherRestModel(o.getId(), o.getDescription(), o.getDescription(), o.getIcon())
            };
            hourlyWeathers.add(hourly);
        }

        return hourlyWeathers.toArray(new HourlyRestModel[hourlyWeathers.size()]);
    }

    private DailyRestModel[] convertToDailyRestModels(List<RoomDailyWeather> _dailyWeathers){
        List<DailyRestModel> dailyWeathers = new ArrayList<>();

        for (RoomDailyWeather o:_dailyWeathers) {
            DailyRestModel daily = new DailyRestModel();
            daily.dt = o.getDt();
            daily.temp = convertToTempRestModels(o);
            daily.weather = convertToWeatherRestModels(o);
            dailyWeathers.add(daily);
        }

        return dailyWeathers.toArray(new DailyRestModel[_dailyWeathers.size()]);
    }

    private TempRestModel convertToTempRestModels(RoomDailyWeather daily){
        TempRestModel tempRestModel = new TempRestModel();
        tempRestModel.day = daily.tempDay;

        return tempRestModel;
    }
}
