package com.th3hero.projectmanagerserver.controllers;

import com.th3hero.projectmanagerserver.exceptions.ActionAlreadyPreformedException;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import com.th3hero.projectmanagerserver.exceptions.FailedExpectedEntityRetrievalException;

import jakarta.persistence.EntityNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public void entityNotFoundException(EntityNotFoundException e) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(FailedExpectedEntityRetrievalException.class)
    public void failedEntityRetrieval(FailedExpectedEntityRetrievalException e) {
        e.printStackTrace();
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler(ActionAlreadyPreformedException.class)
    public void actionAlreadyPreformedException(ActionAlreadyPreformedException e) {
        throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
    }

}