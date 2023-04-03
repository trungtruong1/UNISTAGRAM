package com.unistagram.service;

import com.unistagram.model.Movie;

public interface MovieService {

    public Movie getMovieById(int id);

    public long count();
}
