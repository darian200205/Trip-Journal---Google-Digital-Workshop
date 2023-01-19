package com.example.traveljournal.domain;

import androidx.room.TypeConverter;

public class TripTypeConverter {
    @TypeConverter
    public static TripType toTripType(String tripType){
        return TripType.valueOf(tripType);
    }

    @TypeConverter
    public static String fromTripType(TripType tripType){
        return tripType.name();
    }
}
