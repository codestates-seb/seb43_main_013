package com.CreatorConnect.server.board.comments.jobcomment.mapper;

import com.CreatorConnect.server.board.comments.common.dto.CommentDto;
import com.CreatorConnect.server.board.comments.common.dto.CommentResponseDto;
import com.CreatorConnect.server.board.comments.common.entity.CommentPK;
import com.CreatorConnect.server.board.comments.jobcomment.entity.JobComment;
import com.CreatorConnect.server.board.jobboard.entity.JobBoard;
import com.CreatorConnect.server.board.recomments.common.dto.ReCommentResponseDto;
import com.CreatorConnect.server.board.recomments.jobrecomment.entity.JobReComment;
import com.CreatorConnect.server.board.recomments.jobrecomment.mapper.JobReCommentMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JobCommentMapper {
    private final JobReCommentMapper mapper;

    public JobCommentMapper(JobReCommentMapper mapper) {
        this.mapper = mapper;
    }

    public CommentResponseDto.Details jobCommentToCommentDetailsResponse(JobComment comment){
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
        details.setRecomments(jobReCommentListToJobReCommentResponseDTOList(comment.getJobReComments()));
        return details;
    }

    public List<CommentResponseDto.Details> jobCommentsToCommentDetailsResponses(List<JobComment> comments) {
        if ( comments == null ) {
            return null;
        }

        List<CommentResponseDto.Details> list = new ArrayList<CommentResponseDto.Details>( comments.size() );
        for ( JobComment jobComment : comments ) {
            list.add( jobCommentToCommentDetailsResponse( jobComment ) );
        }

        return list;
    }
    public JobComment dtoToJobComment(Long id, CommentDto.Post postDto, JobBoard jobBoard){

        // dto-entity 매핑
        JobComment jobComment = new JobComment();
        CommentPK commentPK = new CommentPK(id, jobBoard.getMaxCommentCount()+1);
        jobComment.setCommentPK(commentPK);
        jobComment.setContent(postDto.getContent());
        jobComment.setMember(postDto.getMember());
        jobComment.setJobBoard(jobBoard);

        // board commentCount +1
        jobBoard.setCommentCount(jobBoard.getCommentCount()+1);
        jobBoard.setMaxCommentCount(jobBoard.getMaxCommentCount()+1);
        return jobComment;
    }
    protected List<ReCommentResponseDto.Details> jobReCommentListToJobReCommentResponseDTOList(List<JobReComment> list) {
        if ( list == null ) {
            return null;
        }

        List<ReCommentResponseDto.Details> list1 = new ArrayList<>( list.size() );
        for ( JobReComment jobReComment : list ) {
            list1.add( mapper.jobReCommentToReCommentDetailsResponse( jobReComment ) );
        }

        return list1;
    }
}