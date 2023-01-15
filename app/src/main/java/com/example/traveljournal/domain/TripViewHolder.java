package com.example.traveljournal.domain;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traveljournal.R;
import com.example.traveljournal.navigation.ui.home.RecyclerViewInterface;


public class TripViewHolder extends RecyclerView.ViewHolder{

    private final ImageView imageView;
    private final TextView name, destination;
    private final RatingBar ratingBar;


    public TripViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageViewTrip);
        name = itemView.findViewById(R.id.textViewTripName);
        destination = itemView.findViewById(R.id.textViewTripDestination);
        ratingBar = itemView.findViewById(R.id.ratingBarTripRating);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(recyclerViewInterface != null) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION) {
                        recyclerViewInterface.onItemClick(position);
                    }
                }
            }
        });
    }

    public ImageView getImageView() {
        return imageView;
    }

    public TextView getName() {
        return name;
    }

    public TextView getDestination() {
        return destination;
    }

    public RatingBar getRatingBar() {
        return ratingBar;
    }

}
