package com.CreatorConnect.server.auth.oauth.controller;

import com.CreatorConnect.server.member.dto.MemberResponseDto;
import com.CreatorConnect.server.member.entity.Member;
import com.CreatorConnect.server.member.mapper.MemberMapper;
import com.CreatorConnect.server.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class OauthController {

    private final MemberMapper mapper;
    private final MemberService memberService;

    public OauthController(MemberMapper mapper, MemberService memberService) {
        this.mapper = mapper;
        this.memberService = memberService;
    }


    // google
    // http://localhost:8080/oauth2/authorization/google
    // kakao
    // https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=d7774b0de8bd81c958657f202701d306&redirect_uri=http://localhost:8080/auth/kakao/callback
    // naver
    // https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=JAnr85GxwFcBiBMCvdpL&state=vninaeonfd&redirect_uri=http://localhost:8080/auth/naver/callback

    @GetMapping("/api/login/oauth2")
    public ResponseEntity oauthSuccessController(Authentication authentication) {

        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = oauthToken.getPrincipal();
        String email = (String) oAuth2User.getAttributes().get("email");

        Member member = memberService.findVerifiedMember(email);
        MemberResponseDto response = mapper.memberToMemberResponseDto(member);

        return new ResponseEntity(response, HttpStatus.OK);
    }

}

