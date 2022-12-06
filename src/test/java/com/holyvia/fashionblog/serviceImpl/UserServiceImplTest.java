package com.holyvia.fashionblog.serviceImpl;

import com.holyvia.fashionblog.dtos.UserLoginDto;
import com.holyvia.fashionblog.dtos.UserSignupDto;
import com.holyvia.fashionblog.enums.Gender;
import com.holyvia.fashionblog.enums.Role;
import com.holyvia.fashionblog.exceptions.IncorrectValueEntryException;
import com.holyvia.fashionblog.exceptions.UserAlreadyExists;
import com.holyvia.fashionblog.models.User;
import com.holyvia.fashionblog.repositories.UserRepo;
import com.holyvia.fashionblog.services.serviceImpl.UserServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    @Mock
    UserRepo userRepo;

    @Mock
    HttpSession session;

    @InjectMocks
    UserServiceImpl userService;

    private User user;
    private User user1;
    private UserSignupDto userSignupDto;
    private UserLoginDto userLoginDto;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = User.builder()
                .name("Victor Madu")
                .email("madu@gmail.com")
                .gender(Gender.MALE)
                .password("unserious")
                .role(Role.ADMIN)
                .build();
        userSignupDto = UserSignupDto.builder()
                .name("Victor Madu")
                .email("madu@gmail.com")
                .gender(Gender.MALE)
                .password("unserious")
                .role(Role.ADMIN)
                .build();
        userLoginDto = UserLoginDto.builder()
                .email("madu@gmail.com")
                .password("unserious")
                .build();
        user1 = User.builder()
                .user_id(2L)
                .name("Victor Madu")
                .email("madu@gmail.com")
                .gender(Gender.MALE)
                .password("unserious")
                .role(Role.ADMIN)
                .build();
        when(userRepo.existsByEmail(anyString())).thenReturn(false);
        when(userRepo.save(user)).thenReturn(user);
        when(userRepo.findUserByEmailAndPassword("madu@gmail.com", "unserious")).thenReturn(Optional.ofNullable(user1));
    }
    @Test
    void createUser() {
        User actual = userService.createUser(userSignupDto);
        Assertions.assertEquals(user, actual);
    }

    @Test
    void loginUser() {
        var actual = userService.loginUser(userLoginDto);
        Assertions.assertEquals(userLoginDto.getEmail(), actual.getEmail());
        Assertions.assertEquals(userLoginDto.getPassword(), actual.getPassword());
    }

    @Test
    void logoutUser() {
        String result = userService.logoutUser();
        String expected = "Logged out";
        Assertions.assertEquals(expected, result);
    }
    @Test
    void shouldThrowAlreadyExistsExceptionIfUserAlreadyExists(){
        when(userRepo.existsByEmail(anyString())).thenReturn(true);
        Assertions.assertThrows(UserAlreadyExists.class, () ->userService.createUser(userSignupDto));
    }
    @Test
    void shouldThrowExceptionIfPasswordAndEmailIsEmpty(){
     UserLoginDto userLoginDto1 = UserLoginDto.builder()
                .email("")
                .password("")
                .build();
        when(userRepo.findUserByEmailAndPassword("", "")).thenReturn(Optional.ofNullable(user1));
        Assertions.assertThrows(IncorrectValueEntryException.class, () ->userService.loginUser(userLoginDto1));
    }
}