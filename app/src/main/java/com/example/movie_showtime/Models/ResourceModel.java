package com.example.movie_showtime.Models;

import java.util.List;

public class ResourceModel {

    List<videoObject> videos = null;

    public List<videoObject> getVideos() {
        return videos;
    }

    public void setVideos(List<videoObject> videos) {
        this.videos = videos;
    }
}
