package com.CreatorConnect.server.auth.oauth.controller;

import com.CreatorConnect.server.auth.oauth.attribute.AccessTokenDto;
import com.CreatorConnect.server.auth.oauth.attribute.KakaoUserInfoDto;
import com.CreatorConnect.server.auth.oauth.service.KakaoApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
@RestController
public class KakaoController {

    private final KakaoApiService kakaoApiService;

    public KakaoController(KakaoApiService kakaoApiService) {
        this.kakaoApiService = kakaoApiService;
    }

    private static final String kakaoApiKey = "e6722e9848c6db656a44edaf1bbe8f09";
    private static final String kakaoSecretKey = "KEHIiQRBd5UCjSfqaz7jFJXofxdAdd0Q";
    private static final String kakaoRedirectUri = "http://localhost:8080/login/oauth2/code/kakao";
    private static final String kakaoEndpoint = "https://kauth.kakao.com/oauth/authorize";
    private static final String kakaoTokenUri = "https://kauth.kakao.com/oauth/token";

    @GetMapping("/login/oauth2/kakao")
    @ResponseBody
    public ResponseEntity kakaoLogin(HttpSession session) {

        String authorizationRequestUrl = String.format("%s?response_type=code&client_id=%s&redirect_uri=%s",
                kakaoEndpoint, kakaoApiKey, kakaoRedirectUri);

        return new ResponseEntity<>(authorizationRequestUrl, HttpStatus.OK);
    }

    @GetMapping ("/login/oauth2/code/kakao")
    public ResponseEntity kakaoCallback(@RequestParam("code") String code, HttpSession session) {
        AccessTokenDto accessToken = kakaoApiService.getAccessToken(code);
        KakaoUserInfoDto userInfo = kakaoApiService.getKakaoUserInfo(accessToken.getAccessToken());
        session.setAttribute("userId", userInfo.getId());
        session.setAttribute("userName", userInfo.getNickname());
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/login")
    public ResponseEntity login() throws IOException {

        return new ResponseEntity(HttpStatus.OK);
    }

}

