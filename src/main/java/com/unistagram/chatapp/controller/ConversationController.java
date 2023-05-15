package com.unistagram.chatapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unistagram.userapp.exception.ObjectIdException;
import com.unistagram.userapp.exception.ParameterErrorNumberException;
import com.unistagram.userapp.exception.ParameterErrorStringException;
import com.mongodb.client.result.UpdateResult;
import com.unistagram.chatapp.model.Conversation;
import com.unistagram.chatapp.service.ConversationService;
import com.unistagram.userapp.model.User;
import com.unistagram.userapp.service.UserService;

@RestController
@RequestMapping("/conversations")
public class ConversationController {

    @Autowired
    private ConversationService conversationService;
    @Autowired
    private UserService userService;

    @ExceptionHandler(ObjectIdException.class)
    public ResponseEntity<String> handleObjectIdException() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("Something wrong when saving the user");
    }

    @ExceptionHandler(ParameterErrorNumberException.class)
    public ResponseEntity<String> handleParameterErrorNumber(ParameterErrorNumberException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(ex.getMessage());
    }

    @ExceptionHandler(ParameterErrorStringException.class)
    public ResponseEntity<String> handleParameterErrorString(ParameterErrorStringException ex) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                             .body(ex.getMessage());
    }

    @GetMapping("/")
    public ResponseEntity<List<Conversation>> getAllConversations() {
        return ResponseEntity.ok(conversationService.getAllConversations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Conversation> getConversation(@PathVariable("id") String id) {
        Optional<Conversation> conversation = conversationService.getConversationById(id);
        if(conversation.isEmpty()) {
            throw new ParameterErrorNumberException("Conversation id does not exist!");
        }
        return ResponseEntity.ok(conversation.get());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<List<Conversation>> getConversationsByUser(@PathVariable("id") int id) {
        Optional<User> check_user = userService.getUserById(id);
        if(check_user.isEmpty()) {
            throw new ParameterErrorStringException("User id does not exist!");
        }
        return ResponseEntity.ok(conversationService.getConversationsByUser(check_user.get().getId()));
    }

    @PutMapping("/end/{id}")
    public ResponseEntity<Conversation> endConversationById(@PathVariable("id") String id) {
        Optional<Conversation> conversation = conversationService.getConversationById(id);
        if(conversation.isEmpty()) {
            throw new ParameterErrorNumberException("Conversation id does not exist!");
        }
        conversationService.endConversation(id);
        Conversation new_coversation = conversation.get();
        new_coversation.end();
        return ResponseEntity.ok(new_coversation);
    }

}
