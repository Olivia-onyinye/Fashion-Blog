package com.holyvia.fashionblog.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlreadyExistException extends RuntimeException{
    private String debugMessage;

    public AlreadyExistException(String message) {
        super(message);
    }

    public AlreadyExistException(String message, String debugMessage) {
        super(message);
        this.debugMessage = debugMessage;
    }
}

