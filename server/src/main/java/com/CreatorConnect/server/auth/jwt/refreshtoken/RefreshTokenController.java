package com.CreatorConnect.server.auth.jwt.refreshtoken;

import com.CreatorConnect.server.auth.jwt.JwtTokenizer;
import com.CreatorConnect.server.auth.jwt.TokenService;
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

    /**
     * 액세스 토큰을 갱신하는 엔드포인트
     *
     * @param request HTTP 요청 객체
     * @return 액세스 토큰 갱신 결과에 따른 응답
     */
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
            // 리프레시 토큰의 클레임에서 이메일을 추출하여 회원을 조회
            Jws<Claims> claims = jwtTokenizer.getClaims(refreshToken, jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey()));
            String email = claims.getBody().getSubject();
            Optional<Member> optionalMember = memberRepository.findByEmail(email);

            if (optionalMember.isPresent()) {
                // TokenService 를 사용하여 새로운 액세스 토큰 발급
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
