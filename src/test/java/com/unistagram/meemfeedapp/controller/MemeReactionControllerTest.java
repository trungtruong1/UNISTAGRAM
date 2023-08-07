package com.unistagram.meemfeedapp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.unistagram.memefeedapp.controller.MemeReactionController;
import com.unistagram.memefeedapp.model.MemeReaction;
import com.unistagram.memefeedapp.service.MemeReactionService;
import com.unistagram.userapp.exception.ParameterErrorNumberException;
import com.unistagram.userapp.exception.ParameterErrorStringException;
import com.unistagram.userapp.service.UserService;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {MemeReactionController.class})
@ExtendWith(SpringExtension.class)
class MemeReactionControllerTest {
    @Autowired
    private MemeReactionController memeReactionController;

    @MockBean
    private MemeReactionService memeReactionService;

    @MockBean
    private UserService userService;

    @Test
    void testHandleObjectIdException() {
        ResponseEntity<String> actualHandleObjectIdExceptionResult = memeReactionController.handleObjectIdException();
        assertEquals("Something wrong when saving the message", actualHandleObjectIdExceptionResult.getBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualHandleObjectIdExceptionResult.getStatusCode());
        assertTrue(actualHandleObjectIdExceptionResult.getHeaders().isEmpty());
    }

    @Test
    void testHandleParameterErrorNumber() {
        ResponseEntity<String> actualHandleParameterErrorNumberResult = memeReactionController
                .handleParameterErrorNumber(new ParameterErrorNumberException("An error occurred"));
        assertEquals("An error occurred", actualHandleParameterErrorNumberResult.getBody());
        assertEquals(HttpStatus.NOT_FOUND, actualHandleParameterErrorNumberResult.getStatusCode());
        assertTrue(actualHandleParameterErrorNumberResult.getHeaders().isEmpty());
    }

    @Test
    void testHandleParameterErrorNumber2() {
        ParameterErrorNumberException ex = mock(ParameterErrorNumberException.class);
        when(ex.getMessage()).thenReturn("Not all who wander are lost");
        ResponseEntity<String> actualHandleParameterErrorNumberResult = memeReactionController
                .handleParameterErrorNumber(ex);
        assertEquals("Not all who wander are lost", actualHandleParameterErrorNumberResult.getBody());
        assertEquals(HttpStatus.NOT_FOUND, actualHandleParameterErrorNumberResult.getStatusCode());
        assertTrue(actualHandleParameterErrorNumberResult.getHeaders().isEmpty());
        verify(ex).getMessage();
    }

    @Test
    void testHandleParameterErrorString() {
        ResponseEntity<String> actualHandleParameterErrorStringResult = memeReactionController
                .handleParameterErrorString(new ParameterErrorStringException("An error occurred"));
        assertEquals("An error occurred", actualHandleParameterErrorStringResult.getBody());
        assertEquals(HttpStatus.NOT_ACCEPTABLE, actualHandleParameterErrorStringResult.getStatusCode());
        assertTrue(actualHandleParameterErrorStringResult.getHeaders().isEmpty());
    }

    @Test
    void testHandleParameterErrorString2() {
        ParameterErrorStringException ex = mock(ParameterErrorStringException.class);
        when(ex.getMessage()).thenReturn("Not all who wander are lost");
        ResponseEntity<String> actualHandleParameterErrorStringResult = memeReactionController
                .handleParameterErrorString(ex);
        assertEquals("Not all who wander are lost", actualHandleParameterErrorStringResult.getBody());
        assertEquals(HttpStatus.NOT_ACCEPTABLE, actualHandleParameterErrorStringResult.getStatusCode());
        assertTrue(actualHandleParameterErrorStringResult.getHeaders().isEmpty());
        verify(ex).getMessage();
    }

