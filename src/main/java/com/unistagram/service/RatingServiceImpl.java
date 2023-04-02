package com.unistagram.service;

import org.springframework.stereotype.Service;

import com.unistagram.model.Rating;
import com.unistagram.repositories.RatingRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class RatingServiceImpl implements RatingService{
    
    @Autowired
    private RatingRepository ratingRepository;

    @Override
    public String save(Rating rating){
        return ratingRepository.save(rating).getId();
    }

    @Override
    public List<Rating> getRating(int rating){
        return ratingRepository.findAll();
    }
}
