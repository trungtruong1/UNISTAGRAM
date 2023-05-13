package com.unistagram.chatapp.service;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.mongodb.client.result.UpdateResult;
import com.unistagram.chatapp.model.Conversation;
import com.unistagram.chatapp.repositories.ConversationRepository;

@Service
public class ConversationServiceImpl implements ConversationService {

    @Autowired
    private ConversationRepository conversationRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public String save(Conversation conversation) {
        return conversationRepository.save(conversation).getId().toString();
    }

    @Override
    public List<Conversation> getAllConversations() {
        return mongoTemplate.find(new Query(), Conversation.class);
    }

    @Override
    public Optional<Conversation> getConversationById(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        Conversation conversation = mongoTemplate.findOne(query, Conversation.class);
        if(conversation == null) {
            return Optional.empty();
        }
        return Optional.of(conversation);
    }

    @Override
    public UpdateResult updateConversationById(String id, Conversation updateed_conversation) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update()
        .set("client1", updateed_conversation.getClient1())
        .set("client2", updateed_conversation.getClient2())
        .set("status", updateed_conversation.getStatus())
        ;
        UpdateResult result = mongoTemplate.updateFirst(query, update, Conversation.class);
        return result;
    }

    @Override
    public List<Conversation> getConversationsByUser(String id) {
        Query query = new Query(Criteria.where("").orOperator(Criteria.where("client1").is(id),
                                                    Criteria.where("client2").is(id))
        );
        return mongoTemplate.find(query, Conversation.class);
    }
    
}
