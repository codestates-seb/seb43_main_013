package com.CreatorConnect.server.auth.oauth.handler;

import com.CreatorConnect.server.auth.jwt.TokenService;
import com.CreatorConnect.server.auth.oauth.service.OAuth2MemberService;
import com.CreatorConnect.server.auth.utils.CustomAuthorityUtils;
import com.CreatorConnect.server.member.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;

@Slf4j
@Component
public class OAuth2MemberSuccessHandler extends SimpleUrlAuthenticationSuccessHandler { // OAuth2 인증이 성공한 경우 호출

    private final CustomAuthorityUtils authorityUtils;
    private final OAuth2MemberService oAuth2MemberService;
    private final TokenService tokenService;

    public OAuth2MemberSuccessHandler(CustomAuthorityUtils authorityUtils, OAuth2MemberService oAuth2MemberService, TokenService tokenService) {
        this.authorityUtils = authorityUtils;
        this.oAuth2MemberService = oAuth2MemberService;
        this.tokenService = tokenService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        var oAuth2User = (OAuth2User)authentication.getPrincipal(); // authentication 객체에서 OAuth2 사용자 정보를 추출
        String email = String.valueOf(oAuth2User.getAttributes().get("email"));
        List<String> authorities = authorityUtils.createRoles(email);

        Member member = oAuth2MemberService.saveOauthMember(oAuth2User); // OAuth2 사용자 정보 저장

        redirect(request, response, member);
    }

    // 토큰 생성 후 파라미터에 담아 리다이렉트
    private void redirect(HttpServletRequest request, HttpServletResponse response, Member member) throws IOException {

        String accessToken = tokenService.delegateAccessToken(member);
        String refreshToken = tokenService.delegateRefreshToken(member);

        String uri = createURI(accessToken, refreshToken).toString();
        getRedirectStrategy().sendRedirect(request, response, uri);
        // redirect strategy 를 가져와서 리디렉션 수행
    }

    private URI createURI(String accessToken, String refreshToken) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("access_token", "Bearer " + accessToken);
        queryParams.add("refresh_token", refreshToken);

        return UriComponentsBuilder
                .newInstance()
//                .scheme("http")
                .scheme("https")
                .host("www.hard-coding.com")
//                .port(3000)
//                .port(8080)
                .path("/login/oauth")
                .queryParams(queryParams)
                .build()
                .toUri();
    }

}