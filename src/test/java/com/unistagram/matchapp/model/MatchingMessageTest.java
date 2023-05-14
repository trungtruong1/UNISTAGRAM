package com.unistagram.matchapp.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MatchingMessageTest {
    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link MatchingMessage#MatchingMessage()}
     *   <li>{@link MatchingMessage#setName(String)}
     *   <li>{@link MatchingMessage#getName()}
     * </ul>
     */
    @Test
    void testConstructor() {
        MatchingMessage actualMatchingMessage = new MatchingMessage();
        actualMatchingMessage.setName("Name");
        assertEquals("Name", actualMatchingMessage.getName());
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link MatchingMessage#MatchingMessage(String)}
     *   <li>{@link MatchingMessage#setName(String)}
     *   <li>{@link MatchingMessage#getName()}
     * </ul>
     */
    @Test
    void testConstructor2() {
        MatchingMessage actualMatchingMessage = new MatchingMessage("Name");
        actualMatchingMessage.setName("Name");
        assertEquals("Name", actualMatchingMessage.getName());
    }
}

