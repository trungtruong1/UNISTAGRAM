package com.unistagram.matchapp.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class MatchingRecieveTest {
    @Test
    void testConstructor() {
        assertNull((new MatchingRecieve()).getUserId());
        assertEquals("Not all who wander are lost", (new MatchingRecieve("Not all who wander are lost", false)).getUserId());
    }
}

