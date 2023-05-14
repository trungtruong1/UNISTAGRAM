package com.unistagram.chatapp.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class ConversationTest {
    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link Conversation#Conversation()}
     *   <li>{@link Conversation#getClient1()}
     *   <li>{@link Conversation#getClient2()}
     *   <li>{@link Conversation#getId()}
     *   <li>{@link Conversation#getStatus()}
     * </ul>
     */
    @Test
    void testConstructor() {
        Conversation actualConversation = new Conversation();
        assertNull(actualConversation.getClient1());
        assertNull(actualConversation.getClient2());
        assertNull(actualConversation.getId());
        assertNull(actualConversation.getStatus());
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link Conversation#Conversation(String, String, Conversation.Status)}
     *   <li>{@link Conversation#getClient1()}
     *   <li>{@link Conversation#getClient2()}
     *   <li>{@link Conversation#getId()}
     *   <li>{@link Conversation#getStatus()}
     * </ul>
     */
    @Test
    void testConstructor2() {
        Conversation actualConversation = new Conversation("Client1", "Client2", Conversation.Status.ONGOING);

        assertEquals("Client1", actualConversation.getClient1());
        assertEquals("Client2", actualConversation.getClient2());
        assertNull(actualConversation.getId());
        assertEquals(Conversation.Status.ONGOING, actualConversation.getStatus());
    }
}

