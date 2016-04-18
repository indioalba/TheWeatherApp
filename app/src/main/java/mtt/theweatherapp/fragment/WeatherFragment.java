package mtt.theweatherapp.fragment;

import android.app.ProgressDialog;
import android.location.Address;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.johnhiott.darkskyandroidlib.models.WeatherResponse;

import butterknife.Bind;
import butterknife.ButterKnife;
import mtt.theweatherapp.DAO.RequestCitiesDAO;
import mtt.theweatherapp.DAO.RequestWeatherDAO;
import mtt.theweatherapp.Interface.WeatherInterface;
import mtt.theweatherapp.R;
import mtt.theweatherapp.utils.MyUtils;

/**
 * Created by manuel on 20/3/16.
 */
public class WeatherFragment extends Fragment {

    @Bind(R.id.tvWeatherCity)
    TextView tvCity;
    @Bind(R.id.ivWeatherIcon)
    ImageView ivIcon;
    @Bind(R.id.tvWeatherHumidity)
    TextView tvHumidity;
    @Bind(R.id.tvWeatherTemperature)
    TextView tvTemperature;
    @Bind(R.id.tvWeatherTime)
    TextView tvTime;
    @Bind(R.id.tvWeatherWind)
    TextView tvWind;
    @Bind(R.id.ivWeatherRefresh)
    ImageView ivWeatherRefresh;

    private Long tsLong; // timestamp
    private ProgressDialog pd;

    public WeatherFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static WeatherFragment newInstance(int sectionNumber) {
        WeatherFragment fragment = new WeatherFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.weather, container, false);
        // bind the view to butterknife
        ButterKnife.bind(this, rootView);
        //setRetainInstance(true);

        return rootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // catch refresh button click
        ivWeatherRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd = new ProgressDialog(getContext());
                pd.setMessage(getString(R.string.looking_to_the_sky));
                pd.show();
                setWeather(RequestCitiesDAO.getCurrentCity(getContext()));
            }
        });

        setWeather(RequestCitiesDAO.getCurrentCity(getContext()));
    }

    private void setWeather(Address cCity){

        // check connection
        if(!MyUtils.isThereConnection(getContext())){
            Toast.makeText(getContext(), getString(R.string.no_connection), Toast.LENGTH_LONG).show();
        }else {
            // get current timestamp
            tsLong = System.currentTimeMillis() / 1000;

            // make request
            RequestWeatherDAO.getWeatherByTime(tsLong, cCity, new WeatherInterface() {
                @Override
                public void onForecastSuccess(WeatherResponse weatherResponse) {
                    if(getActivity() != null && isAdded()) {
                        if (pd != null) {
                            pd.dismiss();
                        }
                        // set Values
                        tvTime.setText(getString(R.string.last_updated, MyUtils.timestampToDate(tsLong)));
                        float humidityValue = Float.parseFloat(weatherResponse.getHourly().getData().get(0).getHumidity()) * 100;
                        tvCity.setText(RequestCitiesDAO.getCurrentCity(getContext()).getFeatureName());
                        tvHumidity.setText(getString(R.string.humidity_value, humidityValue));
                        tvTemperature.setText(getString(R.string.temp_value, weatherResponse.getHourly().getData().get(0).getTemperature()));
                        tvWind.setText(getString(R.string.wind_value, weatherResponse.getHourly().getData().get(0).getWindSpeed()));
                        ivIcon.setImageResource(MyUtils.getDrabwableByName(getActivity(), weatherResponse.getHourly().getIcon()));
                    }
                }

                @Override
                public void onForecastFailure(String errorCause) {
                    if(getActivity() != null && isAdded()) {
                        if (pd != null) {
                            pd.dismiss();
                        }
                        Toast.makeText(getContext(), errorCause, Toast.LENGTH_LONG).show();
                        /* do something */
                    }
                }
            });
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}