    @Test
    void testAddReaction() throws Exception {
        MemeReaction memeReaction = new MemeReaction();
        memeReaction.setId("42");
        memeReaction.setMeme_id("Meme id");
        memeReaction.setReaction_id("Reaction id");
        memeReaction.setTimestamp(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        memeReaction.setUser_id("User id");
        Optional<MemeReaction> ofResult = Optional.of(memeReaction);

        MemeReaction memeReaction2 = new MemeReaction();
        memeReaction2.setId("42");
        memeReaction2.setMeme_id("Meme id");
        memeReaction2.setReaction_id("Reaction id");
        memeReaction2.setTimestamp(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        memeReaction2.setUser_id("User id");
        Optional<MemeReaction> ofResult2 = Optional.of(memeReaction2);
        when(memeReactionService.save(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn("Save");
        when(memeReactionService.getMemeReactionById(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(ofResult);
        when(memeReactionService.getMemeReactionByMemeAndUser(Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(ofResult2);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/meme_reactions/add")
                .param("meme_id", "foo")
                .param("reaction_id", "foo")
                .param("user_id", "foo");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(memeReactionController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(406))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("This user has already reacted to this meme!"));
    }

    @Test
    void testRemoveReaction() throws Exception {
        MemeReaction memeReaction = new MemeReaction();
        memeReaction.setId("42");
        memeReaction.setMeme_id("Meme id");
        memeReaction.setReaction_id("Reaction id");
        memeReaction.setTimestamp(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        memeReaction.setUser_id("User id");
        Optional<MemeReaction> ofResult = Optional.of(memeReaction);
        when(memeReactionService.removeReaction(Mockito.<String>any())).thenReturn(true);
        when(memeReactionService.getMemeReactionByMemeAndUser(Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(ofResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/meme_reactions/del")
                .param("meme_id", "foo")
                .param("reaction_id", "foo")
                .param("user_id", "foo");
        ResultActions resultActions = MockMvcBuilders.standaloneSetup(memeReactionController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
        ContentResultMatchers contentResult = MockMvcResultMatchers.content();
        resultActions.andExpect(contentResult.string(Boolean.TRUE.toString()));
    }

    @Test
    void testRemoveReaction2() throws Exception {
        MemeReaction memeReaction = new MemeReaction();
        memeReaction.setId("42");
        memeReaction.setMeme_id("Meme id");
        memeReaction.setReaction_id("Reaction id");
        memeReaction.setTimestamp(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        memeReaction.setUser_id("User id");
        Optional<MemeReaction> ofResult = Optional.of(memeReaction);
        when(memeReactionService.removeReaction(Mockito.<String>any()))
                .thenThrow(new ParameterErrorNumberException("An error occurred"));
        when(memeReactionService.getMemeReactionByMemeAndUser(Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(ofResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/meme_reactions/del")
                .param("meme_id", "foo")
                .param("reaction_id", "foo")
                .param("user_id", "foo");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(memeReactionController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("An error occurred"));
    }

    @Test
    void testRemoveReaction3() throws Exception {
        MemeReaction memeReaction = new MemeReaction();
        memeReaction.setId("42");
        memeReaction.setMeme_id("Meme id");
        memeReaction.setReaction_id("Reaction id");
        memeReaction.setTimestamp(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        memeReaction.setUser_id("User id");
        Optional<MemeReaction> ofResult = Optional.of(memeReaction);
        when(memeReactionService.removeReaction(Mockito.<String>any()))
                .thenThrow(new ParameterErrorStringException("An error occurred"));
        when(memeReactionService.getMemeReactionByMemeAndUser(Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(ofResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/meme_reactions/del")
                .param("meme_id", "foo")
                .param("reaction_id", "foo")
                .param("user_id", "foo");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(memeReactionController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(406))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("An error occurred"));
    }

    @Test
    void testRemoveReaction4() throws Exception {
        when(memeReactionService.removeReaction(Mockito.<String>any())).thenReturn(true);
        when(memeReactionService.getMemeReactionByMemeAndUser(Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(Optional.empty());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/meme_reactions/del")
                .param("meme_id", "foo")
                .param("reaction_id", "foo")
                .param("user_id", "foo");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(memeReactionController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(406))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("This is not your reaction!"));
    }

    @Test
    void testAddReaction2() throws Exception {
        MemeReaction memeReaction = new MemeReaction();
        memeReaction.setId("42");
        memeReaction.setMeme_id("Meme id");
        memeReaction.setReaction_id("Reaction id");
        memeReaction.setTimestamp(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        memeReaction.setUser_id("User id");
        Optional<MemeReaction> ofResult = Optional.of(memeReaction);
        when(memeReactionService.save(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn("Save");
        when(memeReactionService.getMemeReactionById(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(ofResult);
        when(memeReactionService.getMemeReactionByMemeAndUser(Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(Optional.empty());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/meme_reactions/add")
                .param("meme_id", "foo")
                .param("reaction_id", "foo")
                .param("user_id", "foo");
        MockMvcBuilders.standaloneSetup(memeReactionController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":\"42\",\"meme_id\":\"Meme id\",\"reaction_id\":\"Reaction id\",\"user_id\":\"User id\",\"timestamp\":0}"));
    }

    @Test
    void testAddReaction3() throws Exception {
        when(memeReactionService.save(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
                .thenThrow(new ParameterErrorStringException("An error occurred"));
        when(memeReactionService.getMemeReactionById(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
                .thenThrow(new ParameterErrorStringException("An error occurred"));
        when(memeReactionService.getMemeReactionByMemeAndUser(Mockito.<String>any(), Mockito.<String>any()))
                .thenThrow(new ParameterErrorStringException("An error occurred"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/meme_reactions/add")
                .param("meme_id", "foo")
                .param("reaction_id", "foo")
                .param("user_id", "foo");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(memeReactionController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(406))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("An error occurred"));
    }

    @Test
    void testGetReactionsInMeme() throws Exception {
        when(memeReactionService.getMemeReactionsByMemeId(Mockito.<String>any())).thenReturn(new HashMap<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/meme_reactions/{id}", "42");
        MockMvcBuilders.standaloneSetup(memeReactionController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{}"));
    }

    @Test
    void testGetReactionsInMeme2() throws Exception {
        when(memeReactionService.getMemeReactionsByMemeId(Mockito.<String>any()))
                .thenThrow(new ParameterErrorNumberException("An error occurred"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/meme_reactions/{id}", "42");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(memeReactionController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("An error occurred"));
    }
    @Test
    void testGetReactionsInMeme3() throws Exception {
        when(memeReactionService.getMemeReactionsByMemeId(Mockito.<String>any()))
                .thenThrow(new ParameterErrorStringException("An error occurred"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/meme_reactions/{id}", "42");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(memeReactionController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(406))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("An error occurred"));
    }
}

