package com.kytokvinily.vinyls.web;

import com.kytokvinily.vinyls.exception.VinylAlreadyExistsException;
import com.kytokvinily.vinyls.exception.VinylNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class VinylControllerAdvice {

    @ExceptionHandler(VinylNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String vinylNotFoundHandler(VinylNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(VinylAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    String vinylAlreadyExistsHandler(VinylAlreadyExistsException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        var errors = new HashMap<String, String>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}