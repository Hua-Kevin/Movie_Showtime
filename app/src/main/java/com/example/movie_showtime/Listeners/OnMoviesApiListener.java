package com.example.movie_showtime.Listeners;

import com.example.movie_showtime.Models.TMovie.MovieApiResponse;

public interface OnMoviesApiListener<T> {

    void onResponse(MovieApiResponse response);
    void onError(String message);


}
