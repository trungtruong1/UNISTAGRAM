package com.unistagram.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unistagram.exception.ObjectIdException;
import com.unistagram.exception.ParameterErrorNumberException;
import com.unistagram.exception.ParameterErrorStringException;
import com.unistagram.model.User;
import com.unistagram.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @ExceptionHandler(ObjectIdException.class)
    public ResponseEntity<String> handleObjectIdException() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("Something wrong when saving the user");
    }

    @ExceptionHandler(ParameterErrorNumberException.class)
    public ResponseEntity<String> handleParameterErrorNumber() {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                             .body("User id does not exist!");
    }

    @ExceptionHandler(ParameterErrorStringException.class)
    public ResponseEntity<String> handleParameterErrorString() {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                             .body("Parameter is not a number!");
    }

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        String new_id = userService.save(user);
        Optional<User> new_user = userService.getUserById(new_id);
        if(new_user == null) {
            throw new ObjectIdException("Something wrong when saving the user!");
        } 
        return ResponseEntity.ok(new_user.get());
    }

    @GetMapping
    public ResponseEntity<List<User>> getUser(@RequestParam("id") String id) {
        if(id.length() == 0) {
            return ResponseEntity.ok(userService.getUser());
        }
        try {
            int user_id = Integer.parseInt(id);
            List<User> result = userService.getUserById(user_id);
            if(result.size() == 0) {
                throw new ParameterErrorNumberException("User id does not exist!");
            }
            return ResponseEntity.ok(result);
        } catch(NumberFormatException e) {
            throw new ParameterErrorStringException("Parameter is not a number!");
        }
    }
}