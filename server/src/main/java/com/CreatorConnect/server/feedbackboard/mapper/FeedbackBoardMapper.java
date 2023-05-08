package com.CreatorConnect.server.feedbackboard.mapper;


import com.CreatorConnect.server.feedbackboard.dto.FeedbackBoardDto;
import com.CreatorConnect.server.feedbackboard.entity.FeedbackBoard;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FeedbackBoardMapper {
    FeedbackBoard feedbackBoardPostDtoToFeedbackBoard(FeedbackBoardDto.Post feedbackBoardPostDto);
    FeedbackBoardDto.PostResponse feedbackBoardToFeedbackBoardPostResponse(FeedbackBoard feedbackBoard);
    FeedbackBoardDto.DetailsResponse feedbackBoardToFeedbackBoardDetailsResponse(FeedbackBoard feedbackBoard);

}
