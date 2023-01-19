package com.example.traveljournal.domain;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.traveljournal.repository.TripRepository;

import java.util.List;

public class TripViewModel extends AndroidViewModel {

    private TripRepository tripRepository;

    private LiveData<List<Trip>> trips;

    public TripViewModel(@NonNull Application application) {
        super(application);
        tripRepository = new TripRepository(application);
        trips = tripRepository.getAll();
    }

    public void insert(Trip trip) {
        tripRepository.insert(trip);
    }

    public LiveData<List<Trip>> getAll() {
        return trips;
    }

    public LiveData<Trip> findTripById(Integer id) {
        return tripRepository.findTripById(id);
    }

}
