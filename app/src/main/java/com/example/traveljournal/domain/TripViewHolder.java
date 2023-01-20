package com.example.traveljournal.domain;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traveljournal.R;
import com.example.traveljournal.navigation.ui.home.RecyclerViewInterface;


public class TripViewHolder extends RecyclerView.ViewHolder{

    private ImageView imageView;
    private TextView name, destination;
    private RatingBar ratingBar;
    private ImageButton favoriteTripImage;
    private Button addToFavoriteButton;


    public TripViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
        super(itemView);
        initViews(recyclerViewInterface);
    }

    private void initViews(RecyclerViewInterface recyclerViewInterface) {
        imageView = itemView.findViewById(R.id.imageViewTrip);
        name = itemView.findViewById(R.id.textViewTripName);
        destination = itemView.findViewById(R.id.textViewTripDestination);
        ratingBar = itemView.findViewById(R.id.ratingBarTripRating);
        favoriteTripImage = itemView.findViewById(R.id.favoriteTripImage);
        addToFavoriteButton = itemView.findViewById(R.id.addToFavoritebutton);
        initListeners(recyclerViewInterface);
    }

    private void initListeners(RecyclerViewInterface recyclerViewInterface) {
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(recyclerViewInterface != null) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION) {
                        recyclerViewInterface.onLongItemClick(position);
                    }
                    return true;
                }
                return false;
            }
        });

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

        addToFavoriteButton.setOnClickListener(view -> {
            if(recyclerViewInterface != null) {
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION) {
                    recyclerViewInterface.onFavoriteButtonClick(position, true);
                }
            }
        });

        favoriteTripImage.setOnClickListener(view -> {
            if(recyclerViewInterface != null) {
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION) {
                    recyclerViewInterface.onFavoriteButtonClick(position, false);
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

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public void setName(TextView name) {
        this.name = name;
    }

    public void setDestination(TextView destination) {
        this.destination = destination;
    }

    public void setRatingBar(RatingBar ratingBar) {
        this.ratingBar = ratingBar;
    }

    public ImageButton getFavoriteTripImage() {
        return favoriteTripImage;
    }

    public void setFavoriteTripImage(ImageButton favoriteTripImage) {
        this.favoriteTripImage = favoriteTripImage;
    }

    public Button getAddToFavoriteButton() {
        return addToFavoriteButton;
    }

    public void setAddToFavoriteButton(Button addToFavoriteButton) {
        this.addToFavoriteButton = addToFavoriteButton;
    }
}
