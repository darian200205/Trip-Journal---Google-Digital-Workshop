package com.example.traveljournal.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.traveljournal.dao.TripDAO;
import com.example.traveljournal.database.TripJournalDatabase;
import com.example.traveljournal.domain.Trip;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


public class TripRepository {

    private TripDAO tripDAO;
    private LiveData<List<Trip>> trips;

    public TripRepository(Application application) {
        TripJournalDatabase db = TripJournalDatabase.getDatabase(application);
        tripDAO = db.tripDAO();
        trips = tripDAO.getTrips();
    }

    public void insert(Trip trip) {
        TripJournalDatabase.databaseWriterExecutor.execute(
                () -> tripDAO.insertTrip(trip)
        );
    }

    public LiveData<List<Trip>> getAll() {
        return trips;
    }

    public LiveData<Trip> findTripById(Integer id) {
        return tripDAO.findTripById(id);
    }
}
