package com.holyvia.fashionblog.exceptions;

public class AlreadyExistException extends RuntimeException{
    private String message;

    public AlreadyExistException(String message) {
        super(message);
        this.message = message;
    }

    public AlreadyExistException() {
        super("Not found");
        this.message = "Not found";
    }
}
