package com.unistagram.chatapp.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ConversationTest {
    @Test
    public void testConversationModel() {
        Conversation conversation1 = new Conversation("JohnWick", "Jack", Conversation.Status.ONGOING);
        Conversation conversation2 = new Conversation("JohnWick", "Jack", Conversation.Status.ONGOING);
        Conversation conversation3 = new Conversation("JohnWick", "Jack", Conversation.Status.TERMINATED);

        assertEquals("JohnWick", conversation1.getClient1());
    }

}

