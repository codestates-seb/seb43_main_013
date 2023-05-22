package com.CreatorConnect.server.auth.oauth.service;

import com.CreatorConnect.server.auth.utils.CustomAuthorityUtils;
import com.CreatorConnect.server.member.entity.Member;
import com.CreatorConnect.server.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * Security UserDetailsService == OAuth OAuth2UserService
 * */
@Slf4j
@Component
public class OAuth2MemberService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

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

        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        log.info("{}", registrationId);

        return oAuth2User;

    }


    public void saveOauthMember (OAuth2User oAuth2User) {

        String email = String.valueOf(oAuth2User.getAttributes().get("email"));
        String name = String.valueOf(oAuth2User.getAttributes().get("name"));
        String profileImageUrl;
        if (oAuth2User.getAttributes().containsKey("picture")) {
            profileImageUrl = String.valueOf(oAuth2User.getAttributes().get("picture"));
        } else if (oAuth2User.getAttributes().containsKey("profileImage")) {
            profileImageUrl = String.valueOf(oAuth2User.getAttributes().get("profileImage"));
        } else {
            profileImageUrl = null;
        }

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
            member.setVerified(true);

            memberRepository.save(member);
        }
    }
}
