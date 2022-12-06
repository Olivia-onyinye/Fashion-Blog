package com.holyvia.fashionblog.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncorrectValueEntryException extends RuntimeException{
    private String debugMessage;
    public IncorrectValueEntryException(String message){

        super(message);
    }
    public IncorrectValueEntryException(String message, String debugMessage){
        super(message);
        this.debugMessage = debugMessage;
    }
}
