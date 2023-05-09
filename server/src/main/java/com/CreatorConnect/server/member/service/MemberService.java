package com.CreatorConnect.server.member.service;

import com.CreatorConnect.server.auth.event.MemberRegistrationApplicationEvent;
import com.CreatorConnect.server.auth.utils.CustomAuthorityUtils;
import com.CreatorConnect.server.exception.BusinessLogicException;
import com.CreatorConnect.server.exception.ExceptionCode;
import com.CreatorConnect.server.member.entity.Member;
import com.CreatorConnect.server.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final ApplicationEventPublisher publisher;
    private final CustomAuthorityUtils authorityUtils;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, ApplicationEventPublisher publisher,
                         PasswordEncoder passwordEncoder, CustomAuthorityUtils authorityUtils) {
        this.memberRepository = memberRepository;
        this.publisher = publisher;
        this.passwordEncoder = passwordEncoder;
        this.authorityUtils = authorityUtils;
    }

    public Member createMember(Member member) {

        verifyExistsEmail(member.getEmail());

        String encryptPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encryptPassword);

        List<String> roles = authorityUtils.createRoles(member.getEmail());
        member.setRoles(roles);

        if (member.getIntroduction() == null || member.getIntroduction().isEmpty()) {
            member.setIntroduction("자기소개를 입력해 주세요.");
        } if (member.getLink() == null || member.getLink().isEmpty()) {
            member.setLink("youtube link를 입력해 주세요.");
        } if (member.getProfileImageUrl() == null || member.getProfileImageUrl().isEmpty()) {
            member.setProfileImageUrl("https://ibb.co/R7FdWWD");
        }

        Member savedMember = memberRepository.save(member);
        publisher.publishEvent(new MemberRegistrationApplicationEvent(savedMember));

        return savedMember;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public Member updateMember(Long memberId, Member member, String email) {

        verifiedAuthenticatedMember(memberId, email);

        Member findMember = findVerifiedMember(member.getMemberId());

        Optional.ofNullable(member.getPassword())
                .ifPresent(findMember::setPassword);
        Optional.ofNullable(member.getNickname())
                .ifPresent(findMember::setNickname);
        Optional.ofNullable(member.getPhone())
                .ifPresent(findMember::setPhone);
        Optional.ofNullable(member.getIntroduction())
                .ifPresent(findMember::setIntroduction);
        Optional.ofNullable(member.getLink())
                .ifPresent(findMember::setLink);
        Optional.ofNullable(member.getProfileImageUrl())
                .ifPresent(findMember::setProfileImageUrl);

        if (member.getPassword() != null) {
            findMember.setPassword(
                    passwordEncoder.encode(member.getPassword()));
        }

        return memberRepository.save(findMember);
    }

    @Transactional(readOnly = true)
    public Member findMember(Long memberId) {

        return findVerifiedMember(memberId);
    }

    @Transactional(readOnly = true)
    public Page<Member> findMembers(int page, int size) {

        return memberRepository.findAll(PageRequest.of(page, size,
                Sort.by("memberId").descending()));
    }

    public void deleteMember(long memberId, String email) {

        verifiedAuthenticatedMember(memberId, email);
        Member findMember = findVerifiedMember(memberId);

        String delEmail = "del_" + findMember.getEmail();

        findMember.setEmail(delEmail);
        findMember.setMemberStatus(Member.MemberStatus.MEMBER_QUIT);

        memberRepository.save(findMember);
    }

    public void verifyExistsEmail(String email) {

        Optional<Member> member = memberRepository.findByEmail(email);

        if (member.isPresent())
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS, String.format(" %s : 이미 등록된 이메일입니다. 다른 이메일을 사용해주세요. ", email));
    }

    public Member findVerifiedMember(long memberId) {

        Optional<Member> optionalMember = memberRepository.findById(memberId);
        Member findMember = optionalMember.orElseThrow(()
                -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        verifyActivatedMember(findMember);

        return findMember;
    }
    public boolean checkPassword (Long memberId, String password, String email){

        verifiedAuthenticatedMember(memberId, email);
        Member findMember = memberRepository.findByEmail(email).get();
        return passwordEncoder.matches(password, findMember.getPassword());
    }


    public void verifiedAuthenticatedMember(Long memberId, String email) {

        Member findMember = findVerifiedMember(memberId);

        if (email == null || email.isEmpty() ){
            throw new BusinessLogicException(ExceptionCode.MEMBER_FIELD_NOT_FOUND);
        } else if (!email.equals(findMember.getEmail())) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_ALLOWED);
        }

    }

    public void verifyActivatedMember (Member member) {

        if (member.getMemberStatus() == Member.MemberStatus.MEMBER_QUIT) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND, String.format("탈퇴한 회원입니다."));
        } else if (member.getMemberStatus() == Member.MemberStatus.MEMBER_SLEEP) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND, String.format("휴먼 상태의 회원입니다."));
        }

    }


}

