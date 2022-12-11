package com.holyvia.fashionblog.controllers;

import com.holyvia.fashionblog.dtos.UserLoginDto;
import com.holyvia.fashionblog.dtos.UserSignupDto;
import com.holyvia.fashionblog.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AuthenticationController {

    public final UserService userService;


    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@Valid @RequestBody UserSignupDto userSignupDto){
        userService.createUser(userSignupDto);
        return new ResponseEntity<>("Signup Successful", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody UserLoginDto userLoginDto){
        userService.loginUser(userLoginDto);
        return new ResponseEntity<>("Login Successful", HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<String> welcome(){
        return new ResponseEntity<>("welcome Successful", HttpStatus.OK);
    }

    @GetMapping("/log-out")
    public ResponseEntity<String> logout(){
        userService.logoutUser();
        return new ResponseEntity<>("Logout Successful", HttpStatus.OK);
    }
}
