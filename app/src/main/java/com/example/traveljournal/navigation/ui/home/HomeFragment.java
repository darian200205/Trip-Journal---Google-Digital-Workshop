package com.example.traveljournal.navigation.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traveljournal.R;
import com.example.traveljournal.TripFormActivity;
import com.example.traveljournal.domain.Trip;
import com.example.traveljournal.domain.TripAdapter;
import com.example.traveljournal.domain.TripType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class HomeFragment extends Fragment implements RecyclerViewInterface {

    private RecyclerView tripsRecyclerView;
    private List<Trip> tripList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        tripsRecyclerView = view.findViewById(R.id.recyclerViewTrips);
        setUpRecyclerView();
        return view;
    }

    private void initMovies() {
        tripList = new ArrayList<>();
        tripList.add(new Trip("name1", "London", 4, "", TripType.SEA_SIDE, 80, new Date(), new Date()));
        tripList.add(new Trip("name1", "Berlin", 4, "", TripType.SEA_SIDE, 80, new Date(), new Date()));
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
        tripsRecyclerView.addItemDecoration(new DividerItemDecoration(this.requireContext(),
                DividerItemDecoration.HORIZONTAL));
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
        bundle.putBoolean("isReadOnly", true);
        bundle.putSerializable("trip", trip);
        goToTripDetailsIntent.putExtras(bundle);
        startActivity(goToTripDetailsIntent);
    }
}