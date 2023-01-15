package com.example.traveljournal.domain;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;

public class Trip implements Serializable {

    private String name;
    private String destination;
    private Integer rating;
    private String imageUrl;
    private TripType tripType;
    private Integer price;
    private Date startDate;
    private Date endDate;

    public Trip(String name, String destination, Integer rating, String imageUrl, TripType tripType, Integer price, Date startDate, Date endDate) {
        this.name = name;
        this.destination = destination;
        this.rating = rating;
        this.imageUrl = imageUrl;
        this.tripType = tripType;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public TripType getTripType() {
        return tripType;
    }

    public void setTripType(TripType tripType) {
        this.tripType = tripType;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Trip(String name, String destination, Integer rating, String imageUrl) {
        this.name = name;
        this.destination = destination;
        this.rating = rating;
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    @NonNull
    @Override
    public String toString() {
        return "Trip{" +
                "name='" + name + '\'' +
                ", destination='" + destination + '\'' +
                ", rating=" + rating +
                '}';
    }
}
