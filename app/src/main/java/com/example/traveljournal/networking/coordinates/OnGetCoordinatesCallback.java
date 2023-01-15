package com.example.traveljournal.networking.coordinates;

public interface OnGetCoordinatesCallback {
    void onSuccess(Coordinates coordinates);
    void onError();
}
