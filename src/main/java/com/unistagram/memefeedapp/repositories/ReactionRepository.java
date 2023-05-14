package com.unistagram.memefeedapp.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.unistagram.memefeedapp.model.Reaction;

@Repository
public interface ReactionRepository extends MongoRepository<Reaction, String> {
    Reaction findById();
    
}
