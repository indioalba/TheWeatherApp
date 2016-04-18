package mtt.theweatherapp.utils;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.format.DateFormat;
import android.util.Log;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import mtt.theweatherapp.R;

/**
 * Created by manuel on 20/3/16.
 */
public class MyUtils {

    public static String timestampToDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time*1000);
        String date = DateFormat.format("hh:mm:ss", cal).toString();

        return date;
    }

    // return the drawables weather icons
    public static int getDrabwableByName(Activity activity, String iconName) {
        // replace the icon names
        String icon = iconName.replace("-", "_");
        int resId = activity.getResources().getIdentifier(icon, "drawable", activity.getPackageName());
        return resId;
    }

    // return the day name of the week, position 0 = today
    public static String getDayOfWeek(Activity activity, int position) {
        Calendar calendar = Calendar.getInstance();
        String nameDay = "";
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        day += position;
        if (day > 7) {
            day -= 7;
        }

        switch (day) {
            case Calendar.SUNDAY:
                nameDay = activity.getString(R.string.sunday);
                break;
            case Calendar.MONDAY:
                nameDay = activity.getString(R.string.monday);
                break;
            case Calendar.TUESDAY:
                nameDay = activity.getString(R.string.tuesday);
                break;
            case Calendar.WEDNESDAY:
                nameDay = activity.getString(R.string.wednesday);
                break;
            case Calendar.THURSDAY:
                nameDay = activity.getString(R.string.thursday);
                break;
            case Calendar.FRIDAY:
                nameDay = activity.getString(R.string.friday);
                break;
            case Calendar.SATURDAY:
                nameDay = activity.getString(R.string.saturday);
                break;
        }
        return nameDay;
    }

    // look for cities by name
    public static Address getLocationByName(Context context, String cityName) {
        Geocoder geocoder = new Geocoder(context);
        List<Address> addresses =  null;
        try {
            addresses = geocoder.getFromLocationName(cityName, 1);

            if (addresses.size() > 0) {
                double latitude = addresses.get(0).getLatitude();
                double longitude = addresses.get(0).getLongitude();

                Log.i("location By Name", "lat: " + latitude + " long:" + longitude);
            }else{
                return null;
            }
        } catch(IOException e){
            e.printStackTrace();
            return null;
        }
        return addresses.get(0);
    }

    // check network connection
    public static boolean isThereConnection(Context context) {

        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
           return true;
        } else {
            return false;
        }
    }

}
