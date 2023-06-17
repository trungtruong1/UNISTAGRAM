package com.unistagram.matchapp.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.unistagram.chatapp.model.Conversation;
import com.unistagram.chatapp.service.ConversationService;
import com.unistagram.userapp.model.User;
import com.unistagram.userapp.service.UserService;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {MatchingServiceImpl.class})
@ExtendWith(SpringExtension.class)
class MatchingServiceImplTest {
    @MockBean
    private ConversationService conversationService;

    @Autowired
    private MatchingServiceImpl matchingServiceImpl;

    @MockBean
    private UserService userService;

    @Test
    void testJoinQueue() {
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
        when(userService.updateUserInfoById(anyInt(), Mockito.<User>any())).thenReturn(null);
        when(userService.getOthersInQueue(Mockito.<String>any())).thenReturn(new ArrayList<>());
        when(userService.getUserById(anyInt())).thenReturn(ofResult);

        User client = new User();
        client.setActivity(new String[]{"Activity"});
        client.setAge(1);
        client.setEmail("jane.doe@example.org");
        client.setFilm(new String[]{"Film"});
        client.setGender("Gender");
        client.setId("42");
        client.setMusic(new String[]{"Music"});
        client.setNationality("Nationality");
        client.setPassword("iloveyou");
        client.setUserId(1);
        client.setUser_id(1);
        client.setUsername("janedoe");
        client.set_in_queue(true);
        matchingServiceImpl.joinQueue(client);
        verify(userService).updateUserInfoById(anyInt(), Mockito.<User>any());
        verify(userService).getOthersInQueue(Mockito.<String>any());
        verify(userService).getUserById(anyInt());
    }
    @Test
    void testJoinQueue2() {
        when(conversationService.save(Mockito.<Conversation>any())).thenReturn("Save");

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

        ArrayList<User> userList = new ArrayList<>();
        userList.add(user2);
        when(userService.updateUserInfoById(anyInt(), Mockito.<User>any())).thenReturn(null);
        when(userService.getOthersInQueue(Mockito.<String>any())).thenReturn(userList);
        when(userService.getUserById(anyInt())).thenReturn(ofResult);

        User client = new User();
        client.setActivity(new String[]{"Activity"});
        client.setAge(1);
        client.setEmail("jane.doe@example.org");
        client.setFilm(new String[]{"Film"});
        client.setGender("Gender");
        client.setId("42");
        client.setMusic(new String[]{"Music"});
        client.setNationality("Nationality");
        client.setPassword("iloveyou");
        client.setUserId(1);
        client.setUser_id(1);
        client.setUsername("janedoe");
        client.set_in_queue(true);
        matchingServiceImpl.joinQueue(client);
        verify(conversationService).save(Mockito.<Conversation>any());
        verify(userService, atLeast(1)).updateUserInfoById(anyInt(), Mockito.<User>any());
        verify(userService).getOthersInQueue(Mockito.<String>any());
        verify(userService).getUserById(anyInt());
    }
    @Test
    void testJoinQueue3() {
        when(conversationService.save(Mockito.<Conversation>any())).thenReturn("Save");

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
        User user2 = mock(User.class);
        when(user2.getUser_id()).thenReturn(1);
        when(user2.getId()).thenReturn("42");
        doNothing().when(user2).outQueue();
        doNothing().when(user2).setActivity(Mockito.<String[]>any());
        doNothing().when(user2).setAge(anyInt());
        doNothing().when(user2).setEmail(Mockito.<String>any());
        doNothing().when(user2).setFilm(Mockito.<String[]>any());
        doNothing().when(user2).setGender(Mockito.<String>any());
        doNothing().when(user2).setId(Mockito.<String>any());
        doNothing().when(user2).setMusic(Mockito.<String[]>any());
        doNothing().when(user2).setNationality(Mockito.<String>any());
        doNothing().when(user2).setPassword(Mockito.<String>any());
        doNothing().when(user2).setUserId(anyInt());
        doNothing().when(user2).setUser_id(anyInt());
        doNothing().when(user2).setUsername(Mockito.<String>any());
        doNothing().when(user2).set_in_queue(anyBoolean());
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

        ArrayList<User> userList = new ArrayList<>();
        userList.add(user2);
        when(userService.updateUserInfoById(anyInt(), Mockito.<User>any())).thenReturn(null);
        when(userService.getOthersInQueue(Mockito.<String>any())).thenReturn(userList);
        when(userService.getUserById(anyInt())).thenReturn(ofResult);

        User client = new User();
        client.setActivity(new String[]{"Activity"});
        client.setAge(1);
        client.setEmail("jane.doe@example.org");
        client.setFilm(new String[]{"Film"});
        client.setGender("Gender");
        client.setId("42");
        client.setMusic(new String[]{"Music"});
        client.setNationality("Nationality");
        client.setPassword("iloveyou");
        client.setUserId(1);
        client.setUser_id(1);
        client.setUsername("janedoe");
        client.set_in_queue(true);
        matchingServiceImpl.joinQueue(client);
        verify(conversationService).save(Mockito.<Conversation>any());
        verify(userService, atLeast(1)).updateUserInfoById(anyInt(), Mockito.<User>any());
        verify(userService).getOthersInQueue(Mockito.<String>any());
        verify(userService).getUserById(anyInt());
        verify(user2).getUser_id();
        verify(user2).getId();
        verify(user2).outQueue();
        verify(user2).setActivity(Mockito.<String[]>any());
        verify(user2).setAge(anyInt());
        verify(user2).setEmail(Mockito.<String>any());
        verify(user2).setFilm(Mockito.<String[]>any());
        verify(user2).setGender(Mockito.<String>any());
        verify(user2).setId(Mockito.<String>any());
        verify(user2).setMusic(Mockito.<String[]>any());
        verify(user2).setNationality(Mockito.<String>any());
        verify(user2).setPassword(Mockito.<String>any());
        verify(user2).setUserId(anyInt());
        verify(user2).setUser_id(anyInt());
        verify(user2).setUsername(Mockito.<String>any());
        verify(user2).set_in_queue(anyBoolean());
    }

