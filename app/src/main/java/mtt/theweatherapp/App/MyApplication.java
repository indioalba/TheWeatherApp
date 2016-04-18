package mtt.theweatherapp.App;

import android.app.Application;

import com.johnhiott.darkskyandroidlib.ForecastApi;

/**
 * Created by manuel on 20/3/16.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // api key https://developer.forecast.io/
        ForecastApi.create("fdfa57ad9889c42cd663016812602465");

    }
}
