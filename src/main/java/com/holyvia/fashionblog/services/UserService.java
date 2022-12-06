package com.holyvia.fashionblog.services;

import com.holyvia.fashionblog.dtos.UserLoginDto;
import com.holyvia.fashionblog.dtos.UserSignupDto;
import com.holyvia.fashionblog.models.User;

public interface UserService {
    User createUser(UserSignupDto userSignupDto);
    User loginUser(UserLoginDto userLoginDto);
    String logoutUser();
}
