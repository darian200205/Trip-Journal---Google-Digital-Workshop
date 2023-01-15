package com.example.traveljournal.networking.coordinates;

import com.google.gson.annotations.SerializedName;

public class Coordinates {



    @SerializedName("lat")
    private Double lat;

    @SerializedName("lon")
    private Double lon;

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "lat=" + lat +
                ", lon=" + lon +
                '}';
    }
}
