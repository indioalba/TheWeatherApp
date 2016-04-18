package mtt.theweatherapp.DAO;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import mtt.theweatherapp.R;
import mtt.theweatherapp.utils.MyUtils;

/**
 * Created by manuel on 20/3/16.
 */
public class RequestCitiesDAO {

    private static final String PREFERENCES_NAME = "THEFORECAST";
    private static String DEFAULT_CITIES[] = {"London", "New York", "Barcelona", "Dublin"};

    private static ArrayList<Address> alCity;
    private static Address currentCity;


    // in case there are no cities saved, we add the 4 default cities
    public static void initializeApp(Context context){
        alCity = loadData(context);
        if(alCity == null){
            alCity = new ArrayList<Address>();
            addDefaultLocations(context);
        }else{
            if(alCity.size()== 0){
                alCity = new ArrayList<Address>();
                addDefaultLocations(context);
            }
        }
    }
    // look the Address of each city and we add it as a new City
    private static void addDefaultLocations(Context context){
        for( int i = 0; i < DEFAULT_CITIES.length; i++)
        {
            Address newCity = MyUtils.getLocationByName(context, DEFAULT_CITIES[i]);
            if(newCity == null){
                Toast.makeText(context, context.getString(R.string.error_adding_city), Toast.LENGTH_LONG);
                break;
            }
            addNewCity(context, newCity);
        }
    }


    //Get cities from sharedPreferences
    public static ArrayList<Address> loadData (Context context) {

        // Getting the preferences
        SharedPreferences settings = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        // load city list
        String json = settings.getString("city_list", "");
        alCity = gson.fromJson(json, new TypeToken<ArrayList<Address>>() {
        }.getType());
        // load current city
        json = settings.getString("current_city", "");
        currentCity = gson.fromJson(json, Address.class);
        return alCity;
    }

    //Save data in sharedPreferences
    public static void saveData (Context context, ArrayList alCity){
        // To get the editor
        SharedPreferences settings = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = settings.edit();
        Gson gson = new Gson();
        String json = gson.toJson(alCity);
        editor.putString("city_list", json);

        // Commit the edits!
        editor.commit();
    }

    public static Address addNewCity(Context context, Address newCity) {
        // Save in list
        saveCurrentCity(context, newCity);
        alCity.add(newCity);
        saveData(context, alCity);
        return newCity;
    }

    public static void removeCity(Context context, Address city) {
        if(alCity.remove(city)){
            // if the city removed was the current city, we assign the current city to the first city in the list
            if(currentCity.getFeatureName().equalsIgnoreCase(city.getFeatureName())){
                if(alCity.size()>0) {
                    currentCity = alCity.get(0);
                    saveCurrentCity(context, currentCity);
                }
            }
            saveData(context, alCity);
        }

    }

    //Save data in sharedPreferences
    public static void saveCurrentCity (Context context, Address cCity){
        // set currentCity
        currentCity = cCity;
        // To get the editor
        SharedPreferences settings = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = settings.edit();
        Gson gson = new Gson();
        String json = gson.toJson(cCity);
        editor.putString("current_city", json);

        // Commit the edits!
        editor.commit();
    }

    //Get currentCity from sharedPreferences
    public static Address getCurrentCity (Context context) {

        // Getting the preferences
        SharedPreferences settings = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        // load current city
        String json = settings.getString("current_city", "");
        currentCity = gson.fromJson(json, Address.class);
        return currentCity;
    }

}
