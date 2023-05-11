package com.unistagram.userapp.model;

import java.security.Principal;

/** Stores user id for each session. */
public class StompPrincipal implements Principal {

    private final String userId;

    public StompPrincipal(String userId) {
        this.userId = userId;
    }

    @Override
    public String getName() {
        return userId;
    }
}