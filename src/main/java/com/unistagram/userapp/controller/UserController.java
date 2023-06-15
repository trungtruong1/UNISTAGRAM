package com.unistagram.userapp.controller;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.client.result.UpdateResult;
import com.unistagram.userapp.exception.ObjectIdException;
import com.unistagram.userapp.exception.ParameterErrorNumberException;
import com.unistagram.userapp.exception.ParameterErrorStringException;
import com.unistagram.userapp.model.User;
import com.unistagram.userapp.service.UserService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    private static int NOT_FOUND = 404;

    private class AuthResponse {
        String username;
        String password;
        String status;
        String reason;

        AuthResponse() {}
        AuthResponse(String username, String password) {
            this.username = username;
            this.password = password;
            this.status = "ok";
            this.reason = "";
        }
        AuthResponse(String username, String password, String status, String reason) {
            this.username = username;
            this.password = password;
            this.status = status;
            this.reason = reason;
        }

        public String getUsername() { return this.username; }
        public String getPassword() { return this.password; }
        public String getStatus() { return this.status; }
        public String getReason() { return this.reason; }

    }

    @ExceptionHandler(ObjectIdException.class)
    public ResponseEntity<String> handleObjectIdException(ObjectIdException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body(ex.getMessage());
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

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        Optional<User> userOp = userService.getUserByUsername(user.getUsername());
        if(userOp.isPresent()) {
            throw new ParameterErrorStringException("User name already exists.");
        }

        String new_id = userService.save(user);
        Optional<User> new_user = userService.getUserById(new_id);

        return ResponseEntity.ok(new_user.get());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") int id) {
        Optional<User> result = userService.getUserById(id);
        if(result.isPresent()) {
            return ResponseEntity.ok(result.get());
        }
        throw new ParameterErrorNumberException("User id does not exist!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") String id, @RequestBody User user) {
        if(id.length() == 0) {
            throw new ParameterErrorStringException("Parameter is not a number!");
        }
        try {
            int user_id = Integer.parseInt(id);
            UpdateResult result = userService.updateUserInfoById(user_id, user);
            if(result.getMatchedCount() == 0) {
                throw new ParameterErrorNumberException("User id does not exist!");
            }
            if(!result.wasAcknowledged()) {
                throw new ObjectIdException("Something wrong when saving the user!");
            }
            return ResponseEntity.ok(userService.getUserById(user_id).get());
        } catch(NumberFormatException e) {
            throw new ParameterErrorStringException("Parameter is not a number!");
        }
    }

    @PostMapping("api/auth")
    public ResponseEntity<AuthResponse> authUser(@RequestParam String username, @RequestParam String password) {
        Optional<User> userOp = userService.getUserByUsername(username);
        if(userOp.isEmpty()) {
            return ResponseEntity.status(NOT_FOUND).body(new AuthResponse("", "", "failed", "username not found"));
        }
        User user = userOp.get();
        if(!user.getPassword().equals(password)) {
            return ResponseEntity.status(NOT_FOUND).body(new AuthResponse("", "", "failed", "password does not match"));
        }
        return ResponseEntity.ok(new AuthResponse(username, password));
    }
}