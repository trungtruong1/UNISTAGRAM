package com.unistagram.chatapp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unistagram.chatapp.service.ConversationService;
import com.unistagram.chatapp.service.MessageService;
import com.unistagram.userapp.exception.ParameterErrorNumberException;
import com.unistagram.userapp.exception.ParameterErrorStringException;
import com.unistagram.userapp.service.UserService;

import java.util.ArrayList;

import org.apache.catalina.User;
import org.apache.catalina.realm.UserDatabaseRealm;
import org.apache.catalina.users.MemoryUserDatabase;
import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {MessageController.class})
@ExtendWith(SpringExtension.class)
class MessageControllerTest {
    @MockBean
    private ConversationService conversationService;

    @Autowired
    private MessageController messageController;

    @MockBean
    private MessageService messageService;

    @MockBean
    private UserService userService;

    /**
     * Method under test: {@link MessageController#handleObjectIdException()}
     */
    @Test
    void testHandleObjectIdException() {
        ResponseEntity<String> actualHandleObjectIdExceptionResult = messageController.handleObjectIdException();
        assertEquals("Something wrong when saving the message", actualHandleObjectIdExceptionResult.getBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualHandleObjectIdExceptionResult.getStatusCode());
        assertTrue(actualHandleObjectIdExceptionResult.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link MessageController#handleParameterErrorNumber(ParameterErrorNumberException)}
     */
    @Test
    void testHandleParameterErrorNumber() {
        ResponseEntity<String> actualHandleParameterErrorNumberResult = messageController
                .handleParameterErrorNumber(new ParameterErrorNumberException("An error occurred"));
        assertEquals("An error occurred", actualHandleParameterErrorNumberResult.getBody());
        assertEquals(HttpStatus.NOT_FOUND, actualHandleParameterErrorNumberResult.getStatusCode());
        assertTrue(actualHandleParameterErrorNumberResult.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link MessageController#handleParameterErrorNumber(ParameterErrorNumberException)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testHandleParameterErrorNumber2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.unistagram.userapp.exception.ParameterErrorNumberException.getMessage()" because "ex" is null
        //       at com.unistagram.chatapp.controller.MessageController.handleParameterErrorNumber(MessageController.java:67)
        //   See https://diff.blue/R013 to resolve this issue.

        messageController.handleParameterErrorNumber(null);
    }

    /**
     * Method under test: {@link MessageController#handleParameterErrorNumber(ParameterErrorNumberException)}
     */
    @Test
    void testHandleParameterErrorNumber3() {
        ParameterErrorNumberException ex = mock(ParameterErrorNumberException.class);
        when(ex.getMessage()).thenReturn("Not all who wander are lost");
        ResponseEntity<String> actualHandleParameterErrorNumberResult = messageController.handleParameterErrorNumber(ex);
        assertEquals("Not all who wander are lost", actualHandleParameterErrorNumberResult.getBody());
        assertEquals(HttpStatus.NOT_FOUND, actualHandleParameterErrorNumberResult.getStatusCode());
        assertTrue(actualHandleParameterErrorNumberResult.getHeaders().isEmpty());
        verify(ex).getMessage();
    }

    /**
     * Method under test: {@link MessageController#handleParameterErrorString(ParameterErrorStringException)}
     */
    @Test
    void testHandleParameterErrorString() {
        ResponseEntity<String> actualHandleParameterErrorStringResult = messageController
                .handleParameterErrorString(new ParameterErrorStringException("An error occurred"));
        assertEquals("An error occurred", actualHandleParameterErrorStringResult.getBody());
        assertEquals(HttpStatus.NOT_ACCEPTABLE, actualHandleParameterErrorStringResult.getStatusCode());
        assertTrue(actualHandleParameterErrorStringResult.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link MessageController#handleParameterErrorString(ParameterErrorStringException)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testHandleParameterErrorString2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.unistagram.userapp.exception.ParameterErrorStringException.getMessage()" because "ex" is null
        //       at com.unistagram.chatapp.controller.MessageController.handleParameterErrorString(MessageController.java:73)
        //   See https://diff.blue/R013 to resolve this issue.

        messageController.handleParameterErrorString(null);
    }

    /**
     * Method under test: {@link MessageController#handleParameterErrorString(ParameterErrorStringException)}
     */
    @Test
    void testHandleParameterErrorString3() {
        ParameterErrorStringException ex = mock(ParameterErrorStringException.class);
        when(ex.getMessage()).thenReturn("Not all who wander are lost");
        ResponseEntity<String> actualHandleParameterErrorStringResult = messageController.handleParameterErrorString(ex);
        assertEquals("Not all who wander are lost", actualHandleParameterErrorStringResult.getBody());
        assertEquals(HttpStatus.NOT_ACCEPTABLE, actualHandleParameterErrorStringResult.getStatusCode());
        assertTrue(actualHandleParameterErrorStringResult.getHeaders().isEmpty());
        verify(ex).getMessage();
    }

    /**
     * Method under test: {@link MessageController#getMessagesInConversation(String)}
     */
    @Test
    void testGetMessagesInConversation() throws Exception {
        when(messageService.getAllMessageInConversation(Mockito.<String>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/messages/{id}", "42");
        MockMvcBuilders.standaloneSetup(messageController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link MessageController#getMessagesInConversation(String)}
     */
    @Test
    void testGetMessagesInConversation2() throws Exception {
        when(messageService.getAllMessageInConversation(Mockito.<String>any()))
                .thenThrow(new ParameterErrorNumberException("An error occurred"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/messages/{id}", "42");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(messageController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("An error occurred"));
    }

    /**
     * Method under test: {@link MessageController#getMessagesInConversation(String)}
     */
    @Test
    void testGetMessagesInConversation3() throws Exception {
        when(messageService.getAllMessageInConversation(Mockito.<String>any()))
                .thenThrow(new ParameterErrorStringException("An error occurred"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/messages/{id}", "42");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(messageController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(406))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("An error occurred"));
    }

    /**
     * Method under test: {@link MessageController#getMessagesInConversation(String)}
     */
    @Test
    void testGetMessagesInConversation4() throws Exception {
        when(messageService.getAllMessageInConversation(Mockito.<String>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/messages/{id}", "42");
        requestBuilder.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(messageController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link MessageController#sendMessageToConversation(MessageController.SendMessageInfo)}
     */
    @Test
    void testSendMessageToConversation() throws Exception {
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/messages/send")
                .contentType(MediaType.APPLICATION_JSON);
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content((new ObjectMapper()).writeValueAsString(null));
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(messageController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    /**
     * Method under test: {@link MessageController#sendMessageToConversation(MessageController.SendMessageInfo)}
     */
    @Test
    void testSendMessageToConversation2() throws Exception {
        User user = mock(User.class);
        when(user.getName()).thenReturn("Name");
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/messages/send");
        postResult.principal(new UserDatabaseRealm.UserDatabasePrincipal(user, new MemoryUserDatabase()));
        MockHttpServletRequestBuilder contentTypeResult = postResult.contentType(MediaType.APPLICATION_JSON);
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content((new ObjectMapper()).writeValueAsString(null));
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(messageController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }
}
