package com.CreatorConnect.server.board.comments.freecomment.mapper;

import com.CreatorConnect.server.board.comments.common.dto.CommentDto;
import com.CreatorConnect.server.board.comments.common.dto.CommentResponseDto;
import com.CreatorConnect.server.board.comments.common.entity.CommentPK;
import com.CreatorConnect.server.board.comments.freecomment.entity.FreeComment;
import com.CreatorConnect.server.board.freeboard.entity.FreeBoard;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FreeCommentMapper {

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
        details.setReComments(new ArrayList<>());
        return details;
    }

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

    public FreeComment postDtoToFreeComment(Long id, CommentDto.Post postDto, FreeBoard freeBoard){

        // dto-entity 매핑
        FreeComment freeComment = new FreeComment();
        CommentPK commentPK = new CommentPK(id, freeBoard.getMaxCommentCount()+1);
        freeComment.setCommentPK(commentPK);
        freeComment.setContent(postDto.getContent());
        freeComment.setMember(postDto.getMember());
        freeComment.setFreeBoard(freeBoard);

        // board commentCount +1
        freeBoard.setCommentCount(freeBoard.getCommentCount()+1);
        freeBoard.setMaxCommentCount(freeBoard.getMaxCommentCount()+1);
        return freeComment;
    }

}
