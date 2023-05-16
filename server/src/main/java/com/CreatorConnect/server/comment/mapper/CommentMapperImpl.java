package com.CreatorConnect.server.comment.mapper;

import com.CreatorConnect.server.comment.dto.CommentDto;
import com.CreatorConnect.server.comment.dto.CommentResponseDto;
import com.CreatorConnect.server.comment.entity.CommentPK;
import com.CreatorConnect.server.comment.entity.FeedbackComment;
import com.CreatorConnect.server.comment.entity.FreeComment;
import com.CreatorConnect.server.feedbackboard.entity.FeedbackBoard;
import com.CreatorConnect.server.freeboard.entity.FreeBoard;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommentMapperImpl implements CommentMapper{
    @Override
    public CommentResponseDto.Details feedbackCommentToCommentDetailsResponse(FeedbackComment comment){
        CommentResponseDto.Details details = new CommentResponseDto.Details();
        details.setCommentId(comment.getCommentPK().getCommentId());
        details.setContent(comment.getContent());
        details.setMemberId(comment.getMemberId());
        details.setNickname(comment.getNickname());
        details.setEmail(comment.getEmail());
        details.setProfileImageUrl(comment.getProfileImageUrl());
        details.setReCommentCount(comment.getReCommentCount());
        details.setCreatedAt(comment.getCreatedAt());
        details.setModifiedAt(comment.getModifiedAt());
        // details.setReComments();
        return details;
    }
    @Override
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
    public FeedbackComment dtoToFeedbackComment(Long id, CommentDto.Post postDto, FeedbackBoard feedbackBoard){

        // dto-entity 매핑
        FeedbackComment feedbackComment = new FeedbackComment();
        CommentPK commentPK = new CommentPK(id, feedbackBoard.getCommentCount()+1);
        feedbackComment.setCommentPK(commentPK);
        feedbackComment.setContent(postDto.getContent());
        feedbackComment.setMember(postDto.getMember());
        feedbackComment.setFeedbackBoard(feedbackBoard);

        // board commentCount +1
        feedbackBoard.setCommentCount(feedbackBoard.getCommentCount()+1);
        return feedbackComment;
    }

    @Override
    public CommentResponseDto.Details freeCommentToCommentDetailsResponse(FreeComment comment){
        CommentResponseDto.Details details = new CommentResponseDto.Details();
        details.setCommentId(comment.getCommentPK().getCommentId());
        details.setContent(comment.getContent());
        details.setMemberId(comment.getMemberId());
        details.setNickname(comment.getNickname());
        details.setEmail(comment.getEmail());
        details.setProfileImageUrl(comment.getProfileImageUrl());
        details.setReCommentCount(comment.getReCommentCount());
        details.setCreatedAt(comment.getCreatedAt());
        details.setModifiedAt(comment.getModifiedAt());
        // details.setReComments();
        return details;
    }

    @Override
    public List<CommentResponseDto.Details> freeCommentsToCommentDetailsResponses(List<FreeComment> comments) {
        if ( comments == null ) {
            return null;
        }

        List<CommentResponseDto.Details> list = new ArrayList<CommentResponseDto.Details>( comments.size() );
        for ( FreeComment freeComment : comments ) {
            list.add( freeCommentToCommentDetailsResponse( freeComment ) );
        }

        return list;
    }
    @Override
    public FreeComment postDtoToFreeComment(Long id, CommentDto.Post postDto, FreeBoard freeBoard){

        // dto-entity 매핑
        FreeComment freeComment = new FreeComment();
        CommentPK commentPK = new CommentPK(id, freeBoard.getCommentCount()+1);
        freeComment.setCommentPK(commentPK);
        freeComment.setContent(postDto.getContent());
        freeComment.setMember(postDto.getMember());
        freeComment.setFreeBoard(freeBoard);

        // board commentCount +1
        freeBoard.setCommentCount(freeBoard.getCommentCount()+1);
        return freeComment;
    }
}
