package com.example.traveljournal.navigation.ui.home;

public interface RecyclerViewInterface {
    void onItemClick(int position);
    void onLongItemClick(int position);
    void onFavoriteButtonClick(int position, boolean isFavorite);
}