    @Test
    void testJoinQueue4() {
        when(conversationService.save(Mockito.<Conversation>any())).thenReturn("Save");
        User user = mock(User.class);
        when(user.getUser_id()).thenReturn(1);
        when(user.getId()).thenReturn("42");
        doNothing().when(user).joinQueue();
        doNothing().when(user).outQueue();
        doNothing().when(user).setActivity(Mockito.<String[]>any());
        doNothing().when(user).setAge(anyInt());
        doNothing().when(user).setEmail(Mockito.<String>any());
        doNothing().when(user).setFilm(Mockito.<String[]>any());
        doNothing().when(user).setGender(Mockito.<String>any());
        doNothing().when(user).setId(Mockito.<String>any());
        doNothing().when(user).setMusic(Mockito.<String[]>any());
        doNothing().when(user).setNationality(Mockito.<String>any());
        doNothing().when(user).setPassword(Mockito.<String>any());
        doNothing().when(user).setUserId(anyInt());
        doNothing().when(user).setUser_id(anyInt());
        doNothing().when(user).setUsername(Mockito.<String>any());
        doNothing().when(user).set_in_queue(anyBoolean());
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
        User user2 = mock(User.class);
        when(user2.getUser_id()).thenReturn(1);
        when(user2.getId()).thenReturn("42");
        doNothing().when(user2).outQueue();
        doNothing().when(user2).setActivity(Mockito.<String[]>any());
        doNothing().when(user2).setAge(anyInt());
        doNothing().when(user2).setEmail(Mockito.<String>any());
        doNothing().when(user2).setFilm(Mockito.<String[]>any());
        doNothing().when(user2).setGender(Mockito.<String>any());
        doNothing().when(user2).setId(Mockito.<String>any());
        doNothing().when(user2).setMusic(Mockito.<String[]>any());
        doNothing().when(user2).setNationality(Mockito.<String>any());
        doNothing().when(user2).setPassword(Mockito.<String>any());
        doNothing().when(user2).setUserId(anyInt());
        doNothing().when(user2).setUser_id(anyInt());
        doNothing().when(user2).setUsername(Mockito.<String>any());
        doNothing().when(user2).set_in_queue(anyBoolean());
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

        ArrayList<User> userList = new ArrayList<>();
        userList.add(user2);
        when(userService.updateUserInfoById(anyInt(), Mockito.<User>any())).thenReturn(null);
        when(userService.getOthersInQueue(Mockito.<String>any())).thenReturn(userList);
        when(userService.getUserById(anyInt())).thenReturn(ofResult);

        User client = new User();
        client.setActivity(new String[]{"Activity"});
        client.setAge(1);
        client.setEmail("jane.doe@example.org");
        client.setFilm(new String[]{"Film"});
        client.setGender("Gender");
        client.setId("42");
        client.setMusic(new String[]{"Music"});
        client.setNationality("Nationality");
        client.setPassword("iloveyou");
        client.setUserId(1);
        client.setUser_id(1);
        client.setUsername("janedoe");
        client.set_in_queue(true);
        matchingServiceImpl.joinQueue(client);
        verify(conversationService).save(Mockito.<Conversation>any());
        verify(userService, atLeast(1)).updateUserInfoById(anyInt(), Mockito.<User>any());
        verify(userService).getOthersInQueue(Mockito.<String>any());
        verify(userService).getUserById(anyInt());
        verify(user2).getUser_id();
        verify(user2).getId();
        verify(user2).outQueue();
        verify(user2).setActivity(Mockito.<String[]>any());
        verify(user2).setAge(anyInt());
        verify(user2).setEmail(Mockito.<String>any());
        verify(user2).setFilm(Mockito.<String[]>any());
        verify(user2).setGender(Mockito.<String>any());
        verify(user2).setId(Mockito.<String>any());
        verify(user2).setMusic(Mockito.<String[]>any());
        verify(user2).setNationality(Mockito.<String>any());
        verify(user2).setPassword(Mockito.<String>any());
        verify(user2).setUserId(anyInt());
        verify(user2).setUser_id(anyInt());
        verify(user2).setUsername(Mockito.<String>any());
        verify(user2).set_in_queue(anyBoolean());
        verify(user).getUser_id();
        verify(user, atLeast(1)).getId();
        verify(user).joinQueue();
        verify(user).outQueue();
        verify(user).setActivity(Mockito.<String[]>any());
        verify(user).setAge(anyInt());
        verify(user).setEmail(Mockito.<String>any());
        verify(user).setFilm(Mockito.<String[]>any());
        verify(user).setGender(Mockito.<String>any());
        verify(user).setId(Mockito.<String>any());
        verify(user).setMusic(Mockito.<String[]>any());
        verify(user).setNationality(Mockito.<String>any());
        verify(user).setPassword(Mockito.<String>any());
        verify(user).setUserId(anyInt());
        verify(user).setUser_id(anyInt());
        verify(user).setUsername(Mockito.<String>any());
        verify(user).set_in_queue(anyBoolean());
    }

