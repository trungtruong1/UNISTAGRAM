package com.unistagram.memefeedapp.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.unistagram.memefeedapp.model.MemeReaction;

@Repository
public interface MemeReactionRepository extends MongoRepository<MemeReaction, String> {
    MemeReaction findById();
}
