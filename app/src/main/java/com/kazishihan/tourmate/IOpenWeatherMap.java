package com.kazishihan.tourmate;

import com.kazishihan.tourmate.Weither.WeatherResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface IOpenWeatherMap {

//    @GET("forecast")
//    Call<WeatherResult> getForecastWeatherByLatLang(@Query("lat") String lat,
//                                                    @Query("lat") String lng,
//                                                    @Query("lat") String appid,
//                                                    @Query("lat") String unit);



    @GET
    Call<WeatherResult> getWeatherData(@Url String url);


}
