package com.unistagram.matchapp.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MatchingMessageTest {

    @Test
    void testConstructor() {
        MatchingMessage actualMatchingMessage = new MatchingMessage();
        actualMatchingMessage.setContent("Content");
        assertEquals("Content", actualMatchingMessage.getContent());
    }
    @Test
    void testConstructor2() {
        MatchingMessage actualMatchingMessage = new MatchingMessage("Content");
        actualMatchingMessage.setContent("Content");
        assertEquals("Content", actualMatchingMessage.getContent());
    }
}

