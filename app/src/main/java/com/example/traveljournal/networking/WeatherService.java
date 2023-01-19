package com.example.traveljournal.networking;

import com.example.traveljournal.networking.coordinates.Coordinates;
import com.example.traveljournal.networking.coordinates.CoordinatesAPI;
import com.example.traveljournal.networking.coordinates.OnGetCoordinatesCallback;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherService {

    private static final String BASE_WEATHER_URL = "https://api.openweathermap.org/";
    private static WeatherService weatherService;

    private WeatherAPI weatherAPI;
    private CoordinatesAPI coordinatesAPI;

    private WeatherService(WeatherAPI api) {
        this.weatherAPI = api;
    }

    public void setCoordinatesAPI(CoordinatesAPI coordinatesAPI) {
        this.coordinatesAPI = coordinatesAPI;
    }

    public static WeatherService getInstance() {
        if (weatherService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_WEATHER_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            weatherService = new WeatherService(retrofit.create(WeatherAPI.class));
            weatherService.setCoordinatesAPI(retrofit.create(CoordinatesAPI.class));
        }

        return weatherService;
    }

    public void getCoordinates(final OnGetCoordinatesCallback callback, String city) {
        coordinatesAPI.getCoordinates(city)
                .enqueue(new Callback<List<Coordinates>>() {
                    @Override
                    public void onResponse(Call<List<Coordinates>> call, Response<List<Coordinates>> response) {
                        if(response.isSuccessful()) {
                            List<Coordinates> coordinates = response.body();
                            if(coordinates != null && !coordinates.isEmpty()) {
                                callback.onSuccess(coordinates.get(0));
                            } else {
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Coordinates>> call, Throwable t) {
                        callback.onError();
                    }
                });
    }

    public void getWeather(final OnGetWeatherCallback callback, Coordinates coordinates) {
        weatherAPI.getWeather(coordinates.getLat(), coordinates.getLon())
                .enqueue(new Callback<Weather>() {
                    @Override
                    public void onResponse(Call<Weather> call, Response<Weather> response) {
                        if (response.isSuccessful()) {
                            Weather weather = response.body();
                            if (weather != null) {
                                callback.onSuccess(weather);
                            } else {
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(Call<Weather> call, Throwable t) {
                        callback.onError();
                    }
                });
    }
}
