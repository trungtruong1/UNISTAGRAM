package com.unistagram.chatapp.model;

public class MessageReceive {
    private String content;
    private String conversationId;
    private String sender;
    private String time;

    public MessageReceive() {
    }

    public MessageReceive(String content) {
        this.content = content;
    }

    public String getContent() { return this.content; }
    public String getConversationId() { return this.conversationId; }
    public String getSender() { return this.sender; }
    public String getTime() { return this.time; }
}
