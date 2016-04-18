package mtt.theweatherapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.johnhiott.darkskyandroidlib.models.WeatherResponse;

import butterknife.Bind;
import butterknife.ButterKnife;
import mtt.theweatherapp.R;
import mtt.theweatherapp.utils.MyUtils;

/**
 * Created by manuel on 8/4/16.
 */
public class FutureRecycleAdapter extends RecyclerView.Adapter<FutureRecycleAdapter.ViewHolder>{

    Context context;
    public static WeatherResponse wResponse;


    public FutureRecycleAdapter(Context context, WeatherResponse weatherResponse) {
        this.context = context;
        this.wResponse = weatherResponse;       // Object returned from the API
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_future, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        int temMin = (int) wResponse.getDaily().getData().get(position).getTemperatureMin();
        int temMax = (int) wResponse.getDaily().getData().get(position).getTemperatureMax();
        viewHolder.tvRowTempMax.setText(context.getString(R.string.temp_value, temMax));
        viewHolder.tvRowTempMin.setText(context.getString(R.string.temp_value, temMin));
        viewHolder.ivRowIcon.setImageResource(MyUtils.getDrabwableByName((Activity) context, wResponse.getDaily().getData().get(position).getIcon()));
        // get the day of the week
        String day = MyUtils.getDayOfWeek((Activity) context, position);
        viewHolder.tvRowDay.setText(day);
    }

    @Override
    public int getItemCount() {
        return 7;
    }

    // class to recycle views, we don't need in this app anyway
    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tvRowDay)
        TextView tvRowDay;
        @Bind(R.id.tvRowTempMax)
        TextView tvRowTempMax;
        @Bind(R.id.tvRowTempMin)
        TextView tvRowTempMin;
        @Bind(R.id.ivRowIcon)
        ImageView ivRowIcon;

        ViewHolder(View view){
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
