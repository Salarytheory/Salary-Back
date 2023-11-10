package com.salary.config.jwt;

import com.salary.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RefreshTokenRepository {
    private static final String STATE_KEY_PREFIX = "refresh:";
    private static final long STATE_TTL_DAYS = 14;

    private final RedisTemplate<String, String> redisTemplate;

    public void save(String refreshToken, String sub) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String key = STATE_KEY_PREFIX + sub;
        valueOperations.set(key, refreshToken);
        redisTemplate.expire(key, STATE_TTL_DAYS, TimeUnit.DAYS);
    }

    public boolean validate(final String sub) {
        String refreshToken = getRefreshToken(sub);
        return refreshToken != null;
    }

    private String getRefreshToken(final String sub){
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(STATE_KEY_PREFIX + sub);
    }
}