    @Test
    void testIsWaiting() {
        User client = new User();
        client.setActivity(new String[]{"Activity"});
        client.setAge(1);
        client.setEmail("jane.doe@example.org");
        client.setFilm(new String[]{"Film"});
        client.setGender("Gender");
        client.setId("42");
        client.setMusic(new String[]{"Music"});
        client.setNationality("Nationality");
        client.setPassword("iloveyou");
        client.setUserId(1);
        client.setUser_id(1);
        client.setUsername("janedoe");
        client.set_in_queue(true);
        assertTrue(matchingServiceImpl.isWaiting(client));
    }

    @Test
    void testIsWaiting2() {
        User client = mock(User.class);
        when(client.getIs_in_queue()).thenReturn(true);
        doNothing().when(client).setActivity(Mockito.<String[]>any());
        doNothing().when(client).setAge(anyInt());
        doNothing().when(client).setEmail(Mockito.<String>any());
        doNothing().when(client).setFilm(Mockito.<String[]>any());
        doNothing().when(client).setGender(Mockito.<String>any());
        doNothing().when(client).setId(Mockito.<String>any());
        doNothing().when(client).setMusic(Mockito.<String[]>any());
        doNothing().when(client).setNationality(Mockito.<String>any());
        doNothing().when(client).setPassword(Mockito.<String>any());
        doNothing().when(client).setUserId(anyInt());
        doNothing().when(client).setUser_id(anyInt());
        doNothing().when(client).setUsername(Mockito.<String>any());
        doNothing().when(client).set_in_queue(anyBoolean());
        client.setActivity(new String[]{"Activity"});
        client.setAge(1);
        client.setEmail("jane.doe@example.org");
        client.setFilm(new String[]{"Film"});
        client.setGender("Gender");
        client.setId("42");
        client.setMusic(new String[]{"Music"});
        client.setNationality("Nationality");
        client.setPassword("iloveyou");
        client.setUserId(1);
        client.setUser_id(1);
        client.setUsername("janedoe");
        client.set_in_queue(true);
        assertTrue(matchingServiceImpl.isWaiting(client));
        verify(client).getIs_in_queue();
        verify(client).setActivity(Mockito.<String[]>any());
        verify(client).setAge(anyInt());
        verify(client).setEmail(Mockito.<String>any());
        verify(client).setFilm(Mockito.<String[]>any());
        verify(client).setGender(Mockito.<String>any());
        verify(client).setId(Mockito.<String>any());
        verify(client).setMusic(Mockito.<String[]>any());
        verify(client).setNationality(Mockito.<String>any());
        verify(client).setPassword(Mockito.<String>any());
        verify(client).setUserId(anyInt());
        verify(client).setUser_id(anyInt());
        verify(client).setUsername(Mockito.<String>any());
        verify(client).set_in_queue(anyBoolean());
    }

