package com.unistagram.userapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.unistagram.userapp.model.User;
import com.unistagram.userapp.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
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

@ContextConfiguration(classes = {UserServiceImpl.class})
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {
    @MockBean
    private MongoTemplate mongoTemplate;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;


    @Test
    void testSave() {
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
        when(userRepository.save(Mockito.<User>any())).thenReturn(user);

        User user2 = new User();
        user2.setActivity(new String[]{"user_id"});
        user2.setAge(1);
        user2.setEmail("jane.doe@example.org");
        user2.setFilm(new String[]{"user_id"});
        user2.setGender("user_id");
        user2.setId("42");
        user2.setMusic(new String[]{"user_id"});
        user2.setNationality("user_id");
        user2.setPassword("iloveyou");
        user2.setUserId(1);
        user2.setUser_id(1);
        user2.setUsername("janedoe");
        user2.set_in_queue(true);

        ArrayList<User> userList = new ArrayList<>();
        userList.add(user2);
        when(mongoTemplate.find(Mockito.<Query>any(), Mockito.<Class<User>>any())).thenReturn(userList);

        User user3 = new User();
        user3.setActivity(new String[]{"Activity"});
        user3.setAge(1);
        user3.setEmail("jane.doe@example.org");
        user3.setFilm(new String[]{"Film"});
        user3.setGender("Gender");
        user3.setId("42");
        user3.setMusic(new String[]{"Music"});
        user3.setNationality("Nationality");
        user3.setPassword("iloveyou");
        user3.setUserId(1);
        user3.setUser_id(1);
        user3.setUsername("janedoe");
        user3.set_in_queue(true);
        assertEquals("42", userServiceImpl.save(user3));
        verify(userRepository).save(Mockito.<User>any());
        verify(mongoTemplate).find(Mockito.<Query>any(), Mockito.<Class<User>>any());
        assertEquals(2, user3.getUser_id());
    }

    @Test
    void testGetUser() {
        ArrayList<User> userList = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(userList);
        List<User> actualUser = userServiceImpl.getUser();
        assertSame(userList, actualUser);
        assertTrue(actualUser.isEmpty());
        verify(userRepository).findAll();
    }

    @Test
    void testGetOthersInQueue() {
        ArrayList<User> userList = new ArrayList<>();
        when(mongoTemplate.find(Mockito.<Query>any(), Mockito.<Class<User>>any())).thenReturn(userList);
        List<User> actualOthersInQueue = userServiceImpl.getOthersInQueue("Client id");
        assertSame(userList, actualOthersInQueue);
        assertTrue(actualOthersInQueue.isEmpty());
        verify(mongoTemplate).find(Mockito.<Query>any(), Mockito.<Class<User>>any());
    }
    @Test
    void testGetUserById() {
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
        when(mongoTemplate.findOne(Mockito.<Query>any(), Mockito.<Class<User>>any())).thenReturn(user);
        assertTrue(userServiceImpl.getUserById(1).isPresent());
        verify(mongoTemplate).findOne(Mockito.<Query>any(), Mockito.<Class<User>>any());
    }

    @Test
    void testGetUserById2() {
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
        when(userRepository.findById(Mockito.<String>any())).thenReturn(ofResult);
        Optional<User> actualUserById = userServiceImpl.getUserById("42");
        assertSame(ofResult, actualUserById);
        assertTrue(actualUserById.isPresent());
        verify(userRepository).findById(Mockito.<String>any());
    }
    @Test
    void testUpdateUserInfoById() {
        when(mongoTemplate.updateFirst(Mockito.<Query>any(), Mockito.<UpdateDefinition>any(),
                Mockito.<Class<Object>>any())).thenReturn(null);

        User updated_user = new User();
        updated_user.setActivity(new String[]{"Activity"});
        updated_user.setAge(1);
        updated_user.setEmail("jane.doe@example.org");
        updated_user.setFilm(new String[]{"Film"});
        updated_user.setGender("Gender");
        updated_user.setId("42");
        updated_user.setMusic(new String[]{"Music"});
        updated_user.setNationality("Nationality");
        updated_user.setPassword("iloveyou");
        updated_user.setUserId(1);
        updated_user.setUser_id(1);
        updated_user.setUsername("janedoe");
        updated_user.set_in_queue(true);
        assertNull(userServiceImpl.updateUserInfoById(1, updated_user));
        verify(mongoTemplate).updateFirst(Mockito.<Query>any(), Mockito.<UpdateDefinition>any(),
                Mockito.<Class<Object>>any());
    }
    @Test
    void testGetMaxId() {
        User user = new User();
        user.setActivity(new String[]{"user_id"});
        user.setAge(1);
        user.setEmail("jane.doe@example.org");
        user.setFilm(new String[]{"user_id"});
        user.setGender("user_id");
        user.setId("42");
        user.setMusic(new String[]{"user_id"});
        user.setNationality("user_id");
        user.setPassword("iloveyou");
        user.setUserId(1);
        user.setUser_id(1);
        user.setUsername("janedoe");
        user.set_in_queue(true);

        ArrayList<User> userList = new ArrayList<>();
        userList.add(user);
        when(mongoTemplate.find(Mockito.<Query>any(), Mockito.<Class<User>>any())).thenReturn(userList);
        assertEquals(1, userServiceImpl.getMaxId());
        verify(mongoTemplate).find(Mockito.<Query>any(), Mockito.<Class<User>>any());
    }
}

