package mtt.theweatherapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.johnhiott.darkskyandroidlib.models.WeatherResponse;

import butterknife.Bind;
import butterknife.ButterKnife;
import mtt.theweatherapp.R;
import mtt.theweatherapp.utils.MyUtils;

/**
 * Created by manuel on 20/3/16.
 */
public class FutureAdapter  extends BaseAdapter {

    Context context;
    public static WeatherResponse wResponse;


    public FutureAdapter(Context context, WeatherResponse weatherResponse) {
        this.context = context;
        this.wResponse = weatherResponse;       // Object returned from the API
    }

    @Override
    public int getCount() {
        return 7;
    }       // N Days to show

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder view;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.row_future, parent, false);
            view = new ViewHolder(convertView);
            convertView.setTag(view);
        }else{
            view = (ViewHolder) convertView.getTag();
        }
        // print values
        if(wResponse.getDaily() != null) {

            int temMin = (int) wResponse.getDaily().getData().get(position).getTemperatureMin();
            int temMax = (int) wResponse.getDaily().getData().get(position).getTemperatureMax();
            view.tvRowTempMax.setText(context.getString(R.string.temp_value, temMax));
            view.tvRowTempMin.setText(context.getString(R.string.temp_value, temMin));
            view.ivRowIcon.setImageResource(MyUtils.getDrabwableByName((Activity) context, wResponse.getDaily().getData().get(position).getIcon()));
            // get the day of the week
            String day = MyUtils.getDayOfWeek((Activity) context, position);
            view.tvRowDay.setText(day);
        }

        return convertView;
    }

    // class to recycle views, we don't need in this app anyway
    static class ViewHolder{
        @Bind(R.id.tvRowDay)
        TextView tvRowDay;
        @Bind(R.id.tvRowTempMax)
        TextView tvRowTempMax;
        @Bind(R.id.tvRowTempMin)
        TextView tvRowTempMin;
        @Bind(R.id.ivRowIcon)
        ImageView ivRowIcon;

        public ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }
}
