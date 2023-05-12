package com.unistagram.userapp.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.unistagram.userapp.model.User;


@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findById();
    
    @Query("{'is_in_queue' : ?0, 'id' : { $ne: ?1 }}")
    List<User> findByIsInQueueAndExcludingId(boolean isInQueue, String id);
}
