package com.unistagram.chatapp.service;

import java.util.List;
import java.util.Optional;

import com.mongodb.client.result.UpdateResult;
import com.unistagram.chatapp.model.Conversation;

public interface ConversationService {
    String save(Conversation conversation);

    public List<Conversation> getAllConversations();

    public Optional<Conversation> getConversationById(String id);

    public UpdateResult updateConversationById(String id, Conversation updateed_conversation);

}
