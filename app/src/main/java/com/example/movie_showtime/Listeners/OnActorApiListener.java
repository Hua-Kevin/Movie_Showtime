package com.example.movie_showtime.Listeners;

import com.example.movie_showtime.Models.ActorApiResponse;

public interface OnActorApiListener {

    void onResponse(ActorApiResponse response);
    void onError(String message);

}