    @Test
    void testIsWaiting3() {
        User client = mock(User.class);
        when(client.getIs_in_queue()).thenReturn(false);
        doNothing().when(client).setActivity(Mockito.<String[]>any());
        doNothing().when(client).setAge(anyInt());
        doNothing().when(client).setEmail(Mockito.<String>any());
        doNothing().when(client).setFilm(Mockito.<String[]>any());
        doNothing().when(client).setGender(Mockito.<String>any());
        doNothing().when(client).setId(Mockito.<String>any());
        doNothing().when(client).setMusic(Mockito.<String[]>any());
        doNothing().when(client).setNationality(Mockito.<String>any());
        doNothing().when(client).setPassword(Mockito.<String>any());
        doNothing().when(client).setUserId(anyInt());
        doNothing().when(client).setUser_id(anyInt());
        doNothing().when(client).setUsername(Mockito.<String>any());
        doNothing().when(client).set_in_queue(anyBoolean());
        client.setActivity(new String[]{"Activity"});
        client.setAge(1);
        client.setEmail("jane.doe@example.org");
        client.setFilm(new String[]{"Film"});
        client.setGender("Gender");
        client.setId("42");
        client.setMusic(new String[]{"Music"});
        client.setNationality("Nationality");
        client.setPassword("iloveyou");
        client.setUserId(1);
        client.setUser_id(1);
        client.setUsername("janedoe");
        client.set_in_queue(true);
        assertFalse(matchingServiceImpl.isWaiting(client));
        verify(client).getIs_in_queue();
        verify(client).setActivity(Mockito.<String[]>any());
        verify(client).setAge(anyInt());
        verify(client).setEmail(Mockito.<String>any());
        verify(client).setFilm(Mockito.<String[]>any());
        verify(client).setGender(Mockito.<String>any());
        verify(client).setId(Mockito.<String>any());
        verify(client).setMusic(Mockito.<String[]>any());
        verify(client).setNationality(Mockito.<String>any());
        verify(client).setPassword(Mockito.<String>any());
        verify(client).setUserId(anyInt());
        verify(client).setUser_id(anyInt());
        verify(client).setUsername(Mockito.<String>any());
        verify(client).set_in_queue(anyBoolean());
    }

    @Test
    void testMatch() {
        when(userService.getOthersInQueue(Mockito.<String>any())).thenReturn(new ArrayList<>());

        User client = new User();
        client.setActivity(new String[]{"Activity"});
        client.setAge(1);
        client.setEmail("jane.doe@example.org");
        client.setFilm(new String[]{"Film"});
        client.setGender("Gender");
        client.setId("42");
        client.setMusic(new String[]{"Music"});
        client.setNationality("Nationality");
        client.setPassword("iloveyou");
        client.setUserId(1);
        client.setUser_id(1);
        client.setUsername("janedoe");
        client.set_in_queue(true);
        assertTrue(matchingServiceImpl.match(client) == null);
        verify(userService).getOthersInQueue(Mockito.<String>any());
    }

