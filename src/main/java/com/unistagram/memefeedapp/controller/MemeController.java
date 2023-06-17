package com.unistagram.memefeedapp.controller;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.unistagram.memefeedapp.model.Meme;
import com.unistagram.memefeedapp.service.MemeService;
import com.unistagram.userapp.exception.ObjectIdException;
import com.unistagram.userapp.exception.ParameterErrorNumberException;
import com.unistagram.userapp.exception.ParameterErrorStringException;
import com.unistagram.userapp.service.UserService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/memes")
public class MemeController {
    
    @Autowired
    private MemeService memeService;
    @Autowired
    private UserService userService;

    @ExceptionHandler(ObjectIdException.class)
    public ResponseEntity<String> handleObjectIdException() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("Something wrong when saving the message");
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

    @GetMapping("/{id}")
    public ResponseEntity<Meme> getMemeById(@PathVariable("id") String id) {
        Optional<Meme> meme = memeService.getMemeById(id);
        if(meme.isEmpty()) {
            throw new ParameterErrorStringException("The meme id does not exist!");
        }
        return ResponseEntity.ok(meme.get());
    }

    @PostMapping
    public ResponseEntity<Meme> saveMeme(@RequestParam String title, @RequestParam MultipartFile image, @RequestParam String author) throws IOException {
        String meme_id = memeService.save(title, image, author);
        return ResponseEntity.ok(memeService.getMemeById(meme_id).get());
    }

}
