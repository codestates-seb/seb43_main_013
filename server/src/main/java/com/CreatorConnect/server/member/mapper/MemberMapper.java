package com.CreatorConnect.server.member.mapper;

import com.CreatorConnect.server.member.dto.MemberDto;
import com.CreatorConnect.server.member.dto.MemberResponseDto;
import com.CreatorConnect.server.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Qualifier;
import org.mapstruct.factory.Mappers;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    Member memberPostDtoToMember(MemberDto.Post memberDtoPost);

    @Mapping(target = "createdAt", source = "createdAt", qualifiedBy = LocalDateTimeToString.class)
    @Mapping(target = "modifiedAt", source = "modifiedAt", qualifiedBy = LocalDateTimeToString.class)
    public default Member memberPatchDtoToMember(MemberDto.Patch memberDtoPatch){
        Member member = new Member();
        member.setMemberId(memberDtoPatch.getMemberId());
        member.setPassword(memberDtoPatch.getPassword());
        member.setNickname(memberDtoPatch.getNickname());
        member.setPhone(memberDtoPatch.getPhone());
        member.setIntroduction(memberDtoPatch.getIntroduction());
        member.setLink(memberDtoPatch.getLink());
        member.setProfileImageUrl(memberDtoPatch.getProfileImageUrl());

        return member;
    };

    @Mapping(target = "createdAt", source = "createdAt", qualifiedBy = LocalDateTimeToString.class)
    @Mapping(source = "modifiedAt", target = "modifiedAt", qualifiedBy = LocalDateTimeToString.class)
    public default MemberResponseDto memberToMemberResponseDto(Member member){
        MemberResponseDto memberResponseDto = new MemberResponseDto();
        memberResponseDto.setMemberId(member.getMemberId());
        memberResponseDto.setEmail(member.getEmail());
        memberResponseDto.setName(member.getName());
        memberResponseDto.setNickname(member.getNickname());
        memberResponseDto.setPhone(member.getPhone());
        memberResponseDto.setOauth(member.isOauth());
        memberResponseDto.setCreatedAt(member.getCreatedAt());
        memberResponseDto.setModifiedAt(member.getModifiedAt());
        return memberResponseDto;
    };

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.CLASS)
    public @interface LocalDateTimeToString {}

    @LocalDateTimeToString
    default String mapLocalDateTimeToString(LocalDateTime localDateTime) {
        return localDateTime == null ? null : localDateTime.atZone(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    List<MemberResponseDto> membersToMemberResponseDtos(List<Member> members);
}
