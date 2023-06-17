package com.example.movie_showtime.Listeners;

import com.example.movie_showtime.Models.CastApiResponse;

public interface OnCastApiListener {

    void onResponse(CastApiResponse response);
    void onError(String message);

}
