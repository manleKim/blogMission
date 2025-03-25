package com.sprint.mission.blog.handler;

import com.sprint.mission.blog.dto.data.UserRegisterResponse;
import com.sprint.mission.blog.exception.DuplicateUserIdException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateUserIdException.class)
    public ResponseEntity<?> handleDuplicateUserIdException(DuplicateUserIdException e) {
        return ResponseEntity.badRequest().body(new UserRegisterResponse(false, e.getMessage()));
    }
}
