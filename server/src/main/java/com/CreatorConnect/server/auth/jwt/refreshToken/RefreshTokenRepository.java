package com.CreatorConnect.server.auth.jwt.refreshToken;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByEmail(String email);
    Optional<RefreshToken> findByToken(String token);

    // String 타입 리프레시 토큰 저장
    RefreshToken save(RefreshToken refreshToken);
}
