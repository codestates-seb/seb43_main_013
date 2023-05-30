package com.CreatorConnect.server.auth.filter;

import com.CreatorConnect.server.auth.jwt.JwtTokenizer;
import com.CreatorConnect.server.redis.RedisService;
import com.CreatorConnect.server.response.ErrorResponder;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
public class JwtLogoutFilter extends OncePerRequestFilter {
    private final JwtTokenizer jwtTokenizer;

    private final RedisService redisService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String uri = request.getRequestURI();
        String refreshToken = request.getHeader("Refresh-Token");

        // 요청 url이 DELETE /api/refresh-token 이 아닌 경우 필터를 적용하지 않음 || 리프레시 토큰이 없으면 필터 적용하지 않음
        return !request.getMethod().equals("DELETE")
                || !uri.equals("/api/logout")
                || !StringUtils.hasText(refreshToken);
    }

    /**
     * <로그 아웃>
     * 1. request를 통해 accessToken, refreshToken 추출
     * 2. 남은 유효시간 계산
     * 3. redis에 저장된 refreshToken 삭제
     * 4. redis에 accessToken 저장(로그아웃 된 토큰)
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // 1. request를 통해 accessToken, refreshToken 획득
            String accessToken = extractAccessToken(request, response);
            String refreshToken = extractRefreshToken(request, response);

            // 2. 남은 유효시간 계산
            Jws<Claims> claims = jwtTokenizer.getClaims(accessToken, jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey()));
            long remainExpiration = calcuateRemainExpiration(claims);

            // 3. redis에 저장된 refreshToken 삭제
            redisService.deleteRefreshToken(refreshToken);

            // 4. redis에 accessToken 저장 (로그아웃 된 토큰)
            redisService.setAccessTokenLogOut(accessToken, remainExpiration);
            log.info("로그아웃 성공, 남은 만료 시간 : {}", remainExpiration);

            response.setStatus(HttpStatus.OK.value());
            response.setCharacterEncoding("utf-8");
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        } catch (Exception e) {
            ErrorResponder.sendErrorResponse(response, HttpStatus.UNAUTHORIZED);
        }
    }

    // access token 추출
    private String extractAccessToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String accessToken = request.getHeader("Authorization");
        if (!StringUtils.hasText(accessToken) || !accessToken.startsWith("Bearer ")) { // 액세스 토큰이 없는 경우
            ErrorResponder.sendErrorResponse(response, HttpStatus.BAD_REQUEST);
        }
        return accessToken.replace("Bearer ", "");
    }

    // refresh token 추출
    private String extractRefreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String refreshToken = request.getHeader("Refresh-Token");
        if (!StringUtils.hasText(refreshToken)) {
            ErrorResponder.sendErrorResponse(response, HttpStatus.BAD_REQUEST);
        }
        return refreshToken;
    }

    // 남은 유효시간 계산 메서드
    private long calcuateRemainExpiration(Jws<Claims> claimsJws) {
        return claimsJws.getBody().getExpiration().getTime() - new Date().getTime();
    }
}
