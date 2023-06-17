package com.example.movie_showtime.Models;

public class DetailApiResponse {
    String id = "";
    Detail_Ratings ratings;
    Detail_Title  title;
    Detail_Plot plotSummary;
    //List<Detail_Genre> genres = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id.split("/")[2];
    }

    public Detail_Ratings getRatings() {
        return ratings;
    }

    public void setRatings(Detail_Ratings ratings) {
        this.ratings = ratings;
    }

    public Detail_Title getTitle() {
        return title;
    }

    public void setTitle(Detail_Title title) {
        this.title = title;
    }

    public Detail_Plot getPlotSummary() {
        return plotSummary;
    }

    public void setPlotSummary(Detail_Plot plotSummary) {
        this.plotSummary = plotSummary;
    }

    /*public List<Detail_Genre> getResults() {
        return genres;
    }

    public void setResults(List<Detail_Genre> genres) {
        this.genres = genres;
    }*/
}
