package mtt.theweatherapp.fragment;

import android.app.ProgressDialog;
import android.location.Address;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.johnhiott.darkskyandroidlib.models.WeatherResponse;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import mtt.theweatherapp.DAO.RequestCitiesDAO;
import mtt.theweatherapp.DAO.RequestWeatherDAO;
import mtt.theweatherapp.Interface.WeatherInterface;
import mtt.theweatherapp.R;
import mtt.theweatherapp.adapter.FutureAdapter;
import mtt.theweatherapp.adapter.FutureRecycleAdapter;
import mtt.theweatherapp.utils.MyUtils;

/**
 * Created by manuel on 20/3/16.
 */
public class FutureFragment extends Fragment{

//    @Bind(R.id.lvFuture)
//    ListView lvFuture;
    @Bind(R.id.tvFutureCity)
    TextView tvFutureCity;

    FutureAdapter fAdapter;
    WeatherResponse wResponse = new WeatherResponse();
    ProgressDialog pd;

    RecyclerView recyclerView;

    private ArrayList<Integer> alFuture = new ArrayList<Integer>(8);

    public FutureFragment() {
        Log.i("FutureFramgnet", "constructor");
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static FutureFragment newInstance(int sectionNumber) {
        FutureFragment fragment = new FutureFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.future_forecast, container, false);
        ButterKnife.bind(this, rootView);

       /********************** RECYCLE ****************************/
        recyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        pd = new ProgressDialog(getContext());
        pd.setMessage(getString(R.string.looking_to_the_sky));
        setWeatherAllWeek(RequestCitiesDAO.getCurrentCity(getContext()));
    }


    public void setWeatherAllWeek(Address city){

        // check connection
        if(!MyUtils.isThereConnection(getContext())){
            Toast.makeText(getContext(), getString(R.string.no_connection), Toast.LENGTH_LONG).show();
        }else {
            pd.show();
            tvFutureCity.setText(city.getFeatureName());
            RequestWeatherDAO.getWeather(city, new WeatherInterface() {
                @Override
                public void onForecastSuccess(WeatherResponse weatherResponse) {
                    if(getActivity() != null && isAdded()) {
                        if (pd != null) {
                            pd.dismiss();
                        }
                        // adding Adapter to listView
//                    fAdapter = new FutureAdapter(getActivity(), weatherResponse);
//                    lvFuture.setAdapter(fAdapter);

                        /****************** RECYCLE ****************/
                        recyclerView.setAdapter(new FutureRecycleAdapter(getContext(), weatherResponse));
                    }
                }

                @Override
                public void onForecastFailure(String errorCause) {
                    if(getActivity() != null && !isAdded()) {
                        pd.dismiss();

                        Toast.makeText(getContext(), errorCause, Toast.LENGTH_LONG).show();
                        /* do something */
                    }
                }
            });
        }
    }

}