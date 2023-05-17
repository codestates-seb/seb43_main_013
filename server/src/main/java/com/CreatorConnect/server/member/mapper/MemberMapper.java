package com.CreatorConnect.server.member.mapper;

import com.CreatorConnect.server.member.dto.MemberDto;
import com.CreatorConnect.server.member.dto.MemberFollowResponseDto;
import com.CreatorConnect.server.member.dto.MemberResponseDto;
import com.CreatorConnect.server.member.entity.Member;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    Member memberPostDtoToMember(MemberDto.Post memberDtoPost);
    Member memberPatchDtoToMember(MemberDto.Patch memberDtoPatch);
    MemberResponseDto memberToMemberResponseDto(Member member);
    List<MemberResponseDto> membersToMemberResponseDtos(List<Member> members);

    default int getFollowerCount(Member member) {
        return member.getFollowers().size();
    }

    default int getFollowingCount(Member member) {
        return member.getFollowings().size();
    }

    @AfterMapping
    default void setFollowerAndFollowingCounts(Member member, @MappingTarget MemberResponseDto responseDto) {
        responseDto.setFollowerCount(getFollowerCount(member));
        responseDto.setFollowingCount(getFollowingCount(member));
    }

}
