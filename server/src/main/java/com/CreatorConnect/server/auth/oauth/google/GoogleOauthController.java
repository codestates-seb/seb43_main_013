package com.CreatorConnect.server.auth.oauth.google;

import com.CreatorConnect.server.member.dto.MemberResponseDto;
import com.CreatorConnect.server.member.entity.Member;
import com.CreatorConnect.server.member.mapper.MemberMapper;
import com.CreatorConnect.server.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class GoogleOauthController {

    private final OAuth2AuthorizedClientService authorizedClientService;

    public GoogleOauthController(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }

    // http://localhost:8080/oauth2/authorization/google
    @GetMapping("/api/oauth/google")
    public ResponseEntity oauthGoogle (Authentication authentication) {

        var authorizedClient = authorizedClientService.loadAuthorizedClient("google", authentication.getName());

        OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
        log.info("Access Token Value: " + accessToken.getTokenValue());
        log.info("Access Token Type: " + accessToken.getTokenType().getValue());
        log.info("Access Token Scopes: " + accessToken.getScopes());
        log.info("Access Token Issued At: " + accessToken.getIssuedAt());
        log.info("Access Token Expires At: " + accessToken.getExpiresAt());

        return new ResponseEntity<>("oauth google re-direct url", HttpStatus.OK);
    }
}
