package com.sprint.mission.blog.dto.data;

public record LoginResponse(
        boolean success,
        String token
) {
}
