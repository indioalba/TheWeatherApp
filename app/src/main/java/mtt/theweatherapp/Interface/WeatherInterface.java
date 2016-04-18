package mtt.theweatherapp.Interface;

import com.johnhiott.darkskyandroidlib.models.WeatherResponse;

/**
 * Created by manuel on 20/3/16.
 */
public interface WeatherInterface {

    void onForecastSuccess(WeatherResponse weatherResponse);
    void onForecastFailure(String errorCause);
}

