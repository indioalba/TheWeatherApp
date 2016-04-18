package mtt.theweatherapp.adapter;

import android.content.Context;
import android.location.Address;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import mtt.theweatherapp.DAO.RequestCitiesDAO;
import mtt.theweatherapp.R;

/**
 * Created by manuel on 20/3/16.
 */
public class LocationAdapter extends ArrayAdapter{

    Context context;
    public ArrayList<Address> alCities;

    public LocationAdapter(Context context, ArrayList<Address> alCities){
        super(context, 0, alCities);
        this.context = context;
        this.alCities = alCities;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder view;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.row_location, parent, false);
            view = new ViewHolder(convertView);

            convertView.setTag(view);
        }else{
            view = (ViewHolder)convertView.getTag();
        }

        view.cbLocationCity.setText(alCities.get(position).getFeatureName());
        Address cCity = RequestCitiesDAO.getCurrentCity(context);
        if(cCity.getFeatureName().equalsIgnoreCase(alCities.get(position).getFeatureName())){
            view.cbLocationCity.setChecked(true);
        }else{
            view.cbLocationCity.setChecked(false);
        }

        view.cbLocationCity.setTag(position);
        view.cbLocationCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                cb.setChecked(true);

                int cityPosition = (Integer) cb.getTag();
                RequestCitiesDAO.saveCurrentCity(context, alCities.get(cityPosition));
                // alCities.clear();
                // alCities = AppData.loadData(context);
                notifyDataSetChanged();
            }
        });
        // set tag to receive the value later
        view.ivLocationRemove.setTag(position);
        // onClickListener Delete city
        view.ivLocationRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if there is only one city we don't allow the user to delete it
                if(alCities.size()==1){
                    Toast.makeText(context, context.getString(R.string.one_location), Toast.LENGTH_LONG).show();
                    return;
                }
                // delete the city and notify the Adapter
                int cityPosition = (Integer)v.getTag();
                RequestCitiesDAO.removeCity(context, alCities.get(cityPosition));
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    // recycle class
    static class ViewHolder{
        @Bind(R.id.cbLocationCity)
        CheckBox cbLocationCity;
        @Bind(R.id.ivLocationRemove)
        ImageView ivLocationRemove;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
