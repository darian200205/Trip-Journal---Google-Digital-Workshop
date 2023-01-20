package com.example.traveljournal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.traveljournal.domain.Trip;
import com.example.traveljournal.domain.TripType;
import com.example.traveljournal.domain.TripViewModel;
import com.example.traveljournal.navigation.MainActivity;
import com.example.traveljournal.networking.OnGetWeatherCallback;
import com.example.traveljournal.networking.Weather;
import com.example.traveljournal.networking.WeatherService;
import com.example.traveljournal.networking.coordinates.Coordinates;
import com.example.traveljournal.networking.coordinates.OnGetCoordinatesCallback;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso3.ImageViewAction;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TripFormActivity extends AppCompatActivity {

    private static final int PICK_FROM_GALLERY = 1000;
    private ImageView tripImageView;
    private EditText tripName;
    private EditText destination;
    private Spinner tripTypesSpinner;
    private SeekBar price;
    private DatePicker startDate, endDate;
    private RatingBar ratingBar;
    private Button saveButton, pickImageButton;
    private TextView weatherTemperature;
    private TextView weatherDescription;

    private List<String> tripTypes;
    private Trip trip;

    private final WeatherService weatherService = WeatherService.getInstance();

    private TripViewModel tripViewModel;

    private Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_form);
        tripViewModel = new ViewModelProvider(this).get(TripViewModel.class);
        initViews();
    }

    private void initViews() {
        tripImageView = findViewById(R.id.imageViewTrip);
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
        pickImageButton = findViewById(R.id.pickImageButton);
        handleSaveImageButton();
        saveButton.setOnClickListener(view -> saveTrip());
        setUpSpinner();
        loadTripData();
    }

    private void loadTripData() {
        bundle = getIntent().getExtras();
        if(bundle != null) {
            trip = (Trip) bundle.getSerializable("trip");
            initTripDetails(trip);
            if(bundle.getBoolean("isReadOnly")) {
                disableEditing();
                //getWeather();
            }
        }
    }

    private void handleSaveImageButton() {
        pickImageButton.setOnClickListener(view -> pickImage());
    }


    private void pickImage() {
        Intent openGalleryIntent = new Intent(
                Intent.ACTION_OPEN_DOCUMENT,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        );
        startActivityForResult(openGalleryIntent, 3);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            getContentResolver().takePersistableUriPermission(
                    selectedImage, (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            );
            tripImageView.setTag(selectedImage.toString());
            Picasso.get().load(selectedImage)
                    .fit()
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_background)
                    .into(tripImageView);
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
        ratingBar.setIsIndicator(true);
        price.setEnabled(false);
        tripTypesSpinner.setEnabled(false);
        saveButton.setVisibility(View.INVISIBLE);
        pickImageButton.setVisibility(View.GONE);
        pickImageButton.setEnabled(false);
        saveButton.setEnabled(false);
    }

    private void initTripDetails(Trip trip) {
        tripName.setText(trip.getName());
        destination.setText(trip.getDestination());
        price.setProgress(trip.getPrice());
        startDate.updateDate(trip.getStartDate().getYear() + 1900, trip.getStartDate().getMonth(), trip.getStartDate().getDate());
        endDate.updateDate(trip.getEndDate().getYear() + 1900, trip.getEndDate().getMonth(), trip.getEndDate().getDate());
        ratingBar.setRating(trip.getRating());
        tripTypesSpinner.setSelection(getIndex(tripTypesSpinner, trip.getTripType().name()));
        if(trip.getImageUrl() != null && !trip.getImageUrl().isEmpty()) {
            Picasso.get().load(Uri.parse(trip.getImageUrl()))
                    .fit()
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_background)
                    .into(tripImageView);
        }

    }

    private int getIndex(Spinner tripTypesSpinner, String name) {
        for(int i = 0; i < tripTypesSpinner.getCount(); i++) {
            if(tripTypesSpinner.getItemAtPosition(i).toString().equals(name)) {
                return i;
            }
        }
        return 0;
    }

    private void saveTrip() {
        trip = getTrip();
        if(isValidTrip()) {
            tripViewModel.insert(trip);
        }
        Intent goHomeIntent = new Intent(TripFormActivity.this, MainActivity.class);
        startActivity(goHomeIntent);
    }

    private boolean isValidTrip() {
        if(trip.getName().isEmpty()) {
            return false;
        }

        if(trip.getDestination().isEmpty()) {
            return false;
        }

        if(trip.getPrice() <= 0) {
            return false;
        }

        if(trip.getRating() == 0) {
            return false;
        }

        return !trip.getStartDate().after(trip.getEndDate());
    }

    private Trip getTrip() {
        trip.setName(String.valueOf(tripName.getText()));
        trip.setDestination(String.valueOf(destination.getText()));
        trip.setPrice(price.getProgress());
        trip.setRating((int) ratingBar.getRating());
        trip.setTripType(TripType.valueOf(tripTypesSpinner.getSelectedItem().toString()));
        trip.setStartDate(getDateFromDatePicker(startDate));
        trip.setEndDate(getDateFromDatePicker(endDate));
        if(tripImageView.getTag() != null) {
            trip.setImageUrl(tripImageView.getTag().toString());
        }
        return trip;
    }

    private Date getDateFromDatePicker(DatePicker datePicker) {
        int   day  = datePicker.getDayOfMonth();
        int   month= datePicker.getMonth();
        int   year = datePicker.getYear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTime();
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