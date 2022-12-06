package com.holyvia.fashionblog.services.serviceImpl;

import com.holyvia.fashionblog.dtos.UserLoginDto;
import com.holyvia.fashionblog.dtos.UserSignupDto;
import com.holyvia.fashionblog.enums.Role;
import com.holyvia.fashionblog.exceptions.IncorrectValueEntryException;
import com.holyvia.fashionblog.exceptions.ResourceNotFoundException;
import com.holyvia.fashionblog.exceptions.UserAlreadyExists;
import com.holyvia.fashionblog.models.User;
import com.holyvia.fashionblog.repositories.UserRepo;
import com.holyvia.fashionblog.services.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final HttpSession session;

    @Override
    public User createUser(UserSignupDto userSignupDto) {
        boolean oldUser = userRepo.existsByEmail(userSignupDto.getEmail());
        if(oldUser){
            throw new UserAlreadyExists( "Kindly create a new account");}
        User user = User.builder()
                .name(userSignupDto.getName())
                .email(userSignupDto.getEmail())
                .gender(userSignupDto.getGender())
                .password(userSignupDto.getPassword())
                .role(Role.ADMIN)
                .build();
        userRepo.save(user);
        return user;
    }

    @Override
    public User loginUser(UserLoginDto userLoginDto) {
        String userEmail = userLoginDto.getEmail();
        String userPassword = userLoginDto.getPassword();
        if(userEmail.isEmpty() || userPassword.isEmpty()) {
            throw new IncorrectValueEntryException("Fields cannot be empty", "Enter correct details");
        }
        User user = userRepo.findUserByEmailAndPassword(userLoginDto.getEmail(), userLoginDto.getPassword())
                .orElseThrow(()-> new ResourceNotFoundException("enter a valid credential"));
        session.setAttribute("user_id", user.getUser_id());
        return user;
    }

    @Override
    public String logoutUser() {
        session.invalidate();
        return "Logged out";
    }
}
