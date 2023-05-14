package com.unistagram.chatapp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unistagram.chatapp.controller.MessageController.SendMessageInfo;
import com.unistagram.chatapp.model.Conversation;
import com.unistagram.chatapp.model.Message;
import com.unistagram.chatapp.model.Conversation.Status;
import com.unistagram.chatapp.repositories.ConversationRepository;
import com.unistagram.chatapp.service.ConversationService;
import com.unistagram.chatapp.service.MessageService;
import com.unistagram.userapp.exception.ParameterErrorNumberException;
import com.unistagram.userapp.exception.ParameterErrorStringException;
import com.unistagram.userapp.service.UserService;

import java.util.Optional;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {MessageController.class})
@ExtendWith(SpringExtension.class)
@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class MessageControllerTest {
    @MockBean
    private ConversationService conversationService;

    @Autowired
    private MessageController messageController;

    @MockBean
    private MessageService messageService;

    @MockBean
    private UserService userService;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void testSendMessageInfoDefaultConstructor() {
        SendMessageInfo new_message_info = new SendMessageInfo();
        assertEquals(null, new_message_info.getConversation());
        assertEquals(null, new_message_info.getSender());
        assertEquals(null, new_message_info.getContent());
    }

    @Test
    public void testSendMessageInfoConstructor() {
        SendMessageInfo new_message_info = new SendMessageInfo("1", "2", "3");
        assertEquals("1", new_message_info.getConversation());
        assertEquals("2", new_message_info.getSender());
        assertEquals("3", new_message_info.getContent());
    }

    @Test
    public void testSendMessageToConversation() {
        SendMessageInfo new_message_info = new SendMessageInfo("1", "2", "3");
        Assertions.assertThrows(ParameterErrorNumberException.class, () -> messageController.sendMessageToConversation(new_message_info));
    }

    @Test
    public void testSendMessageToConversation2() {
        // Set up mock data
        String conversationId = "1234";
        String sender = "sender1";
        String receiver = "receiver1";
        String content = "content1";
        SendMessageInfo sendMessageInfo = new SendMessageInfo(conversationId, sender, content);
        Conversation conversation = new Conversation("client1", "client2", Conversation.Status.TERMINATED);
        Message message = new Message("1", sender, receiver, content);
        when(conversationService.getConversationById(conversationId)).thenReturn(Optional.of(conversation));
        when(messageService.saveNewMessage(conversationId, sender, receiver, content)).thenReturn("1");
        when(messageService.getMessageById("1")).thenReturn(Optional.of(message));

        // Call the controller method
        Assertions.assertThrows(ParameterErrorStringException.class, () -> messageController.sendMessageToConversation(sendMessageInfo));

    }

    @Test
    public void testSendMessageToConversation3() {
        
        // Set up mock data
        String conversationId = "1234";
        String sender = "sender1";
        String receiver = "receiver1";
        String content = "content1";
        SendMessageInfo sendMessageInfo = new SendMessageInfo(conversationId, sender, content);
        Conversation conversation = new Conversation("client1", "client2", Conversation.Status.ONGOING);
        Message message = new Message("1", sender, receiver, content);
        when(conversationService.getConversationById(conversationId)).thenReturn(Optional.of(conversation));
        when(messageService.saveNewMessage(conversationId, sender, receiver, content)).thenReturn("1");
        when(messageService.getMessageById("1")).thenReturn(Optional.of(message));

        // Call the controller method
        Assertions.assertThrows(ParameterErrorStringException.class, () -> messageController.sendMessageToConversation(sendMessageInfo));
    }

    @Test
    public void testSendMessageToConversation4() {
        
        // Set up mock data
        String conversationId = "1234";
        String sender = "client1";
        String receiver = "client2";
        String content = "content1";
        SendMessageInfo sendMessageInfo = new SendMessageInfo(conversationId, sender, content);
        Conversation conversation = new Conversation("client1", "client2", Conversation.Status.ONGOING);
        Message message = new Message("1", sender, receiver, content);
        when(conversationService.getConversationById(conversationId)).thenReturn(Optional.of(conversation));
        when(messageService.saveNewMessage(conversationId, sender, receiver, content)).thenReturn("1");
        when(messageService.getMessageById("1")).thenReturn(Optional.of(message));

        // Call the controller method
        messageController.sendMessageToConversation(sendMessageInfo);
    }

    @Test
    public void testSendMessageToConversation5() {
        
        // Set up mock data
        String conversationId = "1234";
        String sender = "client2";
        String receiver = "client1";
        String content = "content1";
        SendMessageInfo sendMessageInfo = new SendMessageInfo(conversationId, sender, content);
        Conversation conversation = new Conversation("client1", "client2", Conversation.Status.ONGOING);
        Message message = new Message("1", sender, receiver, content);
        when(conversationService.getConversationById(conversationId)).thenReturn(Optional.of(conversation));
        when(messageService.saveNewMessage(conversationId, sender, receiver, content)).thenReturn("1");
        when(messageService.getMessageById("1")).thenReturn(Optional.of(message));

        messageController.sendMessageToConversation(sendMessageInfo);
    }

}

