package com.unistagram.matchapp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.unistagram.matchapp.service.MatchingService;
import com.unistagram.userapp.exception.ParameterErrorNumberException;
import com.unistagram.userapp.exception.ParameterErrorStringException;
import com.unistagram.userapp.model.User;
import com.unistagram.userapp.service.UserService;

import java.util.Optional;

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

@ContextConfiguration(classes = {MatchingController.class})
@ExtendWith(SpringExtension.class)
class MatchingControllerTest {
    @Autowired
    private MatchingController matchingController;

    @MockBean
    private MatchingService matchingService;

    @MockBean
    private UserService userService;

    /**
     * Method under test: {@link MatchingController#handleObjectIdException()}
     */
    @Test
    void testHandleObjectIdException() {
        ResponseEntity<String> actualHandleObjectIdExceptionResult = matchingController.handleObjectIdException();
        assertEquals("Something wrong when saving the user", actualHandleObjectIdExceptionResult.getBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualHandleObjectIdExceptionResult.getStatusCode());
        assertTrue(actualHandleObjectIdExceptionResult.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link MatchingController#handleParameterErrorNumber()}
     */
    @Test
    void testHandleParameterErrorNumber() {
        ResponseEntity<String> actualHandleParameterErrorNumberResult = matchingController.handleParameterErrorNumber();
        assertEquals("User id does not exist!", actualHandleParameterErrorNumberResult.getBody());
        assertEquals(HttpStatus.NOT_FOUND, actualHandleParameterErrorNumberResult.getStatusCode());
        assertTrue(actualHandleParameterErrorNumberResult.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link MatchingController#handleParameterErrorString()}
     */
    @Test
    void testHandleParameterErrorString() {
        ResponseEntity<String> actualHandleParameterErrorStringResult = matchingController.handleParameterErrorString();
        assertEquals("Parameter is not a number!", actualHandleParameterErrorStringResult.getBody());
        assertEquals(HttpStatus.NOT_ACCEPTABLE, actualHandleParameterErrorStringResult.getStatusCode());
        assertTrue(actualHandleParameterErrorStringResult.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link MatchingController#greeting(String)}
     */
    @Test
    void testGreeting() throws Exception {
        assertEquals("Hello, Not all who wander are lost!",
                matchingController.greeting("Not all who wander are lost").getContent());
    }

    /**
     * Method under test: {@link MatchingController#checkUserInQueue(int)}
     */
    @Test
    void testCheckUserInQueue() throws Exception {
        when(matchingService.isWaiting(Mockito.<User>any())).thenReturn(true);

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
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/matching/check_in_queue/{id}", 1);
        MockMvcBuilders.standaloneSetup(matchingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"is_in_queue\":true}"));
    }

    /**
     * Method under test: {@link MatchingController#checkUserInQueue(int)}
     */
    @Test
    void testCheckUserInQueue2() throws Exception {
        when(matchingService.isWaiting(Mockito.<User>any()))
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
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/matching/check_in_queue/{id}", 1);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(matchingController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(406))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Parameter is not a number!"));
    }

    /**
     * Method under test: {@link MatchingController#checkUserInQueue(int)}
     */
    @Test
    void testCheckUserInQueue3() throws Exception {
        when(matchingService.isWaiting(Mockito.<User>any()))
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
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/matching/check_in_queue/{id}", 1);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(matchingController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("User id does not exist!"));
    }

    /**
     * Method under test: {@link MatchingController#checkUserInQueue(int)}
     */
    @Test
    void testCheckUserInQueue4() throws Exception {
        when(matchingService.isWaiting(Mockito.<User>any())).thenReturn(true);
        when(userService.getUserById(anyInt())).thenReturn(Optional.empty());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/matching/check_in_queue/{id}", 1);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(matchingController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("User id does not exist!"));
    }

    /**
     * Method under test: {@link MatchingController#updateUserToJoinQueue(String)}
     */
    @Test
    void testUpdateUserToJoinQueue() throws Exception {
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
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/matching/join_queue/{id}", "42");
        MockMvcBuilders.standaloneSetup(matchingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":\"42\",\"user_id\":1,\"username\":\"janedoe\",\"password\":\"iloveyou\",\"email\":\"jane.doe@example.org\",\"age"
                                        + "\":1,\"gender\":\"Gender\",\"nationality\":\"Nationality\",\"is_in_queue\":true,\"music\":[\"Music\"],\"film\":[\"Film"
                                        + "\"],\"activity\":[\"Activity\"]}"));
    }

    /**
     * Method under test: {@link MatchingController#updateUserToJoinQueue(String)}
     */
    @Test
    void testUpdateUserToJoinQueue2() throws Exception {
        doNothing().when(matchingService).joinQueue(Mockito.<User>any());

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
        user.set_in_queue(false);
        Optional<User> ofResult = Optional.of(user);
        when(userService.getUserById(anyInt())).thenReturn(ofResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/matching/join_queue/{id}", "42");
        MockMvcBuilders.standaloneSetup(matchingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":\"42\",\"user_id\":1,\"username\":\"janedoe\",\"password\":\"iloveyou\",\"email\":\"jane.doe@example.org\",\"age"
                                        + "\":1,\"gender\":\"Gender\",\"nationality\":\"Nationality\",\"is_in_queue\":false,\"music\":[\"Music\"],\"film\":[\"Film"
                                        + "\"],\"activity\":[\"Activity\"]}"));
    }

    /**
     * Method under test: {@link MatchingController#updateUserToJoinQueue(String)}
     */
    @Test
    void testUpdateUserToJoinQueue3() throws Exception {
        doNothing().when(matchingService).joinQueue(Mockito.<User>any());
        when(userService.getUserById(anyInt())).thenReturn(Optional.empty());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/matching/join_queue/{id}", "42");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(matchingController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("User id does not exist!"));
    }

    /**
     * Method under test: {@link MatchingController#updateUserToJoinQueue(String)}
     */
    @Test
    void testUpdateUserToJoinQueue4() throws Exception {
        doNothing().when(matchingService).joinQueue(Mockito.<User>any());

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
        user.set_in_queue(false);
        Optional<User> ofResult = Optional.of(user);
        when(userService.getUserById(anyInt())).thenReturn(ofResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/matching/join_queue/{id}",
                "Uri Variables", "Uri Variables");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(matchingController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(406))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Parameter is not a number!"));
    }

    /**
     * Method under test: {@link MatchingController#updateUserToJoinQueue(String)}
     */
    @Test
    void testUpdateUserToJoinQueue5() throws Exception {
        doThrow(new ParameterErrorStringException("An error occurred")).when(matchingService)
                .joinQueue(Mockito.<User>any());

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
        user.set_in_queue(false);
        Optional<User> ofResult = Optional.of(user);
        when(userService.getUserById(anyInt())).thenReturn(ofResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/matching/join_queue/{id}", "42");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(matchingController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(406))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Parameter is not a number!"));
    }

    /**
     * Method under test: {@link MatchingController#updateUserToOutQueue(String)}
     */
    @Test
    void testUpdateUserToOutQueue() throws Exception {
        doNothing().when(matchingService).outQueue(Mockito.<User>any());

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
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/matching/out_queue/{id}", "42");
        MockMvcBuilders.standaloneSetup(matchingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":\"42\",\"user_id\":1,\"username\":\"janedoe\",\"password\":\"iloveyou\",\"email\":\"jane.doe@example.org\",\"age"
                                        + "\":1,\"gender\":\"Gender\",\"nationality\":\"Nationality\",\"is_in_queue\":true,\"music\":[\"Music\"],\"film\":[\"Film"
                                        + "\"],\"activity\":[\"Activity\"]}"));
    }

