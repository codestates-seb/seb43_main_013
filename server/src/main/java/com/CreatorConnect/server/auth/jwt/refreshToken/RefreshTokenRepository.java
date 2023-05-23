package com.CreatorConnect.server.auth.jwt.refreshToken;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.repository.CrudRepository;
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

    public void save(final RefreshToken refreshToken) {

        log.info("레디스 레파지토리 진입");
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String memberIdAsString = String.valueOf(refreshToken.getMemberId());
        valueOperations.set(refreshToken.getRefreshToken(), memberIdAsString);
        redisTemplate.expire(refreshToken.getRefreshToken(), 600L, TimeUnit.SECONDS);
    }

    public Optional<RefreshToken> findById(final String refreshToken) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String memberIdAsString = valueOperations.get(refreshToken);

        if (Objects.isNull(memberIdAsString)) {
            return Optional.empty();
        }

        Long memberId = Long.valueOf(memberIdAsString);
        return Optional.of(new RefreshToken(refreshToken, memberId));
    }

    public void deleteById(final String refreshToken) {
        redisTemplate.delete(refreshToken);
    }
}