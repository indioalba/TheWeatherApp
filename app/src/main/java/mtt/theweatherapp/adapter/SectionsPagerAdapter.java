package mtt.theweatherapp.adapter;

/**
 * Created by manuel on 20/3/16.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import mtt.theweatherapp.fragment.FutureFragment;
import mtt.theweatherapp.fragment.LocationFragment;
import mtt.theweatherapp.fragment.WeatherFragment;

// returns a fragment corresponding to one of the tabs.

public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        switch (position){
            case 0: return WeatherFragment.newInstance(position + 1);

            case 1: return FutureFragment.newInstance(position + 1);

            case 2:  return LocationFragment.newInstance(position + 1);
        }
        return null;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Weather";
            case 1:
                return "Forecast";
            case 2:
                return "Location";
        }
        return null;
    }
}