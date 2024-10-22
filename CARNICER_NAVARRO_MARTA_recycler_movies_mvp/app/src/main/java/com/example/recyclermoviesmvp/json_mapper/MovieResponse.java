package com.example.recyclermoviesmvp.json_mapper;

import java.util.List;

public class MovieResponse {
    // URL: https://api.themoviedb.org/3/movie/popular?api_key=61e0d26ead78a0b630a6ffe401e15a6a
    private List<Movie> results;

    public List<Movie> getResults() {
        return results;
    }
    public void setResults(List<Movie> results) {
        this.results = results;
    }
}
