package com.unistagram.service;

import java.util.List;

import com.unistagram.model.Rating;

public interface RatingService {

    String save(Rating rating);

    List<Rating> getRating(int rating);

}
