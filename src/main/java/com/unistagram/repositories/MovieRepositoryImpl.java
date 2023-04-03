package com.unistagram.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
// import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

// import com.mongodb.internal.connection.QueryResult;
import com.unistagram.model.Movie;


@Repository
public class MovieRepositoryImpl implements MovieRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    public Movie findMovieByMovieID(int movie_id) {
        Query query = new Query(Criteria.where("movie_id").is(movie_id));
        Movie result = mongoTemplate.findOne(query, Movie.class);
        return result;
    }

    public Movie findMovieByName(String movie_name) {
        Query query = new Query(Criteria.where("movie_name").is(movie_name));
        Movie result = mongoTemplate.findOne(query, Movie.class);
        return result;
    }

    public long count() {
        return mongoTemplate.count(new Query(), Movie.class);
    }
}
