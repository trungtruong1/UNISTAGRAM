package com.unistagram.service;

import java.util.List;

import com.unistagram.model.Movie;

public interface MovieService {

    String save(Movie movie);

    List<Movie> getMovie(int movie);

}
