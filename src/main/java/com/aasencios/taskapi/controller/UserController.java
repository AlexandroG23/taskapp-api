package com.aasencios.taskapi.controller;

import com.aasencios.taskapi.dto.UpdateUserDTO;
import com.aasencios.taskapi.dto.UserBasicDTO;
import com.aasencios.taskapi.security.JwtUtil;
import com.aasencios.taskapi.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<UserBasicDTO> getCurrentUser(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String email = jwtUtil.extractEmail(token);
        UserBasicDTO dto = userService.getUserBasicInfo(email);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<UserBasicDTO> updateUser(@RequestBody UpdateUserDTO dto, HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String email = jwtUtil.extractEmail(token);
        UserBasicDTO updated = userService.updateUser(email, dto);
        return ResponseEntity.ok(updated);
    }

}
