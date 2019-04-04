package com.kazishihan.tourmate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kazishihan.tourmate.Weither.WeatherResult;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherActivity extends AppCompatActivity {

    private TextView currentWeatherDiscription,currentWeathertemp,currentWeatherWind,currentWeatherHumidity;
    private ImageView currentWeatherIcon;

    private RecyclerView recyclerView;
    private WeitherAdapter weatherAdapter;
    private WeatherResult weatherResult;
    private WeatherResult currentWeatherResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        currentWeatherDiscription = findViewById(R.id.cityNameCurrentTvId);
        currentWeatherIcon = findViewById(R.id.weatherCurrentIconIvId);
        currentWeathertemp = findViewById(R.id.tempCurrentWeitherTvId);
        currentWeatherWind = findViewById(R.id.windCurrentWeitherTvId);
        currentWeatherHumidity = findViewById(R.id.humidityCurrentWeitherTvId);

        weatherResult = new WeatherResult();
        recyclerView=findViewById(R.id.weatherRecyclerViewId);
        getWeatherUpdate();





    }

    private void getWeatherUpdate() {
        IOpenWeatherMap iOpenWeatherMap= RetrofitClass.getRetrofitInstance().create(IOpenWeatherMap.class);

        Call<WeatherResult> weatherResultCall= iOpenWeatherMap.getWeatherData("forecast?lat=23.7533312&lon=90.3769738&units=metric&appid=a0e0d52b2dbb8228d3f19466bb398fd0");

        weatherResultCall.enqueue(new Callback<WeatherResult>() {
            @Override
            public void onResponse(Call<WeatherResult> call, Response<WeatherResult> response) {
                if(response.code()==200)
                {
                    weatherResult = response.body();
                    currentWeatherResult = weatherResult;
                    Toast.makeText(WeatherActivity.this, "success", Toast.LENGTH_SHORT).show();
                    weatherAdapter = new WeitherAdapter(WeatherActivity.this,weatherResult);
                    recyclerView.setLayoutManager(new LinearLayoutManager(WeatherActivity.this));
                    recyclerView.setAdapter(weatherAdapter);

                    currentWeatherDiscription.setText(""+weatherResult.getList().get(0).getWeather().get(0).getDescription());
                    Picasso.get().load(new StringBuilder("https://openweathermap.org/img/w/")
                            .append(weatherResult.getList().get(0).getWeather().get(0).getIcon())
                            .append(".png").toString()).into(currentWeatherIcon);
                    currentWeathertemp.setText("Temp: "+weatherResult.getList().get(0).getMain().getTemp()+" °C");
                    currentWeatherWind.setText("Wind :"+weatherResult.getList().get(0).getWind().getSpeed()+" km/h");

                }
            }

            @Override
            public void onFailure(Call<WeatherResult> call, Throwable t) {

            }
        });
    }
}
