package com.CreatorConnect.server.auth.handler;

import com.CreatorConnect.server.member.entity.Member;
import com.CreatorConnect.server.member.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Component
public class MemberAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final MemberRepository memberRepository;

    public MemberAuthenticationSuccessHandler(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        // 인증 성공 후, 로그를 기록하거나 사용자 정보를 response로 전송하는 등의 추가 작업을 할 수 있다.
        log.info("# Authenticated successfully!");

        // 인증된 사용자의 username 가져오기
        String username = ((UserDetails)authentication.getPrincipal()).getUsername();

        Member member = memberRepository.findByEmail(username).orElseThrow();

        // response JSON 형식으로 응답하기
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"));

        LinkedHashMap<String, Object> resultMap = new LinkedHashMap<>(); // 순서대로 정렬
        resultMap.put("memberId", member.getMemberId());
        resultMap.put("email", username);
        resultMap.put("name", member.getName());
        resultMap.put("nickname", member.getNickname());
        resultMap.put("phone", member.getPhone());
        resultMap.put("oauth", member.isOauth());
        resultMap.put("introduction", member.getIntroduction());
        resultMap.put("link", member.getLink());
        resultMap.put("profileImageUrl", member.getProfileImageUrl());
        resultMap.put("createdAt", member.getCreatedAt());
        resultMap.put("modifiedAt", member.getModifiedAt());

        String jsonResult = objectMapper.writeValueAsString(resultMap);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getOutputStream().write(jsonResult.getBytes());
        response.flushBuffer();
    }
}
