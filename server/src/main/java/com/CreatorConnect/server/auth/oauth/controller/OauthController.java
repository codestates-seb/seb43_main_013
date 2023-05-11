package com.CreatorConnect.server.auth.oauth.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class OauthController {

    private final OAuth2AuthorizedClientService authorizedClientService;

    public OauthController(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }

    // http://localhost:8080/oauth2/authorization/google

    // https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=d7774b0de8bd81c958657f202701d306&redirect_uri=http://localhost:8080/auth/kakao/callback

    @GetMapping("/login/oauth2")
    public ResponseEntity oauthSuccessController(Authentication authentication) {

        return new ResponseEntity("oauth login", HttpStatus.OK);
    }

}

