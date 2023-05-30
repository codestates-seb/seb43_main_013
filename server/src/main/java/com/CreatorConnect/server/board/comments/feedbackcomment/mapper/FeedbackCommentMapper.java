package com.CreatorConnect.server.board.comments.feedbackcomment.mapper;

import com.CreatorConnect.server.board.comments.common.dto.CommentDto;
import com.CreatorConnect.server.board.comments.common.dto.CommentResponseDto;
import com.CreatorConnect.server.board.comments.common.entity.CommentPK;
import com.CreatorConnect.server.board.comments.feedbackcomment.entity.FeedbackComment;
import com.CreatorConnect.server.board.feedbackboard.entity.FeedbackBoard;
import com.CreatorConnect.server.board.recomments.common.dto.ReCommentResponseDto;
import com.CreatorConnect.server.board.recomments.feedbackrecomment.entity.FeedbackReComment;
import com.CreatorConnect.server.board.recomments.feedbackrecomment.mapper.FeedbackReCommentMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FeedbackCommentMapper {

    private final FeedbackReCommentMapper mapper;

    public FeedbackCommentMapper(FeedbackReCommentMapper mapper) {
        this.mapper = mapper;
    }

    public CommentResponseDto.Details feedbackCommentToCommentDetailsResponse(FeedbackComment comment){
        CommentResponseDto.Details details = new CommentResponseDto.Details();
        details.setCommentId(comment.getCommentPK().getCommentId());
        details.setContent(comment.getContent());
        details.setMemberId(comment.getMemberId());
        details.setNickname(comment.getNickname());
        details.setEmail(comment.getEmail());
        details.setProfileImageUrl(comment.getProfileImageUrl());
        details.setRecommentCount(comment.getReCommentCount());
        details.setCreatedAt(comment.getCreatedAt());
        details.setModifiedAt(comment.getModifiedAt());
        details.setRecomments(feedbackReCommentListToFeedbackReCommentResponseDTOList(comment.getFeedbackReComments()));
        return details;
    }

    public List<CommentResponseDto.Details> feedbackCommentsToCommentDetailsResponses(List<FeedbackComment> comments) {
        if ( comments == null ) {
            return null;
        }

        List<CommentResponseDto.Details> list = new ArrayList<CommentResponseDto.Details>( comments.size() );
        for ( FeedbackComment feedbackComment : comments ) {
            list.add( feedbackCommentToCommentDetailsResponse( feedbackComment ) );
        }

        return list;
    }
    public FeedbackComment dtoToFeedbackComment(Long feedbackBoardId, CommentDto.Post postDto, FeedbackBoard feedbackBoard){

        // dto-entity 매핑
        FeedbackComment feedbackComment = new FeedbackComment();
        CommentPK commentPK = new CommentPK(feedbackBoardId, feedbackBoard.getMaxCommentCount()+1);
        feedbackComment.setCommentPK(commentPK);
        feedbackComment.setContent(postDto.getContent());
        feedbackComment.setMember(postDto.getMember());
        feedbackComment.setFeedbackBoard(feedbackBoard);

        // board commentCount +1
        feedbackBoard.setCommentCount(feedbackBoard.getCommentCount()+1);
        feedbackBoard.setMaxCommentCount(feedbackBoard.getMaxCommentCount()+1);
        return feedbackComment;
    }
    protected List<ReCommentResponseDto.Details> feedbackReCommentListToFeedbackReCommentResponseDTOList(List<FeedbackReComment> list) {
        if ( list == null ) {
            return null;
        }

        List<ReCommentResponseDto.Details> list1 = new ArrayList<>( list.size() );
        for ( FeedbackReComment feedbackReComment : list ) {
            list1.add( mapper.feedbackReCommentToReCommentDetailsResponse( feedbackReComment ) );
        }

        return list1;
    }
}
