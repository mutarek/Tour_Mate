package com.kazishihan.tourmate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kazishihan.tourmate.Weither.WeatherResult;
import com.squareup.picasso.Picasso;

public class WeitherAdapter extends RecyclerView.Adapter<WeitherAdapter.ViewGroup> {

    Context context;
    WeatherResult weatherResult;

    public WeitherAdapter(Context context, WeatherResult weatherResult) {
        this.context = context;
        this.weatherResult = weatherResult;
    }

    @NonNull
    @Override
    public ViewGroup onCreateViewHolder(@NonNull android.view.ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.weither_items,viewGroup,false);

        return new ViewGroup(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Picasso.get().load(new StringBuilder("https://openweathermap.org/img/w/")
                .append(weatherResult.getList().get(i).getWeather().get(0).getIcon())
                .append(".png").toString()).into(viewGroup.weitherIcon);

        viewGroup.weitherDate.setText("Date: "+weatherResult.getList().get(i).getDt_txt());
        viewGroup.weitherDescription.setText("Status: "+weatherResult.getList().get(i).getWeather().get(0).getDescription());
        viewGroup.weitherTemp.setText(("Temp :"+weatherResult.getList().get(i).getMain().getTemp()+" °C"));
        viewGroup.weitherWind.setText("Wind :"+weatherResult.getList().get(i).getWind().getSpeed()+" km/h");
        //viewGroup.weitherHumidity.setText(("Humidity :"+weatherResult.getList().get(i).getMain().getHumidity()+" %"));
    }

    @Override
    public int getItemCount() {
        return weatherResult.getList().size();
    }

    public class ViewGroup extends RecyclerView.ViewHolder {
        private ImageView weitherIcon;
        private TextView weitherDate,weitherTemp,weitherWind,weitherHumidity,weitherDescription;

        public ViewGroup(@NonNull View itemView) {
            super(itemView);

            weitherIcon = itemView.findViewById(R.id.weatherItemIvId);
            weitherDate = itemView.findViewById(R.id.dateWeitherItemTvId);
            weitherTemp = itemView.findViewById(R.id.tempWeitherItemTvId);
            weitherWind = itemView.findViewById(R.id.windWeitherItemTvId);
            weitherDescription = itemView.findViewById(R.id.weitherDiscriptionTvId);
            //weitherHumidity = itemView.findViewById(R.id.humidityWeitherItemTvId);


        }
    }
}
