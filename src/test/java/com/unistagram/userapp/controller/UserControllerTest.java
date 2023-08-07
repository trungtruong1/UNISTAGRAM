package com.unistagram.userapp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.result.UpdateResult;
import com.unistagram.userapp.exception.ObjectIdException;
import com.unistagram.userapp.exception.ParameterErrorNumberException;
import com.unistagram.userapp.exception.ParameterErrorStringException;
import com.unistagram.userapp.model.User;
import com.unistagram.userapp.service.UserService;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
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

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
class UserControllerTest {
    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    @Test
    void testHandleObjectIdException() {
        ResponseEntity<String> actualHandleObjectIdExceptionResult = userController.handleObjectIdException(new ObjectIdException("blah blah"));
        assertEquals("blah blah", actualHandleObjectIdExceptionResult.getBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualHandleObjectIdExceptionResult.getStatusCode());
        assertTrue(actualHandleObjectIdExceptionResult.getHeaders().isEmpty());
    }

    @Test
    void testHandleParameterErrorNumber() {
        ResponseEntity<String> actualHandleParameterErrorNumberResult = userController.handleParameterErrorNumber(new ParameterErrorNumberException("blah blah"));
        assertEquals("blah blah", actualHandleParameterErrorNumberResult.getBody());
        assertEquals(HttpStatus.NOT_FOUND, actualHandleParameterErrorNumberResult.getStatusCode());
        assertTrue(actualHandleParameterErrorNumberResult.getHeaders().isEmpty());
    }

    @Test
    void testHandleParameterErrorString() {
        ResponseEntity<String> actualHandleParameterErrorStringResult = userController.handleParameterErrorString(new ParameterErrorStringException("blah blah"));
        assertEquals("blah blah", actualHandleParameterErrorStringResult.getBody());
        assertEquals(HttpStatus.NOT_ACCEPTABLE, actualHandleParameterErrorStringResult.getStatusCode());
        assertTrue(actualHandleParameterErrorStringResult.getHeaders().isEmpty());
    }

