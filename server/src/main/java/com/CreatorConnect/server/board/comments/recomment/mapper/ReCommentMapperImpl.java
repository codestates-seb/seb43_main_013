package com.CreatorConnect.server.board.comments.recomment.mapper;


import com.CreatorConnect.server.board.comments.comment.entity.FeedbackComment;
import com.CreatorConnect.server.board.comments.recomment.dto.ReCommentDto;
import com.CreatorConnect.server.board.comments.recomment.dto.ReCommentResponseDto;
import com.CreatorConnect.server.board.comments.recomment.entity.FeedbackReComment;
import com.CreatorConnect.server.board.comments.recomment.entity.ReCommentPK;
import org.springframework.stereotype.Component;

@Component
public class ReCommentMapperImpl implements ReCommentMapper {
    @Override
    public FeedbackReComment dtoToFeedbackReComment(ReCommentDto.Post postDto, FeedbackComment foundFeedbackComment) {
        // dto-entity 매핑
        FeedbackReComment feedbackReComment = new FeedbackReComment();
        ReCommentPK reCommentPK = new ReCommentPK(foundFeedbackComment.getCommentPK(), foundFeedbackComment.getMaxReCommentCount()+1);
        feedbackReComment.setReCommentPK(reCommentPK);
        feedbackReComment.setContent(postDto.getContent());
        feedbackReComment.setMember(postDto.getMember());
        feedbackReComment.setFeedbackComment(foundFeedbackComment);

        // comment reCommentCount +1
        foundFeedbackComment.setReCommentCount(foundFeedbackComment.getReCommentCount()+1);
        foundFeedbackComment.setMaxReCommentCount(foundFeedbackComment.getMaxReCommentCount()+1);
        return feedbackReComment;
    }

    @Override
    public ReCommentResponseDto.Details feedbackReCommentToReCommentDetailsResponse(FeedbackReComment reComment){
        ReCommentResponseDto.Details details = new ReCommentResponseDto.Details();
        details.setReCommentId(reComment.getReCommentPK().getReCommentId());
        details.setContent(reComment.getContent());
        details.setMemberId(reComment.getMemberId());
        details.setNickname(reComment.getNickname());
        details.setEmail(reComment.getEmail());
        details.setProfileImageUrl(reComment.getProfileImageUrl());
        details.setCreatedAt(reComment.getCreatedAt());
        details.setModifiedAt(reComment.getModifiedAt());
        return details;
    }
}
