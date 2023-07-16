package com.example.movie_showtime.Models.TMovie;

import java.util.List;

public class MovieArrayObject {
    String name = "";
    List<TimeArrayObject> showing = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TimeArrayObject> getShowing() {
        return showing;
    }

    public void setShowing(List<TimeArrayObject> showing) {
        this.showing = showing;
    }
}
