package com.unistagram.repositories;
// import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.unistagram.model.Movie;


@Repository
public interface MovieRepository extends MongoRepository<Movie, String> {

}
