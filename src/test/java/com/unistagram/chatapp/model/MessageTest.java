package com.unistagram.chatapp.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class MessageTest {

    @Test
    public void testMessageModel(){
        Message message1 = new Message("conversation1", "sender1", "receiver1", "content1");
        Message message2 = new Message("conversation1", "sender1", "receiver1", "content1");
        Message message3 = new Message("conversation2", "sender2", "receiver2", "content2");

        assertEquals("conversation1", message1.getConversation());
    }
}

