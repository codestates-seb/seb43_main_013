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

    private static final String clientId = "JAnr85GxwFcBiBMCvdpL";
    private static final String clientSecret = "JsHcHlI0Gl";

    /**
     * 네이버 API를 통해 액세스 토큰 요청
     *
     * @param code 네이버 인증 코드
     * @return OAuthToken 객체, 액세스 토큰 및 기타 토큰 정보 포함
     */
    public OAuthToken tokenRequest(String code) {

        RestTemplate restTemplate = new RestTemplate();

        // HttpHeader 설정
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpBody 설정
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("code", code);

        // HttpHeader 와 HttpBody 를 담아 HTTP 요청 객체 생성
        HttpEntity<MultiValueMap<String, String>> naverTokenRequest = new HttpEntity<>(body, headers); // params : body

        // 네이버 API 엔드포인트로 액세스 토큰 요청을 보내고 응답을 받아옴
        return restTemplate.exchange("https://nid.naver.com/oauth2.0/token", HttpMethod.POST, naverTokenRequest, OAuthToken.class).getBody();
    }

    /**
     * 네이버 API를 통해 사용자 정보 요청
     *
     * @param oAuthToken 액세스 토큰 정보를 담고 있는 OAuthToken 객체
     * @return NaverProfile 객체, 사용자 정보를 담고 있음
     */
    public NaverProfile userInfoRequest(OAuthToken oAuthToken) {

        RestTemplate restTemplate = new RestTemplate();

        // HttpHeader 설정
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + oAuthToken.getAccess_token());
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpHeader 와 HttpBody 를 담아 HTTP 요청 객체 생성
        HttpEntity<MultiValueMap<String, String>> naverProfileRequest = new HttpEntity<>(headers);

        // 네이버 API 엔드포인트로 사용자 정보 요청을 보내고 응답을 받아옴
        return restTemplate.exchange("https://openapi.naver.com/v1/nid/me", HttpMethod.POST, naverProfileRequest, NaverProfile.class).getBody();
    }
}
