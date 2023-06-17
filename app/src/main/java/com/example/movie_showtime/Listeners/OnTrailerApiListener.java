package com.example.movie_showtime.Listeners;

import com.example.movie_showtime.Models.TrailerApiResponse;

public interface OnTrailerApiListener {

    void onResponse(TrailerApiResponse response);
    void onError(String message);

}
