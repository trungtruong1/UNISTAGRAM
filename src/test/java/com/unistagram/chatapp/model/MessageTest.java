package com.unistagram.chatapp.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class MessageTest {

    @Test
    public void testMessageModel(){
        Message message1 = new Message("conversation1", "sender1", "receiver1", "content1");
        Message message2 = new Message("conversation1", "sender1", "receiver1", "content1");
        Message message3 = new Message("conversation2", "sender2", "receiver2", "content2");

        assertEquals(message1, message2);
        assertEquals(message1.toString(), message2.toString());
        assertEquals(message1.hashCode(), message2.hashCode());

        assertNotEquals(message1,message3);
        assertNotEquals(message1.toString(), message3.toString());
        assertNotEquals(message1.hashCode(), message3.hashCode());

    }
}

