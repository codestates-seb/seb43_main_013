package com.CreatorConnect.server.board.recomments.jobrecomment.mapper;

import com.CreatorConnect.server.board.comments.jobcomment.entity.JobComment;
import com.CreatorConnect.server.board.recomments.common.dto.ReCommentDto;
import com.CreatorConnect.server.board.recomments.common.dto.ReCommentResponseDto;
import com.CreatorConnect.server.board.recomments.common.entity.ReCommentPK;
import com.CreatorConnect.server.board.recomments.jobrecomment.entity.JobReComment;
import org.springframework.stereotype.Component;

@Component
public class JobReCommentMapper {
    public JobReComment dtoToJobReComment(ReCommentDto.Post postDto, JobComment foundJobComment) {
        // dto-entity 매핑
        JobReComment jobReComment = new JobReComment();
        ReCommentPK reCommentPK = new ReCommentPK(foundJobComment.getCommentPK(), foundJobComment.getMaxReCommentCount()+1);
        jobReComment.setReCommentPK(reCommentPK);
        jobReComment.setContent(postDto.getContent());
        jobReComment.setMember(postDto.getMember());
        jobReComment.setJobComment(foundJobComment);

        // comment reCommentCount +1
        foundJobComment.setReCommentCount(foundJobComment.getReCommentCount()+1);
        foundJobComment.setMaxReCommentCount(foundJobComment.getMaxReCommentCount()+1);
        return jobReComment;
    }

    public ReCommentResponseDto.Details jobReCommentToReCommentDetailsResponse(JobReComment reComment){
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