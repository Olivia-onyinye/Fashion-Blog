package com.holyvia.fashionblog.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnauthorizedOperationException extends RuntimeException{
    private String debugMessage;

    public UnauthorizedOperationException(String message, String debugMessage) {
        super(message);
        this.debugMessage = debugMessage;
    }

    public UnauthorizedOperationException(String message) {

        super(message);
    }
}
