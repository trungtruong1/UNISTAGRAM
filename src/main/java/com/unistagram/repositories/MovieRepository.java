package com.unistagram.repositories;

import com.unistagram.model.Movie;


public interface MovieRepository {
    public Movie findMovieByMovieID(int movie_id);
    public Movie findMovieByName(String movie_name);
    public long count();
}
