package com.CreatorConnect.server.auth.oauth.service;

import com.CreatorConnect.server.auth.oauth.attribute.AccessTokenDto;
import com.CreatorConnect.server.auth.oauth.attribute.KakaoUserInfoDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class KakaoApiService {
    private final RestTemplate restTemplate;
    private static final String kakaoApiKey = "e6722e9848c6db656a44edaf1bbe8f09";
    private static final String kakaoRedirectUri = "http://localhost:8080/login/oauth2/code/kakao";
    private static final String kakaoTokenUri = "https://kauth.kakao.com/oauth/token";
    private static final String kakaoUserInfoUri = "https://kapi.kakao.com/v2/user/me";

    public KakaoApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public AccessTokenDto getAccessToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoApiKey);
        params.add("redirect_uri", kakaoRedirectUri);
        params.add("code", code);

        URI uri = UriComponentsBuilder.fromUriString(kakaoTokenUri)
                .queryParams(params)
                .build()
                .toUri();

        RequestEntity<Void> request = RequestEntity.post(uri)
                .headers(headers)
                .build();

        ResponseEntity<AccessTokenDto> response = restTemplate.exchange(request, AccessTokenDto.class);

        return response.getBody();
    }

    public KakaoUserInfoDto getKakaoUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        URI uri = UriComponentsBuilder.fromUriString(kakaoUserInfoUri)
                .build()
                .toUri();

        RequestEntity<Void> request = RequestEntity.get(uri)
                .headers(headers)
                .build();

        ResponseEntity<KakaoUserInfoDto> response = restTemplate.exchange(request, KakaoUserInfoDto.class);

        return response.getBody();
    }
}
