package com.CreatorConnect.server.auth.oauth.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class OauthController {

    // google
    // http://localhost:8080/oauth2/authorization/google
    // kakao
    // https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=d7774b0de8bd81c958657f202701d306&redirect_uri=http://localhost:8080/auth/kakao/callback
    // naver
    // https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=JAnr85GxwFcBiBMCvdpL&state=vninaeonfd&redirect_uri=http://localhost:8080/auth/naver/callback

    @GetMapping("/api/login/oauth2")
    public ResponseEntity oauthSuccessController(Authentication authentication) {

        return new ResponseEntity("oauth login", HttpStatus.OK);
    }

}

