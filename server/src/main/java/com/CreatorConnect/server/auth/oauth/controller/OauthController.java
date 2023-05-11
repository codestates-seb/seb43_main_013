package com.CreatorConnect.server.auth.oauth.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class OauthController {

    private final OAuth2AuthorizedClientService authorizedClientService;

    public OauthController(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }

    // http://localhost:8080/oauth2/authorization/google
    @GetMapping("/login/oauth2")
    public ResponseEntity oauthSuccessController(Authentication authentication) {

        var authorizedClientGoogle = authorizedClientService.loadAuthorizedClient("google", authentication.getName());

        OAuth2AccessToken accessTokenGoogle = authorizedClientGoogle.getAccessToken();

        log.info("Google Access Token Value: " + accessTokenGoogle.getTokenValue());
        log.info("Google Access Token Type: " + accessTokenGoogle.getTokenType().getValue());
        log.info("Google Access Token Scopes: " + accessTokenGoogle.getScopes());
        log.info("Google Access Token Issued At: " + accessTokenGoogle.getIssuedAt());
        log.info("Google Access Token Expires At: " + accessTokenGoogle.getExpiresAt());

        return new ResponseEntity("oauth login", HttpStatus.OK);
    }

}

