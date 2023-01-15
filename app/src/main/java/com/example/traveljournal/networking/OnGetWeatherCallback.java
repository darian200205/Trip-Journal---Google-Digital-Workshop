package com.example.traveljournal.networking;

public interface OnGetWeatherCallback {
    void onSuccess(Weather weather);
    void onError();
}
