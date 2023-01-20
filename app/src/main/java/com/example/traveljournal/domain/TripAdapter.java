package com.example.traveljournal.domain;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traveljournal.R;
import com.example.traveljournal.navigation.ui.home.RecyclerViewInterface;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

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
        if(currentTrip.getImageUrl() != null && !Objects.equals(currentTrip.getImageUrl(), "")) {
            Picasso.get().load(Uri.parse(currentTrip.getImageUrl()))
                    .fit()
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_background)
                    .into(holder.getImageView(), new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
                            e.printStackTrace();
                        }
                    });
        }

        if(currentTrip.getFavorite()) {
            holder.getFavoriteTripImage().setEnabled(true);
            holder.getAddToFavoriteButton().setVisibility(View.VISIBLE);
            holder.getAddToFavoriteButton().setEnabled(false);
            holder.getAddToFavoriteButton().setVisibility(View.GONE);
        } else {
            holder.getFavoriteTripImage().setEnabled(false);
            holder.getFavoriteTripImage().setVisibility(View.GONE);
            holder.getAddToFavoriteButton().setEnabled(true);
            holder.getAddToFavoriteButton().setVisibility(View.VISIBLE);
        }
    }




    @Override
    public int getItemCount() {
        return tripList.size();
    }
}
