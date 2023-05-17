package com.CreatorConnect.server.board.comments.recomment.mapper;


import com.CreatorConnect.server.board.comments.comment.entity.FeedbackComment;
import com.CreatorConnect.server.board.comments.recomment.dto.ReCommentDto;
import com.CreatorConnect.server.board.comments.recomment.entity.FeedbackReComment;

public interface ReCommentMapper {
    FeedbackReComment dtoToFeedbackReComment(ReCommentDto.Post postDto, FeedbackComment foundFeedbackComment);
}
