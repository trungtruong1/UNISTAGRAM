package com.unistagram.chatapp.service;

import java.util.List;
import java.util.Optional;

import com.unistagram.chatapp.model.Message;
import com.unistagram.userapp.model.User;

public interface MessageService {

    public String saveNewMessage(String conversation_id, String sender_id, String receiver_id, String content);

    public Optional<Message> getMessageById(String id);

    public List<Message> getAllMessageInConversation(String id);

    
}
