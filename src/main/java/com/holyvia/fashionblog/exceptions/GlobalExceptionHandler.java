package com.holyvia.fashionblog.exceptions;

import com.holyvia.fashionblog.dtos.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiResponse<String> resourceNotFound(ResourceNotFoundException ex){
        return new ApiResponse<>(ex.getMessage(), HttpStatus.NOT_FOUND, null);
    }

    @ExceptionHandler(AlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ApiResponse<String> alreadyExistException(AlreadyExistException ex){
        return new ApiResponse<>(ex.getMessage(), HttpStatus.CONFLICT, null);
    }
}
