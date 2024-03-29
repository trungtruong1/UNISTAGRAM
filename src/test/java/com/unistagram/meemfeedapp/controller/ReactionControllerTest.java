package com.unistagram.meemfeedapp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.unistagram.memefeedapp.controller.ReactionController;
import com.unistagram.memefeedapp.model.Reaction;
import com.unistagram.memefeedapp.service.ReactionService;
import com.unistagram.userapp.exception.ParameterErrorNumberException;
import com.unistagram.userapp.exception.ParameterErrorStringException;
import com.unistagram.userapp.service.UserService;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;

import org.bson.types.Binary;
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
import org.springframework.web.multipart.MultipartFile;

@ContextConfiguration(classes = {ReactionController.class})
@ExtendWith(SpringExtension.class)
class ReactionControllerTest {
    @Autowired
    private ReactionController reactionController;

    @MockBean
    private ReactionService reactionService;

    @MockBean
    private UserService userService;
    @Test
    void testHandleObjectIdException() {
        ResponseEntity<String> actualHandleObjectIdExceptionResult = reactionController.handleObjectIdException();
        assertEquals("Something wrong when saving the message", actualHandleObjectIdExceptionResult.getBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualHandleObjectIdExceptionResult.getStatusCode());
        assertTrue(actualHandleObjectIdExceptionResult.getHeaders().isEmpty());
    }
    @Test
    void testHandleParameterErrorNumber() {
        ResponseEntity<String> actualHandleParameterErrorNumberResult = reactionController
                .handleParameterErrorNumber(new ParameterErrorNumberException("An error occurred"));
        assertEquals("An error occurred", actualHandleParameterErrorNumberResult.getBody());
        assertEquals(HttpStatus.NOT_FOUND, actualHandleParameterErrorNumberResult.getStatusCode());
        assertTrue(actualHandleParameterErrorNumberResult.getHeaders().isEmpty());
    }
    @Test
    void testHandleParameterErrorNumber2() {
        ParameterErrorNumberException ex = mock(ParameterErrorNumberException.class);
        when(ex.getMessage()).thenReturn("Not all who wander are lost");
        ResponseEntity<String> actualHandleParameterErrorNumberResult = reactionController.handleParameterErrorNumber(ex);
        assertEquals("Not all who wander are lost", actualHandleParameterErrorNumberResult.getBody());
        assertEquals(HttpStatus.NOT_FOUND, actualHandleParameterErrorNumberResult.getStatusCode());
        assertTrue(actualHandleParameterErrorNumberResult.getHeaders().isEmpty());
        verify(ex).getMessage();
    }

    @Test
    void testHandleParameterErrorString() {
        ResponseEntity<String> actualHandleParameterErrorStringResult = reactionController
                .handleParameterErrorString(new ParameterErrorStringException("An error occurred"));
        assertEquals("An error occurred", actualHandleParameterErrorStringResult.getBody());
        assertEquals(HttpStatus.NOT_ACCEPTABLE, actualHandleParameterErrorStringResult.getStatusCode());
        assertTrue(actualHandleParameterErrorStringResult.getHeaders().isEmpty());
    }
    @Test
    void testHandleParameterErrorString2() {
        ParameterErrorStringException ex = mock(ParameterErrorStringException.class);
        when(ex.getMessage()).thenReturn("Not all who wander are lost");
        ResponseEntity<String> actualHandleParameterErrorStringResult = reactionController.handleParameterErrorString(ex);
        assertEquals("Not all who wander are lost", actualHandleParameterErrorStringResult.getBody());
        assertEquals(HttpStatus.NOT_ACCEPTABLE, actualHandleParameterErrorStringResult.getStatusCode());
        assertTrue(actualHandleParameterErrorStringResult.getHeaders().isEmpty());
        verify(ex).getMessage();
    }
    @Test
    void testGetMemeById() throws Exception {
        Reaction reaction = new Reaction();
        reaction.setAuthor("JaneDoe");
        reaction.setId("42");
        reaction.setImage(new Binary("AXAXAXAX".getBytes("UTF-8")));
        reaction.setTimestamp(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        reaction.setTitle("Dr");
        Optional<Reaction> ofResult = Optional.of(reaction);
        when(reactionService.getReactionById(Mockito.<String>any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/reactions/{id}", "42");
        MockMvcBuilders.standaloneSetup(reactionController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":\"42\",\"title\":\"Dr\",\"image\":{\"type\":0,\"data\":\"QVhBWEFYQVg=\"},\"author\":\"JaneDoe\",\"timestamp\":0}"));
    }
    @Test
    void testGetMemeById2() throws Exception {
        when(reactionService.getReactionById(Mockito.<String>any())).thenReturn(Optional.empty());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/reactions/{id}", "42");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(reactionController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(406))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("The reaction id does not exist!"));
    }
    @Test
    void testGetMemeById3() throws Exception {
        when(reactionService.getReactionById(Mockito.<String>any()))
                .thenThrow(new ParameterErrorNumberException("An error occurred"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/reactions/{id}", "42");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(reactionController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("An error occurred"));
    }
    @Test
    void testSaveReaction() throws Exception {
        MockHttpServletRequestBuilder paramResult = MockMvcRequestBuilders.get("/reactions").param("author", "foo");
        MockHttpServletRequestBuilder requestBuilder = paramResult.param("image", String.valueOf((Object) null))
                .param("title", "foo");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(reactionController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(405));
    }
}

