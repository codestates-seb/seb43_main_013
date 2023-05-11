package com.CreatorConnect.server.auth.oauth.service;

import com.CreatorConnect.server.auth.oauth.attribute.OAuth2Attribute;
import com.CreatorConnect.server.auth.userdetails.MemberDetailsService;
import com.CreatorConnect.server.auth.utils.CustomAuthorityUtils;
import com.CreatorConnect.server.member.entity.Member;
import com.CreatorConnect.server.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Security UserDetailsService == OAuth OAuth2UserService
 * */
@Slf4j
@Component
public class OAuth2MemberService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;
    private final CustomAuthorityUtils authorityUtils;

    public OAuth2MemberService(MemberRepository memberRepository, CustomAuthorityUtils authorityUtils) {
        this.memberRepository = memberRepository;
        this.authorityUtils = authorityUtils;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        log.info("{}",userRequest.getClientRegistration());
        log.info("{}",userRequest.getAccessToken().getTokenValue());
        log.info("{}",super.loadUser(userRequest).getAttributes());

        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // google
        OAuth2Attribute oAuth2Attribute = OAuth2Attribute.of(registrationId, oAuth2User.getAttributes());
        Map<String, Object> attributes = oAuth2Attribute.convertToMap();

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")), attributes, "email");
    }

    public void saveOauthMember (OAuth2User oAuth2User) {

        String email = String.valueOf(oAuth2User.getAttributes().get("email"));
        String name = String.valueOf(oAuth2User.getAttributes().get("name"));
        String profileImageUrl = String.valueOf(oAuth2User.getAttributes().get("picture"));

        List<String> authorities = authorityUtils.createRoles(email);

        Member member = memberRepository.findByEmail(email).orElse(null);

        if (member == null) {

            String tempPW = UUID.randomUUID().toString().replace("-", "");
            tempPW = tempPW.substring(0,20);

            String tempNickname = UUID.randomUUID().toString().replace("-", "");
            tempNickname = tempNickname.substring(0,7);

            member = new Member();
            member.setRoles(authorities);
            member.setEmail(email);
            member.setName(name);
            member.setPassword(tempPW);
            member.setNickname(tempNickname);
            member.setProfileImageUrl(profileImageUrl);
            member.setOauth(true);

            memberRepository.save(member);
        }
    }
}
