package com.CreatorConnect.server.auth.oauth.controller;

import com.CreatorConnect.server.auth.jwt.JwtTokenizer;
import com.CreatorConnect.server.auth.jwt.TokenService;
import com.CreatorConnect.server.auth.oauth.dto.KakaoProfile;
import com.CreatorConnect.server.auth.oauth.dto.OAuthToken;
import com.CreatorConnect.server.auth.oauth.handler.OAuth2MemberSuccessHandler;
import com.CreatorConnect.server.auth.oauth.service.KakaoApiService;
import com.CreatorConnect.server.auth.oauth.service.OAuth2MemberService;
import com.CreatorConnect.server.auth.utils.CustomAuthorityUtils;
import com.CreatorConnect.server.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.bind.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class KakaoController {

    private final KakaoApiService kakaoApiService;
    private final CustomAuthorityUtils authorityUtils;
    private final OAuth2MemberService oAuth2MemberService;
    private final TokenService tokenService;

    public KakaoController(KakaoApiService kakaoApiService, CustomAuthorityUtils authorityUtils, OAuth2MemberService oAuth2MemberService, TokenService tokenService) {
        this.kakaoApiService = kakaoApiService;
        this.authorityUtils = authorityUtils;
        this.oAuth2MemberService = oAuth2MemberService;
        this.tokenService = tokenService;
    }

    @GetMapping("/auth/kakao/callback")
    public ResponseEntity kakaoCallback(@RequestParam("code") String code, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        OAuthToken oAuthToken = kakaoApiService.tokenRequest(code); // 토큰 가져오기

        KakaoProfile kakaoProfile = kakaoApiService.userInfoRequest(oAuthToken); // 유저정보 가져오기

        Map<String, Object> attributes = new HashMap<>();
        attributes.put("id", kakaoProfile.getId());
        attributes.put("email",kakaoProfile.getKakao_account().getEmail());
        attributes.put("name", kakaoProfile.getKakao_account().getProfile().getNickname());
        attributes.put("profileImage", kakaoProfile.getProperties().getProfile_image());

        log.info("{}", kakaoProfile.getKakao_account().getEmail()); // todo 이메일 선택동의 하지 않았을 때 에러 처리

        OAuth2User oAuth2User = new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                attributes,
                "id"
        );

        Authentication authentication = new OAuth2AuthenticationToken(oAuth2User, Collections.emptyList(), "kakao");

        AuthenticationSuccessHandler successHandler = new OAuth2MemberSuccessHandler(authorityUtils, oAuth2MemberService, tokenService);

        successHandler.onAuthenticationSuccess(request, response, authentication);

        return new ResponseEntity(HttpStatus.OK);
    }

}

