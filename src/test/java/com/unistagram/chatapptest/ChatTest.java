package com.unistagram.chatapptest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.unistagram.chatapp.model.Message;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ChatTest {

    @Test
    public void testCreateMessage(){
        String conversation = "123";
        String sender = "client1";
        String receiver = "client2";
        String content = "Chao em anh dung day tu chieu";

        Message message = new Message(conversation, sender, receiver, content);

        assertEquals(conversation, message.getConversation());
        assertEquals(sender, message.getSender());
        assertEquals(receiver, message.getReceiver());
        assertEquals(content, message.getContent());
        assertNotNull(message.getTimestamp());
    }
}
