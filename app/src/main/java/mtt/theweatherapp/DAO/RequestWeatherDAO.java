package mtt.theweatherapp.DAO;

import android.location.Address;

import com.johnhiott.darkskyandroidlib.RequestBuilder;
import com.johnhiott.darkskyandroidlib.models.Request;
import com.johnhiott.darkskyandroidlib.models.WeatherResponse;

import mtt.theweatherapp.Interface.WeatherInterface;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by manuel on 20/3/16.
 */
public class RequestWeatherDAO {

    // Request weather all week
    public static void getWeather(Address city, final WeatherInterface weatherInterface){
        final RequestBuilder weather = new RequestBuilder();

        // get current timestamp
        final Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();

        Request request = new Request();
        request.setLat(String.valueOf(city.getLatitude()));
        request.setLng(String.valueOf(city.getLongitude()));
        //request.setTime(ts);
        request.setUnits(Request.Units.SI);
        request.setLanguage(Request.Language.ENGLISH);
        request.addExcludeBlock(Request.Block.CURRENTLY);

        weather.getWeather(request, new Callback<WeatherResponse>() {
            @Override
            public void success(WeatherResponse weatherResponse, Response response) {
                weatherInterface.onForecastSuccess(weatherResponse);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                weatherInterface.onForecastFailure(retrofitError.getCause().getMessage());
            }
        });
    }

    // Request current Weather
    public static void getWeatherByTime(Long tsLong, Address city, final WeatherInterface weatherInterface){
        final RequestBuilder weather = new RequestBuilder();

        // get current timestamp
       // final Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();

        Request request = new Request();
        request.setLat(String.valueOf(city.getLatitude()));
        request.setLng(String.valueOf(city.getLongitude()));
        request.setTime(ts);
        request.setUnits(Request.Units.SI);
        request.setLanguage(Request.Language.ENGLISH);
        request.addExcludeBlock(Request.Block.CURRENTLY);

        weather.getWeather(request, new Callback<WeatherResponse>() {
            @Override
            public void success(WeatherResponse weatherResponse, Response response) {
                weatherInterface.onForecastSuccess(weatherResponse);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                weatherInterface.onForecastFailure(retrofitError.getCause().getMessage());
            }
        });
    }
}
