package com.CreatorConnect.server.auth.oauth.controller;

import com.CreatorConnect.server.auth.jwt.JwtTokenizer;
import com.CreatorConnect.server.auth.jwt.TokenService;
import com.CreatorConnect.server.auth.oauth.dto.KakaoProfile;
import com.CreatorConnect.server.auth.oauth.dto.NaverProfile;
import com.CreatorConnect.server.auth.oauth.dto.OAuthToken;
import com.CreatorConnect.server.auth.oauth.handler.OAuth2MemberSuccessHandler;
import com.CreatorConnect.server.auth.oauth.service.KakaoApiService;
import com.CreatorConnect.server.auth.oauth.service.NaverApiService;
import com.CreatorConnect.server.auth.oauth.service.OAuth2MemberService;
import com.CreatorConnect.server.auth.utils.CustomAuthorityUtils;
import com.CreatorConnect.server.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class NaverController {

        private final CustomAuthorityUtils authorityUtils;
        private final OAuth2MemberService oAuth2MemberService;
        private final TokenService tokenService;
        private final NaverApiService naverApiService;

        public NaverController(CustomAuthorityUtils authorityUtils, OAuth2MemberService oAuth2MemberService, TokenService tokenService, NaverApiService naverApiService) {
                this.authorityUtils = authorityUtils;
                this.oAuth2MemberService = oAuth2MemberService;
                this.tokenService = tokenService;
                this.naverApiService = naverApiService;
        }

        @GetMapping("/auth/naver/callback")
        public ResponseEntity naverCallback(@RequestParam("code") String code, @RequestParam("state") String state,
                                            HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

                OAuthToken oAuthToken = naverApiService.tokenRequest(code); // 1.토큰 가져오기

                NaverProfile naverProfile = naverApiService.userInfoRequest(oAuthToken); // 2.유저정보 가져오기

                Map<String, Object> attributes = new HashMap<>();
                attributes.put("id", naverProfile.getResponse().getId());
                attributes.put("email",naverProfile.getResponse().getEmail());
                attributes.put("name", naverProfile.getResponse().getName());
                attributes.put("profileImage", naverProfile.getResponse().getProfile_image());

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
