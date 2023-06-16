package com.unistagram.matchapp.model;

public class MatchingMessage {

    private String content;

    public MatchingMessage() {
    }

    public MatchingMessage(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}