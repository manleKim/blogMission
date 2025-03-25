package com.sprint.mission.blog.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class BasicAuthRepository implements AuthRepository {

    private final Map<String, String> tokens;

    public BasicAuthRepository() {
        tokens = new HashMap<>();
    }

    @Override
    public String save(String userId, String token) {
        tokens.put(userId, token);
        return token;
    }

    @Override
    public Optional<String> getTokenByUserId(String userId) {
        return Optional.ofNullable(tokens.get(userId));
    }

    @Override
    public void deleteTokenById(String userId) {
        tokens.remove(userId);
    }
}
