package com.holyvia.fashionblog.dtos;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private String message;
    private int status;
    private T data;
}
