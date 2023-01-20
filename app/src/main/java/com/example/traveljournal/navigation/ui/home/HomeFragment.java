package com.example.traveljournal.navigation.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traveljournal.R;
import com.example.traveljournal.TripFormActivity;
import com.example.traveljournal.domain.Trip;
import com.example.traveljournal.domain.TripAdapter;
import com.example.traveljournal.domain.TripType;
import com.example.traveljournal.domain.TripViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class HomeFragment extends Fragment implements RecyclerViewInterface {

    private RecyclerView tripsRecyclerView;
    private List<Trip> tripList;
    private TripViewModel tripViewModel;
    private TripAdapter tripAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        tripViewModel = new ViewModelProvider(this).get(TripViewModel.class);
        tripViewModel.getAll().observe(getViewLifecycleOwner(), new Observer<List<Trip>>() {
            @Override
            public void onChanged(List<Trip> trips) {
                setUpRecyclerView();
            }
        });
        tripsRecyclerView = view.findViewById(R.id.recyclerViewTrips);
        setUpRecyclerView();
        return view;
    }

    private void initMovies() {
        tripList = new ArrayList<>();
        if(tripViewModel.getAll().getValue() != null) {
            tripList.addAll(tripViewModel.getAll().getValue());
        }
    }

    private void setTripsAdapter() {
        tripsRecyclerView.setAdapter(new TripAdapter(tripList, this));
    }

    private void setTripsLayoutManager() {
        tripsRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }

    private void setUpRecyclerView() {
        initMovies();
        setTripsLayoutManager();
        setTripsAdapter();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }



    @Override
    public void onItemClick(int position) {
        Intent goToTripDetailsIntent = new Intent(this.getContext(), TripFormActivity.class);
        Trip trip = tripList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("tripId", trip.getId());
        bundle.putBoolean("isReadOnly", true);
        bundle.putSerializable("trip", trip);
        goToTripDetailsIntent.putExtras(bundle);
        startActivity(goToTripDetailsIntent);
    }

    @Override
    public void onLongItemClick(int position) {
        Intent goToTripDetailsIntent = new Intent(this.getContext(), TripFormActivity.class);
        Trip trip = tripList.get(position);
        Bundle bundle = new Bundle();
        bundle.putBoolean("isReadOnly", false);
        bundle.putSerializable("trip", trip);
        goToTripDetailsIntent.putExtras(bundle);
        startActivity(goToTripDetailsIntent);
    }

    @Override
    public void onFavoriteButtonClick(int position, boolean isFavorite) {
        Trip trip = tripList.get(position);
        trip.setFavorite(isFavorite);
        tripViewModel.insert(trip);
    }
}