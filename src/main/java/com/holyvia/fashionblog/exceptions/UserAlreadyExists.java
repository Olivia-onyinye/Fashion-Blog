package com.holyvia.fashionblog.exceptions;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAlreadyExists extends RuntimeException{
    private String debugMessage;

    public UserAlreadyExists(String message) {

        super(message);
    }

    public UserAlreadyExists(String message, String debugMessage) {
        super(message);
        this.debugMessage = debugMessage;
    }
}
