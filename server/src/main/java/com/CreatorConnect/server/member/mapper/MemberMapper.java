package com.CreatorConnect.server.member.mapper;

import com.CreatorConnect.server.member.dto.MemberDto;
import com.CreatorConnect.server.member.dto.MemberResponseDto;
import com.CreatorConnect.server.member.entity.Member;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    Member memberPostDtoToMember(MemberDto.Post memberDtoPost);
    Member memberPatchDtoToMember(MemberDto.Patch memberDtoPatch);
    MemberResponseDto memberToMemberResponseDto(Member member);
    List<MemberResponseDto> membersToMemberResponseDtos(List<Member> members);

}
