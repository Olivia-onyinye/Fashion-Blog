package com.holyvia.fashionblog.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Data
public class ErrorResponse {
    private HttpStatus status;
    private String message;
    private String debugMessage;
}
