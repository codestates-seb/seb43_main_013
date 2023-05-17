package com.CreatorConnect.server.board.recomments.feedbackrecomment.mapper;


import com.CreatorConnect.server.board.comments.feedbackcomment.entity.FeedbackComment;
import com.CreatorConnect.server.board.recomments.common.dto.ReCommentDto;
import com.CreatorConnect.server.board.recomments.common.dto.ReCommentResponseDto;
import com.CreatorConnect.server.board.recomments.common.entity.ReCommentPK;
import com.CreatorConnect.server.board.recomments.feedbackrecomment.entity.FeedbackReComment;
import org.springframework.stereotype.Component;

@Component
public class FeedbackReCommentMapper {

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
