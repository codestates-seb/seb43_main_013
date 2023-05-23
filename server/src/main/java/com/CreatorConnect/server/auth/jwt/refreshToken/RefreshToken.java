package com.CreatorConnect.server.auth.jwt.refreshToken;

import javax.persistence.Id;

// @RedisHash(value = "refreshToken", timeToLive = 604800) // refresh token 유효기간 일주일
public class RefreshToken {

    @Id
    private String refreshToken;

    private Long memberId;

    public RefreshToken(String refreshToken, Long memberId) {
        this.refreshToken = refreshToken;
        this.memberId = memberId;
    }

    public RefreshToken() {

    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public Long getMemberId() {
        return memberId;
    }
}
