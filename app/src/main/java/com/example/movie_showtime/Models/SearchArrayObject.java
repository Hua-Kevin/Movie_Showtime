package com.example.movie_showtime.Models;

public class SearchArrayObject {
    String title = "";

    ImageModel  image;

    String id = "";



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id.split("/")[2];
    }

    public ImageModel getImage() {
        return image;
    }

    public void setImage(ImageModel image) {
        this.image = image;
    }
}
