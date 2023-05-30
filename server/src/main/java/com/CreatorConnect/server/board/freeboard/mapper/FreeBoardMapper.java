package com.CreatorConnect.server.board.freeboard.mapper;

import com.CreatorConnect.server.board.freeboard.dto.FreeBoardDto;
import com.CreatorConnect.server.board.freeboard.entity.FreeBoard;
import com.CreatorConnect.server.board.tag.dto.TagDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FreeBoardMapper {
    // FreeBoardDto.Post -> FreeBoard
//    @Mapping(source = "memberId",target = "member.memberId")
//    @Mapping(source = "categoryId", target = "category.categoryId")
   FreeBoard freeBoardPostDtoToFreeBoard(FreeBoardDto.Post post);

    // FreeBoardDto.Patch -> FreeBoard
    FreeBoard freeBoardPatchDtoToFreeBoard(FreeBoardDto.Patch patch);

    // FreeBoard -> FreeBoardDto.Response
    FreeBoardDto.Response freeBoardToFreeBoardResponseDto(FreeBoard freeBoard);

    // FreeBoard -> FreeBoardDto.PostResponse
    FreeBoardDto.PostResponse freeBoardToFreeBoardPostResponseDto(FreeBoard freeBoard);

    List<FreeBoardDto.Response> freeBoardToFreeBoardResponseDtos(List<FreeBoard> freeBoards);

//    List<Tag> freeBoardPostDtoToTags(List<TagDto.TagInfo> tagInfoDtos);

    // 게시글 목록 조회 시 Response를 위한 매핑
    FreeBoardDto.Response freeBoardToResponse(FreeBoard freeBoard, List<TagDto.TagInfo> tags);









}
