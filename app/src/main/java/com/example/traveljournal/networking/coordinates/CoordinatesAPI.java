package com.example.traveljournal.networking.coordinates;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CoordinatesAPI {
    @GET("/geo/1.0/direct?limit=5&appid=d03a6289f72411cb7470b480bffa0f40")
    Call<List<Coordinates>> getCoordinates(@Query("q")String city);
}
