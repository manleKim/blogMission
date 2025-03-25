package com.sprint.mission.blog.controller;

import com.sprint.mission.blog.dto.LoginRequest;
import com.sprint.mission.blog.dto.UserRegisterRequest;
import com.sprint.mission.blog.dto.data.LoginResponse;
import com.sprint.mission.blog.dto.data.UserRegisterResponse;
import com.sprint.mission.blog.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponse> signUp(@RequestBody @Valid UserRegisterRequest signUpRequest) {
        return ResponseEntity.ok(userService.register(signUpRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.login(loginRequest));
    }

}
