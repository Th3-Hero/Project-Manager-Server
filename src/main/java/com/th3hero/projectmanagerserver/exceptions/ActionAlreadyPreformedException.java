package com.th3hero.projectmanagerserver.exceptions;

public class ActionAlreadyPreformedException extends RuntimeException {
    public ActionAlreadyPreformedException(String message) {
        super(message);
    }
}
