package com.unistagram.matchapp.service;

import com.unistagram.userapp.model.User;

public interface MatchingService {
    
    public void joinQueue(User client);

    public void outQueue(User client);

    public boolean isWaiting(User client);

    // public Optional<User> getOtherRandomWaitingClient(User client);

    public boolean match(User client);

}
