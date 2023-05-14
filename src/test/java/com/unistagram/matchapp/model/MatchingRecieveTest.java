package com.unistagram.matchapp.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class MatchingRecieveTest {
    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link MatchingRecieve#MatchingRecieve()}
     *   <li>{@link MatchingRecieve#getContent()}
     * </ul>
     */
    @Test
    void testConstructor() {
        assertNull((new MatchingRecieve()).getContent());
        assertEquals("Not all who wander are lost", (new MatchingRecieve("Not all who wander are lost")).getContent());
    }
}

