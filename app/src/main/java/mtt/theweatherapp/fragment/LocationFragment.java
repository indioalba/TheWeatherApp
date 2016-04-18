package mtt.theweatherapp.fragment;

import android.content.Context;
import android.location.Address;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import mtt.theweatherapp.DAO.RequestCitiesDAO;
import mtt.theweatherapp.R;
import mtt.theweatherapp.adapter.LocationRecycleAdapter;
import mtt.theweatherapp.utils.MyUtils;

/**
 * Created by manuel on 20/3/16.
 */
public class LocationFragment extends Fragment  implements View.OnClickListener{ //implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks

    @Bind(R.id.acLocationCity)
    TextView acLocationCity;
    @Bind(R.id.btSearch)
    Button btSearch;
//    @Bind(R.id.lvLocation)
//    ListView lvLocation;
    @Bind(R.id.rvLocation)
    RecyclerView rvLocation;

//    private LocationAdapter locationAdapter;
    private LocationRecycleAdapter locationRecycleAdapter;
    private ArrayList<Address> alCities;
    private RecyclerView recyclerView;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static LocationFragment newInstance(int sectionNumber) {
        LocationFragment fragment = new LocationFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.location, container, false);
        ButterKnife.bind(this, rootView);

        btSearch.setOnClickListener(this);
        setRetainInstance(true);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        alCities = RequestCitiesDAO.loadData(getContext());
        if(alCities == null){
            alCities = new ArrayList<Address>();
        }
//        locationAdapter = new LocationAdapter(getContext(), alCities);
//        lvLocation.setAdapter(locationAdapter);
        rvLocation.setLayoutManager(new LinearLayoutManager(getActivity()));
        locationRecycleAdapter = new LocationRecycleAdapter(getContext(), alCities);
        rvLocation.setAdapter(locationRecycleAdapter);


        acLocationCity.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // catch event  "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Search city
                    searchCity();
                    // hide keyboard
                    hideKeyboard(acLocationCity);
                    return true;
                }
                return false;
            }
        });

    }

    private void hideKeyboard(View view){
        // hide keyboard
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void searchCity(){
        // hide keyboard
        hideKeyboard(acLocationCity);
        // check connection
        if(!MyUtils.isThereConnection(getContext())){
            Toast.makeText(getContext(), getString(R.string.no_connection), Toast.LENGTH_LONG).show();
        }else {
            Address newCity = MyUtils.getLocationByName(getContext(), acLocationCity.getText().toString());
            if (newCity == null) {
                Toast.makeText(getContext(), getString(R.string.error_adding_city), Toast.LENGTH_LONG).show();
                return;
            }
            // clean the city name
            acLocationCity.setText("");
            RequestCitiesDAO.addNewCity(getContext(), newCity);
            //alCities.add(newCity);
            locationRecycleAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            // Search new city by name
            case R.id.btSearch:
                searchCity();
                break;
        }
    }

}