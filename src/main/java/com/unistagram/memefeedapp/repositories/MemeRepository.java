package com.unistagram.memefeedapp.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.unistagram.memefeedapp.model.Meme;

@Repository
public interface MemeRepository extends MongoRepository<Meme, String> {
    Meme findById();
    
}
