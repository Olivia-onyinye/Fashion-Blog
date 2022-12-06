package com.holyvia.fashionblog.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryNotFoundException extends RuntimeException{
    private String debugMessage;

    public CategoryNotFoundException(String message, String debugMessage) {
        super(message);
        this.debugMessage = debugMessage;
    }

    public CategoryNotFoundException(String message) {

        super(message);
    }
}
