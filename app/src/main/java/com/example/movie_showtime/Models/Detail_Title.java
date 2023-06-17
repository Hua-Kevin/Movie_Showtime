package com.example.movie_showtime.Models;

public class Detail_Title {

    String title = "";
    String titleType = "";
    int year = 0;
    int runningTimeInMinutes = 0;
    ImageModel image;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleType() {
        return titleType;
    }

    public void setTitleType(String titleType) {
        this.titleType = titleType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getRunningTimeInMinutes() {
        return runningTimeInMinutes;
    }

    public void setRunningTimeInMinutes(int runningTimeInMinutes) {
        this.runningTimeInMinutes = runningTimeInMinutes;
    }

    public ImageModel getImageModel() {
        return image;
    }

    public void setImageModel(ImageModel image) {
        this.image = image;
    }
}
