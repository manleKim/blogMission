package com.sprint.mission.blog.repository;

import java.util.Optional;

public interface AuthRepository {
    String save(String userId, String token);
    Optional<String> getTokenByUserId(String userId);
    void deleteTokenById(String userId);
}
