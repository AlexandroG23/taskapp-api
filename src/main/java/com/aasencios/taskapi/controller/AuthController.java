package com.aasencios.taskapi.controller;

import com.aasencios.taskapi.dto.LoginRequestDTO;
import com.aasencios.taskapi.dto.LoginResponseDTO;
import com.aasencios.taskapi.dto.RegisterRequestDTO;
import com.aasencios.taskapi.dto.UserResponseDTO;
import com.aasencios.taskapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody RegisterRequestDTO request){
        UserResponseDTO response = userService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request){
        LoginResponseDTO response = userService.login(request);
        return ResponseEntity.ok(response);
    }
}
