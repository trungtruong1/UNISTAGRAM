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

        assertEquals(user1, user2);
        assertEquals(user1.toString(), user2.toString());
        assertEquals(user1.hashCode(), user2.hashCode());

        assertNotEquals(user1, user3);
        assertNotEquals(user1.toString(), user3.toString());
        assertNotEquals(user1.hashCode(), user3.hashCode());
    }
}

