package com.CreatorConnect.server.member.service;

import com.CreatorConnect.server.auth.jwt.JwtTokenizer;
import com.CreatorConnect.server.auth.utils.CustomAuthorityUtils;
import com.CreatorConnect.server.exception.BusinessLogicException;
import com.CreatorConnect.server.exception.ExceptionCode;
import com.CreatorConnect.server.helper.event.MemberRegistrationApplicationEvent;
import com.CreatorConnect.server.member.entity.Member;
import com.CreatorConnect.server.member.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Transactional
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final ApplicationEventPublisher publisher;
    private final CustomAuthorityUtils authorityUtils;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenizer jwtTokenizer;

    public MemberService(MemberRepository memberRepository, ApplicationEventPublisher publisher, CustomAuthorityUtils authorityUtils, PasswordEncoder passwordEncoder, JwtTokenizer jwtTokenizer) {
        this.memberRepository = memberRepository;
        this.publisher = publisher;
        this.authorityUtils = authorityUtils;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenizer = jwtTokenizer;
    }

    @Getter
    @Value("${jwt.key}")
    private String secretKey;

    public Member createMember(Member member) {

        verifyExistsEmail(member.getEmail());
        verifyExistsNickname(member.getNickname());
        verifyExistsPhone(member.getPhone());

        String encryptPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encryptPassword);

        List<String> roles = authorityUtils.createRoles(member.getEmail());
        member.setRoles(roles);

        if (member.getProfileImageUrl() == null || member.getProfileImageUrl().isEmpty()) {
            member.setProfileImageUrl("https://cloudflare-ipfs.com/ipfs/Qmd3W5DuhgHirLHGVixi6V76LhCkZUz6pnFt5AJBiyvHye/avatar/754.jpg");
        }

        member.setVerified(false);

        Member savedMember = memberRepository.save(member);

        // todo 이메일 전송 로직 주석 해제
