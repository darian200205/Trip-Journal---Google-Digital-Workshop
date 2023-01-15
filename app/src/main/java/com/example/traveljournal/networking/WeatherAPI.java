package com.example.traveljournal.networking;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface WeatherAPI {
    @GET("/data/2.5/weather?appid=d03a6289f72411cb7470b480bffa0f40")
    Call<Weather> getWeather(@Query("lat")Double lat, @Query("lon")Double lon);
}
