package com.boogieboogie.blog.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobleException {

    @ExceptionHandler(ResourseNotFoundException.class)
    public ResponseEntity<?> resourseNotFoundExceptionHanddler(ResourseNotFoundException e){
        return new ResponseEntity(Map.of("message", e.getMessage()), HttpStatus.NOT_FOUND);
    }

    //handles Validation error messages
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> methodArgumentNotValidExceptionHandller(MethodArgumentNotValidException e){
        Map<String,String>res= new HashMap<>();

        e.getBindingResult().getAllErrors().forEach((error)->{
            String field = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            res.put(field,message);
        });
        return new ResponseEntity<Map<String,String>>(res, HttpStatus.BAD_REQUEST);
    }

}
