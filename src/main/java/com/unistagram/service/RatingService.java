package com.unistagram.service;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.unistagram.model.Movie;
import com.unistagram.model.Rating;
import com.unistagram.model.Result;

public interface RatingService {

    String save(Rating rating);

    Result<List<Object>> getMovieRatingGTE(int rating);

}
