package com.unistagram.userapp.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class UserTest {

    @Test
    void testUserModel() {
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();

        user1.setUser_id(1);
        user2.setUser_id(1);
        user3.setUser_id(2);

        assertEquals(1, user1.getUser_id());
        assertEquals(1, user2.getUser_id());
        assertEquals(2, user3.getUser_id());

    }
}

