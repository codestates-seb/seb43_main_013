package com.CreatorConnect.server.auth.oauth.attribute;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoUserInfoDto {
    private Long id;
    private String nickname;
    private String profileImage;
}