    @Test
    void testMatch2() {
        when(conversationService.save(Mockito.<Conversation>any())).thenReturn("Save");

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

        ArrayList<User> userList = new ArrayList<>();
        userList.add(user);
        when(userService.updateUserInfoById(anyInt(), Mockito.<User>any())).thenReturn(null);
        when(userService.getOthersInQueue(Mockito.<String>any())).thenReturn(userList);

        User client = new User();
        client.setActivity(new String[]{"Activity"});
        client.setAge(1);
        client.setEmail("jane.doe@example.org");
        client.setFilm(new String[]{"Film"});
        client.setGender("Gender");
        client.setId("42");
        client.setMusic(new String[]{"Music"});
        client.setNationality("Nationality");
        client.setPassword("iloveyou");
        client.setUserId(1);
        client.setUser_id(1);
        client.setUsername("janedoe");
        client.set_in_queue(true);
        assertTrue(matchingServiceImpl.match(client).length() > 0);
        verify(conversationService).save(Mockito.<Conversation>any());
        verify(userService, atLeast(1)).updateUserInfoById(anyInt(), Mockito.<User>any());
        verify(userService).getOthersInQueue(Mockito.<String>any());
        assertFalse(client.getIs_in_queue());
    }

    @Test
    void testMatch3() {
        when(conversationService.save(Mockito.<Conversation>any())).thenReturn("Save");
        User user = mock(User.class);
        when(user.getUser_id()).thenReturn(1);
        when(user.getId()).thenReturn("42");
        doNothing().when(user).outQueue();
        doNothing().when(user).setActivity(Mockito.<String[]>any());
        doNothing().when(user).setAge(anyInt());
        doNothing().when(user).setEmail(Mockito.<String>any());
        doNothing().when(user).setFilm(Mockito.<String[]>any());
        doNothing().when(user).setGender(Mockito.<String>any());
        doNothing().when(user).setId(Mockito.<String>any());
        doNothing().when(user).setMusic(Mockito.<String[]>any());
        doNothing().when(user).setNationality(Mockito.<String>any());
        doNothing().when(user).setPassword(Mockito.<String>any());
        doNothing().when(user).setUserId(anyInt());
        doNothing().when(user).setUser_id(anyInt());
        doNothing().when(user).setUsername(Mockito.<String>any());
        doNothing().when(user).set_in_queue(anyBoolean());
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

        ArrayList<User> userList = new ArrayList<>();
        userList.add(user);
        when(userService.updateUserInfoById(anyInt(), Mockito.<User>any())).thenReturn(null);
        when(userService.getOthersInQueue(Mockito.<String>any())).thenReturn(userList);

        User client = new User();
        client.setActivity(new String[]{"Activity"});
        client.setAge(1);
        client.setEmail("jane.doe@example.org");
        client.setFilm(new String[]{"Film"});
        client.setGender("Gender");
        client.setId("42");
        client.setMusic(new String[]{"Music"});
        client.setNationality("Nationality");
        client.setPassword("iloveyou");
        client.setUserId(1);
        client.setUser_id(1);
        client.setUsername("janedoe");
        client.set_in_queue(true);
        assertTrue(matchingServiceImpl.match(client).length() > 0);
        verify(conversationService).save(Mockito.<Conversation>any());
        verify(userService, atLeast(1)).updateUserInfoById(anyInt(), Mockito.<User>any());
        verify(userService).getOthersInQueue(Mockito.<String>any());
        verify(user).getUser_id();
        verify(user).getId();
        verify(user).outQueue();
        verify(user).setActivity(Mockito.<String[]>any());
        verify(user).setAge(anyInt());
        verify(user).setEmail(Mockito.<String>any());
        verify(user).setFilm(Mockito.<String[]>any());
        verify(user).setGender(Mockito.<String>any());
        verify(user).setId(Mockito.<String>any());
        verify(user).setMusic(Mockito.<String[]>any());
        verify(user).setNationality(Mockito.<String>any());
        verify(user).setPassword(Mockito.<String>any());
        verify(user).setUserId(anyInt());
        verify(user).setUser_id(anyInt());
        verify(user).setUsername(Mockito.<String>any());
        verify(user).set_in_queue(anyBoolean());
        assertFalse(client.getIs_in_queue());
    }

