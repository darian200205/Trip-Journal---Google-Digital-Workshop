package com.example.traveljournal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.traveljournal.domain.Trip;
import com.example.traveljournal.domain.TripType;
import com.example.traveljournal.navigation.MainActivity;
import com.example.traveljournal.networking.OnGetWeatherCallback;
import com.example.traveljournal.networking.Weather;
import com.example.traveljournal.networking.WeatherService;
import com.example.traveljournal.networking.coordinates.Coordinates;
import com.example.traveljournal.networking.coordinates.OnGetCoordinatesCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TripFormActivity extends AppCompatActivity {

    private EditText tripName;
    private EditText destination;
    private Spinner tripTypesSpinner;
    private SeekBar price;
    private DatePicker startDate, endDate;
    private RatingBar ratingBar;
    private Button saveButton;
    private TextView weatherTemperature;
    private TextView weatherDescription;

    private List<String> tripTypes;

    private final WeatherService weatherService = WeatherService.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_form);
        initViews();
        setUpSpinner();
    }

    private void initViews() {
        tripName = findViewById(R.id.editTextTripName);
        destination = findViewById(R.id.editTextDestination);
        price = findViewById(R.id.seekBarPrice);
        startDate = findViewById(R.id.datePickerStartDate);
        endDate = findViewById(R.id.datePickerEndDate);
        ratingBar = findViewById(R.id.ratingBarTripForm);
        tripTypesSpinner = findViewById(R.id.spinnerTripTypes);
        saveButton = findViewById(R.id.buttonSaveForm);
        weatherTemperature = findViewById(R.id.textViewTemperature);
        weatherDescription = findViewById(R.id.textViewWeatherDescription);

        saveButton.setOnClickListener(view -> saveTrip());

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            if(bundle.getBoolean("isReadOnly")) {
                disableEditing();
                initTripDetails((Trip) bundle.getSerializable("trip"));
                getWeather();
            }
        }
    }

    private void getWeather() {
        String city = destination.getText().toString();
        weatherService.getCoordinates(new OnGetCoordinatesCallback() {
            @Override
            public void onSuccess(Coordinates coordinates) {
                fetchWeather(coordinates);
            }

            @Override
            public void onError() {
                weatherTemperature.setText(R.string.weather_error);
            }

        }, city);
    }

    private void fetchWeather(Coordinates coordinates) {
        weatherService.getWeather(new OnGetWeatherCallback() {
            @Override
            public void onSuccess(Weather weather) {
                initWeatherDetails(weather);
            }

            @Override
            public void onError() {
                weatherTemperature.setText(R.string.weather_error);
            }
        }, coordinates);
    }

    private void initWeatherDetails(Weather weather) {
        Integer temp = weather.getWeatherStatistics().getTemp().intValue() - 273; // convert from Kelvin
        String description = weather.getWeatherDetails().get(0).getDescription();

        weatherDescription.setText(description);
        weatherTemperature.setText(String.valueOf(temp));
    }

    private void disableEditing() {
        tripName.setEnabled(false);
        destination.setEnabled(false);
        startDate.setEnabled(false);
        endDate.setEnabled(false);
        price.setEnabled(false);
        tripTypesSpinner.setEnabled(false);
        saveButton.setVisibility(View.INVISIBLE);
        saveButton.setEnabled(false);
    }

    private void initTripDetails(Trip trip) {
        tripName.setText(trip.getName());
        destination.setText(trip.getDestination());
        price.setProgress(trip.getPrice());
        startDate.updateDate(trip.getStartDate().getYear() + 1900, trip.getStartDate().getMonth() + 1, trip.getStartDate().getDate());
        endDate.updateDate(trip.getEndDate().getYear() + 1900, trip.getEndDate().getMonth() + 1, trip.getEndDate().getDate());
        ratingBar.setRating(trip.getRating());
        tripTypesSpinner.setSelection(trip.getTripType().ordinal());
    }

    private void saveTrip() {
        Intent goHomeIntent = new Intent(TripFormActivity.this, MainActivity.class);
        startActivity(goHomeIntent);
    }

    private void setUpSpinnerData() {
        tripTypes = new ArrayList<>();
        for(TripType tripType : TripType.values()) {
            tripTypes.add(tripType.name());
        }
    }

    private ArrayAdapter<String> getDefaultAdapter() {
        return new ArrayAdapter<String>(TripFormActivity.this, android.R.layout.simple_spinner_item, tripTypes);
    }

    private void setUpSpinner() {
        setUpSpinnerData();
        tripTypesSpinner.setAdapter(getDefaultAdapter());
    }
}