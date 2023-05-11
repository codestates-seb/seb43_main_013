package com.CreatorConnect.server.auth.oauth.attribute;

import java.util.HashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class OAuth2Attribute {
    private Map<String, Object> attributes;
    private String email;
    private String name;

    public static OAuth2Attribute of(String provider,
                                     Map<String, Object> attributes) {
        switch (provider) {
            case "google":
                return ofGoogle(attributes);
            case "kakao":
                return ofKakao(attributes);
            case "naver":
                return ofNaver(attributes);
            default:
                throw new RuntimeException();
        }
    }

    private static OAuth2Attribute ofGoogle(Map<String, Object> attributes) {
        return OAuth2Attribute.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .attributes(attributes)
                .build();
    }

    private static OAuth2Attribute ofKakao(Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");

        return OAuth2Attribute.builder()
                .name((String) kakaoProfile.get("nickname"))
                .email((String) kakaoAccount.get("email"))
                .attributes(kakaoAccount)
                .build();
    }

    private static OAuth2Attribute ofNaver(Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuth2Attribute.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .attributes(response)
                .build();
    }

    public Map<String, Object> convertToMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("email", email);

        return map;
    }
}
