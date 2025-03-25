package com.sprint.mission.blog.service;

import com.sprint.mission.blog.dto.LoginRequest;
import com.sprint.mission.blog.dto.UserRegisterRequest;
import com.sprint.mission.blog.dto.data.LoginResponse;
import com.sprint.mission.blog.dto.data.UserRegisterResponse;
import com.sprint.mission.blog.entity.User;
import com.sprint.mission.blog.exception.DuplicateUserIdException;
import com.sprint.mission.blog.repository.AuthRepository;
import com.sprint.mission.blog.repository.UserRepository;
import com.sprint.mission.blog.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasicUserService implements UserService {

    private final UserRepository userRepository;
    private final AuthRepository authRepository;
    private final JwtUtil jwtUtil;

    public UserRegisterResponse register(UserRegisterRequest request) {
        if(userRepository.existsById(request.id())){
            throw new DuplicateUserIdException("이미 사용중인 아이디입니다.");
        }
        String hashedPassword = BCrypt.hashpw(request.password(), BCrypt.gensalt());
        User user = new User(request.id(), hashedPassword, request.email(), request.nickname());
        userRepository.save(user);

        return new UserRegisterResponse(true, "회원가입이 완료되었습니다.");
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findById(request.id())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        if (!BCrypt.checkpw(request.password(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        return new LoginResponse(true, authRepository.save(request.id(), jwtUtil.generateToken(user.getId())));
    }

    public boolean existsById(String userId) {
        return userRepository.findById(userId).isPresent();
    }
}
