package com.CreatorConnect.server.auth.jwt.refreshtoken;

import com.CreatorConnect.server.auth.jwt.JwtTokenizer;
import com.CreatorConnect.server.auth.jwt.TokenService;
import com.CreatorConnect.server.exception.JwtVerificationException;
import com.CreatorConnect.server.member.entity.Member;
import com.CreatorConnect.server.member.repository.MemberRepository;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenController {
    private final JwtTokenizer jwtTokenizer;
    private final MemberRepository memberRepository;
    private final TokenService tokenService;
    private final RefreshTokenRepository refreshTokenRepository;

    @PostMapping("/api/refresh-token")
    public ResponseEntity refreshAccessToken(HttpServletRequest request) {
        try {
            return executeRefreshAccessToken(request);

        } catch (JwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh-token");
        }
    }

    private ResponseEntity executeRefreshAccessToken(HttpServletRequest request) {

        String refreshToken = request.getHeader("Refresh-Token");

        if (refreshToken != null) {
            Optional<RefreshToken> findToken = refreshTokenRepository.findById(refreshToken);

            if (!findToken.isPresent()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh-token by redis");
            }

            Jws<Claims> claims = jwtTokenizer.getClaims(refreshToken, jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey()));
            String email = claims.getBody().getSubject();
            Optional<Member> optionalMember = memberRepository.findByEmail(email);

            if (optionalMember.isPresent()) {
                Member member = optionalMember.get();
                String accessToken = tokenService.delegateAccessToken(member);

                Map<String, String> response = new HashMap<>();
                response.put("message", "Access token refreshed");

                return ResponseEntity.ok().header("Authorization", "Bearer " + accessToken).body(response);

            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid member email");
            }

        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing refresh-token");
        }
    }
}
