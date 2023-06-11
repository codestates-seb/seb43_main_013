package com.CreatorConnect.server.auth.jwt.refreshtoken;

import javax.persistence.Id;

// @RedisHash(value = "refreshToken", timeToLive = 604800) - redis repository 사용시 활성화
// 현재 redisTemplate 사용
public class RefreshToken {

    @Id
    private String refreshToken;

    private Long memberId;

    public RefreshToken(String refreshToken, Long memberId) {
        this.refreshToken = refreshToken;
        this.memberId = memberId;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public Long getMemberId() {
        return memberId;
    }
}
