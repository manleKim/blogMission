package com.sprint.mission.blog.service;

import com.sprint.mission.blog.dto.LoginRequest;
import com.sprint.mission.blog.dto.UserRegisterRequest;
import com.sprint.mission.blog.dto.data.LoginResponse;
import com.sprint.mission.blog.dto.data.UserRegisterResponse;

public interface UserService {
    UserRegisterResponse register(UserRegisterRequest request);
    LoginResponse login(LoginRequest request);
    boolean existsById(String userId);
}
