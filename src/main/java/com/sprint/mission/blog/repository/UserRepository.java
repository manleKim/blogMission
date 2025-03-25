package com.sprint.mission.blog.repository;

import com.sprint.mission.blog.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(String id);
    List<User> findAll();
    boolean existsById(String id);
    void deleteById(String id);
}
