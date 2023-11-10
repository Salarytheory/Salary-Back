package com.salary.config.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@AllArgsConstructor
public class RefreshToken {
    @Id
    @Indexed
    private String refreshToken;
    private String sub;
}
