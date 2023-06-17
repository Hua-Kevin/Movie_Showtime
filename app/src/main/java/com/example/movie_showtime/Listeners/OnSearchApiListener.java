package com.example.movie_showtime.Listeners;

import com.example.movie_showtime.Models.SearchApiResponse;

public interface OnSearchApiListener {
    void onResponse(SearchApiResponse response);
    void onError(String message);

}
