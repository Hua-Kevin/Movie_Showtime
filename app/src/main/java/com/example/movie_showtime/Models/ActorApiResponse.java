package com.example.movie_showtime.Models;

import java.util.List;

public class ActorApiResponse {


    List<ActorObject> results = null;

    public List<ActorObject> getResults() {
        return results;
    }

    public void setResults(List<ActorObject> results) {
        this.results = results;
    }

    String id = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
