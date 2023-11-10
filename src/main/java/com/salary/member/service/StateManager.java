package com.salary.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class StateManager {
    private static final String STATE_KEY_PREFIX = "state:";
    private static final long STATE_TTL_MINUTES = 2;

    private final RedisTemplate<String, String> redisTemplate;

    public void saveState(String state) {
        String key = STATE_KEY_PREFIX + state;
        redisTemplate.opsForValue().set(key, "valid", STATE_TTL_MINUTES, TimeUnit.MINUTES);
    }

    public boolean validate(String state) {
        String key = STATE_KEY_PREFIX + state;
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
}
