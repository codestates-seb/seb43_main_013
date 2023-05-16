package com.CreatorConnect.server.board.comments.comment.mapper;

import com.CreatorConnect.server.board.comments.comment.dto.CommentDto;
import com.CreatorConnect.server.board.comments.comment.dto.CommentResponseDto;
import com.CreatorConnect.server.board.comments.comment.entity.FeedbackComment;
import com.CreatorConnect.server.board.comments.comment.entity.FreeComment;
import com.CreatorConnect.server.board.feedbackboard.entity.FeedbackBoard;
import com.CreatorConnect.server.board.freeboard.entity.FreeBoard;

import java.util.List;

public interface CommentMapper {
    CommentResponseDto.Details feedbackCommentToCommentDetailsResponse(FeedbackComment comment);
    List<CommentResponseDto.Details> feedbackCommentsToCommentDetailsResponses(List<FeedbackComment> comments);
    FeedbackComment dtoToFeedbackComment(Long id, CommentDto.Post postDto, FeedbackBoard feedbackBoard);
    CommentResponseDto.Details freeCommentToCommentDetailsResponse(FreeComment comment);
    List<CommentResponseDto.Details> freeCommentsToCommentDetailsResponses(List<FreeComment> comments);
    FreeComment postDtoToFreeComment(Long id, CommentDto.Post postDto, FreeBoard freeBoard);
}
