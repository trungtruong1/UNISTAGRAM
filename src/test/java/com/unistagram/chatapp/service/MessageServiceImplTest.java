package com.unistagram.chatapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.unistagram.chatapp.model.Message;
import com.unistagram.chatapp.repositories.MessageRepository;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {MessageServiceImpl.class})
@ExtendWith(SpringExtension.class)
class MessageServiceImplTest {
    @MockBean
    private MessageRepository messageRepository;

    @Autowired
    private MessageServiceImpl messageServiceImpl;

    @MockBean
    private MongoTemplate mongoTemplate;


    @Test
    void testSave() {
        Message message = new Message("Conversation", "Sender", "Receiver", "Not all who wander are lost");
        message.setId("42");
        when(messageRepository.save(Mockito.<Message>any())).thenReturn(message);
        assertEquals("42",
                messageServiceImpl.save(new Message("Conversation", "Sender", "Receiver", "Not all who wander are lost")));
        verify(messageRepository).save(Mockito.<Message>any());
    }

    @Test
    void testSaveNewMessage() {
        Message message = new Message("Conversation", "Sender", "Receiver", "Not all who wander are lost");
        message.setId("42");
        when(messageRepository.save(Mockito.<Message>any())).thenReturn(message);
        assertEquals("42", messageServiceImpl.saveNewMessage("Conversation id", "Sender id", "Receiver id",
                "Not all who wander are lost"));
        verify(messageRepository).save(Mockito.<Message>any());
    }


    @Test
    void testGetMessageById() {
        when(mongoTemplate.findOne(Mockito.<Query>any(), Mockito.<Class<Message>>any()))
                .thenReturn(new Message("Conversation", "Sender", "Receiver", "Not all who wander are lost"));
        assertTrue(messageServiceImpl.getMessageById("42").isPresent());
        verify(mongoTemplate).findOne(Mockito.<Query>any(), Mockito.<Class<Message>>any());
    }
    @Test
    void testGetMessageById2() {
        when(mongoTemplate.findOne(Mockito.<Query>any(), Mockito.<Class<Message>>any())).thenReturn(null);
        assertFalse(messageServiceImpl.getMessageById("42").isPresent());
        verify(mongoTemplate).findOne(Mockito.<Query>any(), Mockito.<Class<Message>>any());
    }
    @Test
    void testGetAllMessageInConversation() {
        ArrayList<Message> messageList = new ArrayList<>();
        when(mongoTemplate.find(Mockito.<Query>any(), Mockito.<Class<Message>>any())).thenReturn(messageList);
        List<Message> actualAllMessageInConversation = messageServiceImpl.getAllMessageInConversation("42");
        assertSame(messageList, actualAllMessageInConversation);
        assertTrue(actualAllMessageInConversation.isEmpty());
        verify(mongoTemplate).find(Mockito.<Query>any(), Mockito.<Class<Message>>any());
    }
}

