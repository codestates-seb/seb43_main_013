package com.CreatorConnect.server.auth.filter;

import com.CreatorConnect.server.auth.jwt.JwtTokenizer;
import com.CreatorConnect.server.auth.utils.CustomAuthorityUtils;
import com.CreatorConnect.server.exception.BusinessLogicException;
import com.CreatorConnect.server.exception.ExceptionCode;
import com.CreatorConnect.server.member.entity.Member;
import com.CreatorConnect.server.redis.RedisService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j // jwt 검증 필터
@RequiredArgsConstructor
public class JwtVerificationFilter extends OncePerRequestFilter { // OncePerRequestFilter - request 마다 한번 수행

    private final JwtTokenizer jwtTokenizer;

    private final CustomAuthorityUtils authorityUtils;

    private final RedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("# JwtVerificationFilter");

        if(checkResponseMethodAndURI(request)){
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = request.getHeader("Authorization").replace("Bearer ", "");
        String isLogout = (String) redisTemplate.opsForValue().get(accessToken);

        if (!ObjectUtils.isEmpty(isLogout)) { // redis 에 AccessToken 이 있다면 로그아웃 된 토큰
            throw new BusinessLogicException(ExceptionCode.EXPIRED_TOKEN);
        }

        try {
            Map<String, Object> claims = verifyJws(request);
            setAuthenticationToContext(claims); // SecurityContext 에 검증 정보 저장

        } catch (ExpiredJwtException ee) {
            // 액세스 토큰이 만료된 경우
            log.info("catch ExpiredJwtException");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access-Token expired");
            return;

        } catch (Exception e) {
            request.setAttribute("exception", e);
        }

        filterChain.doFilter(request, response); // 인증 성공 시 다음 필터 호출
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String authorization = request.getHeader("Authorization");

        return authorization == null || !authorization.startsWith("Bearer");
        // Authorization 의 값이 null 이거나 Bearer 로 시작하지 않을 때, 토큰 검증을 수행하지 않음
        // JWT 자격증명이 필요하지 않은 요청이라고 판단, 다음 Filter 로 위임
    }

    private Map<String, Object> verifyJws(HttpServletRequest request) { // jwt 검증
        String jws = request.getHeader("Authorization").replace("Bearer ", "");
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
        Map<String, Object> claims = jwtTokenizer.getClaims(jws, base64EncodedSecretKey).getBody();

        return claims;
    }

    private void setAuthenticationToContext(Map<String, Object> claims) { // Authentication 객체 SecurityContext 에 저장

        String email = (String) claims.get("username");
        List<GrantedAuthority> authorities = authorityUtils.createAuthorities((List) claims.get("roles"));
        Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private boolean checkResponseMethodAndURI(HttpServletRequest request){

        if(Objects.equals(request.getMethod(), "GET")){

            String requestURI = request.getRequestURI();

            if(requestURI.contains("/api/search") || requestURI.contains("/api/keyword")){
                return true;
            } else if (requestURI.contains("/api/login") || requestURI.contains("/auth")){
                return true;
            } else if (requestURI.contains("/api/member")){
                return true;
            } else if (requestURI.contains("board")){
                return true;
            } else if (requestURI.contains("/api/notice")){
                return true;
            } else if (requestURI.contains("categor")){
                return true;
            } else if (requestURI.contains("/api/youtubevideos")){
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
