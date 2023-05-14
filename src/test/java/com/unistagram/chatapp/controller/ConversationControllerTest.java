package com.unistagram.chatapp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.unistagram.chatapp.model.Conversation;
import com.unistagram.chatapp.service.ConversationService;
import com.unistagram.userapp.exception.ParameterErrorNumberException;
import com.unistagram.userapp.exception.ParameterErrorStringException;
import com.unistagram.userapp.model.User;
import com.unistagram.userapp.service.UserService;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {ConversationController.class})
@ExtendWith(SpringExtension.class)
class ConversationControllerTest {
    @Autowired
    private ConversationController conversationController;

    @MockBean
    private ConversationService conversationService;

    @MockBean
    private UserService userService;

    /**
     * Method under test: {@link ConversationController#handleObjectIdException()}
     */
    @Test
    void testHandleObjectIdException() {
        ResponseEntity<String> actualHandleObjectIdExceptionResult = conversationController.handleObjectIdException();
        assertEquals("Something wrong when saving the user", actualHandleObjectIdExceptionResult.getBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualHandleObjectIdExceptionResult.getStatusCode());
        assertTrue(actualHandleObjectIdExceptionResult.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link ConversationController#handleParameterErrorNumber(ParameterErrorNumberException)}
     */
    @Test
    void testHandleParameterErrorNumber() {
        ResponseEntity<String> actualHandleParameterErrorNumberResult = conversationController
                .handleParameterErrorNumber(new ParameterErrorNumberException("An error occurred"));
        assertEquals("An error occurred", actualHandleParameterErrorNumberResult.getBody());
        assertEquals(HttpStatus.NOT_FOUND, actualHandleParameterErrorNumberResult.getStatusCode());
        assertTrue(actualHandleParameterErrorNumberResult.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link ConversationController#handleParameterErrorNumber(ParameterErrorNumberException)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testHandleParameterErrorNumber2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.unistagram.userapp.exception.ParameterErrorNumberException.getMessage()" because "ex" is null
        //       at com.unistagram.chatapp.controller.ConversationController.handleParameterErrorNumber(ConversationController.java:41)
        //   See https://diff.blue/R013 to resolve this issue.

        conversationController.handleParameterErrorNumber(null);
    }

    /**
     * Method under test: {@link ConversationController#handleParameterErrorNumber(ParameterErrorNumberException)}
     */
    @Test
    void testHandleParameterErrorNumber3() {
        ParameterErrorNumberException ex = mock(ParameterErrorNumberException.class);
        when(ex.getMessage()).thenReturn("Not all who wander are lost");
        ResponseEntity<String> actualHandleParameterErrorNumberResult = conversationController
                .handleParameterErrorNumber(ex);
        assertEquals("Not all who wander are lost", actualHandleParameterErrorNumberResult.getBody());
        assertEquals(HttpStatus.NOT_FOUND, actualHandleParameterErrorNumberResult.getStatusCode());
        assertTrue(actualHandleParameterErrorNumberResult.getHeaders().isEmpty());
        verify(ex).getMessage();
    }

    /**
     * Method under test: {@link ConversationController#handleParameterErrorString(ParameterErrorStringException)}
     */
    @Test
    void testHandleParameterErrorString() {
        ResponseEntity<String> actualHandleParameterErrorStringResult = conversationController
                .handleParameterErrorString(new ParameterErrorStringException("An error occurred"));
        assertEquals("An error occurred", actualHandleParameterErrorStringResult.getBody());
        assertEquals(HttpStatus.NOT_ACCEPTABLE, actualHandleParameterErrorStringResult.getStatusCode());
        assertTrue(actualHandleParameterErrorStringResult.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link ConversationController#handleParameterErrorString(ParameterErrorStringException)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testHandleParameterErrorString2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.unistagram.userapp.exception.ParameterErrorStringException.getMessage()" because "ex" is null
        //       at com.unistagram.chatapp.controller.ConversationController.handleParameterErrorString(ConversationController.java:47)
        //   See https://diff.blue/R013 to resolve this issue.

        conversationController.handleParameterErrorString(null);
    }

    /**
     * Method under test: {@link ConversationController#handleParameterErrorString(ParameterErrorStringException)}
     */
    @Test
    void testHandleParameterErrorString3() {
        ParameterErrorStringException ex = mock(ParameterErrorStringException.class);
        when(ex.getMessage()).thenReturn("Not all who wander are lost");
        ResponseEntity<String> actualHandleParameterErrorStringResult = conversationController
                .handleParameterErrorString(ex);
        assertEquals("Not all who wander are lost", actualHandleParameterErrorStringResult.getBody());
        assertEquals(HttpStatus.NOT_ACCEPTABLE, actualHandleParameterErrorStringResult.getStatusCode());
        assertTrue(actualHandleParameterErrorStringResult.getHeaders().isEmpty());
        verify(ex).getMessage();
    }

    /**
     * Method under test: {@link ConversationController#getAllConversations()}
     */
    @Test
    void testGetAllConversations() throws Exception {
        when(conversationService.getAllConversations()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/conversations/");
        MockMvcBuilders.standaloneSetup(conversationController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link ConversationController#getAllConversations()}
     */
    @Test
    void testGetAllConversations2() throws Exception {
        when(conversationService.getAllConversations()).thenThrow(new ParameterErrorNumberException("An error occurred"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/conversations/");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(conversationController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("An error occurred"));
    }

    /**
     * Method under test: {@link ConversationController#getAllConversations()}
     */
    @Test
    void testGetAllConversations3() throws Exception {
        when(conversationService.getAllConversations()).thenThrow(new ParameterErrorStringException("An error occurred"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/conversations/");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(conversationController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(406))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("An error occurred"));
    }

    /**
     * Method under test: {@link ConversationController#getAllConversations()}
     */
    @Test
    void testGetAllConversations4() throws Exception {
        when(conversationService.getAllConversations()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/conversations/");
        requestBuilder.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(conversationController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link ConversationController#getConversationsByUser(int)}
     */
    @Test
    void testGetConversationsByUser() throws Exception {
        when(conversationService.getConversationsByUser(Mockito.<String>any())).thenReturn(new ArrayList<>());

        User user = new User();
        user.setActivity(new String[]{"Activity"});
        user.setAge(1);
        user.setEmail("jane.doe@example.org");
        user.setFilm(new String[]{"Film"});
        user.setGender("Gender");
        user.setId("42");
        user.setMusic(new String[]{"Music"});
        user.setNationality("Nationality");
        user.setPassword("iloveyou");
        user.setUserId(1);
        user.setUser_id(1);
        user.setUsername("janedoe");
        user.set_in_queue(true);
        Optional<User> ofResult = Optional.of(user);
        when(userService.getUserById(anyInt())).thenReturn(ofResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/conversations/users/{id}", 1);
        MockMvcBuilders.standaloneSetup(conversationController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link ConversationController#getConversationsByUser(int)}
     */
    @Test
    void testGetConversationsByUser2() throws Exception {
        when(conversationService.getConversationsByUser(Mockito.<String>any()))
                .thenThrow(new ParameterErrorNumberException("An error occurred"));

        User user = new User();
        user.setActivity(new String[]{"Activity"});
        user.setAge(1);
        user.setEmail("jane.doe@example.org");
        user.setFilm(new String[]{"Film"});
        user.setGender("Gender");
        user.setId("42");
        user.setMusic(new String[]{"Music"});
        user.setNationality("Nationality");
        user.setPassword("iloveyou");
        user.setUserId(1);
        user.setUser_id(1);
        user.setUsername("janedoe");
        user.set_in_queue(true);
        Optional<User> ofResult = Optional.of(user);
        when(userService.getUserById(anyInt())).thenReturn(ofResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/conversations/users/{id}", 1);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(conversationController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("An error occurred"));
    }

    /**
     * Method under test: {@link ConversationController#getConversationsByUser(int)}
     */
    @Test
    void testGetConversationsByUser3() throws Exception {
        when(conversationService.getConversationsByUser(Mockito.<String>any()))
                .thenThrow(new ParameterErrorStringException("An error occurred"));

        User user = new User();
        user.setActivity(new String[]{"Activity"});
        user.setAge(1);
        user.setEmail("jane.doe@example.org");
        user.setFilm(new String[]{"Film"});
        user.setGender("Gender");
        user.setId("42");
        user.setMusic(new String[]{"Music"});
        user.setNationality("Nationality");
        user.setPassword("iloveyou");
        user.setUserId(1);
        user.setUser_id(1);
        user.setUsername("janedoe");
        user.set_in_queue(true);
        Optional<User> ofResult = Optional.of(user);
        when(userService.getUserById(anyInt())).thenReturn(ofResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/conversations/users/{id}", 1);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(conversationController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(406))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("An error occurred"));
    }

    /**
     * Method under test: {@link ConversationController#getConversationsByUser(int)}
     */
    @Test
    void testGetConversationsByUser4() throws Exception {
        when(conversationService.getConversationsByUser(Mockito.<String>any())).thenReturn(new ArrayList<>());
        when(userService.getUserById(anyInt())).thenReturn(Optional.empty());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/conversations/users/{id}", 1);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(conversationController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(406))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("User id does not exist!"));
    }

    /**
     * Method under test: {@link ConversationController#getConversation(String)}
     */
    @Test
    void testGetConversation() throws Exception {
        Conversation conversation = new Conversation();
        conversation.setClient1("Client1");
        conversation.setClient2("Client2");
        conversation.setId("42");
        conversation.setStatus(Conversation.Status.ONGOING);
        Optional<Conversation> ofResult = Optional.of(conversation);
        when(conversationService.getConversationById(Mockito.<String>any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/conversations/{id}", "42");
        MockMvcBuilders.standaloneSetup(conversationController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":\"42\",\"client1\":\"Client1\",\"client2\":\"Client2\",\"status\":\"ONGOING\"}"));
    }

    /**
     * Method under test: {@link ConversationController#getConversation(String)}
     */
    @Test
    void testGetConversation2() throws Exception {
        when(conversationService.getConversationById(Mockito.<String>any())).thenReturn(Optional.empty());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/conversations/{id}", "42");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(conversationController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Conversation id does not exist!"));
    }

    /**
     * Method under test: {@link ConversationController#getConversation(String)}
     */
    @Test
    void testGetConversation3() throws Exception {
        when(conversationService.getConversationById(Mockito.<String>any()))
                .thenThrow(new ParameterErrorNumberException("An error occurred"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/conversations/{id}", "42");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(conversationController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("An error occurred"));
    }

    /**
     * Method under test: {@link ConversationController#getConversation(String)}
     */
    @Test
    void testGetConversation4() throws Exception {
        when(conversationService.getConversationById(Mockito.<String>any()))
                .thenThrow(new ParameterErrorStringException("An error occurred"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/conversations/{id}", "42");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(conversationController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(406))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("An error occurred"));
    }
}

