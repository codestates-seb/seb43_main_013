package com.CreatorConnect.server.redis.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate redisTemplate;

    // RefreshToken  redis 저장(key : refreshToken, value : email)
    // expirationMinutes : refresh token 만료 시간
    public void setRefreshToken(String refreshToken, String email, int expirationMinutes) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        //refresh token의 만료 시간까지 저장
        valueOperations.set(refreshToken, email, expirationMinutes);
        log.info("만료시간 : {} min", Duration.ofMinutes(expirationMinutes));
    }

    // redis에 저장된 refresh token 조회
    public String getRefreshToken(String refreshToken) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(refreshToken);
    }

    // AccessToken  redis 저장(key : accessToken, value : "logout")
    public void setAccessTokenLogOut(String accessToken, int expirationMinutes) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(accessToken, "logout", expirationMinutes);
        log.info("Access Token 만료 시간 : {} ", Duration.ofMinutes(expirationMinutes));
    }

    // redis에 저장된 access token 조회
    public String getAccessToken(String accessToken) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(accessToken);
    }

    // redis에 저장된 refresh token 삭제
    public void deleteRefreshToken(String refreshToken) {
        redisTemplate.delete(refreshToken);
    }
}
