package com.unistagram.userapp.model;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

/** Generates user id for each session. */
public class UserIdHandshakeHandler extends DefaultHandshakeHandler {

    @Override
    protected Principal determineUser(ServerHttpRequest request, 
                                      WebSocketHandler wsHandler, 
                                      Map<String, Object> attributes) {
        return new StompPrincipal(UUID.randomUUID().toString());
    }
}