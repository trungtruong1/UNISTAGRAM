package com.unistagram.repositories;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
// import org.springframework.stereotype.Repository;

import com.unistagram.model.Movie;


public interface MovieRepository {
    public Movie findMovieByMovieID(int movie_id);
    public Movie findMovieByName(String movie_name);
    public long count();
}
