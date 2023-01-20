package com.example.traveljournal.domain;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "trip")
public class Trip implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Integer id;

    @NotNull
    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "destination")
    private String destination;

    @ColumnInfo(name = "rating")
    private Integer rating;

    @ColumnInfo(name = "imageUrl")
    private String imageUrl;

    @ColumnInfo(name = "tripType")
    @TypeConverters({TripTypeConverter.class})
    private TripType tripType;

    @ColumnInfo(name = "price")
    private Integer price;

    @ColumnInfo(name = "startDate")
    @TypeConverters({DateConverter.class})
    @NonNull
    private Date startDate;

    @ColumnInfo(name = "endDate")
    @TypeConverters({DateConverter.class})
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

    public Trip() {
        this.name = "";
        this.destination = "";
        this.rating = 0;
        this.imageUrl = "";
        this.tripType = TripType.CITY_BREAK;
        this.price = 0;
        this.startDate = new Date();
        this.endDate = new Date();
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
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