    @Test
    void testGetUser() throws Exception {
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
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/{id}", "42");
        MockMvcBuilders.standaloneSetup(userController)
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

    @Test
    void testGetUser2() throws Exception {
        when(userService.getUserById(anyInt())).thenReturn(Optional.empty());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/{id}", "42");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("User id does not exist!"));
    }

    @Test
    void testGetUser3() throws Exception {
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
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/{id}", "Uri Variables",
                "Uri Variables");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                // .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

    @Test
    void testGetUser4() throws Exception {
        when(userService.getUserById(anyInt())).thenThrow(new ObjectIdException("An error occurred"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/{id}", "42");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(500))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("An error occurred"));
    }
//     @Test
//     void testGetUser5() throws Exception {
//         when(userService.getUserById(anyInt())).thenThrow(new NumberFormatException("?"));
//         MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/{id}", "42");
//         ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController)
//                 .build()
//                 .perform(requestBuilder);
//         actualPerformResult.andExpect(MockMvcResultMatchers.status().is(406))
//                 .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
//                 .andExpect(MockMvcResultMatchers.content().string("Parameter is not a number!"));
//     }

    @Test
    void testSaveUser() throws Exception {
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
        String content = (new ObjectMapper()).writeValueAsString(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(405));
    }
    @Test
    void testUpdateUser() throws Exception {
        UpdateResult updateResult = mock(UpdateResult.class);
        when(updateResult.wasAcknowledged()).thenReturn(true);
        when(updateResult.getMatchedCount()).thenReturn(3L);

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
        when(userService.updateUserInfoById(anyInt(), Mockito.<User>any())).thenReturn(updateResult);

        User user2 = new User();
        user2.setActivity(new String[]{"Activity"});
        user2.setAge(1);
        user2.setEmail("jane.doe@example.org");
        user2.setFilm(new String[]{"Film"});
        user2.setGender("Gender");
        user2.setId("42");
        user2.setMusic(new String[]{"Music"});
        user2.setNationality("Nationality");
        user2.setPassword("iloveyou");
        user2.setUserId(1);
        user2.setUser_id(1);
        user2.setUsername("janedoe");
        user2.set_in_queue(true);
        String content = (new ObjectMapper()).writeValueAsString(user2);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/users/{id}", "42")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(userController)
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
    @Test
    void testUpdateUser2() throws Exception {
        UpdateResult updateResult = mock(UpdateResult.class);
        when(updateResult.wasAcknowledged()).thenReturn(true);
        when(updateResult.getMatchedCount()).thenReturn(3L);
        when(userService.getUserById(anyInt())).thenThrow(new ObjectIdException("An error occurred"));
        when(userService.updateUserInfoById(anyInt(), Mockito.<User>any())).thenReturn(updateResult);

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
        String content = (new ObjectMapper()).writeValueAsString(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/users/{id}", "42")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(500))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("An error occurred"));
    }
    @Test
    void testUpdateUser3() throws Exception {
        UpdateResult updateResult = mock(UpdateResult.class);
        when(updateResult.wasAcknowledged()).thenReturn(true);
        when(updateResult.getMatchedCount()).thenReturn(3L);
        when(userService.getUserById(anyInt())).thenThrow(new ParameterErrorStringException("An error occurred"));
        when(userService.updateUserInfoById(anyInt(), Mockito.<User>any())).thenReturn(updateResult);

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
        String content = (new ObjectMapper()).writeValueAsString(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/users/{id}", "42")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(406))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("An error occurred"));
    }
    @Test
    void testUpdateUser4() throws Exception {
        UpdateResult updateResult = mock(UpdateResult.class);
        when(updateResult.wasAcknowledged()).thenReturn(true);
        when(updateResult.getMatchedCount()).thenReturn(3L);
        when(userService.getUserById(anyInt())).thenThrow(new ParameterErrorNumberException("An error occurred"));
        when(userService.updateUserInfoById(anyInt(), Mockito.<User>any())).thenReturn(updateResult);

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
        String content = (new ObjectMapper()).writeValueAsString(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/users/{id}", "42")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("An error occurred"));
    }
    @Test
    void testUpdateUser5() throws Exception {
        UpdateResult updateResult = mock(UpdateResult.class);
        when(updateResult.wasAcknowledged()).thenReturn(true);
        when(updateResult.getMatchedCount()).thenReturn(3L);
        when(userService.getUserById(anyInt())).thenThrow(new NumberFormatException("?"));
        when(userService.updateUserInfoById(anyInt(), Mockito.<User>any())).thenReturn(updateResult);

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
        String content = (new ObjectMapper()).writeValueAsString(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/users/{id}", "42")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(406))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Parameter is not a number!"));
    }

    @Test
    void testUpdateUser6() throws Exception {
        UpdateResult updateResult = mock(UpdateResult.class);
        when(updateResult.wasAcknowledged()).thenReturn(false);
        when(updateResult.getMatchedCount()).thenReturn(3L);

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
        when(userService.updateUserInfoById(anyInt(), Mockito.<User>any())).thenReturn(updateResult);

        User user2 = new User();
        user2.setActivity(new String[]{"Activity"});
        user2.setAge(1);
        user2.setEmail("jane.doe@example.org");
        user2.setFilm(new String[]{"Film"});
        user2.setGender("Gender");
        user2.setId("42");
        user2.setMusic(new String[]{"Music"});
        user2.setNationality("Nationality");
        user2.setPassword("iloveyou");
        user2.setUserId(1);
        user2.setUser_id(1);
        user2.setUsername("janedoe");
        user2.set_in_queue(true);
        String content = (new ObjectMapper()).writeValueAsString(user2);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/users/{id}", "42")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(500))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Something wrong when saving the user!"));
    }
    @Test
    void testUpdateUser7() throws Exception {
        UpdateResult updateResult = mock(UpdateResult.class);
        when(updateResult.wasAcknowledged()).thenReturn(true);
        when(updateResult.getMatchedCount()).thenReturn(0L);

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
        when(userService.updateUserInfoById(anyInt(), Mockito.<User>any())).thenReturn(updateResult);

        User user2 = new User();
        user2.setActivity(new String[]{"Activity"});
        user2.setAge(1);
        user2.setEmail("jane.doe@example.org");
        user2.setFilm(new String[]{"Film"});
        user2.setGender("Gender");
        user2.setId("42");
        user2.setMusic(new String[]{"Music"});
        user2.setNationality("Nationality");
        user2.setPassword("iloveyou");
        user2.setUserId(1);
        user2.setUser_id(1);
        user2.setUsername("janedoe");
        user2.set_in_queue(true);
        String content = (new ObjectMapper()).writeValueAsString(user2);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/users/{id}", "42")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("User id does not exist!"));
    }

    @Test
    void testUpdateUser8() throws Exception {
        UpdateResult updateResult = mock(UpdateResult.class);
        when(updateResult.wasAcknowledged()).thenReturn(true);
        when(updateResult.getMatchedCount()).thenReturn(3L);

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
        when(userService.updateUserInfoById(anyInt(), Mockito.<User>any())).thenReturn(updateResult);

        User user2 = new User();
        user2.setActivity(new String[]{"Activity"});
        user2.setAge(1);
        user2.setEmail("jane.doe@example.org");
        user2.setFilm(new String[]{"Film"});
        user2.setGender("Gender");
        user2.setId("42");
        user2.setMusic(new String[]{"Music"});
        user2.setNationality("Nationality");
        user2.setPassword("iloveyou");
        user2.setUserId(1);
        user2.setUser_id(1);
        user2.setUsername("janedoe");
        user2.set_in_queue(true);
        String content = (new ObjectMapper()).writeValueAsString(user2);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/users/{id}", "Uri Variables", "Uri Variables")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(406))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Parameter is not a number!"));
    }

    @Test
    void testUpdateUser9() throws Exception {
        User new_user = new User();
        Assertions.assertThrows(ParameterErrorStringException.class, () -> userController.updateUser("", new_user));
    }
}

