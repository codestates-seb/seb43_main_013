package com.CreatorConnect.server.auth.handler;

import com.CreatorConnect.server.auth.jwt.JwtTokenizer;
import com.CreatorConnect.server.auth.utils.CustomAuthorityUtils;
import com.CreatorConnect.server.member.entity.Member;
import com.CreatorConnect.server.member.repository.MemberRepository;
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
public class OAuth2MemberSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils;
    private final MemberRepository memberRepository;

    public OAuth2MemberSuccessHandler(JwtTokenizer jwtTokenizer,
                                      CustomAuthorityUtils authorityUtils,
                                      MemberRepository memberRepository) {
        this.jwtTokenizer = jwtTokenizer;
        this.authorityUtils = authorityUtils;
        this.memberRepository = memberRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        var oAuth2User = (OAuth2User)authentication.getPrincipal();
        String email = String.valueOf(oAuth2User.getAttributes().get("email"));
        String name = String.valueOf(oAuth2User.getAttributes().get("name"));
        String profileImageUrl = String.valueOf(oAuth2User.getAttributes().get("picture"));

        List<String> authorities = authorityUtils.createRoles(email);

        saveMember(email, name, profileImageUrl);
        redirect(request, response, email, authorities);
    }

    private void saveMember(String email, String name, String profileImageUrl) {

        String tempPw = UUID.randomUUID().toString().replace("-", "");
        tempPw = tempPw.substring(0,20);

        String tempnickname = UUID.randomUUID().toString().replace("-", "");
        tempnickname = tempnickname.substring(0,7);

        Member member = new Member();
        member.setEmail(email);
        member.setPassword(tempPw);
        member.setName(name);
        member.setNickname(tempnickname);
        member.setProfileImageUrl(profileImageUrl);
        member.setOauth(true);
        member.setOauthProvider("GOOGLE");

        memberRepository.save(member);
    }

    private void redirect(HttpServletRequest request, HttpServletResponse response, String username, List<String> authorities) throws IOException {
        String accessToken = delegateAccessToken(username, authorities);
        String refreshToken = delegateRefreshToken(username);

        String uri = createURI(accessToken, refreshToken).toString();
        getRedirectStrategy().sendRedirect(request, response, uri);
    }

    private String delegateAccessToken(String username, List<String> authorities) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);
        claims.put("roles", authorities);

        String subject = username;
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());

        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

        return accessToken;
    }

    private String delegateRefreshToken(String username) {
        String subject = username;
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getRefreshTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        String refreshToken = jwtTokenizer.generateRefreshToken(subject, expiration, base64EncodedSecretKey);

        return refreshToken;
    }

    private URI createURI(String accessToken, String refreshToken) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("access_token", accessToken);
        queryParams.add("refresh_token", refreshToken);

        return UriComponentsBuilder
                .newInstance()
                .scheme("http")
                .host("localhost")
//                .port(3000)
                .port(8080)
                .path("api/oauth/google")
                .queryParams(queryParams)
                .build()
                .toUri();
    }

}