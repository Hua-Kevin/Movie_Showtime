package com.example.movie_showtime.Models.TMovie;

import java.util.List;

public class MovieApiResponse {

    List<ShowtimeArrayObject> showtimes = null;
    KnowledgeModel knowledge_graph = null;


    public List<ShowtimeArrayObject> getShowtimes() {
        return showtimes;
    }

    public void setShowtimes(List<ShowtimeArrayObject> showtimes) {
        this.showtimes = showtimes;
    }

    public KnowledgeModel getKnowledge_graph() {
        return knowledge_graph;
    }

    public void setKnowledge_graph(KnowledgeModel knowledge_graph) {
        this.knowledge_graph = knowledge_graph;
    }
}
