package com.unistagram.repositories;
// import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.unistagram.model.User;


@Repository
public interface UserRepository extends MongoRepository<User, String> {

}
