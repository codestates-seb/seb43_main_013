package com.CreatorConnect.server.auth.jwt;

import com.CreatorConnect.server.auth.jwt.refreshtoken.RefreshToken;
import com.CreatorConnect.server.auth.jwt.refreshtoken.RefreshTokenRepository;
import com.CreatorConnect.server.member.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class TokenService { // 토큰 생성
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenizer jwtTokenizer;

    public TokenService(RefreshTokenRepository refreshTokenRepository, JwtTokenizer jwtTokenizer) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtTokenizer = jwtTokenizer;
    }

    public String delegateAccessToken(Member member) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("memberId", member.getMemberId());
        claims.put("username", member.getEmail());
        claims.put("roles", member.getRoles());

        String subject = member.getEmail();
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());

        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

        return accessToken;
    }

    public String delegateRefreshToken(Member member) {

        String subject = member.getEmail();
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getRefreshTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        String refreshToken = jwtTokenizer.generateRefreshToken(subject, expiration, base64EncodedSecretKey);

        RefreshToken rtk = new RefreshToken(refreshToken, member.getMemberId());

        refreshTokenRepository.save(rtk);

        return refreshToken;
    }

}
