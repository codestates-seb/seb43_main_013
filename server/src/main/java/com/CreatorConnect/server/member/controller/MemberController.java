package com.CreatorConnect.server.member.controller;

import com.CreatorConnect.server.exception.BusinessLogicException;
import com.CreatorConnect.server.exception.ExceptionCode;
import com.CreatorConnect.server.member.dto.MemberDto;
import com.CreatorConnect.server.member.dto.MemberFollowResponseDto;
import com.CreatorConnect.server.member.dto.MemberResponseDto;
import com.CreatorConnect.server.member.entity.Member;
import com.CreatorConnect.server.member.mapper.MemberMapper;
import com.CreatorConnect.server.member.repository.MemberRepository;
import com.CreatorConnect.server.member.service.MemberService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Validated
@RestController
public class MemberController {

    private static final String MEMBER_DEFAULT_URL = "/api/member";
    private static final String MEMBER_ALL_MAPPING_URL = "/api/members";

    private final MemberMapper mapper;
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    public MemberController(MemberMapper mapper, MemberService memberService, MemberRepository memberRepository) {
        this.mapper = mapper;
        this.memberService = memberService;
        this.memberRepository = memberRepository;
    }

    @GetMapping("/")
    public ResponseEntity home(){
        return new ResponseEntity("home", HttpStatus.OK);
    }

    @PostMapping("/api/signup")
    public ResponseEntity postMember(@Valid @RequestBody MemberDto.Post memberDtoPost) {

        Member member = mapper.memberPostDtoToMember(memberDtoPost);
        Member createdMember = memberService.createMember(member);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping(MEMBER_DEFAULT_URL + "/{member-id}")
    public ResponseEntity patchMember(@PathVariable("member-id") @Positive Long memberId,
                                      @Valid @RequestBody MemberDto.Patch memberDtoPatch,
                                      Authentication authentication) {

        memberDtoPatch.setMemberId(memberId);
        Member member = mapper.memberPatchDtoToMember(memberDtoPatch);
        Member updateMember = memberService.updateMember(memberId,member,authentication.getName());

        MemberResponseDto responseDto = mapper.memberToMemberResponseDto(updateMember);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping(MEMBER_DEFAULT_URL + "/{member-id}")
    public ResponseEntity getMember(@PathVariable("member-id") @Positive Long memberId) {

        Member findedmember = memberService.findMember(memberId);

        MemberResponseDto responseDto = mapper.memberToMemberResponseDto(findedmember);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(MEMBER_ALL_MAPPING_URL)
    public ResponseEntity getMembers(@RequestParam @Positive int page,
                                     @RequestParam @Positive int size) {

        Page<Member> pageMembers = memberService.findMembers(page - 1, size);
        List<Member> members = pageMembers.getContent();

        List<MemberResponseDto> responseDtos = mapper.membersToMemberResponseDtos(members);

        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    @DeleteMapping(MEMBER_DEFAULT_URL + "/{member-id}")
    public ResponseEntity deleteMember(@PathVariable("member-id") @Positive Long memberId,
                                     Authentication authentication) {

        memberService.deleteMember(memberId, authentication.getName());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/api/member/{member-id}/password")
    public ResponseEntity checkPassword(@PathVariable("member-id") @Positive Long memberId,
                                        @Valid @RequestBody MemberDto.CheckPassword checkPasswordDto,
                                        Authentication authentication) {

        boolean checkPassword =
                memberService.checkPassword(memberId, checkPasswordDto.getPassword(), authentication.getName());

        if (checkPassword) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("비밀번호가 일치하지 않습니다.",HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/api/member/{member-id}/follow")
    public ResponseEntity followMember(@PathVariable("member-id") @Positive Long memberId,
                                       Authentication authentication) {
        // 현재 로그인한 사용자 정보 가져오기
        Member currentMember = memberService.findVerifiedMember(authentication.getName());

        // 팔로우할 사용자 정보 가져오기
        Member memberToFollow = memberService.findVerifiedMember(memberId);

        if (currentMember.getMemberId() == memberToFollow.getMemberId()){
            return new ResponseEntity(
                    new BusinessLogicException(ExceptionCode.INVALID_MEMBER).getExceptionCode().getMessage(),
                    HttpStatus.CONFLICT
            );
        }

        // 현재 로그인한 사용자가 이미 해당 사용자를 팔로우하고 있는지 확인
        boolean isAlreadyFollowing = currentMember.getFollowings().contains(memberToFollow);

        // 현재 로그인한 사용자가 해당 사용자를 팔로우하지 않았다면 팔로우
        if (!isAlreadyFollowing) {
            memberToFollow.follow(currentMember);
            memberRepository.save(memberToFollow);
            memberRepository.save(currentMember);

            return new ResponseEntity<>(HttpStatus.OK);

        } else return new ResponseEntity(
                new BusinessLogicException(ExceptionCode.FOLLOWING_ALREADY_EXISTS).getExceptionCode().getMessage(),
                HttpStatus.CONFLICT);
    }

    @DeleteMapping("/api/member/{member-id}/follow")
    public ResponseEntity unfollowMember(@PathVariable("member-id") @Positive Long memberId,
                                       Authentication authentication) {
        // 현재 로그인한 사용자 정보 가져오기
        Member currentMember = memberService.findVerifiedMember(authentication.getName());

        // 팔로우할 사용자 정보 가져오기
        Member memberToFollow = memberService.findVerifiedMember(memberId);

        // 현재 로그인한 사용자가 이미 해당 사용자를 팔로우하고 있는지 확인
        boolean isAlreadyFollowing = currentMember.getFollowings().contains(memberToFollow);

        if (isAlreadyFollowing){
            memberToFollow.unfollow(currentMember);
            memberRepository.save(memberToFollow);
            memberRepository.save(currentMember);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } else return new ResponseEntity(
                new BusinessLogicException(ExceptionCode.FOLLOWING_ALREADY_DELETED).getExceptionCode().getMessage(),
                HttpStatus.CONFLICT);
    }

    @GetMapping("/api/member/{member-id}/followings")
    public ResponseEntity getFollowings(@PathVariable("member-id") @Positive Long memberId) {

        Member member = memberService.findVerifiedMember(memberId);
        Set<Member> followings = member.getFollowings();

        List<MemberFollowResponseDto> response = followings.stream()
                .map(following -> new MemberFollowResponseDto(
                        following.getMemberId(),
                        following.getNickname(),
                        following.getProfileImageUrl()
                )).collect(Collectors.toList());

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping("/api/member/{member-id}/followers")
    public ResponseEntity getFollowers(@PathVariable("member-id") @Positive Long memberId) {

        Member member = memberService.findVerifiedMember(memberId);
        Set<Member> followers = member.getFollowers();

        List<MemberFollowResponseDto> response = followers.stream()
                .map(follower -> new MemberFollowResponseDto(
                        follower.getMemberId(),
                        follower.getNickname(),
                        follower.getProfileImageUrl()
                )).collect(Collectors.toList());

        return new ResponseEntity(response, HttpStatus.OK);
    }

}
