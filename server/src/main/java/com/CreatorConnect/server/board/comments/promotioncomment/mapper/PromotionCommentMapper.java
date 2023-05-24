package com.CreatorConnect.server.board.comments.promotioncomment.mapper;

import com.CreatorConnect.server.board.comments.common.dto.CommentDto;
import com.CreatorConnect.server.board.comments.common.dto.CommentResponseDto;
import com.CreatorConnect.server.board.comments.common.entity.CommentPK;
import com.CreatorConnect.server.board.comments.promotioncomment.entity.PromotionComment;
import com.CreatorConnect.server.board.promotionboard.entity.PromotionBoard;
import com.CreatorConnect.server.board.recomments.common.dto.ReCommentResponseDto;
import com.CreatorConnect.server.board.recomments.promotionrecomment.entity.PromotionReComment;
import com.CreatorConnect.server.board.recomments.promotionrecomment.mapper.PromotionReCommentMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PromotionCommentMapper {
    private final PromotionReCommentMapper mapper;

    public PromotionCommentMapper(PromotionReCommentMapper mapper) {
        this.mapper = mapper;
    }

    public CommentResponseDto.Details promotionCommentToCommentDetailsResponse(PromotionComment comment){
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
        details.setRecomments(promotionReCommentListToPromotionReCommentResponseDTOList(comment.getPromotionReComments()));
        return details;
    }

    public List<CommentResponseDto.Details> promotionCommentsToCommentDetailsResponses(List<PromotionComment> comments) {
        if ( comments == null ) {
            return null;
        }

        List<CommentResponseDto.Details> list = new ArrayList<CommentResponseDto.Details>( comments.size() );
        for ( PromotionComment promotionComment : comments ) {
            list.add( promotionCommentToCommentDetailsResponse( promotionComment ) );
        }

        return list;
    }
    public PromotionComment dtoToPromotionComment(Long id, CommentDto.Post postDto, PromotionBoard promotionBoard){

        // dto-entity 매핑
        PromotionComment promotionComment = new PromotionComment();
        CommentPK commentPK = new CommentPK(id, promotionBoard.getMaxCommentCount()+1);
        promotionComment.setCommentPK(commentPK);
        promotionComment.setContent(postDto.getContent());
        promotionComment.setMember(postDto.getMember());
        promotionComment.setPromotionBoard(promotionBoard);

        // board commentCount +1
        promotionBoard.setCommentCount(promotionBoard.getCommentCount()+1);
        promotionBoard.setMaxCommentCount(promotionBoard.getMaxCommentCount()+1);
        return promotionComment;
    }
    protected List<ReCommentResponseDto.Details> promotionReCommentListToPromotionReCommentResponseDTOList(List<PromotionReComment> list) {
        if ( list == null ) {
            return null;
        }

        List<ReCommentResponseDto.Details> list1 = new ArrayList<>( list.size() );
        for ( PromotionReComment promotionReComment : list ) {
            list1.add( mapper.promotionReCommentToReCommentDetailsResponse( promotionReComment ) );
        }

        return list1;
    }
}
