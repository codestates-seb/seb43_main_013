package com.CreatorConnect.server.auth.oauth.service;

import com.CreatorConnect.server.auth.oauth.dto.KakaoProfile;
import com.CreatorConnect.server.auth.oauth.dto.OAuthToken;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class KakaoApiService {

    private static final String clientId = "d7774b0de8bd81c958657f202701d306";

    /**
     * 카카오 API를 통해 액세스 토큰 요청
     *
     * @param code 카카오 인증 코드
     * @return OAuthToken 객체, 액세스 토큰 및 기타 토큰 정보 포함
     */
    public OAuthToken tokenRequest(String code) {

        RestTemplate restTemplate = new RestTemplate();

        // HttpHeader
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpBody
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("redirect_uri", "https://api.hard-coding.com/auth/kakao/callback");
        body.add("code", code);

        // HttpHeader 와 HttpBody 를 담아 HTTP 요청 객체 생성
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, headers); // params : body

        // 카카오 API 엔드포인트로 액세스 토큰 요청을 보내고 응답을 받아옴
        return restTemplate.exchange("https://kauth.kakao.com/oauth/token", HttpMethod.POST, kakaoTokenRequest, OAuthToken.class).getBody();
    }

    /**
     * 카카오 API를 통해 사용자 정보 요청
     *
     * @param oAuthToken 액세스 토큰 정보를 담고 있는 OAuthToken 객체
     * @return KakaoProfile 객체, 사용자 정보를 담고 있음
     */
    public KakaoProfile userInfoRequest(OAuthToken oAuthToken) {

        RestTemplate restTemplate = new RestTemplate();

        // HttpHeader 설정
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + oAuthToken.getAccess_token());
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpHeader 와 HttpBody 를 담아 HTTP 요청 객체 생성
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers);

        // 카카오 API 엔드포인트로 사용자 정보 요청을 보내고 응답을 받아옴
        return restTemplate.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.POST, kakaoProfileRequest, KakaoProfile.class).getBody();
    }
}
