package com.example.movie_showtime.Listeners;

import java.util.List;

public interface OnDetailsApiListener<T> {

    void onResponse(List<T> response);
    void onError(String message);

}