//        publisher.publishEvent(new MemberRegistrationApplicationEvent(savedMember.getEmail()));

        return savedMember;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public Member updateMember(Long memberId, Member member) {

        Member findMember = findVerifiedMember(memberId);
        verifiedAuthenticatedMember(findMember.getMemberId());

        verifyExistsNickname(member.getNickname());
        verifyExistsPhone(member.getPhone());

        if (member.getPassword() != null) {
            findMember.setPassword(passwordEncoder.encode(member.getPassword()));
        }
        if (member.getNickname() != null) {
            findMember.setNickname(member.getNickname());
        }
        if (member.getPhone() != null) {
            findMember.setPhone(member.getPhone());
        }
        if (member.getIntroduction() != null) {
            findMember.setIntroduction(member.getIntroduction());
        }
        if (member.getLink() != null) {
            findMember.setLink(member.getLink());
        }
        if (member.getProfileImageUrl() != null) {
            findMember.setProfileImageUrl(member.getProfileImageUrl());
        }

        return memberRepository.save(findMember);
    }

    @Transactional(readOnly = true)
    public Page<Member> findMembers(int page, int size) {

        return memberRepository.findAll(PageRequest.of(page, size,
                Sort.by("memberId").descending()));
    }

    public void deleteMember(Long memberId) {

        Member findMember = findVerifiedMember(memberId);

        // 로그인 유저, 작성한 유저 검증 로직
        verifiedAuthenticatedMember(findMember.getMemberId());

        String delEmail = "del_" + findMember.getEmail();

        findMember.setEmail(delEmail);
        findMember.setMemberStatus(Member.MemberStatus.MEMBER_QUIT);

        memberRepository.save(findMember);
    }

    public void verifyExistsEmail(String email) {

        Optional<Member> member = memberRepository.findByEmail(email);

        if (member.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.EMAIL_EXISTS);
        }
    }

    private void verifyExistsNickname(String nickname) {

        Optional<Member> member = memberRepository.findByNickname(nickname);

        if (member.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.NICKNAME_EXISTS);
        }
    }

    private void verifyExistsPhone(String phone) {

        Optional<Member> member = memberRepository.findByPhone(phone);

        if (member.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.PHONE_EXISTS);
        }
    }

    // 회원가입 시 이메일 인증
    public void confirmEmail(String email) {

        Optional<Member> findMember = memberRepository.findByEmail(email);

        if (findMember.isPresent()) {
            Member member = findMember.get();
            if (!member.isVerified()) {
                member.setVerified(true);
                memberRepository.save(member);
            }
        }
    }

    public Member findVerifiedMember(Long memberId) {

        Optional<Member> optionalMember = memberRepository.findById(memberId);
        Member findMember = optionalMember.orElseThrow(()
                -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        verifyActivatedMember(findMember);

        return findMember;
    }

    public Member findVerifiedMember(String email) {

        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        Member findMember = optionalMember.orElseThrow(()
                -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        verifyActivatedMember(findMember);

        return findMember;
    }

    public boolean checkPassword(Long memberId, String password) {

        Member findMember = findVerifiedMember(memberId);
        verifiedAuthenticatedMember(findMember.getMemberId());

        return passwordEncoder.matches(password, findMember.getPassword());
    }

    // 인증 member 추출 ( access-token )
    public Member jwtTokenToMember (String jwtToken) {

        try {
            String encodeKey = encode(secretKey);

            Jws<Claims> claims = jwtTokenizer.getClaims(jwtToken, encodeKey);
            Claims tokenClaims = claims.getBody();
            String userEmail = tokenClaims.getSubject();

            Member findMember = findVerifiedMember(userEmail);

            return findMember;

        } catch (JwtException e) {
            throw new BusinessLogicException(ExceptionCode.INVALID_TOKEN);
        }

    }

    // 인증 멤버 검증 ( Authentication )
    public void verifiedAuthenticatedByAuthenticationMember(Long memberId) {

        // securitycontextholder 인증 검증 방식
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member findMember = findVerifiedMember(memberId);

        if (!authentication.getName().equals(findMember.getEmail())) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_ALLOWED);
        }

    }

    // 인증 멤버 검증 ( header - access-token )
    public void verifiedAuthenticatedMember(Long memberId) {

        if(getHeader("Authorization") == null){
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_ALLOWED);
        }

        String jws = getHeader("Authorization").substring(7);

        long memberIdFromJws = getMemberIdFromJws(jws);
        long memberIdFromRequest = memberId;

        if (memberIdFromJws != memberIdFromRequest) {
            throw new BusinessLogicException(ExceptionCode.INVALID_TOKEN);
        }
    }

    protected String getHeader(String headerName) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        return request.getHeader(headerName);
    }

    // jws 에서 memberId 추출
    protected long getMemberIdFromJws(String jws) {

        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
        Map<String, Object> claims = jwtTokenizer.getClaims(jws, base64EncodedSecretKey).getBody();

        return Long.parseLong(claims.get("memberId").toString());
    }

    // 현재 로그인한 사용자 객체
    public Member getLoggedinMember(String authorizationToken) {

        String accessToken = authorizationToken.substring(7);
        Long loggedInMemberId = getMemberIdFromJws(accessToken);

        return findVerifiedMember(loggedInMemberId);
    }

    public void verifyActivatedMember (Member member){

        if (member.getMemberStatus() == Member.MemberStatus.MEMBER_QUIT) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND, String.format("탈퇴한 회원입니다."));
        } else if (member.getMemberStatus() == Member.MemberStatus.MEMBER_SLEEP) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND, String.format("휴먼 상태의 회원입니다."));
        }

    }

    public static String encode(String secretKey) {

        // 시크릿 키를 UTF-8 문자열로 인코딩
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);

        // UTF-8로 인코딩된 문자열을 Base64로 인코딩
        String base64EncodedKey = Base64.getEncoder().encodeToString(keyBytes);

        return base64EncodedKey;
    }

}