    /**
     * Method under test: {@link MatchingController#updateUserToOutQueue(String)}
     */
    @Test
    void testUpdateUserToOutQueue2() throws Exception {
        doThrow(new ParameterErrorStringException("An error occurred")).when(matchingService)
                .outQueue(Mockito.<User>any());

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
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/matching/out_queue/{id}", "42");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(matchingController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(406))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Parameter is not a number!"));
    }

    /**
     * Method under test: {@link MatchingController#updateUserToOutQueue(String)}
     */
    @Test
    void testUpdateUserToOutQueue3() throws Exception {
        doThrow(new ParameterErrorNumberException("An error occurred")).when(matchingService)
                .outQueue(Mockito.<User>any());

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
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/matching/out_queue/{id}", "42");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(matchingController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("User id does not exist!"));
    }

    /**
     * Method under test: {@link MatchingController#updateUserToOutQueue(String)}
     */
    @Test
    void testUpdateUserToOutQueue4() throws Exception {
        doThrow(new NumberFormatException("?")).when(matchingService).outQueue(Mockito.<User>any());

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
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/matching/out_queue/{id}", "42");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(matchingController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(406))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Parameter is not a number!"));
    }

    /**
     * Method under test: {@link MatchingController#updateUserToOutQueue(String)}
     */
    @Test
    void testUpdateUserToOutQueue5() throws Exception {
        doNothing().when(matchingService).outQueue(Mockito.<User>any());

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
        user.set_in_queue(false);
        Optional<User> ofResult = Optional.of(user);
        when(userService.getUserById(anyInt())).thenReturn(ofResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/matching/out_queue/{id}", "42");
        MockMvcBuilders.standaloneSetup(matchingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":\"42\",\"user_id\":1,\"username\":\"janedoe\",\"password\":\"iloveyou\",\"email\":\"jane.doe@example.org\",\"age"
                                        + "\":1,\"gender\":\"Gender\",\"nationality\":\"Nationality\",\"is_in_queue\":false,\"music\":[\"Music\"],\"film\":[\"Film"
                                        + "\"],\"activity\":[\"Activity\"]}"));
    }

    /**
     * Method under test: {@link MatchingController#updateUserToOutQueue(String)}
     */
    @Test
    void testUpdateUserToOutQueue6() throws Exception {
        doNothing().when(matchingService).outQueue(Mockito.<User>any());
        when(userService.getUserById(anyInt())).thenReturn(Optional.empty());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/matching/out_queue/{id}", "42");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(matchingController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("User id does not exist!"));
    }

    /**
     * Method under test: {@link MatchingController#updateUserToOutQueue(String)}
     */
    @Test
    void testUpdateUserToOutQueue7() throws Exception {
        doNothing().when(matchingService).outQueue(Mockito.<User>any());

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
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/matching/out_queue/{id}",
                "Uri Variables", "Uri Variables");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(matchingController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(406))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Parameter is not a number!"));
    }
}

