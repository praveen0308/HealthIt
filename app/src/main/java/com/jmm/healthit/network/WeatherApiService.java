package com.jmm.healthit.network;

import com.jmm.healthit.model.news.ResponseModel;
import com.jmm.healthit.model.weather.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiService {

    @GET("current.json")
    Call<WeatherResponse> getCurrentWeather(
            @Query("key") String apiKey,
            @Query("q") String cityName,
            @Query("aqi") String aqi

    );
}
