package com.CreatorConnect.server.feedbackboard.mapper;


import com.CreatorConnect.server.feedbackboard.dto.FeedbackBoardDto;
import com.CreatorConnect.server.feedbackboard.dto.FeedbackBoardResponseDto;
import com.CreatorConnect.server.feedbackboard.entity.FeedbackBoard;
import com.CreatorConnect.server.tag.dto.TagDto;
import com.CreatorConnect.server.tag.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FeedbackBoardMapper {
    FeedbackBoard feedbackBoardPostDtoToFeedbackBoard(FeedbackBoardDto.Post feedbackBoardPostDto);
    FeedbackBoardResponseDto.Post feedbackBoardToFeedbackBoardPostResponse(FeedbackBoard feedbackBoard);
    FeedbackBoard feedbackBoardPatchDtoToFeedbackBoard(FeedbackBoardDto.Patch feedbackBoardPatchDto);
    FeedbackBoardResponseDto.Patch feedbackBoardToFeedbackBoardPatchResponse(FeedbackBoard feedbackBoard);
    FeedbackBoardResponseDto.Details feedbackBoardToFeedbackBoardDetailsResponse(FeedbackBoard feedbackBoard);
    List<FeedbackBoardResponseDto.Details>  feedbackBoardsToFeedbackBoardDetailsResponses (List<FeedbackBoard> feedbackBoards);

    // 게시글 목록 조회 시 Response를 위한 매핑
    FeedbackBoardResponseDto.Details feedbackBoardToResponse(FeedbackBoard feedbackBoard, List<TagDto.TagInfo> tags);

}
