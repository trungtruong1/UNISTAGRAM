package com.unistagram.chatapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.unistagram.chatapp.model.Conversation;
import com.unistagram.chatapp.repositories.ConversationRepository;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.UpdateDefinition;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ConversationServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ConversationServiceImplTest {
    @MockBean
    private ConversationRepository conversationRepository;

    @Autowired
    private ConversationServiceImpl conversationServiceImpl;

    @MockBean
    private MongoTemplate mongoTemplate;
    @Test
    void testSave() {
        Conversation conversation = new Conversation();
        conversation.setClient1("Client1");
        conversation.setClient2("Client2");
        conversation.setId("42");
        conversation.setStatus(Conversation.Status.ONGOING);
        when(conversationRepository.save(Mockito.<Conversation>any())).thenReturn(conversation);

        Conversation conversation2 = new Conversation();
        conversation2.setClient1("Client1");
        conversation2.setClient2("Client2");
        conversation2.setId("42");
        conversation2.setStatus(Conversation.Status.ONGOING);
        assertEquals("42", conversationServiceImpl.save(conversation2));
        verify(conversationRepository).save(Mockito.<Conversation>any());
    }

    @Test
    void testGetAllConversations() {
        ArrayList<Conversation> conversationList = new ArrayList<>();
        when(mongoTemplate.find(Mockito.<Query>any(), Mockito.<Class<Conversation>>any())).thenReturn(conversationList);
        List<Conversation> actualAllConversations = conversationServiceImpl.getAllConversations();
        assertSame(conversationList, actualAllConversations);
        assertTrue(actualAllConversations.isEmpty());
        verify(mongoTemplate).find(Mockito.<Query>any(), Mockito.<Class<Conversation>>any());
    }

    @Test
    void testGetConversationById() {
        Conversation conversation = new Conversation();
        conversation.setClient1("Client1");
        conversation.setClient2("Client2");
        conversation.setId("42");
        conversation.setStatus(Conversation.Status.ONGOING);
        when(mongoTemplate.findOne(Mockito.<Query>any(), Mockito.<Class<Conversation>>any())).thenReturn(conversation);
        assertTrue(conversationServiceImpl.getConversationById("42").isPresent());
        verify(mongoTemplate).findOne(Mockito.<Query>any(), Mockito.<Class<Conversation>>any());
    }

    @Test
    void testUpdateConversationById() {
        when(mongoTemplate.updateFirst(Mockito.<Query>any(), Mockito.<UpdateDefinition>any(),
                Mockito.<Class<Object>>any())).thenReturn(null);

        Conversation updateed_conversation = new Conversation();
        updateed_conversation.setClient1("Client1");
        updateed_conversation.setClient2("Client2");
        updateed_conversation.setId("42");
        updateed_conversation.setStatus(Conversation.Status.ONGOING);
        assertNull(conversationServiceImpl.updateConversationById("42", updateed_conversation));
        verify(mongoTemplate).updateFirst(Mockito.<Query>any(), Mockito.<UpdateDefinition>any(),
                Mockito.<Class<Object>>any());
    }
    @Test
    void testGetConversationsByUser() {
        ArrayList<Conversation> conversationList = new ArrayList<>();
        when(mongoTemplate.find(Mockito.<Query>any(), Mockito.<Class<Conversation>>any())).thenReturn(conversationList);
        List<Conversation> actualConversationsByUser = conversationServiceImpl.getConversationsByUser("42");
        assertSame(conversationList, actualConversationsByUser);
        assertTrue(actualConversationsByUser.isEmpty());
        verify(mongoTemplate).find(Mockito.<Query>any(), Mockito.<Class<Conversation>>any());
    }
}

