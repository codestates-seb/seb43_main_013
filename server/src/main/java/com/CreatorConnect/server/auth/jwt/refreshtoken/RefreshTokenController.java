package com.CreatorConnect.server.auth.jwt.refreshtoken;

import com.CreatorConnect.server.auth.jwt.JwtTokenizer;
import com.CreatorConnect.server.auth.jwt.TokenService;
import com.CreatorConnect.server.member.entity.Member;
import com.CreatorConnect.server.member.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity refreshAccessToken (HttpServletRequest request) {
        String refreshToken = request.getHeader("Refresh-Token");
        if (refreshToken != null) {

            try {
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
            } catch (JwtException e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh-token");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing refresh-token");
        }
    }

}

