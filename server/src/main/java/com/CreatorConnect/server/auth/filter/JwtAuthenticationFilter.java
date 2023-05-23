package com.CreatorConnect.server.auth.filter;

import com.CreatorConnect.server.auth.jwt.refreshToken.TokenService;
import com.CreatorConnect.server.member.dto.MemberLoginDto;
import com.CreatorConnect.server.member.entity.Member;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @SneakyThrows // 예외처리 무시
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

        ObjectMapper objectMapper = new ObjectMapper();
        MemberLoginDto loginDto = objectMapper.readValue(request.getInputStream(), MemberLoginDto.class);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override // spring security 인증 성공시 호출
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws ServletException, IOException {
        Member member = (Member) authResult.getPrincipal();

        String accessToken = tokenService.delegateAccessToken(member);
        String refreshToken = tokenService.delegateRefreshToken(member);

        response.setHeader("Authorization", "Bearer " + accessToken);
        response.setHeader("Refresh-Token", refreshToken);
        response.setHeader("Access-Control-Expose-Headers", "Content-Length, Authorization, Refresh-Token");

        this.getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
    }
    // 인증 실패시 MemberAuthenticationFailureHandler onAuthenticationFailure() 호출

}
