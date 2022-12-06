package com.holyvia.fashionblog.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginDto {
    private String email;
    private String password;
}
