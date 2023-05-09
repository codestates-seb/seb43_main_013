package com.CreatorConnect.server.freeboard.mapper;

import com.CreatorConnect.server.category.entity.Category;
import com.CreatorConnect.server.freeboard.dto.FreeBoardDto;
import com.CreatorConnect.server.freeboard.entity.FreeBoard;
import com.CreatorConnect.server.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FreeBoardMapper {
    // FreeBoardDto.Post -> FreeBoard
//    @Mapping(source = "memberId",target = "member.memberId")
//    @Mapping(source = "categoryId", target = "category.categoryId")
   FreeBoard freeBoardPostDtoToFreeBoard(FreeBoardDto.Post post);







}
