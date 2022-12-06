package com.holyvia.fashionblog.dtos;

import com.holyvia.fashionblog.enums.Gender;
import com.holyvia.fashionblog.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSignupDto {
    private String name;

    @Email
    @NotNull
    private String email;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
}
