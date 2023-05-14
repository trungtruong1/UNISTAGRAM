package com.unistagram.matchapp.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MatchingMessageTest {

    @Test
    void testConstructor() {
        MatchingMessage actualMatchingMessage = new MatchingMessage();
        actualMatchingMessage.setName("Name");
        assertEquals("Name", actualMatchingMessage.getName());
    }
    @Test
    void testConstructor2() {
        MatchingMessage actualMatchingMessage = new MatchingMessage("Name");
        actualMatchingMessage.setName("Name");
        assertEquals("Name", actualMatchingMessage.getName());
    }
}

