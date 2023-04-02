package com.unistagram.service;

import java.util.List;

import com.unistagram.model.Movie;
import com.unistagram.model.Rating;
import com.unistagram.model.Result;

public interface RatingService {

    String save(Rating rating);

    Result<List<Movie>> getMovieRatingGTE(int rating);

}
