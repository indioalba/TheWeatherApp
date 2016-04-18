package mtt.theweatherapp.adapter;

import android.content.Context;
import android.location.Address;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import mtt.theweatherapp.DAO.RequestCitiesDAO;
import mtt.theweatherapp.R;

/**
 * Created by manuel on 8/4/16.
 */
public class LocationRecycleAdapter extends RecyclerView.Adapter<LocationRecycleAdapter.ViewHolder> {


    ArrayList<Address> alCities;
    Context context;

    public LocationRecycleAdapter(Context context, ArrayList<Address> alCities) {
        this.context  = context;
        this.alCities = alCities;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_location, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.cbLocationCity.setText(alCities.get(position).getFeatureName());
        Address cCity = RequestCitiesDAO.getCurrentCity(context);
        if(cCity.getFeatureName().equalsIgnoreCase(alCities.get(position).getFeatureName())){
            holder.cbLocationCity.setChecked(true);
        }else{
            holder.cbLocationCity.setChecked(false);
        }

        holder.cbLocationCity.setTag(position);
        holder.cbLocationCity.setOnClickListener(new View.OnClickListener() {
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
        holder.ivLocationRemove.setTag(position);
        // onClickListener Delete city
        holder.ivLocationRemove.setOnClickListener(new View.OnClickListener() {
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
    }

    @Override
    public int getItemCount() {
        return alCities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.cbLocationCity)
        CheckBox cbLocationCity;
        @Bind(R.id.ivLocationRemove)
        ImageView ivLocationRemove;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
