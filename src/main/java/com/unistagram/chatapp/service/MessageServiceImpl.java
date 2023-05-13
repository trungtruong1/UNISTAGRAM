package com.unistagram.chatapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.unistagram.chatapp.model.Conversation;
import com.unistagram.chatapp.model.Message;
import com.unistagram.chatapp.repositories.MessageRepository;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    private String save(Message message) {
        return messageRepository.save(message).getId().toString();
    }

    @Override
    public String saveNewMessage(String conversation_id, String sender_id, String receiver_id, String content) {
        Message new_message = new Message(conversation_id, sender_id, receiver_id, content);
        return save(new_message);
    }

    @Override
    public Optional<Message> getMessageById(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        Message message = mongoTemplate.findOne(query, Message.class);
        if(message == null) {
            return Optional.empty();
        }
        return Optional.of(message);
    }

    @Override
    public List<Message> getAllMessageInConversation(String id) {
        Query query = new Query(Criteria.where("conversation").is(id));
        return mongoTemplate.find(query, Message.class); 
    }
}
