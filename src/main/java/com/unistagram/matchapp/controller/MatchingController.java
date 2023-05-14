package com.unistagram.matchapp.controller;

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
import org.springframework.web.util.HtmlUtils;

import com.unistagram.userapp.exception.ObjectIdException;
import com.unistagram.userapp.exception.ParameterErrorNumberException;
import com.unistagram.userapp.exception.ParameterErrorStringException;
import com.unistagram.matchapp.model.MatchingRecieve;
import com.unistagram.matchapp.service.MatchingService;
import com.unistagram.userapp.model.User;
import com.unistagram.userapp.service.UserService;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

@RestController
@RequestMapping("/matching")
public class MatchingController {

    private class CheckInQueue {
        private boolean is_in_queue;
        public CheckInQueue(boolean is_in_queue) { this.is_in_queue = is_in_queue; }

        public boolean getIs_in_queue() { return this.is_in_queue; }
    }

    @Autowired
    private UserService userService;
    @Autowired
    private MatchingService matchingService;

    @ExceptionHandler(ObjectIdException.class)
    public ResponseEntity<String> handleObjectIdException() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("Something wrong when saving the user");
    }

    @ExceptionHandler(ParameterErrorNumberException.class)
    public ResponseEntity<String> handleParameterErrorNumber() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body("User id does not exist!");
    }

    @ExceptionHandler(ParameterErrorStringException.class)
    public ResponseEntity<String> handleParameterErrorString() {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                             .body("Parameter is not a number!");
    }

    // @MessageMapping("/joinQueue")
    // public ResponseEntity<User> joinQueue(@Payload ) {
    // }

    @PutMapping("/join_queue/{id}")
    public ResponseEntity<User> updateUserToJoinQueue(@PathVariable("id") String id) {
        if(id.length() == 0) {
            throw new ParameterErrorStringException("Parameter is not a number!");
        }
        try {
            int user_id = Integer.parseInt(id);
            Optional<User> queried_user = userService.getUserById(user_id);
            if(queried_user.isEmpty()) {
                throw new ParameterErrorNumberException("User id does not exist!");
            }
            if(queried_user.get().getIs_in_queue()) {
                return ResponseEntity.ok(userService.getUserById(user_id).get());    
            }
            matchingService.joinQueue(queried_user.get());
            return ResponseEntity.ok(userService.getUserById(user_id).get());
        } catch(NumberFormatException e) {
            throw new ParameterErrorStringException("Parameter is not a number!");
        }
    }

    @PutMapping("/out_queue/{id}")
    public ResponseEntity<User> updateUserToOutQueue(@PathVariable("id") String id) {
        if(id.length() == 0) {
            throw new ParameterErrorStringException("Parameter is not a number!");
        }
        try {
            int user_id = Integer.parseInt(id);
            Optional<User> queried_user = userService.getUserById(user_id);
            if(queried_user.isEmpty()) {
                throw new ParameterErrorNumberException("User id does not exist!");
            }
            if(!queried_user.get().getIs_in_queue()) {
                return ResponseEntity.ok(userService.getUserById(user_id).get());    
            }
            matchingService.outQueue(queried_user.get());
            return ResponseEntity.ok(userService.getUserById(user_id).get());
        } catch(NumberFormatException e) {
            throw new ParameterErrorStringException("Parameter is not a number!");
        }
    }

    @GetMapping("/check_in_queue/{id}")
    public ResponseEntity<CheckInQueue> checkUserInQueue(@PathVariable("id") int id) {
        Optional<User> user = userService.getUserById(id);
        if(user.isEmpty()) {
            throw new ParameterErrorNumberException("User id does not exist!");
        }
        return ResponseEntity.ok(new CheckInQueue(matchingService.isWaiting(user.get())));
    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public MatchingRecieve greeting(String message) throws Exception {
        System.out.println("New message!");
        Thread.sleep(1000); // simulated delay
        return new MatchingRecieve("Hello, " + HtmlUtils.htmlEscape(message) + "!");
    }
}