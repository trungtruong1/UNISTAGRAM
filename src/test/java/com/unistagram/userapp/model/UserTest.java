package com.unistagram.userapp.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class UserTest {
    /**
     * Methods under test:
     *
     * <ul>
     *   <li>default or parameterless constructor of {@link User}
     *   <li>{@link User#setUserId(int)}
     *   <li>{@link User#joinQueue()}
     *   <li>{@link User#outQueue()}
     *   <li>{@link User#getActivity()}
     *   <li>{@link User#getAge()}
     *   <li>{@link User#getEmail()}
     *   <li>{@link User#getFilm()}
     *   <li>{@link User#getGender()}
     *   <li>{@link User#getId()}
     *   <li>{@link User#getIs_in_queue()}
     *   <li>{@link User#getMusic()}
     *   <li>{@link User#getNationality()}
     *   <li>{@link User#getUser_id()}
     *   <li>{@link User#getUsername()}
     * </ul>
     */
    @Test
    void testConstructor() {
        User actualUser = new User();
        actualUser.setUserId(1);
        actualUser.joinQueue();
        actualUser.outQueue();
        assertNull(actualUser.getActivity());
        assertEquals(0, actualUser.getAge());
        assertNull(actualUser.getEmail());
        assertNull(actualUser.getFilm());
        assertNull(actualUser.getGender());
        assertNull(actualUser.getId());
        assertFalse(actualUser.getIs_in_queue());
        assertNull(actualUser.getMusic());
        assertNull(actualUser.getNationality());
        assertEquals(1, actualUser.getUser_id());
        assertNull(actualUser.getUsername());
    }
}

