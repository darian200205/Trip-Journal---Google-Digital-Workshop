package com.example.traveljournal.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.traveljournal.domain.Trip;

import java.util.List;

import kotlin.ParameterName;

@Dao
public interface TripDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTrip(Trip trip);

    @Query("SELECT * from trip ORDER BY name, startDate ASC")
    LiveData<List<Trip>> getTrips();

    @Query("SELECT * FROM trip WHERE id = :id")
    LiveData<Trip> findTripById(Integer id);
}
