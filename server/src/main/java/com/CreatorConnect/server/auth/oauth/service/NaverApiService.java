package com.CreatorConnect.server.auth.oauth.service;

import com.CreatorConnect.server.auth.oauth.dto.KakaoProfile;
import com.CreatorConnect.server.auth.oauth.dto.NaverProfile;
import com.CreatorConnect.server.auth.oauth.dto.OAuthToken;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class NaverApiService {
    private final RestTemplate restTemplate;
    private static final String clientId = "JAnr85GxwFcBiBMCvdpL";
    private static final String clientSecret = "JsHcHlI0Gl";

    public NaverApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public OAuthToken tokenRequest(String code) {

        RestTemplate restTemplate = new RestTemplate();

        // HttpHeader
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //HttpBody
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("code", code);

        // HttpHeader 와 HttpBody 담기
        HttpEntity<MultiValueMap<String, String>> naverTokenRequest = new HttpEntity<>(body, headers); // params : body

        return restTemplate.exchange("https://nid.naver.com/oauth2.0/token", HttpMethod.POST, naverTokenRequest, OAuthToken.class).getBody();
    }

    public NaverProfile userInfoRequest(OAuthToken oAuthToken) {

        ///유저정보 요청
        RestTemplate restTemplate = new RestTemplate();

        //HttpHeader
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + oAuthToken.getAccess_token());
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> naverProfileRequest = new HttpEntity<>(headers);

        return restTemplate.exchange("https://openapi.naver.com/v1/nid/me", HttpMethod.POST, naverProfileRequest, NaverProfile.class).getBody();
    }
}
