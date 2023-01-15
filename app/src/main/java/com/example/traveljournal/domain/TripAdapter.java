package com.example.traveljournal.domain;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traveljournal.R;
import com.example.traveljournal.navigation.ui.home.RecyclerViewInterface;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripViewHolder> {

    private final List<Trip> tripList;
    private final RecyclerViewInterface recyclerViewInterface;

    public TripAdapter(List<Trip> tripList, RecyclerViewInterface recyclerViewInterface) {
        this.tripList = tripList;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_item, parent, false);
        return new TripViewHolder(itemView, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, int position) {
        Trip currentTrip = tripList.get(position);
        holder.getName().setText(currentTrip.getName());
        holder.getDestination().setText(currentTrip.getDestination());
        holder.getRatingBar().setRating(currentTrip.getRating());
        if(currentTrip.getImageUrl().length() > 0) {
            Picasso.get().load(currentTrip.getImageUrl())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_background)
                    .into(holder.getImageView());
        }
    }



    @Override
    public int getItemCount() {
        return tripList.size();
    }
}
