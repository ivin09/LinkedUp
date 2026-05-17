package com.linkedup.user_service.controller;

import com.linkedup.user_service.dto.LoginRequestDto;
import com.linkedup.user_service.dto.SignUpRequestDto;
import com.linkedup.user_service.dto.UserDto;
import com.linkedup.user_service.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    private ResponseEntity<UserDto> signUp(@RequestBody SignUpRequestDto signUpRequestDto){
        UserDto userDto = authService.signUp(signUpRequestDto);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    private ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto){
        String token = authService.login(loginRequestDto);
        return ResponseEntity.ok(token);
    }
}
