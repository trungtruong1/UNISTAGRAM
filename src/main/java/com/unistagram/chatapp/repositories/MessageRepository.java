package com.unistagram.chatapp.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.unistagram.chatapp.model.Message;

@Repository
public interface MessageRepository extends MongoRepository<Message, String> {
    Message findById();
}
