package com.salary.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class StateManager {
    private static final String STATE_KEY_PREFIX = "state:";
    private static final long STATE_TTL_MINUTES = 2;

    private final RedisTemplate<String, String> redisTemplate;

    @Value("${spring.state.secret-key}")
    private String secretKey;

    public void saveState(String state, String nonce) {
        if(validate(state, nonce)){
            String key = STATE_KEY_PREFIX + state;
            redisTemplate.opsForValue().set(key, "valid", STATE_TTL_MINUTES, TimeUnit.MINUTES);
        }else{
            throw new IllegalArgumentException();
        }
    }

    public boolean isExists(String state) {
        String key = STATE_KEY_PREFIX + state;
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    private boolean validate(String state, String nonce){
        try {
            System.out.println(secretKey);
            String input = secretKey + nonce;
            MessageDigest digest1 = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest1.digest(input.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte hashedByte : hashedBytes) {
                hexString.append(String.format("%02X", hashedByte));
            }
            String validationState = hexString.toString();
            System.out.println(validationState + " // " + state);
            return validationState.equals(state);
        }catch (Exception e){
            log.error("system error : ", e);
            return false;
        }
    }
}