    @Test
    void testMatch4() {
        when(conversationService.save(Mockito.<Conversation>any())).thenReturn("Save");
        User user = mock(User.class);
        when(user.getUser_id()).thenReturn(1);
        when(user.getId()).thenReturn("42");
        doNothing().when(user).outQueue();
        doNothing().when(user).setActivity(Mockito.<String[]>any());
        doNothing().when(user).setAge(anyInt());
        doNothing().when(user).setEmail(Mockito.<String>any());
        doNothing().when(user).setFilm(Mockito.<String[]>any());
        doNothing().when(user).setGender(Mockito.<String>any());
        doNothing().when(user).setId(Mockito.<String>any());
        doNothing().when(user).setMusic(Mockito.<String[]>any());
        doNothing().when(user).setNationality(Mockito.<String>any());
        doNothing().when(user).setPassword(Mockito.<String>any());
        doNothing().when(user).setUserId(anyInt());
        doNothing().when(user).setUser_id(anyInt());
        doNothing().when(user).setUsername(Mockito.<String>any());
        doNothing().when(user).set_in_queue(anyBoolean());
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

        ArrayList<User> userList = new ArrayList<>();
        userList.add(user);
        when(userService.updateUserInfoById(anyInt(), Mockito.<User>any())).thenReturn(null);
        when(userService.getOthersInQueue(Mockito.<String>any())).thenReturn(userList);
        User client = mock(User.class);
        when(client.getUser_id()).thenReturn(1);
        when(client.getId()).thenReturn("42");
        doNothing().when(client).outQueue();
        doNothing().when(client).setActivity(Mockito.<String[]>any());
        doNothing().when(client).setAge(anyInt());
        doNothing().when(client).setEmail(Mockito.<String>any());
        doNothing().when(client).setFilm(Mockito.<String[]>any());
        doNothing().when(client).setGender(Mockito.<String>any());
        doNothing().when(client).setId(Mockito.<String>any());
        doNothing().when(client).setMusic(Mockito.<String[]>any());
        doNothing().when(client).setNationality(Mockito.<String>any());
        doNothing().when(client).setPassword(Mockito.<String>any());
        doNothing().when(client).setUserId(anyInt());
        doNothing().when(client).setUser_id(anyInt());
        doNothing().when(client).setUsername(Mockito.<String>any());
        doNothing().when(client).set_in_queue(anyBoolean());
        client.setActivity(new String[]{"Activity"});
        client.setAge(1);
        client.setEmail("jane.doe@example.org");
        client.setFilm(new String[]{"Film"});
        client.setGender("Gender");
        client.setId("42");
        client.setMusic(new String[]{"Music"});
        client.setNationality("Nationality");
        client.setPassword("iloveyou");
        client.setUserId(1);
        client.setUser_id(1);
        client.setUsername("janedoe");
        client.set_in_queue(true);
        assertTrue(matchingServiceImpl.match(client).length() > 0);
        verify(conversationService).save(Mockito.<Conversation>any());
        verify(userService, atLeast(1)).updateUserInfoById(anyInt(), Mockito.<User>any());
        verify(userService).getOthersInQueue(Mockito.<String>any());
        verify(user).getUser_id();
        verify(user).getId();
        verify(user).outQueue();
        verify(user).setActivity(Mockito.<String[]>any());
        verify(user).setAge(anyInt());
        verify(user).setEmail(Mockito.<String>any());
        verify(user).setFilm(Mockito.<String[]>any());
        verify(user).setGender(Mockito.<String>any());
        verify(user).setId(Mockito.<String>any());
        verify(user).setMusic(Mockito.<String[]>any());
        verify(user).setNationality(Mockito.<String>any());
        verify(user).setPassword(Mockito.<String>any());
        verify(user).setUserId(anyInt());
        verify(user).setUser_id(anyInt());
        verify(user).setUsername(Mockito.<String>any());
        verify(user).set_in_queue(anyBoolean());
        verify(client).getUser_id();
        verify(client, atLeast(1)).getId();
        verify(client).outQueue();
        verify(client).setActivity(Mockito.<String[]>any());
        verify(client).setAge(anyInt());
        verify(client).setEmail(Mockito.<String>any());
        verify(client).setFilm(Mockito.<String[]>any());
        verify(client).setGender(Mockito.<String>any());
        verify(client).setId(Mockito.<String>any());
        verify(client).setMusic(Mockito.<String[]>any());
        verify(client).setNationality(Mockito.<String>any());
        verify(client).setPassword(Mockito.<String>any());
        verify(client).setUserId(anyInt());
        verify(client).setUser_id(anyInt());
        verify(client).setUsername(Mockito.<String>any());
        verify(client).set_in_queue(anyBoolean());
    }
}

