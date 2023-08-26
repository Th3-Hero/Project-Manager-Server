package com.th3hero.projectmanagerserver.controllers;

import com.th3hero.projectmanagerserver.exceptions.ActionAlreadyPreformedException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public void entityNotFoundException(EntityNotFoundException e) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(ActionAlreadyPreformedException.class)
    public void actionAlreadyPreformedException(ActionAlreadyPreformedException e) {
        throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
    }

}