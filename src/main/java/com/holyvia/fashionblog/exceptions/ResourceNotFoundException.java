package com.holyvia.fashionblog.exceptions;

public class ResourceNotFoundException extends RuntimeException{
    private String message;

    public ResourceNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    public ResourceNotFoundException() {
        super("Not found");
        this.message = "Not found";
    }

}