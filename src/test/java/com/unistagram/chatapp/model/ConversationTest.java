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


        assertEquals(conversation1, conversation2);
        assertEquals(conversation1.toString(), conversation2.toString());
        assertEquals(conversation1.hashCode(), conversation2.hashCode());

        assertNotEquals(conversation1,conversation3);
        assertNotEquals(conversation1.toString(), conversation3.toString());
        assertNotEquals(conversation1.hashCode(), conversation3.hashCode());
    }

}

