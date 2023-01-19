package com.example.traveljournal.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.traveljournal.dao.TripDAO;
import com.example.traveljournal.domain.Trip;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Trip.class}, version = 2)
public abstract class TripJournalDatabase extends RoomDatabase {

    public static final ExecutorService databaseWriterExecutor = Executors.newFixedThreadPool(4);

    private static TripJournalDatabase INSTANCE;

    public abstract TripDAO tripDAO();

    public static TripJournalDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(),
                    TripJournalDatabase.class,
                    "trip_journal_db"
            ).fallbackToDestructiveMigration().build();
        }
        return INSTANCE;
    }
}
