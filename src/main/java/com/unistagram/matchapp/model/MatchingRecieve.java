package com.unistagram.matchapp.model;

public class MatchingRecieve {

    private String userId;
    private boolean cancel;

    public MatchingRecieve() {
    }

    public MatchingRecieve(String userId, boolean cancel) {
        this.userId = userId;
        this.cancel = cancel;
    }

    public String getUserId() {
        return this.userId;
    }

    public boolean getCancel() {
        return this.cancel;
    }
}