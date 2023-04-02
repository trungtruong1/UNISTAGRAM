package com.unistagram.service;

import org.springframework.stereotype.Service;

import com.unistagram.model.Movie;
import com.unistagram.model.Rating;
import com.unistagram.model.Result;
import com.unistagram.repositories.RatingRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class RatingServiceImpl implements RatingService{
    
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private MovieService movieService;

    @Override
    public String save(Rating rating){
        return ratingRepository.save(rating).getId();
    }

    @Override
    public Result<List<Movie>> getMovieRatingGTE(int rating){
        if(rating < 1 || rating > 5) {
            return new Result<List<Movie>>(false);
        }
        int numMovies = 3952; // TODO: Need to change this
        int[] moviesSum = new int[numMovies + 1];
        int[] moviesCount = new int[numMovies + 1];
        Arrays.fill(moviesSum, 0);
        Arrays.fill(moviesCount, 0);
        List<Rating> ratingList = ratingRepository.findAll();
        for (Rating user_rating: ratingList) {
            moviesSum[user_rating.getMovieId()] += user_rating.getRating();
            moviesCount[user_rating.getMovieId()]++;
        }

        List<Movie> result = new ArrayList<Movie>(0);
        for (int i = 1; i <= numMovies; i++) {
            if(moviesCount[i] == 0) {
                continue;
            }
            double movieRating = moviesSum[i] / moviesCount[i];
            if(movieRating >= rating) {
                result.add(movieService.getMovieById(i));
            }
        }

        return new Result<List<Movie>>(true, result);
    }
}
