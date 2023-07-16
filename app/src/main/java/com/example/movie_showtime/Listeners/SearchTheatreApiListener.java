package com.example.movie_showtime.Listeners;

import com.example.movie_showtime.Models.Theatre.TheatreApiResponse;

public interface SearchTheatreApiListener {
    void onResponse(TheatreApiResponse response);
    void onError(String message);

}
