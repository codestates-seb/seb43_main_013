package com.CreatorConnect.server.board.recomments.freerecomment.mapper;

import com.CreatorConnect.server.board.comments.freecomment.entity.FreeComment;
import com.CreatorConnect.server.board.recomments.common.dto.ReCommentDto;
import com.CreatorConnect.server.board.recomments.common.dto.ReCommentResponseDto;
import com.CreatorConnect.server.board.recomments.common.entity.ReCommentPK;
import com.CreatorConnect.server.board.recomments.freerecomment.entity.FreeReComment;
import org.springframework.stereotype.Component;

@Component
public class FreeReCommentMapper {
    public FreeReComment dtoToFreeReComment(ReCommentDto.Post postDto, FreeComment foundFreeComment) {
        // dto-entity 매핑
        FreeReComment freeReComment = new FreeReComment();
        ReCommentPK reCommentPK = new ReCommentPK(foundFreeComment.getCommentPK(), foundFreeComment.getMaxReCommentCount()+1);
        freeReComment.setReCommentPK(reCommentPK);
        freeReComment.setContent(postDto.getContent());
        freeReComment.setMember(postDto.getMember());
        freeReComment.setFreeComment(foundFreeComment);

        // comment reCommentCount +1
        foundFreeComment.setReCommentCount(foundFreeComment.getReCommentCount()+1);
        foundFreeComment.setMaxReCommentCount(foundFreeComment.getMaxReCommentCount()+1);
        return freeReComment;
    }

    public ReCommentResponseDto.Details freeReCommentToReCommentDetailsResponse(FreeReComment reComment){
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