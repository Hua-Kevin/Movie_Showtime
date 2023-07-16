package com.example.movie_showtime.Models.TMovie;

import com.example.movie_showtime.Models.Theatre.LocalModel;

public class SearchShowtimeApiResponse {
    LocalModel local_results = null;

    public LocalModel getLocal_results() {
        return local_results;
    }

    public void setLocal_results(LocalModel local_results) {
        this.local_results = local_results;
    }
}
