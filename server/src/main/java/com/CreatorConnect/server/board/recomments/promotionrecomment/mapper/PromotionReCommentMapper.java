package com.CreatorConnect.server.board.recomments.promotionrecomment.mapper;

import com.CreatorConnect.server.board.comments.promotioncomment.entity.PromotionComment;
import com.CreatorConnect.server.board.recomments.common.dto.ReCommentDto;
import com.CreatorConnect.server.board.recomments.common.dto.ReCommentResponseDto;
import com.CreatorConnect.server.board.recomments.common.entity.ReCommentPK;
import com.CreatorConnect.server.board.recomments.promotionrecomment.entity.PromotionReComment;
import org.springframework.stereotype.Component;

@Component
public class PromotionReCommentMapper {
    public PromotionReComment dtoToPromotionReComment(ReCommentDto.Post postDto, PromotionComment foundPromotionComment) {
        // dto-entity 매핑
        PromotionReComment promotionReComment = new PromotionReComment();
        ReCommentPK reCommentPK = new ReCommentPK(foundPromotionComment.getCommentPK(), foundPromotionComment.getMaxReCommentCount()+1);
        promotionReComment.setReCommentPK(reCommentPK);
        promotionReComment.setContent(postDto.getContent());
        promotionReComment.setMember(postDto.getMember());
        promotionReComment.setPromotionComment(foundPromotionComment);

        // comment reCommentCount +1
        foundPromotionComment.setReCommentCount(foundPromotionComment.getReCommentCount()+1);
        foundPromotionComment.setMaxReCommentCount(foundPromotionComment.getMaxReCommentCount()+1);
        return promotionReComment;
    }

    public ReCommentResponseDto.Details promotionReCommentToReCommentDetailsResponse(PromotionReComment reComment){
        ReCommentResponseDto.Details details = new ReCommentResponseDto.Details();
        details.setRecommentId(reComment.getReCommentPK().getReCommentId());
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
