package com.CreatorConnect.server.auth.jwt.refreshtoken;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Repository
public class RefreshTokenRepository {

    private RedisTemplate redisTemplate;

    public RefreshTokenRepository(final RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 리프레시 토큰을 Redis 에 저장하고 해당 멤버 ID와 함께 연결.
     * 리프레시 토큰의 유효기간을 7일로 설정.
     *
     * @param refreshToken 저장할 리프레시 토큰
     */
    public void save(final RefreshToken refreshToken) {

        //  Redis 에서 문자열 값에 대한 작업을 수행하기 위해 ValueOperations 객체 생성
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

        // (Long -> String) Redis 에 String 으로 저장하기 위해 변환.
        String memberIdAsString = String.valueOf(refreshToken.getMemberId());

        // Redis 에 리프레시 토큰을 키로, memberIdAsString 을 값으로 설정.
        valueOperations.set(refreshToken.getRefreshToken(), memberIdAsString);

        // 리프레시 토큰의 유효기간을 7일로 설정.
        redisTemplate.expire(refreshToken.getRefreshToken(), 7L, TimeUnit.DAYS);
    }

    public Optional<RefreshToken> findById (final String refreshToken) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String memberIdAsString = valueOperations.get(refreshToken);

        // Redis 에서 주어진 리프레시 토큰의 값에 해당하는 멤버 ID를 조회.
        // 값이 없는 경우 Optional.empty()를 반환합니다.
        if (Objects.isNull(memberIdAsString)) {
            return Optional.empty();
        }

        Long memberId = Long.valueOf(memberIdAsString);

        return Optional.of(new RefreshToken(refreshToken, memberId));
    }

    public void deleteById (final String refreshToken) {
        redisTemplate.delete(refreshToken);
    }
}