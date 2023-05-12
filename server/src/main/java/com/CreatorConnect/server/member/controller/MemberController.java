package com.CreatorConnect.server.member.controller;

import com.CreatorConnect.server.member.dto.MemberDto;
import com.CreatorConnect.server.member.dto.MemberResponseDto;
import com.CreatorConnect.server.member.entity.Member;
import com.CreatorConnect.server.member.mapper.MemberMapper;
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

@Validated
@RestController
public class MemberController {
    private static final String MEMBER_DEFAULT_URL = "/api/member";
    private static final String MEMBER_ALL_MAPPING_URL = "/api/members";

    private final MemberService memberService;
    private final MemberMapper mapper;

    public MemberController(MemberService memberService, MemberMapper mapper) {
        this.memberService = memberService;
        this.mapper = mapper;
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
    public ResponseEntity deleteUser(@PathVariable("member-id") @Positive Long memberId,
                                     Authentication authentication) {

        memberService.deleteMember(memberId, authentication.getName());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
