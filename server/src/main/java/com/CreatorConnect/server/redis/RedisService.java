package com.CreatorConnect.server.redis;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate redisTemplate;

    // AccessToken redis에 저장 (key : accessToken, value : "logout")
    public void setAccessTokenLogOut(String accessToken, long expiration) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(accessToken, "logout", expiration, TimeUnit.MILLISECONDS);
        String expirationTime = String.format("%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes(expiration),
                TimeUnit.MILLISECONDS.toSeconds(expiration) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(expiration))
        );
        log.info("Access Token 만료 시간 : {}", expirationTime);

    }

    // Redis에 저장된 accessToken 조회
    public String getAccessToken(String accessToken) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(accessToken).replace("Bearer ", "");
    }

    // RefreshToken redis 저장 (key : refreshToken, value : email)
    public void setRefreshToken(String refreshToken, String email, long expiration) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        // refresh token의 만료시간 까지 저장
        valueOperations.set(refreshToken, email, expiration);
        log.info("Refresh Token 만료 시간 : {} min", expiration);
    }

    // redis에 저장된 refreshToken 조회
    public String getRefreshToken(String refreshToken) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(refreshToken);
    }

    // redis에 저장된 refreshToken 삭제
    public void deleteRefreshToken(String refreshToken) {
        redisTemplate.delete(refreshToken);
    }

}
