package com.unistagram.service;

import org.springframework.stereotype.Service;

import com.unistagram.model.Rating;
import com.unistagram.model.Result;
import com.unistagram.repositories.RatingRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.ReplaceRootOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;

@Service
public class RatingServiceImpl implements RatingService{
    
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public String save(Rating rating){
        return ratingRepository.save(rating).getId();
    }

    @Override
    public Result<List<Object>> getMovieRatingGTE(double rating){
        if(rating < 1 || rating > 5) {
            return new Result<List<Object>>(false);
        }

        GroupOperation avgRating = Aggregation.group("$movie_id")
                                            .avg("$rating").
                                            as("avg_rating");

        MatchOperation filterRating = Aggregation.match(
            Criteria.where("avg_rating").gte(rating)
        );

        SortOperation sortOperation = Aggregation.sort(Sort.Direction.ASC, "movie_id");

        LookupOperation movieLookup = Aggregation.lookup("movie", "_id", "movie_id", "matching_movie");

        UnwindOperation unwindMovie = Aggregation.unwind("$matching_movie");

        ReplaceRootOperation replaceRoot = Aggregation.replaceRoot("$matching_movie");

        ProjectionOperation projectMovie = Aggregation.project("movie_name", "genre")
                                                      .andExclude("_id");

        Aggregation aggregation = Aggregation.newAggregation(
            avgRating,
            filterRating,
            sortOperation,
            movieLookup,
            unwindMovie,
            replaceRoot,
            projectMovie
        );

        AggregationResults<Object> results = mongoTemplate.aggregate(
            aggregation, 
            "rating", 
            Object.class
        );
        return new Result<List<Object>>(true, results.getMappedResults());
    }
}
