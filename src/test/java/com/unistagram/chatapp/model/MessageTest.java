package com.unistagram.chatapp.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class MessageTest {
    /**
     * Method under test: {@link Message#Message(String, String, String, String)}
     */
    @Test
    void testConstructor() {
        Message actualMessage = new Message("Conversation", "Sender", "Receiver", "Not all who wander are lost");

        assertEquals("Not all who wander are lost", actualMessage.getContent());
        assertEquals("Sender", actualMessage.getSender());
        assertEquals("Receiver", actualMessage.getReceiver());
        assertNull(actualMessage.getId());
        assertEquals("Conversation", actualMessage.getConversation());
    }
}

