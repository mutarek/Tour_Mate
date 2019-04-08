package com.kazishihan.tourmate;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.kazishihan.tourmate.Weither.WeatherResult;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherActivity extends AppCompatActivity {

    private TextView currentWeatherDiscription, currentWeathertemp, currentWeatherWind, currentWeatherHumidity;
    private ImageView currentWeatherIcon;

    private RecyclerView recyclerView;
    private WeitherAdapter weatherAdapter;
    private WeatherResult weatherResult;
    private WeatherResult currentWeatherResult;

    private double lat = 0;
    private double lon = 0;
    private String units = "metric";
    String url;

    FusedLocationProviderClient fusedLocationProviderClient;

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
        recyclerView = findViewById(R.id.weatherRecyclerViewId);
            //getLocationPermission();
/////////////permission///////////
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getMyLocation();

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }


    }

    private void getMyLocation() {

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();
        locationTask.addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if(task.isSuccessful())
                {
                    Location location = task.getResult();
                    url =String.format("forecast?lat=%f&lon=%f&units=%s&appid=%s",location.getLatitude(),location.getLongitude(),units,getResources().getString(R.string.appid));
                   // Toast.makeText(WeatherActivity.this, String.valueOf(location.getLatitude()), Toast.LENGTH_SHORT).show();
                    getWeatherUpdate();
                }

            }
        });

    }


    private void getWeatherUpdate() {

        IOpenWeatherMap iOpenWeatherMap= RetrofitClass.getRetrofitInstance().create(IOpenWeatherMap.class);

//        String url =String.format("forecast?lat=%f&lon=%f&units=%s&appid=%s",lat,lon,units,getResources().getString(R.string.appid));
       //"forecast?lat=23.7533312&lon=90.3769738&units=metric&appid=a0e0d52b2dbb8228d3f19466bb398fd0"


        Call<WeatherResult> weatherResultCall= iOpenWeatherMap.getWeatherData(url);

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
                    //Toast.makeText(WeatherActivity.this, ""+weatherResult.getCity().getCountry(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WeatherResult> call, Throwable t) {

            }
        });
    }
}
