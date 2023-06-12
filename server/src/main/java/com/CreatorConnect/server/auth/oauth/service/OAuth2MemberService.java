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

    // 클라이언트에서 전달받은 OAuth2 멤버 정보를 서버의 Member 엔티티로 저장
    public Member saveOauthMember(OAuth2User oAuth2User) {

        // OAuth2User 에서 이메일과 이름을 추출
        String email = String.valueOf(oAuth2User.getAttributes().get("email"));
        String name = String.valueOf(oAuth2User.getAttributes().get("name"));

        String profileImageUrl;
        // 클라이언트마다 프로필 이미지를 의미하는 이름이 달라 처리
        if (oAuth2User.getAttributes().containsKey("picture")) {
            profileImageUrl = String.valueOf(oAuth2User.getAttributes().get("picture"));
        } else if (oAuth2User.getAttributes().containsKey("profileImage")) {
            profileImageUrl = String.valueOf(oAuth2User.getAttributes().get("profileImage"));
        } else {
            profileImageUrl = null;
        }

        // 이메일을 기반으로 권한(authorities) 생성
        List<String> authorities = authorityUtils.createRoles(email);

        // 이메일로 이미 등록된 회원인지 조회
        Member member = memberRepository.findByEmail(email).orElse(null);

        // 등록된 회원이 없을 경우 새로운 회원을 생성하여 저장
        if (member == null) {

            // 임시 비밀번호와 임시 닉네임 생성
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

            return memberRepository.save(member);
        }

        // 이미 등록된 회원이 있을 경우 해당 회원을 반환
        return member;
    }
}
