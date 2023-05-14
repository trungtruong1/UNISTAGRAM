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

    /**
     * Method under test: {@link MessageServiceImpl#save(Message)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testSave() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "String.toString()" because the return value of "com.unistagram.chatapp.model.Message.getId()" is null
        //       at com.unistagram.chatapp.service.MessageServiceImpl.save(MessageServiceImpl.java:25)
        //   See https://diff.blue/R013 to resolve this issue.

        when(messageRepository.save(Mockito.<Message>any()))
                .thenReturn(new Message("Conversation", "Sender", "Receiver", "Not all who wander are lost"));
        messageServiceImpl.save(new Message("Conversation", "Sender", "Receiver", "Not all who wander are lost"));
    }

    /**
     * Method under test: {@link MessageServiceImpl#save(Message)}
     */
    @Test
    void testSave2() {
        Message message = new Message("Conversation", "Sender", "Receiver", "Not all who wander are lost");
        message.setId("42");
        when(messageRepository.save(Mockito.<Message>any())).thenReturn(message);
        assertEquals("42",
                messageServiceImpl.save(new Message("Conversation", "Sender", "Receiver", "Not all who wander are lost")));
        verify(messageRepository).save(Mockito.<Message>any());
    }

    /**
     * Method under test: {@link MessageServiceImpl#save(Message)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testSave3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.unistagram.chatapp.model.Message.getId()" because the return value of "com.unistagram.chatapp.repositories.MessageRepository.save(Object)" is null
        //       at com.unistagram.chatapp.service.MessageServiceImpl.save(MessageServiceImpl.java:25)
        //   See https://diff.blue/R013 to resolve this issue.

        when(messageRepository.save(Mockito.<Message>any())).thenReturn(null);
        messageServiceImpl.save(new Message("Conversation", "Sender", "Receiver", "Not all who wander are lost"));
    }

    /**
     * Method under test: {@link MessageServiceImpl#saveNewMessage(String, String, String, String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testSaveNewMessage() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "String.toString()" because the return value of "com.unistagram.chatapp.model.Message.getId()" is null
        //       at com.unistagram.chatapp.service.MessageServiceImpl.save(MessageServiceImpl.java:25)
        //       at com.unistagram.chatapp.service.MessageServiceImpl.saveNewMessage(MessageServiceImpl.java:31)
        //   See https://diff.blue/R013 to resolve this issue.

        when(messageRepository.save(Mockito.<Message>any()))
                .thenReturn(new Message("Conversation", "Sender", "Receiver", "Not all who wander are lost"));
        messageServiceImpl.saveNewMessage("Conversation id", "Sender id", "Receiver id", "Not all who wander are lost");
    }

    /**
     * Method under test: {@link MessageServiceImpl#saveNewMessage(String, String, String, String)}
     */
    @Test
    void testSaveNewMessage2() {
        Message message = new Message("Conversation", "Sender", "Receiver", "Not all who wander are lost");
        message.setId("42");
        when(messageRepository.save(Mockito.<Message>any())).thenReturn(message);
        assertEquals("42", messageServiceImpl.saveNewMessage("Conversation id", "Sender id", "Receiver id",
                "Not all who wander are lost"));
        verify(messageRepository).save(Mockito.<Message>any());
    }

    /**
     * Method under test: {@link MessageServiceImpl#saveNewMessage(String, String, String, String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testSaveNewMessage3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.unistagram.chatapp.model.Message.getId()" because the return value of "com.unistagram.chatapp.repositories.MessageRepository.save(Object)" is null
        //       at com.unistagram.chatapp.service.MessageServiceImpl.save(MessageServiceImpl.java:25)
        //       at com.unistagram.chatapp.service.MessageServiceImpl.saveNewMessage(MessageServiceImpl.java:31)
        //   See https://diff.blue/R013 to resolve this issue.

        when(messageRepository.save(Mockito.<Message>any())).thenReturn(null);
        messageServiceImpl.saveNewMessage("Conversation id", "Sender id", "Receiver id", "Not all who wander are lost");
    }

    /**
     * Method under test: {@link MessageServiceImpl#getMessageById(String)}
     */
    @Test
    void testGetMessageById() {
        when(mongoTemplate.findOne(Mockito.<Query>any(), Mockito.<Class<Message>>any()))
                .thenReturn(new Message("Conversation", "Sender", "Receiver", "Not all who wander are lost"));
        assertTrue(messageServiceImpl.getMessageById("42").isPresent());
        verify(mongoTemplate).findOne(Mockito.<Query>any(), Mockito.<Class<Message>>any());
    }

    /**
     * Method under test: {@link MessageServiceImpl#getMessageById(String)}
     */
    @Test
    void testGetMessageById2() {
        when(mongoTemplate.findOne(Mockito.<Query>any(), Mockito.<Class<Message>>any())).thenReturn(null);
        assertFalse(messageServiceImpl.getMessageById("42").isPresent());
        verify(mongoTemplate).findOne(Mockito.<Query>any(), Mockito.<Class<Message>>any());
    }

    /**
     * Method under test: {@link MessageServiceImpl#getAllMessageInConversation(String)}
     */
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

