package com.unistagram.matchapp.model;

public class MatchingMessage {

    private String name;

    public MatchingMessage() {
    }

    public MatchingMessage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}