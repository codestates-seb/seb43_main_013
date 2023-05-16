package com.CreatorConnect.server.comment.mapper;

import com.CreatorConnect.server.comment.dto.CommentDto;
import com.CreatorConnect.server.comment.dto.CommentResponseDto;
import com.CreatorConnect.server.comment.entity.FeedbackComment;
import com.CreatorConnect.server.comment.entity.FreeComment;
import com.CreatorConnect.server.feedbackboard.entity.FeedbackBoard;
import com.CreatorConnect.server.freeboard.entity.FreeBoard;

import java.util.List;

public interface CommentMapper {
    CommentResponseDto.Details feedbackCommentToCommentDetailsResponse(FeedbackComment comment);
    List<CommentResponseDto.Details> feedbackCommentsToCommentDetailsResponses(List<FeedbackComment> comments);
    FeedbackComment dtoToFeedbackComment(Long id, CommentDto.Post postDto, FeedbackBoard feedbackBoard);
    CommentResponseDto.Details freeCommentToCommentDetailsResponse(FreeComment comment);
    List<CommentResponseDto.Details> freeCommentsToCommentDetailsResponses(List<FreeComment> comments);
    FreeComment postDtoToFreeComment(Long id, CommentDto.Post postDto, FreeBoard freeBoard);
}
