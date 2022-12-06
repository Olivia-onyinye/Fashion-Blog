package com.holyvia.fashionblog.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostNotFoundException extends RuntimeException{
    private String debugMessage;

    public PostNotFoundException(String message, String debugMessage) {
        super(message);
        this.debugMessage = debugMessage;
    }

    public PostNotFoundException(String message) {
        super(message);
    }
}
