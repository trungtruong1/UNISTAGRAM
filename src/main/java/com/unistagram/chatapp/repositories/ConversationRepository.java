package com.unistagram.chatapp.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.unistagram.chatapp.model.Conversation;

@Repository
public interface ConversationRepository extends MongoRepository<Conversation, String> {
    Conversation findById();
}
