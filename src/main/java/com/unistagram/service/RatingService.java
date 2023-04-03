package com.unistagram.service;

import java.util.List;


import com.unistagram.model.Rating;
import com.unistagram.model.Result;

public interface RatingService {

    String save(Rating rating);

    Result<List<Object>> getMovieRatingGTE(double rating);

}
