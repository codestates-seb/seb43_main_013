package com.CreatorConnect.server.board.comments.recomment.mapper;


import com.CreatorConnect.server.board.comments.comment.entity.FeedbackComment;
import com.CreatorConnect.server.board.comments.recomment.dto.ReCommentDto;
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
}
