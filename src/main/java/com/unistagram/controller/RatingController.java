package com.unistagram.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.unistagram.exception.ParameterErrorNumberException;
import com.unistagram.exception.ParameterErrorStringException;
import com.unistagram.model.Rating;
import com.unistagram.model.Result;
import com.unistagram.service.RatingService;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @ExceptionHandler(ParameterErrorNumberException.class)
    public ResponseEntity<String> handleParameterErrorNumber() {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                             .body("Parameter is not in the range of [1, 5]!");
    }

    @ExceptionHandler(ParameterErrorStringException.class)
    public ResponseEntity<String> handleParameterErrorString() {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                             .body("Parameter is not a number!");
    }

    @PostMapping
    public String save(@RequestBody Rating rating) {
        return ratingService.save(rating);
    }

    @GetMapping("/{rating}")
    @ResponseBody
    public ResponseEntity<List<Object>> getRating(@PathVariable("rating") String rating) {
        try {
            double ratingNum = Double.parseDouble(rating);
            Result<List<Object>> result = ratingService.getMovieRatingGTE(ratingNum);
            if(result.isOK()) {
                return ResponseEntity.ok(result.getResult());
            }
            throw new ParameterErrorNumberException("Parameter is not in the range of [1, 5]!");
        } catch (NumberFormatException e) {
            throw new ParameterErrorStringException("Parameter is not a number!");
        }
    }
}
